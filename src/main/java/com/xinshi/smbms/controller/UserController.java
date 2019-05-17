package com.xinshi.smbms.controller;


import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import com.xinshi.smbms.pojo.Page;
import com.xinshi.smbms.pojo.Role;
import com.xinshi.smbms.pojo.User;
import com.xinshi.smbms.service.UserService;
import com.xinshi.smbms.util.MD5Util;
import com.xinshi.smbms.util.UploadFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/jsp/user")
public class UserController  extends HandlerInterceptorAdapter {
    //实现用户业务层
    @Resource(name = "userService")
    private UserService userService;
    //接收分页集合
    private Page<User> userList;
    //接收角色集合
    private List<Role> roleList;


    /**
     * 用户登录
     * @param userCode  用户编码
     * @param userPassword  用户密码
     * @param session   会话
     * @param request   请求
     * @return
     */
    @RequestMapping(value = "/login")
    public String doLogin(@RequestParam(required = false)  String userCode,
                          @RequestParam(required = false) String userPassword,HttpSession session,HttpServletRequest request, HttpServletResponse response){

        User userCodeOrPwd = userService.findUserCodeOrPwd(userCode, userPassword);
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        if(userCodeOrPwd!=null){
            session.setAttribute("userSession",userCodeOrPwd);
            return "redirect:main.html";
        }
            session.setAttribute("error","用户名或者密码错误！");
        return "redirect:login.html";
    }


    /**
     * 查看分页所有的用户信息
     * @param queryname
     * @param queryUserRole
     * @param request
     * @param pageIndex
     * @param session
     * @return
     */
    @RequestMapping(value = "/queryAllPage")
    public String findAllUser(@RequestParam(required = false) String queryname,
                              @RequestParam(required = false)String queryUserRole,
                              @RequestParam(required = false)String pageIndex,HttpServletRequest request  ,HttpSession session,Model model){
        //查询角色全部的值
        roleList = userService.findAllRoleName();
        User user=new User();
        user.setUsername(queryname);
        //角色不等于null 时成功赋值
        if(queryUserRole != null){
            user.setUserrole(Integer.valueOf(queryUserRole));
        }
        if(pageIndex == null){
            pageIndex="1";
        }
        userList = userService.findPageUsers(user, Integer.valueOf(pageIndex), 5);
        model.addAttribute("queryUserName",queryname);
        model.addAttribute("queryUserRole",queryUserRole);
        model.addAttribute("userList",userList);
        model.addAttribute("roleList",roleList);
        return "userlist";
    }

    /**
     * 添加用户信息
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "addByUser",method = RequestMethod.POST)
    public String additionUser(User user, HttpSession session,@RequestParam(value = "picture",required = false) MultipartFile uploadFile,Model model) {
        User userSession =(User) session.getAttribute("userSession");
        user.setCreatedby(userSession.getId());
        user.setCreationdate(new Date());
        //加载上传文件
        UploadFile.doFirst(uploadFile,model,session);
        //赋值文件名
        user.setIdPicPath(uploadFile.getOriginalFilename());
        if(userService.addUser(user) != 0){
            return "redirect:/jsp/user/queryAllPage";
        }
        return "useradd";
    }

    /**
     * 查找单个用户信息
     * @return
     */
    @RequestMapping(value = "/findByIDUser")
    public String findByIDUser(Model model,@RequestParam(required = false) String uid){
        model.addAttribute("user",userService.selectByPrimaryKey(Integer.valueOf(uid)));
        return "userview";
    }

    /**
     * 根据用户 ID 查看 显示修改信息
     * @return
     */
    @RequestMapping(value = "/queryByIDUser")
    public String queryByIDUser(Model model,@RequestParam(required = false) String uid){
        model.addAttribute("user",userService.selectByPrimaryKey(Integer.valueOf(uid)));
        //查询角色全部的值
       model.addAttribute("roleList",  userService.findAllRoleName());
        return "usermodify";
    }

    /**
     * 修改用户信息
     * @return
     */
    @RequestMapping(value = "/updateByUser",method = RequestMethod.POST)
    public String updateByUser(Model model,User user,HttpSession session){
        User u=(User) session.getAttribute("userSession");
        if(u!=null){
            user.setModifyby(u.getId());
        }
        user.setModifydate(new Date());
        if(userService.uodateByUser(user)>0){
            return "redirect:/jsp/user/queryAllPage";
        }
        return "queryByIDUser";
    }
    /**
     * 根据用户ID 删除
     * @return
     */
    @RequestMapping(value = "/deleteByIDUser",method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public String   deleteByIDUser(User user,Model model,HttpServletResponse response,HttpSession session) {
        Map<String ,String > map=new HashMap<>();
        if(!StringUtils.isNullOrEmpty(String.valueOf(user.getId()))){
            if(userService.deleteByIDUser(user.getId())>0){
                map.put("delResult","true");
            }else{
                map.put("delResult","false");
            }
        }else{
            map.put("delResult","notexist");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 输出JSON 到页面显示角色名称
     */
    @RequestMapping(value = "/RoleListUser",produces ="text/html;charset=utf-8" )
    @ResponseBody
    public String verifyRoleName(){
        List<Role> allRoleName = userService.findAllRoleName();
        //把角色信息 输出 JSON 格式
        return JSON.toJSONString(allRoleName);
    }

    /**
     * 验证用户是否存在
     * @param usercode
     * @return
     */
    @RequestMapping(value = "/findByuserCodeUser",produces = "text/html;charset=utf-8")
    @ResponseBody
    public void findByCodeUser(@RequestParam(required = false) String usercode,HttpServletResponse response) throws IOException {
        User byUserCode = userService.findByUserCode(usercode);
        PrintWriter writer = response.getWriter();
        if(byUserCode != null){
            writer.write("{\"userCode\":\"exist\"}");
        }else{
            writer.write("{\"userCode\":\"false\"}");
        }
        writer.flush();
        writer.close();
    }



    @RequestMapping(value="/login.html")
    public String login(HttpServletRequest request,HttpServletResponse response){
        return "../login";
    }

    /**
     * 用户登录成功后重定向跳转页面
     * @param session
     * @return
     */
    @RequestMapping(value = "/main.html")
    public String main(HttpSession session){
        if(session.getAttribute("userSession") == null){
            return "redirect:user/login.html";
        }
        return "frame";
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/updatepassWordByUser",method = RequestMethod.POST)
    public String updatepassWordByUser(@RequestParam(required = false) String oldpassword,@RequestParam(required = false)String newpassword,HttpSession session){
        User  u =(User) session.getAttribute("userSession");
        if(userService.updatePassWord(newpassword,u.getId())  != 0){
            return "redirect:/jsp/user/login.html";
        };
        return "pwdmodify";
    }

    /**
     * 验证旧密码是否正确
     *
     * @return
     */
    @RequestMapping(value = "/verifyBywornPwdUser")
    public void verifyBywornPwdUser(HttpServletResponse response,HttpSession session,@RequestParam(required = false) String oldpassword){
        User  u =(User) session.getAttribute("userSession");
        try {
            PrintWriter writer = response.getWriter();
            User result = userService.findByPassWord(oldpassword,u.getId());
            if(oldpassword.equals("")  || oldpassword == null){
                writer.write("{\"result\":\"error\"}");
            }else{
                if(result != null ){
                    writer.write("{\"result\":\"true\"}");
                }else if(result  == null){
                    writer.write("{\"result\":\"false\"}");
                }else{
                    writer.write("{\"result\":\"sessionerror\"}");
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出系统返回登录页面
     * @param session
     * @return
     */
    @RequestMapping(value = "/logoutUser")
    public String logoutUser(HttpSession session,HttpServletRequest request){
        //清除session
        session.invalidate();
       return "../login";
    }
}

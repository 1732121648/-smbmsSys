package com.xinshi.smbms.controller;


import com.alibaba.fastjson.JSON;
import com.xinshi.smbms.pojo.Bill;
import com.xinshi.smbms.pojo.Role;
import com.xinshi.smbms.pojo.User;
import com.xinshi.smbms.service.RoleService;
import com.xinshi.smbms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色 (控制层)
 */
@Controller
@RequestMapping("/jsp")
public class RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private UserService userService;

    /**
     * 查询所有的角色
     * @param model
     * @return
     */
    @RequestMapping(value = "/roleAllList")
    public String  pageRole(Model model){
        model.addAttribute("roles",roleService.findAllRoleName());
        return "rolelist";
    }

    /**
     * 根据角色ID 查看角色数据
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryByRoleID/{id}",method = RequestMethod.GET)
    public String findByRoleID(@PathVariable  Integer id, Model model){
        model.addAttribute("model",roleService.findRoleByID(new Role(id)));
        return "rolemodify";
    }

    /**
     * 修改角色
     * @param role
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/updateByRole",method = RequestMethod.POST)
    public String updateByRole(Role role, Model model, HttpSession session){
        role.setModifyBy(((User)session.getAttribute("userSession")).getId());
        role.setModifyDate(new Date());
        if(roleService.updateRole(role) != 0){
            return pageRole(model);
        }
        return "redirect:/jsp/queryByRoleID{id}";
    }

    /**
     * 添加角色
     * @param role
     * @param session
     * @return
     */
    @RequestMapping(value = "/addByRole",method = RequestMethod.POST)
    public String addRole(Role role,HttpSession session){
        role.setCreatedBy(((User)session.getAttribute("userSession")).getId());
        role.setCreationDate(new Date());
        if(roleService.addRole(role) != 0){
            return "redirect:/jsp/roleAllList";
        }
        return "roleadd";
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteByID",produces = "text/html;charset=utf-8")
    @ResponseBody
    public Object deleteByID(Integer id){
        Map<String ,String> map=new HashMap<>();
        User user = userService.selectByUserRole(id);
        if(user != null){
            map.put("delResult","'"+user+"'");
        }else{
            int result = roleService.deleteRole(id);
            if(result != 0){
                map.put("delResult","true");
            }else{
                map.put("delResult","false");
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 验证角色编码是否存在
     * @param role
     * @return
     */
    @RequestMapping(value = "/findByRoleCode",produces = "text/html;charset=utf-8")
    @ResponseBody
    public Object findByRoleCode(Role role){
        Role roleByID = roleService.findRoleByID(role);
        Map<String ,String> map=new HashMap<>();
        if(roleByID != null){
            map.put("RoleCode","exist");
        }else{
            map.put("RoleCode","false");
        }
        return JSON.toJSONString(map);
    }
}

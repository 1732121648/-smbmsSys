package com.xinshi.smbms.chap10.UserController3;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 控制器
 */
@Controller     //类似Struts的Action
@RequestMapping("/user3")
public class UserController3 {

    private  static Logger logger=Logger.getLogger(UserController3.class);
   /* @RequestMapping("/index")       // 请求url地址映射，类似Struts的action-mapping
    public String index(){
        logger.info("spring MVC , Spring ");
        System.out.println(" spring MVC , Spring  ");
        return  "index";
    }*/

    //请求参数 ，请求方法进行映射
    //@RequestParam  注解指定对应的请求参数  有三个参数 1、value 参数名 2、required :是否必需，默认为true  3、defaultValue 默认参数名 ，不建议使用
    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
    public String welcome(@RequestParam(value = "username",required = false) String  username){
        System.out.println(" spring MVC 3  Username  "+ username);
        return "welcome";
    }

    //请求参数 ，请求方法进行映射
    //@RequestParam  注解指定对应的请求参数  有三个参数 1、value 参数名 2、required :是否必需，默认为true  3、defaultValue 默认参数名 ，不建议使用
    @RequestMapping(value = "/welcome2",method = RequestMethod.GET)
    public ModelAndView welcome2(@RequestParam(value = "username",required = false) String  username){
        ModelAndView andView=new ModelAndView();
        andView.addObject("username",username);
        andView.setViewName("weclome");
        System.out.println(" spring MVC 3  Welcome 2 Username  "+ username);
        return andView;
    }

    /**
     * 参数传递 ： controller to  view - (Model)
     * @param username
     * @param model
     * @return
     */
    @RequestMapping(value = "/welcome3",method = RequestMethod.GET)
    public String  welcome3(@RequestParam(value = "username",required = false) String  username, Model model){
        model.addAttribute("username",username);
        System.out.println(" spring MVC 3  Username  "+ username);
        return "welcome";
    }

    /**
     * 参数传递 ： controller to  view - (Model)
     * @param username
     * @param model
     * @return
     */
    @RequestMapping(value = "/welcome4",method = RequestMethod.GET)
    public String  welcome4(@RequestParam(value = "username",required = false) String  username, Model model){
        model.addAttribute("username",username);
        model.addAttribute(username);

      /*  User user =new User();
        user.setUsername(username);

        model.addAttribute("currentUser",user);
        model.addAttribute(user);*/

        System.out.println(" spring MVC 3d  Username  "+ username);
        return "welcome";
    }




}

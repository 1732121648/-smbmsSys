package com.xinshi.smbms.chap10.usercontroller2;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制器
 */
@Controller     //类似Struts的Action
@RequestMapping("/user2")
public class UserController2 {

    private  static Logger logger=Logger.getLogger(UserController2.class);
/*    @RequestMapping("/index")       // 请求url地址映射，类似Struts的action-mapping
    public String index(){
        logger.info("spring MVC , Spring ");
        System.out.println(" spring MVC , Spring  ");
        return  "index";
    }*/

/*
    @RequestMapping("/welcome")
    public String welcome(@RequestParam String  username){
        System.out.println(" spring MVC 2  Username"+ username);
        return "welcome";
    }
*/


   /* private List<User> userList=new ArrayList<>();

    public UserController2(){
        userList.add(new User("test01","yoghurt测试1","11111",1,new Date(),"123456789012","深圳市",1,2,new Date()));
        userList.add(new User("zhangsan","张三","11111",1,new Date(),"12344324012","深圳市",1,2,new Date()));
        userList.add(new User("lisi","里斯","11111",1,new Date(),"2346789012","深圳市",1,2,new Date()));
        userList.add(new User("zisahn","子珊","11111",1,new Date(),"14323489012","福田区",1,2,new Date()));
        userList.add(new User("linse","吝啬","11111",1,new Date(),"342342342323","罗湖区",1,2,new Date()));
    }


    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String list(@RequestParam(required = false) String username, Model model){
        logger.info("查询条件 ： username "+username  +"，，获取 userlist=== ");
        System.out.println("查询条件 ： username "+username  +"，，获取 userlist=== ");
        if(username != null && !username.equals("")){
            for(User user:userList){
                if(user.getUsername().indexOf(username) != -1){
                    userList.add(user);
                }else{
                    model.addAttribute("userList",userList);
                }
            }
        }else{
            model.addAttribute("userList",userList);
        }
        return "userlist";
    }
*/

}

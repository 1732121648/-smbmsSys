package com.xinshi.smbms.controller;

import com.mysql.jdbc.StringUtils;
import com.xinshi.smbms.pojo.Bill;
import com.xinshi.smbms.pojo.Page;
import com.xinshi.smbms.pojo.Provider;
import com.xinshi.smbms.pojo.User;
import com.xinshi.smbms.service.BillService;
import com.xinshi.smbms.service.ProviderService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 供应商 （控制层）
 */
@Controller
@RequestMapping("/jsp")
public class ProviderController {
    @Resource
    private ProviderService providerService;
    @Resource
    private BillService billService;
    private Page<Provider> providerList;

    /**
     * 实现供应商分页查询
     * @param model    model视图对象
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/allByProvider.do")
    public String pageProList(Model model,@RequestParam(required = false,defaultValue = "1") String pageIndex, Provider provider){
        providerList = providerService.findProByCodeAndProName(provider, Integer.valueOf(pageIndex), 5);
        model.addAttribute("providerList",providerList);
        model.addAttribute("queryProCode",provider.getProcode());
        model.addAttribute("queryProName",provider.getProname());
        return "providerlist";
    }

    /**
     * 根据供应商ID 查看单个供应商的信息
     * @return
     */
    @RequestMapping(value = "/queryByIDUpdateProvider")
    public String queryByIDUpdateProvider(Provider provider,Model model){
        model.addAttribute("provider" , providerService.findProById(provider));
        return "providermodify";
    }

    /**
     * 修改供应商信息
     * @return
     */
    @RequestMapping(value = "/updateByProvider",method = RequestMethod.POST)
    public String updateByProvider(Model model, HttpSession session,Provider provider){
        User u =(User) session.getAttribute("userSession");
        provider.setModifydate(new Date());
        provider.setModifyby(u.getId());
        //不等于0 就成功
        if(providerService.updateByPrimaryKeySelective(provider) != 0){
            return  "redirect:/jsp/allByProvider.do"; //刷新数据
        };
        //失败显示修改页面并查询根据供应商ID查显示到页面
        return  "redirect:/jsp/queryByIDUpdateProvider";
    }

    /**
     * 查看单个供应商信息
     * @return
     */
    @RequestMapping(value = "/queryByIdProvider")
    public String queryByIdProvider(Provider provider,Model model){
        model.addAttribute("provider", providerService.findProById(provider));
        return "providerview";
    }

    /**
     * 增加供应商
     * @return
     */
    @RequestMapping(value = "/addByProvider",method = RequestMethod.POST)
    public String addByProvider(Provider provider,HttpSession session){
        User u =(User) session.getAttribute("userSession");
        //创建时间
        provider.setCreationdate(new Date());
        //创建者
        provider.setCreatedby(u.getId());
        if(providerService.insertSelectiveProvider(provider) !=  0){
            return "redirect:/jsp/allByProvider.do";
        }
        return "provideradd";
    }

    /**
     * 删除供应商
     * @return
     */
    @RequestMapping(value = "/deleteByProvider",produces="application/json")
    public String deleteByProvider(Provider provider,HttpServletResponse response)  {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            //判断有没有存在供应商
            if(!StringUtils.isNullOrEmpty(Integer.valueOf(provider.getId()).toString())){
                Bill bill=new Bill();
                bill.setProviderId(provider.getId());
                //查询订单是否存在供应商ID  如果存在显示订单有多少订单，否则删除
                int count = billService.countRowBill(bill);
                if(count == 0){
                    int result = providerService.deleteByPrimaryKey(provider.getId());
                    if( result ==  1){      //删除成功
                        writer.write("{\"delResult\":\"true\"}");
                    }else if(result == -1){     //删除失败
                        writer.write("{\"delResult\":\"false\"}");
                    }
                }else{
                    writer.write("{\"delResult\":\""+count+"\"}");
                }
            }else{
                writer.write("{\"delResult\":\"notexit\"}");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}

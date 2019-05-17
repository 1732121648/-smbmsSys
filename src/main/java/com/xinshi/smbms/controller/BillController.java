package com.xinshi.smbms.controller;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import com.xinshi.smbms.pojo.Bill;
import com.xinshi.smbms.pojo.Page;
import com.xinshi.smbms.pojo.Provider;
import com.xinshi.smbms.pojo.User;
import com.xinshi.smbms.service.BillService;
import com.xinshi.smbms.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/jsp")
public class BillController  {

    @Resource
    private BillService billService;
    //分页数据
    private Page<Bill> billPage;
    @Resource
    private ProviderService providerService;

    /**
     * 订单分页
     * @return
     */
    @RequestMapping(value = "/allPageBill")
    public String allPageBill(Bill bill, @RequestParam(required = false,defaultValue = "1") Integer pageIndex, Model model){
        //根据条件查询订单信息 、 实现订单分页
        model.addAttribute("billPage",billService.findPageBill(bill,pageIndex , 5));
        //查询所有的供应商数据
        model.addAttribute("providerList",providerService.allProvider());
        return "billlist";
    }

    /**
     * 根据订单ID 查询 订单详情
     * @return
     */
    @RequestMapping(value = "/findByIDBill/{id}",method = RequestMethod.GET)
    public String findByIDBill(@PathVariable String id, Model model){
        model.addAttribute("bill", billService.findByIDBill(Integer.valueOf(id)));
        return "billview";
    }

    /**
     *  增加订单功能
     * @return
     */
    @RequestMapping(value = "addBill",method = RequestMethod.POST)
    public String addBill(Bill bill, Model model, HttpSession session){
        User userSession =(User) session.getAttribute("userSession");
        //赋值创建时间
        bill.setCreationDate(new Date());
        bill.setCreatedBy(userSession.getId());
        //调用订单添加方法 > 0 成功 否则失败
        if(billService.addByBill(bill)!= 0){
            return "redirect:/jsp/allPageBill";
        }
        return "billadd";
    }

    /**
     * 使用ajax加载供应商信息
     * @return
     */
    @RequestMapping(value = "/queryByBill",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String  queryByBill(){
        List<Provider> providers = providerService.allProvider();
        return JSON.toJSONString(providers);
    }

    /**
     *  查询订单并显示修改页面
     * @return
     */
    @RequestMapping(value = "/findByIDShowBill/{id}",method = RequestMethod.GET)
    public String findByIDShowBill(@PathVariable Integer id,Model model){
        model.addAttribute("bill",billService.findByIDBill(id));
        return "billmodify";
    }
    /**
     * 修改订单信息
     * @return
     */
    @RequestMapping(value = "/updateByIDBill",method = RequestMethod.POST)
    public String updateByIDBill(Bill bill,HttpSession session){
        User userSession =(User) session.getAttribute("userSession");
        bill.setModifyBy(userSession.getId());
        bill.setModifyDate(new Date());
        if(billService.updateByBill(bill) != 0 ){
            return "redirect:/jsp/allPageBill";
        }
        return "redirect:/jsp/findByIDShowBill/{id}";
    }

    /**
     * 删除订单信息
     * @return
     */
    @RequestMapping(value = "deleteByBill",produces = "text/html;charset=utf-8")
    @ResponseBody
    public void deleteByBill(HttpServletResponse response, @RequestParam Integer id){
        StringBuffer sb=new StringBuffer();
        if(!StringUtils.isNullOrEmpty(id.toString())){
            //执行删除方法等于0就成功
            if(billService.deleteBill(id)>0){
                //声明JSON 格式
                sb.append("{\"delResult\":\"true\"}");
            }else {
                sb.append("{\"delResult\":\"false\"}");
            }
        }else{
            sb.append("{\"delResult\":\"notexist\"}");
        }
        PrintWriter writer =null;
        try {
            writer = response.getWriter();
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.xinshi.smbms.service;

import com.xinshi.smbms.pojo.Bill;
import com.xinshi.smbms.pojo.Page;
import org.apache.ibatis.annotations.Param;

public interface BillService {

    /**
     *  条件查询bill
     * @param bill  订单
     * @param pageNo  当前页
     * @param pageSize  当前页大小
     * @return
     */
    Page<Bill> findPageBill(Bill bill, int pageNo, int pageSize);

    /**
     * 查询订单单个信息
     * @param id
     * @return
     */
    Bill findByIDBill(int id);

    /**
     *修改订单信息
     * @param bill
     * @return
     */
    int updateByBill(Bill bill);

    /**
     * 删除订单
     * @param id
     * @return
     */
    int deleteBill(int id);

    /**
     * 增加订单功能
     * @param bill
     * @return
     */
    int addByBill(Bill bill);

    /**
     * 统计总行数
     * @param bill
     * @return
     */
    int countRowBill(@Param("bill") Bill bill);
}

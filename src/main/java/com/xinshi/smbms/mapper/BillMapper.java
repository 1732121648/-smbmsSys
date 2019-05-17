package com.xinshi.smbms.mapper;

import com.xinshi.smbms.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {

    /**
     *  条件查询bill
     * @param bill  订单
     * @param pageNo  当前页
     * @param pageSize  当前页大小
     * @return
     */
    List<Bill> findPageBill(@Param("bill") Bill bill, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 统计总行数
     * @param bill
     * @return
     */
    int countRowBill(@Param("bill") Bill bill);

    /**
     * 查询单个订单信息
     * @param bill
     * @return
     */
    Bill selectByPrimaryKey(Bill bill);

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
    int addBill(Bill bill);
}

package com.xinshi.smbms.service.impl;

import com.xinshi.smbms.mapper.BillMapper;
import com.xinshi.smbms.pojo.Bill;
import com.xinshi.smbms.pojo.Page;
import com.xinshi.smbms.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("billService")
public class BillServiceImpl implements BillService {

    @Autowired
    @Qualifier("billMapper")
    private BillMapper mapper;

    public BillMapper getMapper() {
        return mapper;
    }
    public void setMapper(BillMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Page<Bill> findPageBill(Bill bill, int pageNo, int pageSize) {
        Page<Bill> page=new Page<Bill>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalRow(mapper.countRowBill(bill));
        page.setTotalPage((page.getTotalRow()+pageSize-1)/pageSize);
        page.setDatas(mapper.findPageBill(bill,(pageNo-1)*pageSize,pageSize));
        return page;
    }

    @Override
    public Bill findByIDBill(int id) {
        return mapper.selectByPrimaryKey(new Bill(id));
    }

    @Override
    public int updateByBill(Bill bill) {
        return mapper.updateByBill(bill);
    }

    @Override
    public int deleteBill(int id) {
        return mapper.deleteBill(id);
    }

    @Override
    public int addByBill(Bill bill) {
        return mapper.addBill(bill);
    }

    @Override
    public int countRowBill(Bill bill) {
        return mapper.countRowBill(bill);
    }


}

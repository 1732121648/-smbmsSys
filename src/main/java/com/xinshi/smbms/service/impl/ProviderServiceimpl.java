package com.xinshi.smbms.service.impl;

import com.xinshi.smbms.mapper.ProviderMapper;
import com.xinshi.smbms.pojo.Page;
import com.xinshi.smbms.pojo.Provider;
import com.xinshi.smbms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("providerService")
public class ProviderServiceimpl implements ProviderService {

    @Autowired
    @Qualifier("providerMapper")
    private ProviderMapper mapper;

    public ProviderMapper getMapper() {
        return mapper;
    }

    public void setMapper(ProviderMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Provider> allProvider() {
        return mapper.allProvider();
    }

    @Override
    public Page<Provider> findProByCodeAndProName(Provider provider, int pageNo, int pageSize) {
        Page<Provider> page=new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalRow(mapper.countRowProvider(provider));
        page.setTotalPage((page.getTotalRow()+pageSize-1)/pageSize);
        page.setDatas(mapper.findProByCodeAndProName(provider,(pageNo-1)*pageSize,pageSize));
        return page;
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Provider findProById(Provider provider) {
        return mapper.selectByPrimaryKey(provider);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int deleteByPrimaryKey(int id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Provider provider) {
        return mapper.updateByPrimaryKeySelective(provider);
    }

    @Override
    public int insertSelectiveProvider(Provider provider) {
        return mapper.insertSelectiveProvider(provider);
    }

}

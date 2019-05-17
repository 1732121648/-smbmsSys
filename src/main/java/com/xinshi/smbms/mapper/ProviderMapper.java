package com.xinshi.smbms.mapper;

import com.xinshi.smbms.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {

    /**
     * 查询所有供应商的信息
     * @return
     */
    List<Provider> allProvider();

    /**
     * 查询所有供应商 按照条件查询
     * @return
     */
    List<Provider>  findProByCodeAndProName(@Param("provider") Provider provider, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 统计供应商总行数
     * @param provider
     * @return
     */
    int countRowProvider(@Param("provider") Provider provider);

    /**
     * 按照供应商的ID 查看供应商信息
     * @param provider
     * @return
     */
    Provider selectByPrimaryKey(Provider provider);

    /**
     * 根据供应商ID删除信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(int id);

    /**
     * 修改供应商的信息
     * @param provider
     * @return
     */
    int updateByPrimaryKeySelective(Provider provider);

    /**
     * 增加供应商
     * @param provider
     * @return
     */
    int insertSelectiveProvider(Provider provider);
}

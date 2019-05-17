package com.xinshi.smbms.service;

import com.xinshi.smbms.pojo.Page;
import com.xinshi.smbms.pojo.Role;

import java.util.List;

/**
 * 角色业务层
 */
public interface RoleService {

    /**
     * 查询分页角色
     * @return
     */
    List<Role> findAllRoleName();

    /**
     * 修改角色
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    int deleteRole(int id);

    /**
     * 根据角色ID 查询信息
     * @return
     */
    Role findRoleByID(Role role);

    /**
     * 添加角色
     * @param role
     * @return
     */
    int addRole(Role role);



}

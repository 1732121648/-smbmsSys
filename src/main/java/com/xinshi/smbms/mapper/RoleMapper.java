package com.xinshi.smbms.mapper;

import com.xinshi.smbms.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    /**
     * 查询分页角色
     * @return
     */
    List<Role> findAllRoleName();

    /**
     * 统计角色数量
     * @return
     */
    int countRole();

    /**
     * 添加角色
     * @param role
     * @return
     */
    int addRole(Role role);

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
    int deleteRole(@Param("id") int id);

    /**
     * 根据角色ID 查询信息
     * @return
     */
    Role findRoleByID(Role role);

}

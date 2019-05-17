package com.xinshi.smbms.service.impl;

import com.xinshi.smbms.mapper.RoleMapper;
import com.xinshi.smbms.pojo.Page;
import com.xinshi.smbms.pojo.Role;
import com.xinshi.smbms.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceimpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRoleName() {
        return roleMapper.findAllRoleName();
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public int deleteRole(int id) {
        return roleMapper.deleteRole(id);
    }

    @Override
    public Role findRoleByID(Role role) {
        return roleMapper.findRoleByID(role);
    }


    @Override
    public int addRole(Role role) {
        return roleMapper.addRole(role);
    }
}

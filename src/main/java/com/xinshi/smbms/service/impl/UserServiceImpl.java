package com.xinshi.smbms.service.impl;

import com.xinshi.smbms.mapper.UserMapper;
import com.xinshi.smbms.pojo.Page;
import com.xinshi.smbms.pojo.Role;
import com.xinshi.smbms.pojo.User;
import com.xinshi.smbms.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

   @Resource
    private UserMapper userMapper;

    @Override
    public User selectByUserRole(int userrole) {
        return userMapper.selectByUserRole(userrole);
    }

    @Override
    public User findUserCodeOrPwd(String userCode, String passWord) {
        User user=new User(userCode,passWord);
        return userMapper.findUserCodeOrPwd(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<User> findPageUsers(User user, int pageNo, int pageSize) {
        Page<User> page=new Page<User>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalRow(userMapper.countRowUser(user));
        page.setTotalPage((page.getTotalRow()+pageSize-1)/pageSize);
        page.setDatas(userMapper.findPageUser(user,(pageNo-1)*pageSize,pageSize));
        return page;
    }
    @Override
    public List<Role> findAllRoleName() {
        return userMapper.findAllRoleName();
    }
    @Override
    public User selectByPrimaryKey(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByIDUser(int id) {
        return userMapper.deleteByIDUser(id);
    }

    @Override
    public int uodateByUser(User user) {
        return userMapper.updateOrAddUser(user);
    }

    @Override
    public User findByUserCode(String userCode) {
        return userMapper.findUserCodeOrPwd(new User(userCode));
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User findByVerifyPwd(User user) {
        return userMapper.findUserCodeOrPwd(user);
    }

    @Override
    public int updatePassWord(String password,int id) {
        return userMapper.updatePassWord(password,id);
    }

    @Override
    public User findByPassWord(String password, int id) {
        return userMapper.findByPassWord(password, id);
    }

    public UserMapper getMapper() {
        return userMapper;
    }

    public void setMapper(UserMapper mapper) {
        this.userMapper = mapper;
    }
}

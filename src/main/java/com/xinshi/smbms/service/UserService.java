package com.xinshi.smbms.service;

import com.xinshi.smbms.pojo.Page;
import com.xinshi.smbms.pojo.Role;
import com.xinshi.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    User selectByUserRole(@Param("userrole") int userrole);
    /**
     * 执行登录功能
     * @param userCode
     * @param passWord
     * @return
     */
    User findUserCodeOrPwd(String userCode, String passWord);
    /**
     * 实现分页查询
     * @param user
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<User> findPageUsers(User user, int pageNo, int pageSize);
    /**
     * 查询全部角色名称
     * @return
     */
    List<Role> findAllRoleName();
    /**
     * 根据用户ID 查询 相关信息
     * @return
     */
    User selectByPrimaryKey(int id);
    /**
     * 根据用户 ID 删除 用户
     * @param id
     * @return
     */
    int deleteByIDUser(int id);
    /**
     * 修改用户
     * @param user
     * @return
     */
    int uodateByUser(User user);

    /**
     * 根据用户编码查找是否在存在
     * @param userCode
     * @return
     */
    User findByUserCode(String userCode);
    /**
     * 增加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 验证旧密码是否存在
     * @param user
     * @return
     */
    User findByVerifyPwd(User user);

    /**
     * 修改密码
     * @param password
     * @return
     */
    int updatePassWord(String password, int id);

    /**
     * 验证旧密码
     * @param password
     * @param id
     * @return
     */
    User findByPassWord(String password, int id);

}

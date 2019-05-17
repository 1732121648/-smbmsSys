package com.xinshi.smbms.mapper;

import com.xinshi.smbms.pojo.Role;
import com.xinshi.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 这里的@MapperScan就是上面所讲的Mapper扫描器中所需要的配置，会自动生成代理对象。
 * 注意，接口中的方法名称要和对应的MyBatis映射文件中的语句的id值一样，因为生成的
 * 动态代理，会根据这个匹配相应的Sql语句执行。另外就是方法的参数和返回值也需要注
 * 意。接口中的方法如何定义，对应的MyBatis映射文件就应该进行相应的定义。
 * 最后，标注中的userDao是用来作为Spring的Bean的id(或name)进行使用的，方便我
 * 们在Service层进行注入使用。
 */

public interface UserMapper {

    /**
     * 查询用户名和密码
     * @param user
     * return
     */
    User findUserCodeOrPwd(User user);


    List<User> findPageUser(@Param("user") User user, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 统计总行数
     * @return
     */
    int countRowUser(User user);

    /**
     * 查询全部角色名称
     * @return
     */
    List<Role> findAllRoleName();
    /**
     * 根据用户ID 查询 相关信息
     * @return
     */
    User selectByPrimaryKey(@Param("id") int id);

    User selectByUserRole(@Param("userrole") int userrole);

    /**
     * 根据用户 ID 删除 用户
     * @param id
     * @return
     */
    int deleteByIDUser(@Param("id") int id);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int updateOrAddUser(User user);

    /**
     * 增加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 修改密码
     * @param password
     * @return
     */
    int updatePassWord(@Param("userpassword") String password, @Param("id") int id);

    /**
     * 验证旧密码
     * @param password
     * @param id
     * @return
     */
    User findByPassWord(@Param("userpassword") String password, @Param("id") int id);



}

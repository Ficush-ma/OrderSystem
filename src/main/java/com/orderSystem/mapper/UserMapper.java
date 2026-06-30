package com.orderSystem.mapper;

import com.orderSystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /*
        增加用户
     */
    void addUser(User user);
    /*
        查询用户密码
     */
    String getUserPassword(String userName);
    /*
        用户名获取ID
     */
    int getUserId(String userName);
    /*
        id获取用户
     */
    User getUserById(int id);
    /*
        设置用户余额
     */
    void setBalance(int userId,int num);

}

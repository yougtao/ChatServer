package cn.yongtao.mapper;

import cn.yongtao.pojo.User;

public interface UserMapper
{
    // 根据id查询用户信息
    User queryUser(int id);

    // 根据用户名查询密码
    String queryPassword(String username);

    // 根据用户名 查询ID
    int queryIdByUsername(String username);

    // 创建用户
    void insertUser(User user);

    // 修改上次登录时间
    void updateLastTime(int id);
}

package cn.yongtao.mapper;

import cn.yongtao.pojo.User;

public interface UserMapper
{
    // 密码
    User queryUser(int id);

    // 根据用户名查询密码
    String queryPassword(String username);

    // 创建用户
    void insertUser(User user);
}

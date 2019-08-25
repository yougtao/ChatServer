package cn.yongtao.service;

import cn.yongtao.common.BCrypt;
import cn.yongtao.mapper.UserMapper;
import cn.yongtao.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public User loginUser(String username, String password) {

        String passwordHash = userMapper.queryPassword(username);

        User user = null;
        if (BCrypt.checkpw(password, passwordHash)) {
            System.out.println("It matches");

            // 查询用户信息
            int id = userMapper.queryIdByUsername(username);
            user = userMapper.queryUser(id);

            // 修改登录时间
            userMapper.updateLastTime(id);
        }
        return user;
    }


    public void logupUser(String username, String password) {
        // 加盐
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        // 将hashed写入数据库password
        User user = new User(username, hashed);
        userMapper.insertUser(user);
    }

    // 用户名是否重复
    public int queryIdByUsername(String username) {
        return userMapper.queryIdByUsername(username);
    }

}// end

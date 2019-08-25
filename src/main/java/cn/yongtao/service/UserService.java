package cn.yongtao.service;

import cn.yongtao.common.BCrypt;
import cn.yongtao.mapper.UserMapper;
import cn.yongtao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public void loginUser(String username, String password) {

        String passwordHash = userMapper.queryPassword(username);

        if (BCrypt.checkpw(password, passwordHash))
            System.out.println("It matches");
        else
            System.out.println("It does not match");
    }


    public void logupUser(String username, String password) {
        // 加盐
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        // 将hashed写入数据库password
        User user = new User(username, hashed);
        userMapper.insertUser(user);
    }

}// end

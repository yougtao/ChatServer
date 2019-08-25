package cn.yongtao.service;

import cn.yongtao.common.BCrypt;
import cn.yongtao.mapper.UserMapper;
import cn.yongtao.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public User loginUser(String username, String password) {

        User user = userMapper.queryPassword(username);

        if (user == null)
            return null;

        if (BCrypt.checkpw(password, user.getPassword())) {
            // 查询用户信息
            user = userMapper.queryUser(user.getId());

            // 修改登录时间
            userMapper.updateLastTime(user.getId());
        }
        user.setPassword("");
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


    // 查询好友
    public List<User> queryFriend(int id) {
        return userMapper.queryFriend(id);
    }

}// end

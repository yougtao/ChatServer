package cn.yongtao.controller;

import cn.yongtao.common.Constant;
import cn.yongtao.common.Message;
import cn.yongtao.common.MessageType;
import cn.yongtao.common.ResultMessage;
import cn.yongtao.pojo.User;
import cn.yongtao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController
{
    public Map<Integer, Method> methods = new HashMap<>();

    @Autowired
    UserService userService;

    public LoginController() {
        Method[] methods = LoginController.class.getDeclaredMethods();
        for (Method method : methods) {
            MessageType requestType = method.getAnnotation(MessageType.class);
            if (requestType == null)
                continue;

            this.methods.put(requestType.value(), method);
        }
    }

    // 登录
    @MessageType(Constant.USER_LOGIN)
    public Message login(ResultMessage msg) {
        String body = msg.getBody();
        String[] strings = body.split(",");

        User user = userService.loginUser(strings[0], strings[1]);
        Map<String, Object> map = new HashMap<>();
        if (user != null) {
            map.put("code", 200);
            map.put("msg","success");
            map.put("user", user);
        } else {
            map.put("code", 401);
            map.put("msg", "password");
        }
        return new Message(0, Constant.USER_LOGIN_RETURN, map);
    }

    // 注册
    @MessageType(Constant.USER_LOGUP)
    public void logup(ResultMessage msg) {
        String body = msg.getBody();
        String[] strings = body.split(",");

        String username = strings[0];

        // 用户名是否存在
        if (userService.queryIdByUsername(username) == 0)
            userService.logupUser(username, strings[1]);
    }


    // 用户名是否存在
    @MessageType(262)
    public void existUsername(ResultMessage msg) {
        int id = userService.queryIdByUsername(msg.getBody());
        System.out.println("结果：" + id);
    }

}// end

package cn.yongtao.controller;

import cn.yongtao.common.Constant;
import cn.yongtao.common.MessageType;
import cn.yongtao.common.ResultMessage;
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
    public void login(ResultMessage msg) {
        String body = msg.getBody();
        String[] strings = body.split(",");

        userService.loginUser(strings[0], strings[1]);
    }

//    注册
    @MessageType(Constant.USER_LOGUP)
    public void logup(ResultMessage msg){
        String body = msg.getBody();
        String[] strings = body.split(",");

        userService.logupUser(strings[0], strings[1]);
    }
}// end

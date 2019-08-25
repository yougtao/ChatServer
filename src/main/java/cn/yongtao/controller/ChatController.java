package cn.yongtao.controller;

import cn.yongtao.common.MessageType;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ChatController
{
    public Map<Integer, Method> methods = new HashMap<>();

    public ChatController() {
        Method[] methods = LoginController.class.getDeclaredMethods();
        for (Method method : methods) {
            MessageType requestType = method.getAnnotation(MessageType.class);
            if (requestType == null)
                continue;

            this.methods.put(requestType.value(), method);
        }
    }
}// end

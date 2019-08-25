package cn.yongtao.controller;

import cn.yongtao.common.Constant;
import cn.yongtao.common.LoginUsersSockets;
import cn.yongtao.common.Message;
import cn.yongtao.common.MessageType;
import cn.yongtao.pojo.User;
import cn.yongtao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FriendController
{
    public Map<Integer, Method> methods = new HashMap<>();

    @Autowired
    UserService userService;

    public FriendController() {
        Method[] methods = FriendController.class.getDeclaredMethods();
        for (Method method : methods) {
            MessageType requestType = method.getAnnotation(MessageType.class);
            if (requestType == null)
                continue;

            this.methods.put(requestType.value(), method);
        }
    }

    // 获取好友列表
    @MessageType(Constant.USER_FRIEND)
    public void queryFriend(Message message) {
        int id = Integer.parseInt((String) message.getBody());
        List<User> friends = userService.queryFriend(id);

        LoginUsersSockets.sendTo(id, Constant.USER_FRIEND_RETURN, friends);
    }

    // 申请 添加好友
    public void applyAddFriend() {

    }

    // 应答 加为好友
    public void replyAddFriend() {

    }

}// end

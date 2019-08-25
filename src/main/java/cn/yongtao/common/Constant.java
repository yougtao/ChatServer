package cn.yongtao.common;

public class Constant
{

    // 1.登录
    private static final int LOGIN = 1 << 8;
    // 用户登录
    public static final int USER_LOGIN = LOGIN + 1;
    public static final int USER_LOGIN_RETURN = USER_LOGIN + 1;
    // 用户退出
    public static final int USER_LOGOUT = LOGIN + 3;

    //    注册
    public static final int USER_LOGUP = LOGIN + 5;

    //    心跳包

    //


    //    2.用户
    private static final int USER = 2 << 8;


    //    3.聊天
    private static final int CHAT = 3 << 8;
}

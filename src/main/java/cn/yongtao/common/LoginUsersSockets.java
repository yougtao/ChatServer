package cn.yongtao.common;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginUsersSockets
{
    private static Map<Integer, Channel> map = new ConcurrentHashMap<>();


    public static void add(int id, Channel ctx) {
        map.put(id, ctx);
    }

    public static Channel get(Integer id) {
        return map.get(id);
    }

    public static void remove(int id) {
        map.remove(id);
    }

    public static void remove(Channel channel) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() == channel) {
                map.remove(entry.getKey());
            }
        }
    }

}// end

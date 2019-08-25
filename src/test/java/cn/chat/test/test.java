package cn.chat.test;

import cn.yongtao.common.BCrypt;

public class test
{
    public static void main(String[] args) {
        String password = "password";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashed);
        // gensalt's log_rounds parameter determines the complexity
        // the work factor is 2**log_rounds, and the default is 10
        String hashed2 = BCrypt.hashpw(password, BCrypt.gensalt(12));

        // Check that an unencrypted password matches one that has
        // previously been hashed
        String candidate = "testpassword";
        //String candidate = "wrongtestpassword";
        if (BCrypt.checkpw(candidate, hashed2))
            System.out.println("It matches");
        else
            System.out.println("It does not match");
    }

}

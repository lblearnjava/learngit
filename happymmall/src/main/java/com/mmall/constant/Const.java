package com.mmall.constant;

import java.security.PublicKey;

/**
 * Created by lanbingdepingguo on 2017/7/24.
 */
public class Const {
    public static final String CURRENT_USER = "CURRENT_USER";

    public interface Role {
        int ROLE_CUSTUMER = 0;//普通用户，用于登陆portal
        int ROLE_ADMIN = 1;//管理员用户
    }
    public interface Sale {
        int ON_SALE=1;
        int NOT_OnSALE=0;
    }

    public static final String EMAIL = "email";
    public static final String USER_NAME = "username";

    public interface  Check{
        int CHECKED=1;
        int UN_CHECKED=0;
    }
    public  interface  Cart{
        String LIMIT_NUM_SUCCESS="success";
        String LIMIT_NUM_FAIL="fail";

    }

}

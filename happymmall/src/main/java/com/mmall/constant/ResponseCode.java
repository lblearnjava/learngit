package com.mmall.constant;

/**
 * Created by lanbingdepingguo on 2017/7/24.
 */
public enum ResponseCode {
       //枚举之间一定是逗号
        FAIL(1,"FAIL"),
        SUCCESS(0,"SUCCESS"),
        NEED_LOGIN(10,"NEED_LOGIN");

        private final int code;
        private final String desc;


        ResponseCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }






}

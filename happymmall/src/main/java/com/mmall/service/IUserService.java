package com.mmall.service;

import com.mmall.constant.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by lanbingdepingguo on 2017/7/23.
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String>  register(User user);
    ServerResponse<String> checkValid(String str,String type);
    ServerResponse<String> get_questtion(String username);
    ServerResponse<String> forget_check_answer(String username,String question,String answer);
    ServerResponse<String> resetpassword(String Token,String username,String password);
    ServerResponse<String> resetpassword_LOGIN(String username,String newPassword,String oldPasswor);
    ServerResponse<String> updateInformation(User user);
    ServerResponse<User>  getUserInformationById(int id);
    boolean  checkAdmin(User user);

    


}

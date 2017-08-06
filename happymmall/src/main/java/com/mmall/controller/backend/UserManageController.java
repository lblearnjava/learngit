package com.mmall.controller.backend;

import com.mmall.constant.Const;
import com.mmall.constant.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lanbingdepingguo on 2017/7/28.
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(HttpSession session, @RequestParam("username") String username,
                                      @RequestParam("password") String password){

        ServerResponse<User> response=iUserService.login(username,password);
        if(response.isSuccess()){
            if(iUserService.checkAdmin(response.getData())){
                session.setAttribute(Const.CURRENT_USER,response.getData());
                return response;
            }
        }
        return  ServerResponse.createByErrorMessage("不是管理员用户，请重新登陆");
    }


}

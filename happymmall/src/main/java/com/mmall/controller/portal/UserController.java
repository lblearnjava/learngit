package com.mmall.controller.portal;

import com.mmall.constant.ResponseCode;
import com.mmall.constant.ServerResponse;
import com.mmall.constant.TokenCache;
import com.mmall.pojo.User;
import com.sun.tools.internal.ws.processor.model.Request;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.mmall.service.IUserService;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mmall.constant.Const;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by lanbingdepingguo on 2017/7/23.
 */
@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());


        }

        return response;


    }

    /**
     * 用户注册
     *
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(HttpSession session, User user) {
        ServerResponse<String> response = iUserService.register(user);
        return response;


    }

    /**
     * 实时监测注册时输入内容是否正确
     *
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> check_valid(HttpSession session, @RequestParam(value = "str") String str, @RequestParam(value = "type") String type) {
        ServerResponse<String> response = iUserService.checkValid(str, type);
        return response;

    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {

        session.removeAttribute(Const.CURRENT_USER);
        if (session.getAttribute(Const.CURRENT_USER) != null) {
            return ServerResponse.createByErrorMessage("服务器异常，登出不成功！！");


        }
        return ServerResponse.createBySuccessMessage("退出登录成功！");

    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_user_info(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccess(user);

        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息");


    }


    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forget_get_question(String username) {
        ServerResponse<String> response = iUserService.get_questtion(username);
        return response;
    }


    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forget_check_answer(String username, String question, String answer) {

        return iUserService.forget_check_answer(username, question, answer);


        //iUserService.forget_check_answer(String username,String question,String answer);

    }

    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forget_reset_password(HttpSession session, @RequestParam("Token") String Token, @Param("username") String username, @Param("password") String password) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户登陆，请先登陆");

        }
        return iUserService.resetpassword(Token, username, password);
    }

    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> reset_password(HttpSession session, @Param("newPassword") String newPassword, @Param("oldPassword") String oldPassword) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }


        String name = user.getUsername();
        return iUserService.resetpassword_LOGIN(name, newPassword, oldPassword);

    }

    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateInformation(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);

        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }

        return iUserService.updateInformation(user);

    }

    /**
     * 更新信息的个人详细信息查询
     */
    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,无法获取当前用户信息,status=10,强制登录");

        }

        return iUserService.getUserInformationById(currentUser.getId());


    }

    @RequestMapping(value = "{test1}/{test2}/test.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> test(@PathVariable(value = "test1") String test, @PathVariable(value = "test2") String test2) {
        System.out.println(test);
        System.out.println(test2);
        return null;


    }


//    @RequestMapping(value = "checkUsername.do", method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse<String> register( HttpSession session,User user) {
//
//        ServerResponse<String> response=iUserService.register(user);
//
//        return response;
//
//
//    }


    //    @ResponseBody
//    @RequestMapping("add.do")
//    public User login(User user) {
//        Boolean b = iUserService.login(user);
//        if (b != true) {
//            System.out.println("登录成功");
//            return user;
//        }
//        User u = new User();
//        return u;
//    }
//
//    @RequestMapping("test.do")
//    public String test() {
//        System.out.println("test");
//
//
//        return "forward:/user/test1";
//
//
//    }
//
//    @RequestMapping("test1.do")
//    public String test1(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("final  test");
//
//        return "test";
//
//
//    }
//
    @RequestMapping("testFileUpload.do")
    @ResponseBody
    public Object testFileUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) throws IOException {

        String path = request.getSession().getServletContext().getRealPath("/upload");
        System.out.println(path);

        String fileName = file.getOriginalFilename();

        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        String fileUrl = fileName;
        return fileUrl;
    }


}

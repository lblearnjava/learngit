package com.mmall.service.iml;


import com.mmall.constant.Const;
import com.mmall.constant.ResponseCode;
import com.mmall.constant.ServerResponse;
import com.mmall.constant.TokenCache;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.mmall.dao.UserMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by lanbingdepingguo on 2017/7/23.
 */
@Service("iUserService")
public class UserServiceIml implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登陆逻辑
     *
     * @param username
     * @param password
     * @return
     */

    @Override
    public ServerResponse<User> login(String username, String password) {

        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return new ServerResponse<User>(ResponseCode.FAIL.getCode(), "用户名不正确");


        }
        //md5加密   to do
        User user = userMapper.login(username, password);
        if (user == null) {
            return new ServerResponse<User>(ResponseCode.FAIL.getCode(), "密码不正确");


        }
        user.setPassword(StringUtils.EMPTY);
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), user);

    }

    @Override
    public ServerResponse<String> register(User user) {

        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USER_NAME);
        if (!validResponse.isSuccess()) {
            return validResponse;

        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;

        }


        user.setRole(Const.Role.ROLE_CUSTUMER);
        //md5 加密  to  出了问题，后面解决
        user.setPassword(user.getPassword());
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            return new ServerResponse(ResponseCode.FAIL.getCode(), "插入失败");
        }

        return new ServerResponse(ResponseCode.SUCCESS.getCode(), "校验成功，已插入数据库");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {

        if (StringUtils.isNotBlank(type)) {
            //type不为空才开始校验
            int resultCount = 0;
            if (Const.USER_NAME.equals(type)) {
                resultCount = userMapper.checkUsername(str);
                if (resultCount != 0) {
                    return new ServerResponse(ResponseCode.FAIL.getCode(), "校验不成功！用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                resultCount = userMapper.checkEmail(str);
                if (resultCount != 0) {
                    return new ServerResponse(ResponseCode.FAIL.getCode(), "校验不成功,email已经存在");

                }
            }

            return ServerResponse.createBySuccessMessage("校验成功");
        }

        return ServerResponse.createByErrorMessage("参数不正确");

    }

    /**
     * 用户问题的获取
     */

    @Override
    public ServerResponse<String> get_questtion(String username) {
        String question = userMapper.get_questtion(username);
        if (question == null) {
            return ServerResponse.createByErrorMessage("用户名不存在");

        }
        if (StringUtils.isBlank(question)) {

            return ServerResponse.createByErrorMessage("该用户为设置密码提示问题");
        }


        return ServerResponse.createBySuccess(question);
    }

    /**
     * 校验提交密码
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @Override
    public ServerResponse<String> forget_check_answer(String username, String question, String answer) {
        int resultCount=userMapper.forget_check_answer(username,question,answer);
        if(resultCount==1){
            /**
             * 等于0，存到guwa缓存，以便下一个接口调用
             *
             */
            String token=UUID.randomUUID().toString();
            TokenCache.setKey("token_"+username,token);

            /**
             * to do
             */
            return  ServerResponse.createBySuccess(token);
        }else{
            return  ServerResponse.createByErrorMessage("答案不正确");
        }




    }

    /**
     * 忘记密码的修改密码
     * @param Token
     * @param username
     * @param password
     * @return
     */
    @Override
    public ServerResponse<String> resetpassword(String Token, String username, String password) {

        if(StringUtils.isNotBlank(Token)&&Token.equals(TokenCache.getKey("token_"+username))) {

            int resultCount = userMapper.resetpassword(username, password);
            if (resultCount > 0) {
                return  ServerResponse.createBySuccessMessage("密码修改成功");

            }
            return  ServerResponse.createByErrorMessage("密码修改失败（用户名不正确）");

        }





        return ServerResponse.createByErrorMessage("token验证失败");
    }

    /**
     * 登陆状态下重置密码
     * @param username
     * @param newPassword
     * @param oldPasswor
     * @return
     */
    @Override
    public ServerResponse<String> resetpassword_LOGIN(String username, String newPassword, String oldPassword) {

       int resultCount= userMapper.resetpassword_LOGIN(username,newPassword,oldPassword);
      if(resultCount>0){

          return  ServerResponse.createBySuccessMessage("修改密码成功");
      }




        return ServerResponse.createByErrorMessage("旧密码不正确");
    }

    /**
     * 登陆状态下更新用户个人信息
     * @param user
     * @return
     */
    @Override
    public ServerResponse<String> updateInformation(User user) {

        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("email已存在,请更换email再尝试更新");
        }

        User updateUser=new User();
        updateUser.setPassword(user.getAnswer());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setRole(Const.Role.ROLE_CUSTUMER);
        updateUser.setId(user.getId());
        resultCount=userMapper.updateByPrimaryKeySelective(updateUser);
        if(resultCount>0){

         return  ServerResponse.createBySuccessMessage("更新用户个人信息成功");
        }
        return ServerResponse.createByErrorMessage("更新用户个人信息失败");

    }

    @Override
    public ServerResponse<User> getUserInformationById(int id) {
        User currentUser=userMapper.selectByPrimaryKey(id);
        if(currentUser==null){
            return ServerResponse.createByErrorMessage("无法获取当前用户登录信息");


        }

        return ServerResponse.createBySuccess(currentUser);


    }
    /**
     * backend
     */
    @Override
    public boolean checkAdmin(User user){
        return  user!=null&&user.getRole().intValue()==Const.Role.ROLE_ADMIN;

    }


























}

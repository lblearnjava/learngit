package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);





    User login(@Param("username")String username,@Param("password")String password);
    int checkUsername(String username);
    int checkEmail(String email);
    String  get_questtion(String username);
    int forget_check_answer(@Param("username") String username,@Param("question") String question,@Param("answer") String  answer);

   int  resetpassword(@Param("username") String username,@Param("password") String password);
    int resetpassword_LOGIN(@Param("username") String username, @Param("newPassword") String newPassword, @Param("oldPassword") String oldPassword);
    int checkEmailByUserId(@Param(value="email")String email,@Param(value="userId")Integer userId);
}
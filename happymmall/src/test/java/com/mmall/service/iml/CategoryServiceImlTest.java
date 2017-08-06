package com.mmall.service.iml;

import com.mmall.constant.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by lanbingdepingguo on 2017/7/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CategoryServiceImlTest {

    //注入测试类
    @Autowired
    private IUserService iUserService;


    @Test
    public void testLogin() throws Exception {
        ServerResponse<User> response= iUserService.login("lanbing","fsdfsdfs");
        System.out.println(response.getData());


    }


}
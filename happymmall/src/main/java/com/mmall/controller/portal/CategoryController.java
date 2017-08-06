package com.mmall.controller.portal;

import com.mmall.constant.Const;
import com.mmall.constant.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by lanbingdepingguo on 2017/7/26.
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryController {
    @Autowired
   private ICategoryService iCategoryService;
    @Autowired
    private  IUserService iUserService;





    @RequestMapping(value = "get_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Category>> get_category(@RequestParam(value = "categoryId") Integer categoryId, HttpSession session){

        User currentUser=(User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return  ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }

        return  iCategoryService.selectPrallelChilderenByCategoryId( categoryId);
    }
    @RequestMapping(value = "add_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> add_category(HttpSession session,@RequestParam(value = "parentId",defaultValue ="0") Integer parentId,@RequestParam("categoryName") String categoryName ){

        User currentUser=(User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return  ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        if(!iUserService.checkAdmin(currentUser)){
            return ServerResponse.createByErrorMessage("不是管理员用户，请用管理员账号登陆");
        }

        return  iCategoryService.addCategory(parentId,categoryName);
    }
    @RequestMapping(value = "set_category_name.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setCategoryName (HttpSession session,Category category){
        User currentUser=(User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return  ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        if(!iUserService.checkAdmin(currentUser)){
            return ServerResponse.createByErrorMessage("不是管理员用户，请用管理员账号登陆");
        }
        return iCategoryService.setCategoryName(category);
    }

    @RequestMapping(value = "get_deep_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Integer>>   getDeepCategory(HttpSession session,Integer categoryId){
        User currentUser=(User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return  ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        if(!iUserService.checkAdmin(currentUser)){
            return ServerResponse.createByErrorMessage("不是管理员用户，请用管理员账号登陆");
        }
        return  iCategoryService.getDeepCategory(categoryId);
    }












}








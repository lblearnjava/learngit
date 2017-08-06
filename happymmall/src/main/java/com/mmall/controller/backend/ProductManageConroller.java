package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.constant.Const;
import com.mmall.constant.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lanbingdepingguo on 2017/7/27.
 */
@Controller
@RequestMapping("/manage/product/")
public class ProductManageConroller {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "save.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> saveOrUpdate(Product product, HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        if (!iUserService.checkAdmin(currentUser)) {
            return ServerResponse.createByErrorMessage("不是管理员用户，请用管理员账号登陆");
        }
        return iProductService.saveOrUpdate(product);

    }


    @RequestMapping(value = "set_sale_status.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setSaleStatus(@RequestParam("productId") Integer productId, @RequestParam("status") Integer status, HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        if (!iUserService.checkAdmin(currentUser)) {
            return ServerResponse.createByErrorMessage("不是管理员用户，请用管理员账号登陆");
        }
        return iProductService.setSaleStatus(productId, status);


    }

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        if (!iUserService.checkAdmin(currentUser)) {
            return ServerResponse.createByErrorMessage("不是管理员用户，请用管理员账号登陆");
        }
        return iProductService.list(pageNum, pageSize);
    }

    /**
     * 后天获取产品详情接口
     */

    @RequestMapping(value = "detail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ProductDetailVo> getProductDetail(@RequestParam(value = "productId") Integer productId, HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        if (!iUserService.checkAdmin(currentUser)) {
            return ServerResponse.createByErrorMessage("不是管理员用户，请用管理员账号登陆");
        }

        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value = "search.do")
    @ResponseBody
    public ServerResponse<PageInfo> searchByIdAndName(@RequestParam(value = "productId", required=false) Integer productId,
                                                      @RequestParam(value = "productName", required=false) String productName,
                                                      @RequestParam(value = "pageNum", defaultValue="1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue="2") Integer pageSize,
                                                      HttpSession session) {

        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        if (!iUserService.checkAdmin(currentUser)) {
            return ServerResponse.createByErrorMessage("不是管理员用户，请用管理员账号登陆");
        }


        return iProductService.searchByIdAndName(productId, productName, pageNum, pageSize);
    }


}

package com.mmall.controller.portal;

import com.mmall.constant.Const;
import com.mmall.constant.ResponseCode;
import com.mmall.constant.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;

import com.mmall.vo.CartVo;
import com.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lanbingdepingguo on 2017/7/29.
 */
@Controller
@RequestMapping("/cart/")

public class CartController {
    @Autowired
    private ICartService iCartService;


    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse<CartVo> addToCart(HttpSession session, @RequestParam("productId") Integer productId, @RequestParam("count") Integer count) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        return iCartService.addToCart(productId, count, currentUser.getId());


    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse<CartVo> updateCart(HttpSession session, @RequestParam("productId") Integer productId, @RequestParam("count") Integer count) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        return iCartService.updateCart(productId, count, currentUser.getId());


    }

    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse<CartVo> delete_product(HttpSession session, @RequestParam("productIds") String productIds) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        return iCartService.delete_product(productIds, currentUser.getId());
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(currentUser.getId());
    }

    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> get_cart_product_count(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.getCartTotalProductNum(currentUser.getId());
    }

    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> select_all(HttpSession session, @RequestParam(value = "productId", required = false) Integer productId, @RequestParam("checked") Integer checked) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());

        }
        return iCartService.selectAll(currentUser.getId(), productId, checked);
    }


    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> un_select_all(HttpSession session, @RequestParam(value = "productId", required = false) Integer productId, @RequestParam("checked") Integer checked) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());

        }
        return iCartService.selectAll(currentUser.getId(), productId, checked);
    }

    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<CartVo> select(HttpSession session, @RequestParam(value = "productId", required = false) Integer productId, @RequestParam("checked") Integer checked) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());

        }
        return iCartService.selectAll(currentUser.getId(), productId, checked);
    }

    @RequestMapping("un_select.do")
    @ResponseBody
    public ServerResponse<CartVo> un_select(HttpSession session, @RequestParam(value = "productId", required = false) Integer productId, @RequestParam("checked") Integer checked) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());

        }
        return iCartService.selectAll(currentUser.getId(), productId, checked);
    }


}

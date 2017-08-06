package com.mmall.service;

import com.mmall.constant.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * Created by lanbingdepingguo on 2017/7/29.
 */
public interface ICartService  {
    ServerResponse<CartVo>  addToCart(Integer productId,Integer count,Integer userId);
    ServerResponse<CartVo>  updateCart(Integer productId,Integer count,Integer userId);
    ServerResponse<CartVo> delete_product(String productIds,Integer userId);
    ServerResponse<CartVo> list(Integer userId);
    ServerResponse<Integer>  getCartTotalProductNum(Integer userId);
    ServerResponse<CartVo>   selectAll(Integer userId,Integer productId,int checked);



}

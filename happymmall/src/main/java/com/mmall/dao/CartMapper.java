package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    Integer upadteByUserIdAndProductId(Cart cart);

    List<Cart> selectCartByUserId(Integer userId);

    Integer getAllCheckedStatus(Integer userId);

    Integer deleteProductByUserIdAndProductIds(@Param("userId") Integer userId, @Param("lists") List<String> lists);

    Integer get_Cart_Product_Total_Num(Integer userId);

    Integer selectALL(@Param("userId") Integer userId, @Param("productId") Integer productId,@Param("checked") int checked);


}
package com.mmall.service.iml;

import com.google.common.collect.Lists;
import com.mmall.constant.Const;
import com.mmall.constant.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanbingdepingguo on 2017/7/29.
 */
@Service("iCartService")
public class CartServiceIml implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> addToCart(Integer productId, Integer count, Integer userId) {
        if (productId == null || count == null || count == 0) {
            return ServerResponse.createByErrorMessage("参数错误，无法添加");
        }
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        int resultCount = 0;
        if (cart != null) {
            //更新
            Cart cartItem = new Cart();
            cartItem.setChecked(Const.Check.CHECKED);
            cartItem.setQuantity(cart.getQuantity() + count);
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            resultCount = cartMapper.upadteByUserIdAndProductId(cartItem);
        } else {
            //新增
            Cart cartItem2 = new Cart();
            cartItem2.setQuantity(count);
            cartItem2.setChecked(Const.Check.CHECKED);
            cartItem2.setProductId(productId);
            cartItem2.setUserId(userId);
            resultCount = cartMapper.insert(cartItem2);

        }
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("添加购物车失败");
        }

        /**
         * 组装返回给前端的cartVo
         */
        return ServerResponse.createBySuccess(getCartVoLimit(userId));
    }


    @Override
    public ServerResponse<CartVo> updateCart(Integer productId, Integer count, Integer userId) {
        if (productId == null || count == null || count == 0) {
            return ServerResponse.createByErrorMessage("参数错误，无法添加");


        }
        Cart cartItem = new Cart();
        cartItem.setProductId(productId);
        cartItem.setUserId(userId);
        cartItem.setQuantity(count);


        int resultCount = cartMapper.upadteByUserIdAndProductId(cartItem);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("更新失败");

        }


        return ServerResponse.createBySuccess(getCartVoLimit(userId));
    }

    @Override
    public ServerResponse<CartVo> selectAll(Integer userId, Integer productId,int checked) {
        if (userId == null) {
            return ServerResponse.createByErrorMessage("失败");
        }
        int resultCount = cartMapper.selectALL(userId, productId,checked);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("失败");

        }


        return ServerResponse.createBySuccess(getCartVoLimit(userId));
    }

    @Override
    public ServerResponse<CartVo> delete_product(String productIds, Integer userId) {
        if (userId == null || StringUtils.isBlank(productIds)) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        String[] productStr = productIds.split(",");
        List<String> lists = new ArrayList<>();
        for (String item : productStr) {
            lists.add(item);
        }

        int resultCount = cartMapper.deleteProductByUserIdAndProductIds(userId, lists);
        if (resultCount == 0) {
            ServerResponse.createByErrorMessage("删除失败");
        }


        return ServerResponse.createBySuccess(getCartVoLimit(userId));
    }

    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        if (userId == null) {
            return ServerResponse.createByErrorMessage("获取购物车列表失败");
        }

        return ServerResponse.createBySuccess(getCartVoLimit(userId));

    }

    @Override
    public ServerResponse<Integer> getCartTotalProductNum(Integer userId) {

        int num = cartMapper.get_Cart_Product_Total_Num(userId);
        return ServerResponse.createBySuccess(num);
    }

    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    cartProductVo.setMain_image(product.getMainImage());
                    cartProductVo.setName(product.getName());
                    cartProductVo.setSubtitle(product.getSubtitle());
                    cartProductVo.setStatus(product.getStatus());
                    cartProductVo.setPrice(product.getPrice());
                    cartProductVo.setStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        //库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuntity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuntity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    cartProductVo.setChecked(cartItem.getChecked());
                }

                if (cartItem.getChecked() == Const.Check.CHECKED) {
                    //如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoLists(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return cartVo;
    }


    private boolean getAllCheckedStatus(Integer userId) {
        return cartMapper.getAllCheckedStatus(userId) == 0;

    }


}

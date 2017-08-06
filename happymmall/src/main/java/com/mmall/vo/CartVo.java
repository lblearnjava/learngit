package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lanbingdepingguo on 2017/7/30.
 */
public class CartVo {
    private List<CartProductVo> cartProductVoLists;
    private BigDecimal cartTotalPrice;

    private Boolean allChecked;//是否已经都勾选
    private String imageHost;

    public List<CartProductVo> getCartProductVoLists() {
        return cartProductVoLists;
    }

    public void setCartProductVoLists(List<CartProductVo> cartProductVoLists) {
        this.cartProductVoLists = cartProductVoLists;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}

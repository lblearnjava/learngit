package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.constant.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;
import com.sun.corba.se.spi.activation.Server;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

/**
 * Created by lanbingdepingguo on 2017/7/27.
 */
public interface IProductService {
    ServerResponse<String>  saveOrUpdate(Product product);
    ServerResponse<String> setSaleStatus(Integer productId,Integer status);
    ServerResponse<PageInfo> list(int pageNum, int pageSize);
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
    ServerResponse<PageInfo>  searchByIdAndName(Integer productId, String productName,Integer pageNum,Integer pageSize);
    ServerResponse<ProductDetailVo> getPortalProductDetail(Integer productId);
    ServerResponse<PageInfo> searchByCategoryIdAndProductName(Integer categoryId,String keyWord,Integer pageNum,Integer pageSize,String orderBy);
}

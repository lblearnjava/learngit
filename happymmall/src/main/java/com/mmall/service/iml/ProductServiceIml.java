package com.mmall.service.iml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.constant.Const;
import com.mmall.constant.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lanbingdepingguo on 2017/7/27.
 */

@Service("iProductService")
public class ProductServiceIml implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;

    @Override
    public ServerResponse<String> saveOrUpdate(Product product) {
        if (product == null) {
            return ServerResponse.createByErrorMessage("新增或更新品类参数错误");
        } else {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String subImages = product.getSubImages();
                String[] str = StringUtils.split(subImages, ",");
                if (str.length > 0) {
                    product.setMainImage(str[0]);
                }

                if (product.getId() != null) {
                    //说明为更新
                    int resutltCount = productMapper.updateByPrimaryKey(product);
                    if (resutltCount == 0) {
                        return ServerResponse.createByErrorMessage("更新品类失败");
                    }


                } else {

                    int resultCount = productMapper.insert(product);
                    if (resultCount == 0) {
                        return ServerResponse.createByErrorMessage("新增品类失败");
                    }


                }
            }

        }
        return ServerResponse.createBySuccessMessage("新增或更新品类成功");
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {


            return ServerResponse.createByErrorMessage("传递参数错误");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int resultCount = productMapper.updateByPrimaryKeySelective(product);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("修改状态失败");


        }
        return ServerResponse.createBySuccessMessage("修改状态成功！");


    }

    @Override
    public ServerResponse<PageInfo> list(int pageNum, int pageSize) {

        if (productMapper.list() == null || productMapper.list().isEmpty()) {

            return ServerResponse.createByErrorMessage("查找不到产品");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.list();

        PageInfo pageResult = new PageInfo(productList);

        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 前台查看商品详情的接口
     *
     * @param productId
     * @return
     */

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessage("参数传递错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {

            return ServerResponse.createByErrorMessage("该商品不存在");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);


        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse<ProductDetailVo> getPortalProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessage("参数传递错误");
        }
       Product product=productMapper.selectByPrimaryKey(productId);
       if(product==null){
           return  ServerResponse.createByErrorMessage("商品已删除或下架");

       }
       if(product.getStatus()==Const.Sale.NOT_OnSALE){
           return  ServerResponse.createByErrorMessage("商品已删除或下架");
       }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);


    }


    @Override
    public ServerResponse<PageInfo> searchByIdAndName(Integer productId, String productName, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(productName)) {
            StringBuffer sb = new StringBuffer();
            productName = sb.append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.searchByIdAndName(productId, productName);
        PageHelper.startPage(pageNum, pageSize);

        PageInfo pageResult = new PageInfo(productList);

        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> searchByCategoryIdAndProductName(Integer categoryId, String keyWord,
                                                                     Integer pageNum, Integer pageSize,

                                                                     String orderBy) {
        List<Integer> categoryIdList = new ArrayList<Integer>();
        if (categoryId != null) {
            categoryIdList = iCategoryService.getDeepCategory(categoryId).getData();
        }

        if (StringUtils.isNotBlank(keyWord)) {
            StringBuilder sb = new StringBuilder();
            keyWord = sb.append("%").append(keyWord).append("%").toString();
        }
        PageHelper.startPage(pageNum, pageSize);

        PageHelper.orderBy("price desc");


        List<Product> productList = productMapper.selectByCategoryIdAndProductName(categoryIdList.size() == 0 ? null : categoryIdList,StringUtils.isBlank(keyWord) ? null : keyWord);
        PageInfo pageInfo = new PageInfo(productList);

        return ServerResponse.createBySuccess(pageInfo);

    }


    public ProductDetailVo assembleProductDetailVo(Product product) {
        if (product == null) {
            return null;
        }
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            ServerResponse.createByErrorMessage("找不到该产品的分类");
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setParentCategoryId(category.getParentId());
        productDetailVo.setId(product.getId());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setName(product.getName());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setCreateTime(product.getCreateTime());
        productDetailVo.setUpdateTime(product.getUpdateTime());
        productDetailVo.setImageHost("http://happymmall/");


        return productDetailVo;
    }


}

package com.mmall.service;

import com.mmall.constant.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by lanbingdepingguo on 2017/7/26.
 */
public interface ICategoryService {
    ServerResponse<List<Category>>  selectPrallelChilderenByCategoryId(Integer categoryId);
      ServerResponse<String>   addCategory(Integer parentId,String categoryName);
      ServerResponse<String>  setCategoryName(Category category);
      ServerResponse<List<Integer>> getDeepCategory(Integer categoryId);


}

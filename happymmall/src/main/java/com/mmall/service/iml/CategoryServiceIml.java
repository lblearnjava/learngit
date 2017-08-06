package com.mmall.service.iml;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.constant.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lanbingdepingguo on 2017/7/26.
 */
@Service("iCategoryService")
public class CategoryServiceIml implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public ServerResponse<List<Category>> selectPrallelChilderenByCategoryId(Integer categoryId) {
        if(categoryId==null){
          return  ServerResponse.createByErrorMessage("获取父节点失败");

        }
        List<Category>  categoryList=categoryMapper.selectPrallelChilderenByCategoryId(categoryId);
        if(categoryList.isEmpty()){
            return  ServerResponse.createByErrorMessage("该品类没有子节点");
        }
        return  ServerResponse.createBySuccess(categoryList);

    }

    @Override
    public ServerResponse<String> addCategory(Integer parentId,String categoryName ) {
        if(StringUtils.isBlank(categoryName)||parentId==null){
            return  ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category=new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true);
        int resutCount=categoryMapper.insert(category);
        if(resutCount==0){
            return  ServerResponse.createByErrorMessage("添加品类失败");

        }
        return ServerResponse.createBySuccessMessage("添加品类成功");
    }

    @Override
    public ServerResponse<String> setCategoryName(Category category) {
        if(category.getId()==null){
            return  ServerResponse.createByErrorMessage("未传入categoryid,无法更新");

        }
        if(StringUtils.isBlank(category.getName())){
            return  ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        int resultCount=categoryMapper.updateByPrimaryKeySelective(category);
        if(resultCount==0){
         return  ServerResponse.createBySuccessMessage("更新品类失败");
        }

        return ServerResponse.createBySuccessMessage("更新品类成功");
    }

    @Override
    public ServerResponse<List<Integer>> getDeepCategory(Integer categoryId) {
        Set<Category> categorySet= Sets.newHashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer> categoryIdList= Lists.newArrayList();
        if(categoryId!=null){
            for(Category catergoryItem:categorySet){
                categoryIdList.add(catergoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }


    public  Set<Category>  findChildCategory(Set<Category>  categorySet,Integer categoryId) {
        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null){
        categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectPrallelChilderenByCategoryId(categoryId);
        for (Category categoryItem : categoryList) {
           findChildCategory(categorySet,categoryItem.getId());
        }
        return  categorySet;
    }













































}

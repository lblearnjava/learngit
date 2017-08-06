package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.constant.Const;
import com.mmall.constant.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.vo.ProductDetailVo;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lanbingdepingguo on 2017/7/28.
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IUserService iUserService;

    /**
     * toDo  还有问题，后面处理
     *
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping(value = "detail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ProductDetailVo> getProductDetail(@RequestParam(value = "productId") Integer productId, HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }


        return iProductService.getPortalProductDetail(productId);
    }


    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = " orderBy", defaultValue = "") String orderBy, HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆，请先登陆");

        }
        if (!iUserService.checkAdmin(currentUser)) {
            return ServerResponse.createByErrorMessage("不是管理员用户，请用管理员账号登陆");
        }
        return iProductService.searchByCategoryIdAndProductName(categoryId, keyword, pageNum, pageSize, orderBy);


    }


}

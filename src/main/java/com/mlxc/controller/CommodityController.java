package com.mlxc.controller;

import com.mlxc.entity.Comment;
import com.mlxc.entity.Commodity;
import com.mlxc.entity.User;
import com.mlxc.service.CommentService;
import com.mlxc.service.CommodityService;
import com.mlxc.utils.ResultModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private HttpSession session;

    @GetMapping("/get-commodities")
    public ResultModel getCommoditys() {
        ResultModel resultModel = new ResultModel();

        List<Commodity> commodityList = commodityService.getCommodities();


        resultModel.setErrcode(1);
        resultModel.setErrmsg("获取成功");
        resultModel.setData(commodityList);

        return resultModel;
    }

    @PostMapping("/remove-commodity")
    public ResultModel removeCommodity(long commodityId) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if (user.getUserType() == 0) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("普通用户无法下架商品");
            return resultModel;
        }

        commodityService.removeCommodity(commodityId);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("删除成功");

        return resultModel;
    }

    @GetMapping("/get-commodities-admin")
    public ResultModel getCommoditiesAdmin() {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if (user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");
            return resultModel;
        }
        List<Commodity> commodityList = null;
        if (user.getUserType() == 2) {
            commodityList = commodityService.getCommodities();
        } else if (user.getUserType() == 1) {
            commodityList = commodityService.getCommoditiesByUserId(user.getUserId());
        }

        resultModel.setErrcode(1);
        resultModel.setErrmsg("获取成功");
        resultModel.setData(commodityList);


        return resultModel;
    }

    @GetMapping("/get-commodities-bussiness")
    public ResultModel getCommoditiesByBussiness() {
        ResultModel resultModel = new ResultModel();


        User user = getCurrentUser();
        if (user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");
            return resultModel;
        }
        if (user.getUserType() == 0) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前账户类型为普通用户");
            return resultModel;
        }

        List<Commodity> commodityList;
        if (user.getUserType() == 2) {
            commodityList = commodityService.getCommodities();
        } else {
            commodityList = commodityService.getCommoditiesByUserId(user.getUserId());
        }

        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");
        resultModel.setData(commodityList);

        return resultModel;
    }

    @GetMapping("/get-comments")
    public ResultModel getComments(long commodityId) {
        ResultModel resultModel = new ResultModel();

        List<Comment> commentList = commentService.getCommentsByCommodityId(commodityId);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");
        resultModel.setData(commentList);

        return resultModel;
    }

    @GetMapping("/commodity-info")
    public ResultModel getCommodityInfo(long commodityId) {
        ResultModel resultModel = new ResultModel();

        Commodity commodity = commodityService.getCommodityById(commodityId);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");
        resultModel.setData(commodity);

        return resultModel;
    }



//    @PostMapping("/add-commodity")
//    public ResultModel addCommodity(Commodity commodity) {
//        ResultModel resultModel = new ResultModel();
//
//        User user = getCurrentUser();
//        if(user == null) {
//            resultModel.setErrcode(0);
//            resultModel.setErrmsg("当前用户未登录");
//
//            return resultModel;
//        }
//
//        commodity.setUserid(user.getUserId());
//        commodityService.addCommodity(commodity);
//
//        resultModel.setErrcode(1);
//        resultModel.setErrmsg("添加成功");
//
//        return resultModel;
//    }

    private User getCurrentUser() {
        Object user =  session.getAttribute("user");

        if(user == null) {
            return null;
        }

        if(user instanceof User) {
            return (User) user;
        }

        return null;
    }

    public CommodityService getCommodityService() {
        return commodityService;
    }

    public void setCommodityService(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
}

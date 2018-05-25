package com.mlxc.controller;

import com.mlxc.entity.Activity;
import com.mlxc.entity.Comment;
import com.mlxc.entity.Commodity;
import com.mlxc.entity.User;
import com.mlxc.service.ActivityService;
import com.mlxc.service.CommodityService;
import com.mlxc.service.UserService;
import com.mlxc.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class FormController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext servletContext;

    @PostMapping("/add-commodity")
    public String addCommodity(HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();

        String bussinessName = request.getParameter("bussinessname");
        double bussinessPrice = Double.parseDouble(request.getParameter("bussinessprice"));
        int count = Integer.parseInt(request.getParameter("bussinesscount"));
        String des = request.getParameter("des");
        String imageURL = null;

        try {
            imageURL = uploadImage(request, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Commodity commodity = new Commodity();
        commodity.setCommodityName(bussinessName);
        commodity.setPrice(bussinessPrice);
        commodity.setRest(count);
        commodity.setDes(des);
        commodity.setImageURL(imageURL);
        commodity.setUserid(user.getUserId());


        commodityService.addCommodity(commodity);

        return "personal-center";
    }

    @PostMapping("/add-comment")
    public String addComment(HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile) {
        Comment comment = new Comment();

        User user = getCurrentUser();
        String content = request.getParameter("content");
        long commodityId = Long.parseLong(request.getParameter("commodityId"));
        String imageURL = null;

        try {
            imageURL = uploadImage(request, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        comment.setCommentTime(new Date().getTime());
        comment.setCommodityId(commodityId);
        comment.setContent(content);
        comment.setUserId(user.getUserId());
        comment.setUserName(user.getUserName());
        comment.setImageURL(imageURL);

        userService.addComent(comment);


        return "personal-center";
    }

    @PostMapping("/save-activity")
    public String addActivity(HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile) {
        ResultModel resultModel = new ResultModel();

        String activityName = request.getParameter("activityname");
        int activitylimit = Integer.parseInt(request.getParameter("activitylimit"));
        String activityDate = request.getParameter("activitydate");
        String activityTime = request.getParameter("activitytime");
        String des = request.getParameter("des");
        String imageURL = null;

        String timeStr = activityDate+" "+activityTime;

        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
        Date time = null;
        try {
            time = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            imageURL = uploadImage(request, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Activity activity = new Activity();
        activity.setActivityName(activityName);
        activity.setNumLimit(activitylimit);
        activity.setDes(des);
        activity.setImageURL(imageURL);
        activity.setStartTime(time.getTime());
        activity.setCreateTime(new Date().getTime());

        activityService.addActivity(activity);


        return "personal-center";
    }


    @GetMapping("/logout")
    public String logout() {
        ResultModel resultModel = new ResultModel();

        session.removeAttribute("user");

        resultModel.setErrcode(1);
        resultModel.setErrmsg("注销成功");

        return "index";
    }

    private String uploadImage(HttpServletRequest request, MultipartFile multipartFile) throws IOException {
        String imagePath = "images";
        File uploadDir = new File(imagePath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        if (multipartFile != null && multipartFile.getContentType().startsWith("image")) {
            String imageName = String.valueOf(new Date().getTime())+".png"; //获取原文件名
            InputStream inputStream = multipartFile.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(imagePath+"/"+imageName);
            byte[] buf = new byte[1024];
            int len = 0;

            while ((len = inputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
                fileOutputStream.flush();
            }

            inputStream.close();
            fileOutputStream.close();

            String serverName = request.getServerName();
            int port = request.getServerPort();
            String imageURL = "/images/"+imageName;

            return imageURL;
        }

        return "";
    }

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

}

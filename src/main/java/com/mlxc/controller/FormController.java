package com.mlxc.controller;

import com.mlxc.entity.Commodity;
import com.mlxc.entity.User;
import com.mlxc.service.CommodityService;
import com.mlxc.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Controller
public class FormController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private HttpSession session;

    @PostMapping("/add-commodity")
    public String addCommodity(HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile) {
        ResultModel resultModel = new ResultModel();

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


        commodityService.addCommodity(commodity);

        return "personal-center";
    }

    private String uploadImage(HttpServletRequest request, MultipartFile multipartFile) throws IOException {
        String imagePath = request.getServletContext().getRealPath("/bussinessimage");
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
            String imageURL = "http://"+serverName+":"+port+"/bussinessimage/"+imageName;

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

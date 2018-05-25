package com.mlxc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Value("${imagePath}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/image/**").addResourceLocations("file:C:/工作空间/Projects/x-space/image/");

//
//        String path = WebAppConfig.class.getResource("/").toString();
//        path = path.substring(0, path.lastIndexOf("bvson")) + "images/";
//        path = path.replace("jar:","");
//        try {
//            path = URLDecoder.decode(path,"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        File pathFile = new File("images");
//        if (!pathFile.exists()){
//            pathFile.mkdirs();
//        }else if(pathFile.isFile()){
//            pathFile.delete();
//            pathFile.mkdirs();
//        }

        registry.addResourceHandler("/images/**").addResourceLocations(imagePath);
//        registry.addResourceHandler("/image/**").addResourceLocations(path);
        super.addResourceHandlers(registry);
    }



}

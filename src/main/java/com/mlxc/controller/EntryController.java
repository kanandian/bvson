package com.mlxc.controller;

import groovy.lang.GrabExclude;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntryController {

    @GetMapping("/")
    public String entry() {
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/common-header")
    public String commonHeader() {
        return "common-header";
    }

    @GetMapping("/common-footer")
    public String commonFooter() {
        return "common-footer";
    }

    @GetMapping("/personal-center")
    public String personalCenter() {
        return "personal-center";
    }

    @GetMapping("/buy-bussiness")
    public String buyBussiness() {
        return "buy-bussiness";
    }

    @GetMapping("/add-commodity")
    public String addCommodity() {
        return "add-commodity";
    }

    @GetMapping("/commodity-details")
    public String commodityDetails() {
        return "commodity-details";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart() {
        return "shopping-cart";
    }

    @GetMapping("/attend-activity")
    public String attendActivity() {
        return "attend-activity";
    }

    @GetMapping("/add-activity")
    public String addActivity() {
        return "add-activity";
    }

    @GetMapping("/activity-details")
    public String activityDetails() {
        return "activity-details";
    }

    @GetMapping("/commodity-admin")
    public String commodityAdmin() {
        return "commodity-admin";
    }

    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }

    @GetMapping("/my-activity")
    public String myActivity() {
        return "my-activity";
    }

    @GetMapping("/attend-info")
    public String attendInfo() {
        return "attend-info";
    }

}

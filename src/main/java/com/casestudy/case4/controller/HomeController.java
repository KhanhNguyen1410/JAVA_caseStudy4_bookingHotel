package com.casestudy.case4.controller;

import com.casestudy.case4.model.Hotel;
import com.casestudy.case4.model.User;
import com.casestudy.case4.model.UserPrinciple;
import com.casestudy.case4.service.hotel.IHotelService;
import com.casestudy.case4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IHotelService iHotelService;

    @ModelAttribute("userCurrent")
    private User getPrincipal() {
        User userCurrent = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            userCurrent = iUserService.findByUserName(((UserDetails)principal).getUsername());
        }
        return userCurrent;
    }

    @ModelAttribute("list")
    public  Page<Hotel> homePageUser(Pageable pageable){
       return iHotelService.findAllByStatusIsFalse(pageable);
    }

    @GetMapping("/user")
    public String userPage(Model model) {
//        model.addAttribute("userCurrent", getPrincipal());
        Long PROVINCE_HANOI = (long) 1;
        Long PROVINCE_DN = (long) 3;
        Long PROVINCE_HT = (long) 4;
        model.addAttribute("province_hn", PROVINCE_HANOI);
        model.addAttribute("province_dn", PROVINCE_DN);
        model.addAttribute("province_ht", PROVINCE_HT);
        return "user";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        User userCurrent = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userCurrent = iUserService.findByUserName(((UserDetails)principal).getUsername());
        model.addAttribute("userCurrent", userCurrent);
        return "admin";
    }

    @GetMapping("/logout")
    public String logoutPage(Model model) {
        model.addAttribute("message", "Đăng xuất thành công");
        return "logout";
    }

    @GetMapping("/")
    public String homePage(Model model, Pageable pageable) {
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "access-denied";
    }
}

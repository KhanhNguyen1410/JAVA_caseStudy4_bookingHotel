package com.casestudy.case4.controller;

import com.casestudy.case4.model.Hotel;
import com.casestudy.case4.model.Role;
import com.casestudy.case4.model.User;
import com.casestudy.case4.model.UserPrinciple;
import com.casestudy.case4.service.hotel.IHotelService;
import com.casestudy.case4.service.type_room.ITypeRoomService;
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
    @Autowired
    private ITypeRoomService iTypeRoomService;

    @ModelAttribute("userCurrent")
    private User getPrincipal() {
        User userCurrent = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            userCurrent = iUserService.findByUserName(((UserDetails)principal).getUsername());
        }
        return userCurrent;
    }
//    @ModelAttribute("isAdmin")
//    public boolean checkAdmin(){
//        boolean isAdmin = false;
//        for (Role role: getPrincipal().getRoles()){
//            if (role.getName().equals("ROLE_ADMIN")){
//                isAdmin = true;
//            }
//        }
//        return isAdmin;
//    }

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

    @GetMapping("/home")
    public ModelAndView homePage(Pageable pageable){
        Page<Hotel> list= iHotelService.findAllByStatusIsFalse(pageable);
        ModelAndView modelAndView= new ModelAndView("home");
        boolean isAdmin = false;
        for (Role role: getPrincipal().getRoles()){
            if (role.getName().equals("ROLE_ADMIN")){
                isAdmin = true;
            }
        }
        modelAndView.addObject("list", list);
        modelAndView.addObject("isAdmin",isAdmin);
//        modelAndView.addObject("userCurrent",getPrincipal());
        return modelAndView;
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

    @GetMapping("/logoutSuccessfully")
    public String logoutPage(Model model) {
        model.addAttribute("message", "Đăng xuất thành công");
        return "logout";
    }
    @GetMapping("/test")
    public String logoutPa1ge(Model model) {
        model.addAttribute("message", "Đăng xuất thành công");
        return "test";
    }

    @GetMapping("/")
    public String homePage(Model model, Pageable pageable) {
        model.addAttribute("user", new User());
        return "home";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "access-denied";
    }
}

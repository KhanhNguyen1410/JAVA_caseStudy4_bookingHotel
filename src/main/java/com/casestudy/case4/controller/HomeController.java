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
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IHotelService iHotelService;
    @Autowired
    private ITypeRoomService iTypeRoomService;

    @ModelAttribute("isAdmin")
    public boolean checkAdmin() {
        boolean isAdmin = false;
        if (getPrincipal() != null) {
            for (Role role : getPrincipal().getRoles()) {
                if (role.getName().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                }
            }
        }
        return isAdmin;
    }

    @ModelAttribute("userCurrent")
    public User getPrincipal() {
        User userCurrent = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            return null;
        }
        UserPrinciple userPrinciple = (UserPrinciple) principal;
        userCurrent = iUserService.findByUserName(userPrinciple.getUsername());
        return userCurrent;
    }
    @ModelAttribute("list")
    public  Page<Hotel> homePageUser(Pageable pageable){
       return iHotelService.findAllByStatusIsFalse(pageable);
    }

    @GetMapping("/user")
    public String userPage(Model model) {
        Long PROVINCE_HANOI = (long) 1;
        Long PROVINCE_HCM = (long) 2;
        Long PROVINCE_DN = (long) 3;
        Long PROVINCE_HT = (long) 4;
        model.addAttribute("province_hn", PROVINCE_HANOI);
        model.addAttribute("province_HCM", PROVINCE_HCM);
        model.addAttribute("province_dn", PROVINCE_DN);
        model.addAttribute("province_ht", PROVINCE_HT);
        return "user";
    }

    @GetMapping("/home")
    public ModelAndView homePage(@PageableDefault(6) Pageable pageable){
        Page<Hotel> list= iHotelService.findAllByStatusIsFalse(pageable);
        Long PROVINCE_HN = 1L;
        Long PROVINCE_HCM = 2L;
        Long PROVINCE_DN = 3L;
        Long PROVINCE_HT = 4L;
        ModelAndView modelAndView= new ModelAndView("home");
        modelAndView.addObject("list", list);
        modelAndView.addObject("user", new User());
        modelAndView.addObject("province_HN", PROVINCE_HN);
        modelAndView.addObject("province_HCM", PROVINCE_HCM);
        modelAndView.addObject("province_DN", PROVINCE_DN);
        modelAndView.addObject("province_HT", PROVINCE_HT);
        return modelAndView;
    }
    @GetMapping("/")
    public ModelAndView homePage2(@PageableDefault(6) Pageable pageable){
        Page<Hotel> list= iHotelService.findAllByStatusIsFalse(pageable);
        Long PROVINCE_HN = 1L;
        Long PROVINCE_HCM = 2L;
        Long PROVINCE_DN = 3L;
        Long PROVINCE_HT = 4L;
        ModelAndView modelAndView= new ModelAndView("home");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("list", list);
        modelAndView.addObject("province_HN", PROVINCE_HN);
        modelAndView.addObject("province_HCM", PROVINCE_HCM);
        modelAndView.addObject("province_DN", PROVINCE_DN);
        modelAndView.addObject("province_HT", PROVINCE_HT);
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

    @GetMapping("/logoutSuccessful")
  public RedirectView  logoutPage() {
        return new RedirectView("/home");
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model) {
        model.addAttribute("message","Truy cập không khả dụng");
        return "home";
    }
}

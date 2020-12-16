package com.casestudy.case4.controller;

import com.casestudy.case4.model.Orders;
import com.casestudy.case4.model.Role;
import com.casestudy.case4.model.User;
import com.casestudy.case4.model.UserPrinciple;
import com.casestudy.case4.service.hotel.IHotelService;
import com.casestudy.case4.service.orders.IOrdersService;
import com.casestudy.case4.service.province.IProvinceService;
import com.casestudy.case4.service.type_room.ITypeRoomService;
import com.casestudy.case4.service.user.IUserService;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping(value = "/admin")
public class OrdersController {
    @Autowired
    private IHotelService hotelService;

    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IUserService userService;
    @Autowired
    private ITypeRoomService iTypeRoomService;
    @Autowired
    private IOrdersService iOrdersService;

//    @ModelAttribute("waitingList")
//    public Page<Orders> allOrders(Pageable pageable){
//        return iOrdersService.findAllByStatusIsFalse(pageable);
//    }
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
    @PostMapping("/list-orders")
    public ModelAndView listOrders(@RequestParam Long id , Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("orders/list");
        Page<Orders> orders ;
    if (checkAdmin()){
         orders = iOrdersService.findAllByStatusIsFalse(pageable);
    }
    else {
        orders = iOrdersService.findAllByUserId(getPrincipal().getId(), pageable);
    }
        modelAndView.addObject("orders", orders);
        return  modelAndView;
    }
    @PostMapping("/list-orders-done")
    public ModelAndView listOrdersDone(@RequestParam Long id,Pageable pageable){
        Page<Orders> orders ;
        ModelAndView modelAndView = new ModelAndView("orders/list");
        if (checkAdmin()){
            orders = iOrdersService.findAllByStatusIsTrue(pageable);
        }
        else {
            orders = iOrdersService.findAllByStatusIsTrueAndUserId(getPrincipal().getId(), pageable);
        }
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }
    @PostMapping("/list-orders-slacking")
    public ModelAndView listOrdersSlacking(@RequestParam Long id,Pageable pageable){
        Page<Orders> orders = iOrdersService.findAllByStatusIsTrue(pageable);
        ModelAndView modelAndView = new ModelAndView("orders/list");
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }
}

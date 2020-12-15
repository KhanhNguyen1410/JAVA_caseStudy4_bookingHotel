package com.casestudy.case4.controller;

import com.casestudy.case4.model.*;
import com.casestudy.case4.repository.orders.IOrdersRepository;
import com.casestudy.case4.service.orderdetails.IOrderDetailsService;
import com.casestudy.case4.service.orders.IOrdersService;
import com.casestudy.case4.service.room.IRoomService;
import com.casestudy.case4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Controller
public class OrderDetailController {
    @Autowired
    private IOrdersService iOrdersService;
    @Autowired
    private IOrderDetailsService iOrderDetailsService;
    @Autowired
    private IRoomService iRoomService;
    @Autowired
    private IUserService iUserService;

    @ModelAttribute("userCurrent")
    private User getPrincipal(){
        User userCurrent = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userCurrent = iUserService.findByUserName(((UserDetails)principal).getUsername());
        return userCurrent;
    }
    @GetMapping("/user/create-booking/{id}")
    public ModelAndView booking(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/orders/booking");
        modelAndView.addObject("orderDetailsForm", new OrderDetails());
//        Orders orders = iOrdersService.save(new Orders());
        Orders orders = new Orders();
//        Orders ordersCurrent = iOrdersService.save(orders);
//        modelAndView.addObject("orderCurrent",ordersCurrent);
        modelAndView.addObject("orderCurrent",orders);
        modelAndView.addObject("id_room", id);
        return modelAndView;
    }
    @PostMapping("/create-booking")
    public ModelAndView bookingSuccess(@ModelAttribute OrderDetailForm orderDetailForm, @RequestParam("id_room") Long id_room, @ModelAttribute Orders orderCurrent ){
//        Calendar calendar = Calendar.getInstance();
        LocalDate date = LocalDate.now();
        Orders orders = new Orders(orderCurrent.getId(),date,orderCurrent.isStatus(),getPrincipal());
        iOrdersService.save(orders);
        Room room= iRoomService.findAllById(id_room);
        int DISCOUNT = 1;
        OrderDetails orderDetails1 = new OrderDetails(orderDetailForm.getId(),orders,room,DISCOUNT,orderDetailForm.getCheckIn(),orderDetailForm.getCheckOut());
        iOrderDetailsService.save(orderDetails1);
        ModelAndView modelAndView = new ModelAndView("/orders/orders-success");
        modelAndView.addObject("orderDetails",orderDetails1);
        Long time = ChronoUnit.DAYS.between(orderDetails1.getCheckIn(), orderDetails1.getCheckOut());
        Double total = time*orderDetails1.getRoom().getPrice();
        modelAndView.addObject("total",total);
        return modelAndView;

    }
}

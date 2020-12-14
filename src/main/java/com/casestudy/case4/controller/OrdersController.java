package com.casestudy.case4.controller;

import com.casestudy.case4.model.Orders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping(value = "/admin")
public class OrdersController {
    @GetMapping("/list-orders")
    public ModelAndView listOrders(){
        ModelAndView modelAndView = new ModelAndView("orders/list");
        return  modelAndView;
    }
    @PostMapping("/create-orders")
    public ModelAndView createOrders(@RequestParam Orders orders){
        ModelAndView modelAndView = new ModelAndView("orders/orders-success");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return modelAndView;
    }
}

package com.casestudy.case4.controller;

import com.casestudy.case4.model.*;
import com.casestudy.case4.service.hotel.IHotelService;
import com.casestudy.case4.service.province.IProvinceService;
import com.casestudy.case4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IUserService userService;

//    @Value("${upload.path}")
    @Value("${upload.path}")
    String fileUpload;
    @ModelAttribute("userCurrent")
    private User getPrincipal(){
        User userCurrent = null;
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrinciple userPrinciple = (UserPrinciple) principal;
        userCurrent = iUserService.findByUserName(userPrinciple.getUsername());
        return userCurrent;
    }

    @ModelAttribute("users")
    public Iterable<User> users(){
        return userService.findAll();
    }
    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("/admin/list-hotel")
    public ModelAndView hotelListAdmin(Pageable pageable){
        Page<Hotel> list= hotelService.findAllByStatusIsFalse(pageable);
        ModelAndView modelAndView= new ModelAndView("hotel/list");
        modelAndView.addObject("list", list);
        modelAndView.addObject("userCurrent",getPrincipal());
        return modelAndView;
    }
    @GetMapping(value = "/user/list-hotel")
    public ModelAndView hotelListUser(Pageable pageable){
        Page<Hotel> list= hotelService.findAllByStatusIsFalse(pageable);
        ModelAndView modelAndView= new ModelAndView("hotel/list");
        modelAndView.addObject("list", list);
        modelAndView.addObject("userCurrent",getPrincipal());
        return modelAndView;
    }
    @GetMapping("/home")
    public ModelAndView homePage(Pageable pageable){
        Page<Hotel> list= hotelService.findAllByStatusIsFalse(pageable);
        ModelAndView modelAndView= new ModelAndView("index");
        modelAndView.addObject("list", list);
        modelAndView.addObject("userCurrent",getPrincipal());
        return modelAndView;
    }

    @GetMapping("/admin/create-hotel")
    public ModelAndView showCreateFormAdmin() {
        ModelAndView modelAndView = new ModelAndView("/hotel/create");
        modelAndView.addObject("hotelForm", new HotelForm());
        return modelAndView;
    }
    @GetMapping("/user/create-hotel")
    public ModelAndView showFormCreateUser(){
        ModelAndView modelAndView= new ModelAndView("hotel/create");
        modelAndView.addObject("hotelForm", new HotelForm());
        return modelAndView;
    }
    @PostMapping("/create-hotel")
    public RedirectView saveBook(@ModelAttribute HotelForm hotelForm, Model model) {
        Hotel hotel = new Hotel(hotelForm.getId(),hotelForm.getName(), hotelForm.getAddressDetails(), hotelForm.getHotline() , hotelForm.getDescription()
        , hotelForm.isStatus(), hotelForm.getProvince(), hotelForm.getUser());
        MultipartFile multipartFile = hotelForm.getCoveImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(hotelForm.getCoveImage().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hotel.setImage(fileName);
        User userCurrent = getPrincipal();
        hotel.setUser(userCurrent);
        hotelService.save(hotel);
        model.addAttribute("hotelForm", new HotelForm());
        return new RedirectView("/home");
    }

    //    public ModelAndView saveHotel(@ModelAttribute Hotel hotel){
//        hotelService.save(hotel);
//        ModelAndView modelAndView= new ModelAndView("hotel/create");
//        modelAndView.addObject("hotel", new Hotel());
//        modelAndView.addObject("message","Save Hotel Successful!!!");
//        return modelAndView;
//    }
    @GetMapping("/admin/edit-hotel/{id}")
    public ModelAndView showFormEdit(@PathVariable Long id){
        Optional<Hotel> hotel=hotelService.findById(id);
        ModelAndView modelAndView= new ModelAndView("hotel/edit");
        modelAndView.addObject("hotel",hotel.get());
        return modelAndView;
    }
    @PostMapping("/admin/edit-hotel")
    public ModelAndView updateHotel(@ModelAttribute Hotel hotel){
        hotelService.save(hotel);
        ModelAndView modelAndView= new ModelAndView("hotel/edit");
        modelAndView.addObject("hotel",new Hotel());
        modelAndView.addObject("message","Update Hotel Successfully!!!");
        return modelAndView;
    }
    @GetMapping("/admin/delete-hotel/{id}")
    public ModelAndView disable(@PathVariable Long id, Pageable pageable){
        Page<Hotel> hotels = hotelService.findAllByStatusIsFalse(pageable);
        hotelService.remove(id);
        ModelAndView modelAndView= new ModelAndView("hotel/list");
        modelAndView.addObject("list",hotels);
        return modelAndView;
    }
}

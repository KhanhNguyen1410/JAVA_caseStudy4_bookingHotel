package com.casestudy.case4.controller;

import com.casestudy.case4.model.Role;
import com.casestudy.case4.model.User;
import com.casestudy.case4.model.UserPrinciple;
import com.casestudy.case4.service.role.IRoleService;
import com.casestudy.case4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;


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

    @ModelAttribute("listRole")
    public Iterable<Role> listRole() {
        return iRoleService.findAll();
    }

    @GetMapping("/list-user")
    public ModelAndView listUser(){

        ModelAndView modelAndView= new ModelAndView("admin/listUser");
        Iterable<User> users= iUserService.findAll();
        modelAndView.addObject("users", users);
        modelAndView.addObject("userCurrent", getPrincipal());
        return modelAndView;
    }

    @GetMapping("/create-user")
    public ModelAndView showListForm() {
        ModelAndView modelAndView = new ModelAndView("user/CreateUserTh");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/create-user")
    public ModelAndView saveCustomer(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("user/CreateUserTh");
        if (!bindingResult.hasFieldErrors()) {
            if (user.getRoles()== null){
                Role role = iRoleService.findRoleByName("ROLE_USER");
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                user.setRoles(roles);
            }
            user.setPassWord(passwordEncoder.encode(user.getPassWord()));
            iUserService.save(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("message", "New user created successfully");
        }
        return modelAndView;
    }
    @GetMapping("/list-userAble")
    public ModelAndView listUserAble(Pageable pageable){
        ModelAndView modelAndView= new ModelAndView("admin/listUser");
        Page<User> users= iUserService.findAllByStatusIsFalse(pageable);
        modelAndView.addObject("users", users);
        modelAndView.addObject("userCurrent", getPrincipal());
        return modelAndView;
    }
    @GetMapping("/list-userDisable")
    public ModelAndView listUserDisable(Pageable pageable){
        ModelAndView modelAndView= new ModelAndView("admin/listUser");
        Page<User> users= iUserService.findAllByStatusIsTrue(pageable);
        modelAndView.addObject("users", users);
        modelAndView.addObject("userCurrent", getPrincipal());
        return modelAndView;
    }

}

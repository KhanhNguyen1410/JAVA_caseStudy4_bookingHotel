package com.casestudy.case4.controller;

import com.casestudy.case4.model.Role;
import com.casestudy.case4.model.User;
import com.casestudy.case4.model.UserPrinciple;
import com.casestudy.case4.service.role.IRoleService;
import com.casestudy.case4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sun.security.util.Password;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @ModelAttribute("listRole")
    public Iterable<Role> listRole() {
        return iRoleService.findAll();
    }

    @ModelAttribute("isAdmin")
    public boolean checkAdmin(){
        boolean isAdmin = false;
        for (Role role: getPrincipal().getRoles()){
            if (role.getName().equals("ROLE_ADMIN")){
                isAdmin = true;
            }
        }
        return isAdmin;
    }


    public User getPrincipal(){
        User userCurrent = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrinciple userPrinciple = (UserPrinciple) principal;
        userCurrent = iUserService.findByUserName(userPrinciple.getUsername());
        return userCurrent;
    }

    //    @RequestMapping(value = "/createNewUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public User createUserName(@RequestBody User user) {
//        Role role = roleService.findRoleByName("ROLE_USER");
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        user.setRoles(roles);
//        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
//        return userService.save(user);
//    }
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
            Role role = iRoleService.findRoleByName("ROLE_USER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            user.setPassWord(passwordEncoder.encode(user.getPassWord()));
            iUserService.save(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("message", "New user created successfully");
        }
        return modelAndView;
    }
    @PostMapping("/user/edit-user/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<User> user= iUserService.findById(id);
        ModelAndView modelAndView= new ModelAndView("user/edit");
        modelAndView.addObject("user", user.get());
        modelAndView.addObject("userCurrent",getPrincipal());
         return modelAndView;
    }
    @GetMapping("/admin/edit-user/{id}")
    public ModelAndView showEditFormAdmin(@PathVariable Long id){
        Optional<User> user= iUserService.findById(id);
        boolean isAdmin = false;
        for (Role role: getPrincipal().getRoles()){
            if (role.getName().equals("ROLE_ADMIN")){
                isAdmin = true;
            }
        }
        ModelAndView modelAndView= new ModelAndView("user/edit");
        modelAndView.addObject("user", user.get());
        modelAndView.addObject("isAdmin", isAdmin);
        modelAndView.addObject("userCurrent",getPrincipal());
        return modelAndView;
    }
    @PostMapping("/edit-user")
    public ModelAndView updateUser(@RequestParam String password, @Valid @ModelAttribute User user, BindingResult bindingResult){
        if(password != "" || password == null) {
            user.setPassWord(passwordEncoder.encode(password));
        }
        iUserService.save(user);
        ModelAndView modelAndView= new ModelAndView("user/edit");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("message", "Updated USER successful!!!");
        return modelAndView;
    }
    @GetMapping("/admin/delete-user/{id}")
    public RedirectView deleteUser(@PathVariable Long id ,@PageableDefault(14) Pageable pageable){
        iUserService.remove(id);
//        Iterable<User> list= iUserService.findAll();
        Page<User> list = iUserService.findAllUserPage(pageable);
        ModelAndView modelAndView= new ModelAndView("admin/listUser");
        modelAndView.addObject("list", list);
        return new RedirectView("/admin/list-user/");
    }
    @GetMapping("/admin/enable-user/{id}")
    public RedirectView enableUser(@PathVariable Long id ,@PageableDefault(14) Pageable pageable){
        iUserService.enable(id);
        Page<User> list = iUserService.findAllUserPage(pageable);
        ModelAndView modelAndView= new ModelAndView("admin/listUser");
        modelAndView.addObject("list", list);
        return new RedirectView("/admin/list-user/");
    }

}

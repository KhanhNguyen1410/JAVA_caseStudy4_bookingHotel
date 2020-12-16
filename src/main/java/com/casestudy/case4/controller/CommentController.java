package com.casestudy.case4.controller;

import com.casestudy.case4.model.*;
import com.casestudy.case4.service.comment.ICommentService;
import com.casestudy.case4.service.hotel.IHotelService;
import com.casestudy.case4.service.role.IRoleService;
import com.casestudy.case4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommentController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IHotelService iHotelService;
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

    @RequestMapping(value = "/create-comment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Comment deleteSmartphone(@RequestBody CommentForm commentForm){
        Hotel hotel= iHotelService.findAllById(commentForm.getHotel_id());
//        User user = iUserService.findAllById(commentForm.getUser_id());
        User user= getPrincipal();
        Comment comment = new Comment(commentForm.getId(), user, hotel, commentForm.getContent(), commentForm.getRate());
        return iCommentService.save(comment);
    }
    @GetMapping("/list-comment")
    public Iterable<Comment> comments(){
        return iCommentService.findAll();
    }
    @RequestMapping(value = "/listComment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable<Comment> allComment(){
        return iCommentService.findAll();
    }

}

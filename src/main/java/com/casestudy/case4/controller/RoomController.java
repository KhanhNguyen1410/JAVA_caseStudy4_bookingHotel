package com.casestudy.case4.controller;

import com.casestudy.case4.model.*;
import com.casestudy.case4.service.comment.ICommentService;
import com.casestudy.case4.service.hotel.IHotelService;
import com.casestudy.case4.service.room.IRoomService;
import com.casestudy.case4.service.type_room.ITypeRoomService;
import com.casestudy.case4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.xml.ws.Holder;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class RoomController {
    @Value("${upload.path}/room")
    String fileUpload;

    @Autowired
    private IRoomService iRoomService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ITypeRoomService iTypeRoomService;
    @Autowired
    private IHotelService iHotelService;
    @Autowired
    private ICommentService iCommentService;

    @ModelAttribute("typeRooms")
    public Iterable<TypeRoom> findAllTypeRoom(){
        return iTypeRoomService.findAll();
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
//    @ModelAttribute("allComment")
//    public Page<Comment> comments(){
//        return iCommentService.findAllByHotelId();
//    }

    @ModelAttribute("userCurrent")
    public User getPrincipal(){
        User userCurrent = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrinciple userPrinciple = (UserPrinciple) principal;
        userCurrent = iUserService.findByUserName(userPrinciple.getUsername());
        return userCurrent;
    }



    @GetMapping("/admin/details-hotel/{id}")
    public ModelAndView detailHotel(@PathVariable Long id, Pageable pageable){
        Page<Room> rooms=iRoomService.findAllByHotelId(id ,pageable);
        Page<Comment> allComment = iCommentService.findAllByHotelId(id,pageable);
        ModelAndView modelAndView= new ModelAndView("room/detailsRoomHotel");
        modelAndView.addObject("allComment",allComment);
        modelAndView.addObject("rooms",rooms);
        modelAndView.addObject("comment",new Comment());
        modelAndView.addObject("id_details", id);
        return modelAndView;
    }
    @GetMapping("/user/details-hotel/{id}")
    public ModelAndView detailHotelUser(@PathVariable Long id, Pageable pageable){
        Page<Room> rooms=iRoomService.findAllByHotelId(id ,pageable);
        Hotel hotel= iHotelService.findAllById(id);
        Page<Comment> allComment = iCommentService.findAllByHotelId(id,pageable);
        ModelAndView modelAndView= new ModelAndView("room/detailsRoomHotel");
        modelAndView.addObject("allComment",allComment);
        modelAndView.addObject("rooms",rooms);
        modelAndView.addObject("comment",new CommentForm());
        modelAndView.addObject("id_details", id);
        modelAndView.addObject("hotelCurrent",hotel);
        return modelAndView;
    }

    @GetMapping("/user/create-room/{id}")
    public ModelAndView showCreateRoom(@PathVariable Long id){
        ModelAndView modelAndView= new ModelAndView("room/create");
        modelAndView.addObject("roomForm", new RoomForm());
        modelAndView.addObject("id_hotel", id);
        return modelAndView;
    }
    @GetMapping("/admin/create-room/{id}")
    public ModelAndView showCreateRoomAdmin(@PathVariable Long id){
        ModelAndView modelAndView= new ModelAndView("room/create");
        modelAndView.addObject("roomForm", new RoomForm());
        modelAndView.addObject("id_hotel", id);
        return modelAndView;
    }
    @PostMapping("/create-room")
    public RedirectView saveBook(@ModelAttribute RoomForm roomForm, Model model, @RequestParam Long id_hotel) {
       Hotel hotel = iHotelService.findAllById(id_hotel);
        roomForm.setHotel(hotel);
        Room room = new Room(roomForm.getId(), roomForm.getName(), roomForm.getPrice(),roomForm.getDescription(),
                roomForm.getHotel(), roomForm.getTypeRoom());
        MultipartFile multipartFile = roomForm.getCoverImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(roomForm.getCoverImage().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        room.setImage(fileName);

        iRoomService.save(room);
        model.addAttribute("roomHotel", new RoomForm());
        return new RedirectView("/user");
    }

}

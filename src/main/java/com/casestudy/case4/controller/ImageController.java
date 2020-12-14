package com.casestudy.case4.controller;


import com.casestudy.case4.model.Image;
import com.casestudy.case4.model.ImageForm;
import com.casestudy.case4.model.Room;
import com.casestudy.case4.service.image.ImageService;
import com.casestudy.case4.service.room.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private IRoomService roomService;

    @ModelAttribute("rooms")
    public Iterable<Room> rooms(){
        return roomService.findAll();
    }
    @Value("${upload.path}")
    private String fileUpload;

    @GetMapping("/image")
    public ModelAndView listImage(){
        Iterable<Image> list= imageService.findAll();
        ModelAndView modelAndView= new ModelAndView("image/list");
        modelAndView.addObject("list", list);
        return modelAndView;
    }


    @GetMapping("/admin/create-image")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView= new ModelAndView("image/create");
        modelAndView.addObject("imageForm", new ImageForm());
        return modelAndView;
    }
    @PostMapping("/admin/create-image")
    public RedirectView saveImage(@ModelAttribute ImageForm imageForm){
        Image image= new Image(imageForm.getRoom());
        MultipartFile multipartFile= imageForm.getUrl();
        String fileName= multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(imageForm.getUrl().getBytes(),new File(this.fileUpload+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.setUrl(fileName);
        imageService.save(image);
        return new RedirectView("/image");
    }
    @GetMapping("/delete-image/{id}")
    public ModelAndView deleteImage(@PathVariable Long id){
        imageService.remove(id);
        Iterable<Image> list= imageService.findAll();
        ModelAndView modelAndView= new ModelAndView("image/list");
        modelAndView.addObject("list", list);
        return modelAndView;
    }

}

package com.casestudy.case4.model;

import org.springframework.web.multipart.MultipartFile;

public class ImageForm {
    private Long id;
    private MultipartFile url;
    private Room room;

    public ImageForm() {
    }

    public ImageForm(Long id, MultipartFile url, Room room) {
        this.id = id;
        this.url = url;
        this.room = room;
    }

    public ImageForm(MultipartFile url, Room room) {
        this.url = url;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getUrl() {
        return url;
    }

    public void setUrl(MultipartFile url) {
        this.url = url;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

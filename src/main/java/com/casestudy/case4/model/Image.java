package com.casestudy.case4.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    private Room room;

    public Image() {
    }

    public Image(Room room) {
        this.room = room;
    }

    public Image(Long id, String url, Room room) {
        this.id = id;
        this.url = url;
        this.room = room;
    }
}

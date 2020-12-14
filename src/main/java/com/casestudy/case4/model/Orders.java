package com.casestudy.case4.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Date dayBook;
    @Column(columnDefinition = "boolean default false")
    private boolean status;
    @ManyToOne
    private User user;

    public Orders() {
    }

    public Orders(Long id, Date dayBook, boolean status, User user) {
        this.id = id;
        this.dayBook = dayBook;
        this.status = status;
        this.user = user;
    }
}

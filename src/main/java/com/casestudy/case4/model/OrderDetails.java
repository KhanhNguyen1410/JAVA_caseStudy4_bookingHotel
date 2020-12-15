package com.casestudy.case4.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Orders orders;
    @ManyToOne
    private Room room;
    @Column(columnDefinition = "INTEGER default 0")
    private int discount;
//    @NotEmpty(message = "vui long chon ngay check in")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
//    @NotEmpty(message = "vui long chon ngay checkOut")
    private LocalDate checkOut;

    public OrderDetails() {
    }

    public OrderDetails(Long id, Orders orders, Room room, int discount, LocalDate checkIn, LocalDate checkOut) {
        this.id = id;
        this.orders = orders;
        this.room = room;
        this.discount = discount;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}

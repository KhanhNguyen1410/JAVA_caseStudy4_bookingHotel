package com.casestudy.case4.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class OrderDetailForm {
    private Long id;
    private Long orders;
    private Long room;
    private Date checkIn;
    private Date checkOut;
    public OrderDetailForm() {
    }

    public OrderDetailForm(Long id, Long orders, Long room, Date checkIn, Date checkOut) {
        this.id = id;
        this.orders = orders;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public Long getRoom() {
        return room;
    }

    public void setRoom(Long room) {
        this.room = room;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }
}

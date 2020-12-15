package com.casestudy.case4.model;

import java.time.LocalDate;

public class OrderDetailForm {
    private Long id;
    private Long orders;
    private Long room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    public OrderDetailForm() {
    }

    public OrderDetailForm(Long id, Long orders, Long room, LocalDate checkIn, LocalDate checkOut) {
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

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }
}

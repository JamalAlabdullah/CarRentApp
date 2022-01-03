package com.mob3000.group11;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    int orderNr;
    ArrayList carsInfo;
    String name;
    String email;
    String phone;
    Date pickUpDate;
    Date returnDate;

    public Order(int orderNr, ArrayList carsInfo, String name, String email, String phone, Date pickUpDate, Date returnDate) {
        this.orderNr = orderNr;
        this.carsInfo = carsInfo;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(int orderNr) {
        this.orderNr = orderNr;
    }

    public ArrayList getCars() {
        return carsInfo;
    }

    public void setCars(ArrayList cars) {
        this.carsInfo = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

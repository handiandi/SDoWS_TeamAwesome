/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.niceview;

/**
 *
 * @author Troels
 */
public class HotelInformation {
    final String bookingnumber;
    final Hotel hotel;
    final int price;
    
    public HotelInformation(String boString, Hotel hotel, double price ){
        this.bookingnumber=boString;
        this.hotel = hotel;
        this.price=price;
    }

    public String getBookingnumber() {
        return bookingnumber;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public int getPrice() {
        return price;
    }
    
}

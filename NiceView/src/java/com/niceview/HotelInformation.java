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
    final int bookingNumber;
    final Hotel hotel;
    final int price;
    
    
    public HotelInformation(int bookingNumber, Hotel hotel, int price ){
        this.bookingNumber=bookingNumber;
        this.hotel = hotel;
        this.price=price;
    }

    public int getBookingnumber() {
        return bookingNumber;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public int getPrice() {
        return price;
    }
    
}

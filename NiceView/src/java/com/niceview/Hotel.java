/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.niceview;

//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jesper
 */
//@XmlRootElement
public class Hotel {
    final String name;
    final String address;
    final String city;
    final Boolean creditCardGuarantee;
    final int price;
    final String reservationService;
    
    public Hotel(){
        this.name = null;
        this.address = null;
        this.city = null;
        this.creditCardGuarantee = null;
        this.price = 0;
        this.reservationService = null;
    }
    
    public Hotel(String name, String address, String city, Boolean creditCardGuarantee, int price, String reservationService, int bookingNo){
        this.name = name;
        this.address = address;
        this.city = city;
        this.creditCardGuarantee = creditCardGuarantee;
        this.price = price;
        this.reservationService = reservationService;
    }
    
    public String getCity(){
        return city;
    }
    
    public String getName(){
        return name;
    }
    
    public String getAddress(){
        return address;
    }
    
    public Boolean getCreditCardGuarantee(){
        return creditCardGuarantee;
    }
    
    public int getPrice(){
        return price;
    }
    
    public String getReservationService(){
        return reservationService;
    }
   
    
    public String toString(){
        return name + address + city + creditCardGuarantee + price + reservationService ;
    }
}
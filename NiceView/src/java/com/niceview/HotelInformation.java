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
public class HotelInformation {
    private String name = null;
    private String address = null;
    private String city = null;
    private Boolean creditCardGuarantee = null;
    private int price = 0;
    private String reservationService = null;
    private int bookingNo = 0;
    
    public HotelInformation(){
        
    }
    
    public HotelInformation(String name, String address, String city, Boolean creditCardGuarantee, int price, String reservationService, int bookingNo){
        this.name = name;
        this.address = address;
        this.city = city;
        this.creditCardGuarantee = creditCardGuarantee;
        this.price = price;
        this.reservationService = reservationService;
        this.bookingNo = bookingNo;
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
    
    public String getCreditCardGuarantee(){
        return creditCardGuarantee.toString();
    }
    
    public int getPrice(){
        return price;
    }
    
    public String getReservationService(){
        return reservationService;
    }
    
    public int getBookingNo(){
        return bookingNo;
    }
    
    public String toString(){
        return name + address + city + creditCardGuarantee + price + reservationService + getBookingNo();
    }

    public void setPrice(int price) {
        this.price = price;
    }

    protected void setBookingNo(int bookingNo) {
        this.bookingNo = bookingNo;
    }
    
    
}
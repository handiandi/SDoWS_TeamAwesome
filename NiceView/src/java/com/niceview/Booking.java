/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.niceview;

/**
 *
 * @author Mr. Awesome
 */
public class Booking {
    private String   hotelName;
    private Address  address;
    private String   bookingNumber;
    private boolean  creditCardGuarentee;
    private int      priceOfStay;
    private String   bookingService;
    
    public Booking(String hotelName, Address address, int days, boolean creditCardGuarentee, int priceOfStay, String bookingService, int hotelId){
        this.hotelName = hotelName;
        this.address = address;
        this.creditCardGuarentee = creditCardGuarentee;
        this.priceOfStay = priceOfStay;
        this.bookingService = bookingService;
        this.bookingNumber = createBookingNumber(days, hotelId);
    }

    public String getHotelName() {
        return hotelName;
    }

    public Address getAddress() {
        return address;
    }

    public String createBookingNumber(int days, int hotelId) {
        return priceOfStay + "-" + hotelId;
    }

    public boolean isCreditCardGuarentee() {
        return creditCardGuarentee;
    }

    public int getPriceOfStay() {
        return priceOfStay;
    }

    public String getBookingService() {
        return bookingService;
    }
    
}
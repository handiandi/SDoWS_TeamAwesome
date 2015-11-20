/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lameduck.service;


/**
 *
 * @author jeppe
 */
class FlightInformation {
    final int bookingNumber;
    final double price;
    final String airlineReservationService;
    final Flight flight;

    public FlightInformation(int bookingNumber, double price, String airlineReservationService, Flight flight) {
        this.bookingNumber = bookingNumber;
        this.price = price;
        this.airlineReservationService = airlineReservationService;
        this.flight = flight;
    }

    public int getBookingNumber() {
        return bookingNumber;
    }

    public double getPrice() {
        return price;
    }

    public String getAirlineReservationService() {
        return airlineReservationService;
    }

    public Flight getFlight() {
        return flight;
    }
    

    
}
    


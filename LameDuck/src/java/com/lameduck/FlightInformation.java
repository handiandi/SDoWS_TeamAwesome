/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lameduck;


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
    
    public class Flight{
        final String startAirport;
        final String endAirport;
        final String liftOffTime;
        final String landingTime;
        final String carrier;

        public String getStartAirport() {
            return startAirport;
        }

        public String getEndAirport() {
            return endAirport;
        }

        public String getLiftOffTime() {
            return liftOffTime;
        }

        public String getLandingTime() {
            return landingTime;
        }

        public String getCarrier() {
            return carrier;
        }

        public Flight(String startAirport, String endAirport, String liftOffTime, String landingTime, String carrier) {
            this.startAirport = startAirport;
            this.endAirport = endAirport;
            this.liftOffTime = liftOffTime;
            this.landingTime = landingTime;
            this.carrier = carrier;
        }
        
    }
    
}

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

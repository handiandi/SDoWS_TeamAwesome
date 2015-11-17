/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.niceview;

import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import  com.niceview.FastMoney;
/**
 *
 * @author jesper
 */
@WebService(serviceName = "NiceView")
public class NiceView {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getHotels")
    public String[] getHotels (@WebParam(name = "city") String city, @WebParam(name = "arrivalDate") Date arrivalDate, @WebParam(name = "departureDate") Date departureDate) {
        return null;
    }
    
    @WebMethod(operationName = "bookHotel")
    public boolean bookHotel (@WebParam(name = "bookingNumber") int bookingNumber, @WebParam(name = "creditcardInformation") String creditcardInformation) throws BookingFailedException {
       
        return false;
    }
    
    @WebMethod(operationName = "cancelHotel")
    public boolean cancelHotel (@WebParam(name = "bookingNumber") int bookingNumber) throws BookingFailedException {
        return false;
    }
}

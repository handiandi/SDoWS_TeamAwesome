/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lameduck;

import com.lameduck.FlightInformation.Flight;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author jeppe
 */
@WebService(serviceName = "LameDuck")
public class LameDuck {

    /**
     * Web service operation
     */
    
    @WebMethod(operationName = "getFlights")
    public FlightInformation[] getFlights(@WebParam(name = "startsAt") String startsAt, @WebParam(name = "endsAt") String endsAt, @WebParam(name = "date") Date date) {
        //TODO write some interesting hard coded flightinformation object
        // FlightInformation(int,double,String,Flight)
        // Flight(String, String, Calendar, Calendar, String)
        FlightInformation[] output = 
        { new FlightInformation(0,0.0,"LameDuck",null), };
        return output;
    }
    
   
    /**
     * Web service operation
     */
    @WebMethod(operationName = "bookFlight")
    public boolean bookFlight(@WebParam(name = "bookingNumber") int bookingNumber, @WebParam(name = "creditcardInformation") dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcardInformation) throws BookingFailedException {
        //TODO write your implementation code here:
        return false;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "cancelFlight")
    public boolean cancelFlight(@WebParam(name = "bookingNumber") int bookingNumber,@WebParam(name = "price") int price,@WebParam(name = "creditcardInformation") dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcardInformation){
        
        return false;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lameduck;

import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

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
    public java.lang.String[] getFlights(@WebParam(name = "startsAt") String startsAt, @WebParam(name = "endsAt") String endsAt, @WebParam(name = "date") Date date) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "bookFlight")
    public boolean bookFlight(@WebParam(name = "bookingNumber") int bookingNumber, @WebParam(name = "creditcardInformation") String creditcardInformation) throws BookingFailedException {
        //TODO write your implementation code here:
        return false;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "cancelFlight")
    public boolean cancelFlight(@WebParam(name = "bookingNumber") int bookingNumber,@WebParam(name = "price") int price,@WebParam(name = "creditcardInformation") String creditcardInformation){
        
        return false;
    }
}

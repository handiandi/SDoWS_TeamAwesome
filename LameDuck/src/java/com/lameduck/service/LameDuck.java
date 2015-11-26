/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lameduck.service;

import dk.dtu.imm.fastmoney.BankService;
import dk.dtu.imm.fastmoney.CreditCardFaultMessage;
import dk.dtu.imm.fastmoney.types.AccountType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author jeppe
 */
@WebService(serviceName = "LameDuck")
public class LameDuck {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;
    private static final AccountType ACCOUNT;
    private static final String ACCOUNT_NUMBER = "50208812";
    private static final String ACCOUNT_NAME = "LameDuck";
    private static final int GROUP = 14;
    private static List<FlightInformation> availableFlights;
    private static List<FlightInformation> bookedFlights;
    static{
        ACCOUNT = new AccountType();
        ACCOUNT.setName(ACCOUNT_NAME);
        ACCOUNT.setNumber(ACCOUNT_NUMBER);
        availableFlights = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Flight f = new Flight("Copenhagen","Stockholm","00,00,01,01,1970","02,00,01,01,1970","Planes'r'Us");
            availableFlights.add(new FlightInformation(i,i*1000-0.05,"LameDuck",f));
        }
    }
    /**
     * Web service operation
     */
    private boolean matches(Flight f1, String start,String end,String date){
        boolean b1 = f1.startAirport.equals(start);
        boolean b2 = f1.endAirport.equals(end);
        boolean b3 = f1.liftOffTime.equals(end);
        return b1&&b2&&b3;
    }

    /**
     *
     * @param startsAt
     * @param endsAt
     * @param date
     * @return
     */
    @WebMethod(operationName = "getFlights")
    public List<FlightInformation> getFlights(@WebParam(name = "startsAt") String startsAt, @WebParam(name = "endsAt") String endsAt, @WebParam(name = "date") String date) {
        //TODO write some interesting hard coded flightinformation object
        List<FlightInformation> output = new ArrayList<>();
        availableFlights.stream().forEach((f) -> {
            Flight flight = f.getFlight();
            if (matches(flight,startsAt,endsAt,date)) {
                output.add(f);
            }
        });
        return output;
        
        
    }
    
   
    /**
     * Web service operation
     * @param bookingNumber is the booking number associated with and unique to the flight
     * @param creditcardInformation object containing credit card data of the customer
     * @return a boolean denoting whether the booking was successful
     * @throws com.lameduck.service.BookingFailedException
     */
    @WebMethod(operationName = "bookFlight")
    public boolean bookFlight(@WebParam(name = "bookingNumber") int bookingNumber, @WebParam(name = "creditcardInformation") dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcardInformation) throws BookingFailedException {
        boolean hasPaid = false;
        FlightInformation f = null;
        try {
            for(FlightInformation fi: availableFlights) if(bookingNumber==fi.getBookingNumber()) f = fi;
            if(f == null) throw new BookingFailedException("Booking number does not exist");
            if(validateCreditCard(GROUP,creditcardInformation,(int)(f.getPrice()))) 
                hasPaid = chargeCreditCard(GROUP,creditcardInformation,(int)(f.getPrice()),ACCOUNT);
            if(hasPaid){
                availableFlights.remove(f);
                bookedFlights.add(f);
            }
            return true;
        } catch (CreditCardFaultMessage ex) {
            Logger.getLogger(LameDuck.class.getName()).log(Level.SEVERE, null, ex);
            if(hasPaid) try {
                if(f == null) throw new BookingFailedException("Booking number does not exist");
                refundCreditCard(GROUP,creditcardInformation,(int)(f.getPrice()),ACCOUNT);
            } catch (CreditCardFaultMessage ex1) {
                Logger.getLogger(LameDuck.class.getName()).log(Level.SEVERE, null, ex1);
                throw new BookingFailedException("Booking failed; Payment received; Refund failed");
            }
            if(bookedFlights.contains(f)){
                bookedFlights.remove(f);
                availableFlights.add(f);
            }
            throw new BookingFailedException("Booking failed; Payment not received");
        }
    }
    
    /**
     * Web service operation
     * @param bookingNumber is the booking number associated with and unique to the flight
     * @param price is the original price of the flight, half of which will be refunded
     * @param creditcardInformation object containing credit card data of the customer
     * @return a boolean which will be true if the refund was successful
     */
    @WebMethod(operationName = "cancelFlight")
    public boolean cancelFlight(@WebParam(name = "bookingNumber") int bookingNumber,@WebParam(name = "price") int price,@WebParam(name = "creditcardInformation") dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcardInformation){
        boolean result = false;
        try{
            if(!(result = refundCreditCard(GROUP,creditcardInformation,price/2,ACCOUNT)))
                throw new RefundFailedException();
        }catch(RefundFailedException | CreditCardFaultMessage re){
            Logger.getLogger(LameDuck.class.getName()).log(Level.SEVERE, null, re);
        }finally{
            return result;
        }
    }

    private boolean validateCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }

    private boolean chargeCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.fastmoney.types.AccountType account) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.chargeCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean refundCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.fastmoney.types.AccountType account) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.refundCreditCard(group, creditCardInfo, amount, account);
    }
}

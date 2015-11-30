/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import dk.dtu.imm.fastmoney.BankService;
import dk.dtu.imm.fastmoney.CreditCardFaultMessage;
import dk.dtu.imm.fastmoney.types.AccountType;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceRef;
import ws.lameduck.BookingFailedException;
import ws.lameduck.BookingFailedFault;

/**
 *
 * @author jeppe
 */
@WebService(serviceName = "LameDuckService", portName = "LameDuckBindingPort", endpointInterface = "ws.lameduck.LameDuck", targetNamespace = "http://LameDuck.ws", wsdlLocation = "WEB-INF/wsdl/LameDuck/LameDuck.wsdl")
public class LameDuck {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;
    private static final AccountType ACCOUNT;
    private static final String ACCOUNT_NUMBER = "50208812";
    private static final String ACCOUNT_NAME = "LameDuck";
    private static final int GROUP = 14;
    private static List<ws.lameduck.FlightInformation> availableFlights;
    private static List<ws.lameduck.FlightInformation> bookedFlights;
    static{
        ACCOUNT = new AccountType();
        ACCOUNT.setName(ACCOUNT_NAME);
        ACCOUNT.setNumber(ACCOUNT_NUMBER);
        availableFlights = new ArrayList<>();
        javax.xml.datatype.XMLGregorianCalendar gcd1 = null,gcd2 = null;
            try {
                gcd1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2008,10,1));
                gcd2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2009,10,1));
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(LameDuck.class.getName()).log(Level.SEVERE, null, ex);
            }
        for(int i = 0; i < 10; i++){
            ws.lameduck.Flight f = new ws.lameduck.Flight();
            f.setCarrier("omg");
            f.setEndAirport("Stockholm");
            f.setStartAirport("Copenhagen");
            f.setLiftOffTime(gcd1);
            f.setLandingTime(gcd2);
            ws.lameduck.FlightInformation fi = new ws.lameduck.FlightInformation();
            fi.setBookingNumber(i);
            fi.setPrice(1000);
            fi.setFlight(f);
            fi.setAirlineReservationService("LameDuck");
            fi.setStatus("available");
            availableFlights.add(fi);
        }
    }
    public java.util.List<ws.lameduck.FlightInformation> getFlights(java.lang.String startLocation, java.lang.String endLocation, javax.xml.datatype.XMLGregorianCalendar date) {
        //TODO implement this method
        return availableFlights;
    }

    public boolean bookFlight(ws.lameduck.BookFlight request) throws BookingFailedFault {
        boolean hasPaid = false;
        ws.lameduck.FlightInformation flightInfo = null;
        try{
            for(ws.lameduck.FlightInformation fi:availableFlights)
                if(request.getBookingNumber() == fi.getBookingNumber()){
                    flightInfo = fi;
                    break;
                }
            if(!validateCreditCard(GROUP,request.getCreditCardInfo(),flightInfo.getPrice())) throw new BookingFailedFault("",new BookingFailedException());
            
            hasPaid = chargeCreditCard(GROUP,request.getCreditCardInfo(),flightInfo.getPrice(),ACCOUNT);
            availableFlights.remove(flightInfo);
            bookedFlights.add(flightInfo);
            flightInfo.setStatus("booked");
        }catch(Exception e){
            Logger.getLogger(LameDuck.class.getName()).log(Level.SEVERE, null, e);
        }
        return hasPaid;
    }

    public boolean cancelFlight(ws.lameduck.CancelFlight request) throws CreditCardFaultMessage {
        //TODO implement this method
        for(ws.lameduck.FlightInformation fi: bookedFlights)
            if(fi.getBookingNumber()== request.getBookingNumber()){
                if(!refundCreditCard(GROUP,request.getCreditCardInfo(),request.getPrice()/2,ACCOUNT)) break;
                fi.setStatus("available");
                bookedFlights.remove(fi);
                availableFlights.add(fi);
                return true;
            }
        return false;
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

    private boolean validateCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }
    
}

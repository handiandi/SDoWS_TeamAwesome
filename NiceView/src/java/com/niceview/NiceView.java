/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.niceview;

import bank.ws.AccountType;
import bank.ws.BankService;
import bank.ws.CreditCardFaultMessage;
import bank.ws.FastMoney;
import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.xml.ws.WebServiceRef;
/**
 *
 * @author jesper
 */
@WebService(serviceName = "NiceView")
public class NiceView {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;
    
        final Hotel CasaDeLyngby = new Hotel("Casa de Lyngby", "Lyngbyhovedgade 12", "Lyngby", true, 200, "reservationDOTdk", 0);
        final Hotel Hotellet = new Hotel("Hotellet", "Nørre Alle 75", "København", false, 50, "StudentOffers", 0);
        private ArrayList<Hotel> allHotelsList = new ArrayList<>();   
        private ArrayList<HotelInformation> hotelsInAreaList = new ArrayList<>();
        private int bookRef = 0;
        
        private String[] arrivalDateArray;
        private String[] departureDateArray;
        private ArrayList<HotelInformation> bookedHotelsList;
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getHotels")
    public ArrayList<HotelInformation> getHotel(@WebParam(name = "city") 
            String city, @WebParam(name = "arrivalDate") String arrivalDate, 
            @WebParam(name = "departureDate") String departureDate) {
        allHotelsList.clear();
        hotelsInAreaList.clear();
        allHotelsList.add(CasaDeLyngby);
        allHotelsList.add(Hotellet);
        

        

        //int j = 0;
        //ArrayList<HotelInformation> bookingList = new ArrayList<HotelInformation>();
        //Find hotels
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:dd:MM:yyyy");
        int days=0;
        try{
        Date arrivDate = sdf.parse(arrivalDate);
        Date depDate = sdf.parse(departureDate);
        long dif = depDate.getTime()-arrivDate.getTime();
        days =  (int) TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS); //check this works...
        }catch(Exception e){
        }
        
        for (Hotel hotel : allHotelsList) {
            if (hotel.getCity().equalsIgnoreCase(city)) 
                hotelsInAreaList.add(new HotelInformation(getBookingRef(), hotel, hotel.getPrice()*days));
            
        }
                   
        
        
        return hotelsInAreaList;
    }
    
    @WebMethod(operationName = "bookHotel")
    public boolean bookHotel (@WebParam(name = "bookingNumber") 
            String bookingNumber, @WebParam(name = "creditcardInformation") 
            bank.ws.CreditCardInfoType creditcardInformation) 
            throws BookingFailedException, CreditCardFaultMessage {
       
        
        for ( HotelInformation hotel : hotelsInAreaList ){
            if (hotel.getBookingnumber().equals(bookingNumber)){
                //do stuff
                //validate credit card if guarantee needed
                //charge credit card, evt. catch exception + fault handling
                if(hotel.getHotel().getCreditCardGuarantee()){
                    if(!validateCreditCard(14, creditcardInformation, hotel.getPrice())){
                        //fault handling
                        break;
                    }
                        
                }
                //book
                AccountType niceAccount = new AccountType();
                niceAccount.setName("NiceView");
                niceAccount.setNumber("50308815");
                chargeCreditCard(14, creditcardInformation, hotel.price, niceAccount);
                
                
                bookedHotelsList.add(hotel);
            }
        }
        
        return false;
    }
    
    @WebMethod(operationName = "cancelHotel")
    public boolean cancelHotel (@WebParam(name = "bookingNumber") int bookingNumber)  {
        return false;
    }
    
    
    private String getBookingRef(){
        return "hot" + bookRef;
    }

    private boolean chargeCreditCard(int group, bank.ws.CreditCardInfoType creditCardInfo, int amount, bank.ws.AccountType account) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        bank.ws.BankPortType port = service.getBankPort();
        return port.chargeCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean refundCreditCard(int group, bank.ws.CreditCardInfoType creditCardInfo, int amount, bank.ws.AccountType account) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        bank.ws.BankPortType port = service.getBankPort();
        return port.refundCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean validateCreditCard(int group, bank.ws.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        bank.ws.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }
    
}

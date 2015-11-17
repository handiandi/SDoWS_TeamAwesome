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
import java.util.ArrayList;
/**
 *
 * @author jesper
 */
@WebService(serviceName = "NiceView")
public class NiceView {
    
        private HotelInformation CasaDeLyngby = new HotelInformation("Casa de Lyngby", "Lyngbyhovedgade 12", "Lyngby", true, 200, "reservationDOTdk", 0);
        private HotelInformation Hotellet = new HotelInformation("Hotellet", "Nørre Alle 75", "København", false, 50, "StudentOffers", 0);
        private ArrayList<HotelInformation> hotelList = new ArrayList<HotelInformation>();   
        private ArrayList<HotelInformation> bookingList = new ArrayList<HotelInformation>(); //tempbooking list
        
        private String[] arrivalDateArray;
        private String[] departureDateArray;
        private ArrayList<HotelInformation> bookedHotels;
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getHotels")
    public ArrayList<HotelInformation> getHotel(@WebParam(name = "city") String city, @WebParam(name = "arrivalDate") String arrivalDate, @WebParam(name = "departureDate") String departureDate) {
        hotelList.clear();
        bookingList.clear();
        hotelList.add(CasaDeLyngby);
        hotelList.add(Hotellet);
        int j = 0;
        ArrayList<HotelInformation> bookingList = new ArrayList<HotelInformation>();
        
            //TODO: Hotel magic
        
        return bookingList;
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

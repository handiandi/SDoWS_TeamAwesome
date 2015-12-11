/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import lameDuckClient.Flight;
import lameDuckClient.FlightInformation;
import niceViewClient.Hotel;
import niceViewClient.HotelInformation;

/**
 *
 * @author Anders
 */
public class Itinerary {
    List<FlightInformation> flights = new ArrayList<>();
    List<HotelInformation> hotels = new ArrayList<>();
    
    private boolean booked = false;
    public Itinerary(){
        
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

   

    public List<FlightInformation> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightInformation> flights) {
        this.flights = flights;
    }
    
    public void addFlight(FlightInformation f){
        this.flights.add(f);
    }
    
    public void addHotel(HotelInformation h){
        this.hotels.add(h);
    }
    public void sortLists(){
        List<FlightInformation> sortedFlightList = new ArrayList<>();
        //List<FlightInformation> custormerFlights = iteneraryFlights.get(customerID);
        
        //Flight tempFlight = temp.getFlight();
        while(this.flights.size()>0){
            FlightInformation temp = new FlightInformation();
            temp = this.flights.get(0);
            int tempIndex = 0;
            int index = 0;
            for(FlightInformation f : this.flights){
                Flight ff = f.getFlight();
                Flight tempFlight = temp.getFlight();
                
                if(tempFlight.getLiftOffTime().compare(ff.getLiftOffTime())==1){ //If ff is earlier than tempFlight
                    index = tempIndex;
                }
                tempIndex++;
            } //end for-loop
            sortedFlightList.add(this.flights.remove(index));
            
        }//end while-loop   
        this.setFlights(sortedFlightList);
        
    }
    
    public List<FlightInformation> getBookings(){
        List<FlightInformation> list = new ArrayList<>();
        for(FlightInformation f : this.flights){
            if(f.getStatus().equals("Booked")){
                list.add(f);
            }
        }
        return list;
    }
    
    public boolean isFlightInItinerary(int bookingNumber){
        boolean returnVal = false;
        for(FlightInformation f : this.flights){
            if(f.getBookingNumber() == bookingNumber){
                returnVal = true;
                break;
            }
        }
        return returnVal;
    }
    
    public boolean isHotelInItinerary(int bookingNumber){
        boolean returnVal = false;
        for(HotelInformation h : this.hotels){
            if(h.getBookingNumber() == bookingNumber){
                returnVal = true;
                break;
            }
        }
        return returnVal;
    }
    
    public boolean setFlightStatus(int bookingNumber, String status){
        boolean returnVal = false;
        for(FlightInformation f : this.flights){
            if(f.getBookingNumber() == bookingNumber){
                f.setStatus(status);
                returnVal = true;
                break;
            }
        }
        return returnVal;
    }
    
    public boolean setHotelStatus(int bookingNumber, String status){
        boolean returnVal = false;
        for(HotelInformation h : this.hotels){
            if(h.getBookingNumber() == bookingNumber){
                h.setStatus(status);
                returnVal = true;
                break;
            }
        }
        return returnVal;
    }

    public List<HotelInformation> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelInformation> hotels) {
        this.hotels = hotels;
    }
    
    public XMLGregorianCalendar getEarlistDate(){
        XMLGregorianCalendar date =null;
        date =flights.get(0).getFlight().getLiftOffTime();
        for(FlightInformation f : flights){
            if(date.compare(f.getFlight().getLiftOffTime()) == 1){
                date =f.getFlight().getLiftOffTime();
            }
        }
        return date;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg;

import java.util.ArrayList;
import java.util.List;
import lameDuckClient.Flight;
import lameDuckClient.FlightInformation;

/**
 *
 * @author Anders
 */
public class Itinerary {
    private int numberOfFlights = 0;
    private int numberOfHotels = 0;
    private List<FlightInformation> flights = new ArrayList<>();
    //List<HotelInformation> hotels = new ArrayList<>();
    
    public Itinerary(){
        
    }

    public int getNumberOfFlights() {
        return numberOfFlights;
    }

    public void setNumberOfFlights() {
        this.numberOfFlights = this.flights.size();
    }

    public int getNumberOfHotels() {
        return numberOfHotels;
    }

    public void setNumberOfHotels(int numberOfHotels) {
        this.numberOfHotels = numberOfHotels;
    }

    public List<FlightInformation> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightInformation> flights) {
        this.flights = flights;
    }
    
    public void addFlight(FlightInformation f){
        this.flights.add(f);
        this.setNumberOfFlights();
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
        
        //Do the same for hotel here!
        
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
    
    public boolean setFlightConfirmed(int bookingNumber){
        boolean returnVal = false;
        for(FlightInformation f : this.flights){
            if(f.getBookingNumber() == bookingNumber){
                f.setStatus("confirmed");
                returnVal = true;
                break;
            }
        }
        return returnVal;
    }
}

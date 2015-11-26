/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import lameDuckClient.FlightInformation;



import niceViewClient.HotelInformation;
/**
 *
 * @author Anders
 */
@Singleton
@Path("/itineraries")
public class Itinerary {
    HashMap<XMLGregorianCalendar, List<HotelInformation>> iteneraryHotels = 
            new HashMap<>();
    
    //HashMap<XMLGregorianCalendar, List<GetFlights>> iteneraryFlights = 
    //        new HashMap<>();
    
    //Order of the Itinerary (order of flights and hotel) - based on bookingNumber
    //This is a pragmatic solution instead of looking at the dates and sort
    List<XMLGregorianCalendar> order = new ArrayList<>(); 
    
    List<HotelInformation> hotelsTemp = new ArrayList<>();
    //List<GetFlights> flightsTemp = new ArrayList<>();
    
    List<HotelInformation> iteneraryHotels2 = new ArrayList<>();
    //List<GetFlights> iteneraryFlights2 = new ArrayList<>();   
            //private boolean booked = false; //Arghh det er booked for hver flight/hotel!! - men b
    
    public Itinerary(){
        
    }
    /*
    public void book(){ //(int bookingNumber)
        //Boooking
        //If Bookig succeed:
        this.booked = true;
        //else, throw exception
        
    }
    */
    
    public void cancel(){
        //if this.booked == true && Date() < start of first travel
        
    }

    @Path("/getFlights")
    @GET
    @Produces("application/json")
    public List<FlightInformation> getFlights(@QueryParam("startsAt") String startsAt, @QueryParam("endsAt") String endsAt, @QueryParam("date") String date) throws DatatypeConfigurationException {
        
        lameDuckClient.LameDuck_Service service = new lameDuckClient.LameDuck_Service();
        lameDuckClient.LameDuck port = service.getLameDuckPort();
        List<FlightInformation> rest = port.getFlights(startsAt, endsAt, date);
        System.out.println(rest.size());
        return rest;
    }
   /*
    @Path("/get_flights")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getFlightsTest() throws DatatypeConfigurationException{
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2015-09-20T18:30:00");
        
        String startsAt = "hej hej";
        String endAt = "farvel farvel";
        
        List<String> res = getFlights(startsAt, endAt, "2015-09-20T18:30:00");
        System.out.println(res.get(0));
        return res.get(0);
        
    }
    */
    
    @Path("/getHotels")
    @GET
    @Produces("application/json")
    public List<niceViewClient.HotelInformation> getHotels(
            @QueryParam("city") String city, 
            @QueryParam("arrivalDate") String arrivalDate, 
            @QueryParam("departureDate") String departureDate) 
    {
        niceViewClient.NiceView_Service service = new niceViewClient.NiceView_Service();
        niceViewClient.NiceView port = service.getNiceViewPort();
        this.hotelsTemp = port.getHotels(city, arrivalDate, departureDate);
        //niceViewClient.HotelInformation h = this.hotelsTemp.get(0);
      
        return this.hotelsTemp;
        
    }
    
    /*
    
    public void getItenerary(){
        order.
    }
    
    
    public void sortItenerary(){
        
    }

    public boolean bookHotel(@QueryParam("bookNumber") int bookingNumber, 
            @QueryParam("creditCard") String creditcardInformation) throws BookingFailedException_Exception {
        niceView.NiceView_Service service = new niceView.NiceView_Service();
        niceView.NiceView port = service.getNiceViewPort();
        boolean res = port.bookHotel(bookingNumber, creditcardInformation);
        if(res){
            //HotelInformation hot = null;
            for (Object h : this.hotelsTemp){
                HotelInformation hotel = (HotelInformation) h;
                if(hotel.getBookingNo() == bookingNumber){
                    iteneraryHotels2.add(hotel);
                    //order.put(hotel.getDate(), hotelsTemp)
                }
            }
        }
        return true;
    }

    public boolean cancelHotel(int bookingNumber) throws BookingFailedException_Exception {
        niceView.NiceView_Service service = new niceView.NiceView_Service();
        niceView.NiceView port = service.getNiceViewPort();
        boolean res = port.cancelHotel(bookingNumber);
        
        if(res){
            //HotelInformation hot = null;
            int i = 0;
            for (Object h : this.hotelsTemp){
                HotelInformation hotel = (HotelInformation) h;
                if(hotel.getBookingNo() == bookingNumber){
                    iteneraryHotels2.remove(i);
                    break;
                    //iteneraryHotels.put(hotel.getDate(), hotelsTemp)
                }
                i++;
            }
        }
        
        
        
        return true;
    }
    
    
    */

   
    
   
    
    
    
    
    
    
    
}

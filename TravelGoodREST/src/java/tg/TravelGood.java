/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javax.activation.MimeType;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


import lameDuckClient.*;

//import niceViewClient.HotelInformation;
/**
 *
 * @author Anders
 */
@Singleton
@Path("/itineraries")
public class TravelGood {
    
    //HashMap<XMLGregorianCalendar, List<HotelInformation>> iteneraryHotels = 
      //      new HashMap<>();
    
    //HashMap<XMLGregorianCalendar, List<GetFlights>> iteneraryFlights = 
    //        new HashMap<>();
    
    //Order of the TravelGood (order of flights and hotel) - based on bookingNumber
    //This is a pragmatic solution instead of looking at the dates and sort
    List<XMLGregorianCalendar> order = new ArrayList<>(); 
    
    //List<HotelInformation> hotelsTemp = new ArrayList<>();
    List<FlightInformation> flightsTemp = new ArrayList<>();
    
    //HashMap<Integer, List<FlightInformation>> iteneraryFlights = new HashMap<>();
    HashMap<Integer, Itinerary> itineraries = new HashMap<>();
    
    //List<HotelInformation> iteneraryHotels2 = new ArrayList<>();
    //List<GetFlights> iteneraryFlights2 = new ArrayList<>();   
            //private boolean booked = false; //Arghh det er booked for hver flight/hotel!! - men b
    
    List<Integer> iteneraryIDs = new ArrayList<>();
    
    public TravelGood(){
        
    }
    /*
    public void book(){ //(int bookingNumber)
        //Boooking
        //If Bookig succeed:
        this.booked = true;
        //else, throw exception
        
    }
    */
    /*
    @Path("/cancelPlanning")
    @GET
    @Produces("application/json")
    public void cancel(){
        //if this.booked == true && Date() < start of first travel
        
    }
    */
    @Path("/createItinerary")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String createItinerary(){
        //if this.booked == true && Date() < start of first travel
        int id = 0;
        if(iteneraryIDs.isEmpty()){
            iteneraryIDs.add(id); //Slettes???
            Itinerary itene = new Itinerary();
            //List<FlightInformation> flights = new ArrayList<>();
            itineraries.put(id, itene);
            return Integer.toString(id);
        }
        id = Collections.max(iteneraryIDs)+1;
        Itinerary itene = new Itinerary();
        //List<FlightInformation> flights = new ArrayList<>();
        itineraries.put(id, itene);
        iteneraryIDs.add(id);
        return Integer.toString(id);
    }
    
    @Path("/getFlights")
    @GET
    @Produces("application/json")
    public List<FlightInformation> getFlights(@QueryParam("startsAt") String startsAt, @QueryParam("endsAt") String endsAt, @QueryParam("date") String date) throws DatatypeConfigurationException {
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar date2 = df.newXMLGregorianCalendar("2015-09-20T18:30:00");
        
        lameDuckClient.LameDuckService service = new lameDuckClient.LameDuckService();
        lameDuckClient.LameDuck port = service.getLameDuckBindingPort3();
        //return port.getFlights(startLocation, endLocation, date);
        flightsTemp = port.getFlights(startsAt, endsAt, date2);
        //System.out.println(rest.size());
        return flightsTemp;
    }
    
    @Path("/addFlight")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response addFlight(@QueryParam("id") int custommerID, 
            @QueryParam("bookno") int bookingNumber){
        if(!checkcustomer(custommerID)){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Bad ID. ID not found").type(MediaType.TEXT_PLAIN).
                    build();
        }
        
        FlightInformation f = findFlightinTemp(bookingNumber);
        if(f == null){
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Flight with booking-number '"+bookingNumber+"' not found. Use getFlights first, to get it").type(MediaType.TEXT_PLAIN).
                    build();
        }
        Itinerary itine = itineraries.get(custommerID);
        itine.addFlight(f);
        return Response.
            status(Response.Status.OK).
            entity("Flight added").type(MediaType.TEXT_PLAIN).
            build();
       
    }
    
    @Path("/bookFlight")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response bookFlight(@QueryParam("id") int customerID, 
            @QueryParam("bookno") int bookingNumber){
        
        if(!checkcustomer(customerID)){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Bad ID. ID not found").type(MediaType.TEXT_PLAIN).
                    build();
        }
        Itinerary itene = itineraries.get(customerID);
       
        if(!itene.isFlightInItinerary(bookingNumber)){ //If the flight is not in the itinerary
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Flight with booking-number '"+bookingNumber+
                            "' not found. Add the flight using getFlights and "
                            + "add, before booking").type(MediaType.TEXT_PLAIN).
                    build();
        }
        BookFlight test = new BookFlight();
        
        AccountType acc = new AccountType();
        acc.setName("???");
        acc.setNumber("???");
        
        
        CreditCardInfoType card = new CreditCardInfoType();
        card.setName("bla bla");
        CreditCardInfoType.ExpirationDate date = new CreditCardInfoType.ExpirationDate();
        date.setMonth(7);
        date.setYear(2018);
        card.setExpirationDate(date);
        test.setBookingNumber(bookingNumber);
        test.setCreditCardInfo(card);
        test.setPrice(0);
        
        try{
            LameDuckService service = new LameDuckService();
            LameDuck port = service.getLameDuckBindingPort3();
            if(port.bookFlight(test)){
                if(!itene.setFlightConfirmed(bookingNumber)){
                    Logger.getLogger(TravelGood.class.getName()).
                            log(Level.SEVERE, "Something wierd happened!");
                }
                
                itineraries.put(customerID, itene);
                return Response.
                    status(Response.Status.OK).
                    entity("Flight w. booking number '"+bookingNumber+"' is booked").
                    type(MediaType.TEXT_PLAIN).
                    build();
                        
            } //end if LameDuck port
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Flight not booked!").
                    type(MediaType.TEXT_PLAIN).
                    build();
        
        }
        catch(BookingFailedFault e){
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("An error has occurred: "+e.getFaultInfo().getMessage()).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
    }
    
    @Path("/getItenerary/{id}")
    @GET
    @Produces("application/json")
    public Response getItenerary(@QueryParam("id") int customerID){
        if(!checkcustomer(customerID)){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Bad ID. ID not found").type(MediaType.TEXT_PLAIN).
                    build();
        }
        Itinerary itene = itineraries.get(customerID);
        itene.sortLists();
        return Response.
                    status(Response.Status.OK).
                    entity(itene).type(MediaType.APPLICATION_JSON).
                    build();
    }
    
   
    
    private void getSortedItenerary(int customerID){
        
    }
    
    
    /*
    
    
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

    private boolean checkcustomer(int customer){
        return itineraries.containsKey(customer);
    }

    
    public void cancelFlight(lameDuckClient.CancelFlight request) {
        
        
        //try{
        LameDuckService service = new LameDuckService();
        LameDuck port = service.getLameDuckBindingPort3();
        boolean res = port.cancelFlight(request);
        //}
       // catch(CreditCardFaultMessage e){
            
        //}
    }

    private FlightInformation findFlightinTemp(int bookingNumber) {
        for(FlightInformation f : flightsTemp){
            if(f.getBookingNumber() == bookingNumber){
                return f;
            }
        }
        return null;
        
    }
    
    
    
}

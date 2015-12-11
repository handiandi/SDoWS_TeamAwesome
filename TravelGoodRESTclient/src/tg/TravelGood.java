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
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


import lameDuckClient.*;
import niceViewClient.BookHotelRequest;
import niceViewClient.BookingFailedMessage;
import niceViewClient.CancellingFailedMessage;
import niceViewClient.GetHotelsRequest;
import niceViewClient.HotelInformation;
import niceViewClient.HotelList;

//import niceViewClient.HotelInformation;
/**
 *
 * @author Anders
 */
@Singleton
@Path("/itineraries")
public class TravelGood {
    List<HotelInformation> hotelsTemp = new ArrayList<>();
    List<FlightInformation> flightsTemp = new ArrayList<>();
    
    HashMap<Integer, Itinerary> itineraries = new HashMap<>();
   
    //List<Integer> iteneraryIDs = new ArrayList<>();
    
    public TravelGood(){
        
    }
  
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
        int id = 0;
        
        if(itineraries.isEmpty()){
            Itinerary itene = new Itinerary();
            itineraries.put(id, itene);
            return Integer.toString(id);
        }
        
        id = Collections.max(itineraries.keySet())+1;
        Itinerary itene = new Itinerary();
        itineraries.put(id, itene);
        return Integer.toString(id);
    }
    
    @Path("/getFlights")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlights(@QueryParam("startsAt") String startsAt, @QueryParam("endsAt") String endsAt, @QueryParam("date") String date) throws DatatypeConfigurationException {
        XMLGregorianCalendar date2 = null;
        try{
            DatatypeFactory df = DatatypeFactory.newInstance();
            date2 = df.newXMLGregorianCalendar(date); //"2015-09-20T18:30:00"
        }
        catch(DatatypeConfigurationException e){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Bad date string format").type(MediaType.TEXT_PLAIN).
                    build();
            
        }
        lameDuckClient.LameDuckService service = new lameDuckClient.LameDuckService();
        lameDuckClient.LameDuck port = service.getLameDuckBindingPort();
        flightsTemp = port.getFlights(startsAt, endsAt, date2);
        //return flightsTemp;
        
        /*
        return Response.
                    status(Response.Status.OK).
                    entity(flightsTemp).type(MediaType.APPLICATION_JSON).
                    build();
        */
        GenericEntity<List<FlightInformation>> entity = 
            new GenericEntity<List<FlightInformation>>(flightsTemp){};
        return Response.ok(entity).build();
    }
    
    @Path("/getHotels")
    @GET
    @Produces("application/json")
    public Response getHotels(@QueryParam("arrival") String arrival, 
                              @QueryParam("depart") String departure, 
                              @QueryParam("city") String city){
        XMLGregorianCalendar arrivalDate = null;
        XMLGregorianCalendar departureDate = null;
        try{
            DatatypeFactory df = DatatypeFactory.newInstance();
            arrivalDate = df.newXMLGregorianCalendar(arrival); //"2015-09-20T18:30:00"
        }
        catch(DatatypeConfigurationException e){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Arrival - Bad date string format").type(MediaType.TEXT_PLAIN).
                    build();
        }
        try{
            DatatypeFactory df = DatatypeFactory.newInstance();
            departureDate = df.newXMLGregorianCalendar(departure); //"2015-09-20T18:30:00"
        }
        catch(DatatypeConfigurationException e){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Departure - Bad date string format").type(MediaType.TEXT_PLAIN).
                    build();
        }
        GetHotelsRequest request = new GetHotelsRequest();
        request.setArrival(arrivalDate);
        request.setCity(city);
        request.setDeparture(departureDate);
         niceViewClient.NiceViewService service = new niceViewClient.NiceViewService();
        niceViewClient.NiceView port = service.getNiceViewBindingPort();
        hotelsTemp = port.getHotels(request).getHotel();
        GenericEntity<List<HotelInformation>> entity = 
            new GenericEntity<List<HotelInformation>>(hotelsTemp){};
        return Response.ok(entity).build();
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
        itine.flights.add(f);//addFlight(f);
        itineraries.put(custommerID, itine);
        return Response.
            status(Response.Status.OK).
            entity("Flight added").type(MediaType.TEXT_PLAIN).
            build();
       
    }
    
    @Path("/addHotel")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response addHotel(@QueryParam("id") int custommerID, 
            @QueryParam("bookno") int bookingNumber){
        if(!checkcustomer(custommerID)){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Bad ID. ID not found").type(MediaType.TEXT_PLAIN).
                    build();
        }
        
        HotelInformation h = findHotelinTemp(bookingNumber);
        if(h == null){
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Flight with booking-number '"+bookingNumber+"' not found. Use getFlights first, to get it").type(MediaType.TEXT_PLAIN).
                    build();
        }
        Itinerary itine = itineraries.get(custommerID);
        itine.addHotel(h);
        return Response.
            status(Response.Status.OK).
            entity("Flight added").type(MediaType.TEXT_PLAIN).
            build();
       
    }
    
    
    @Path("/bookItinerary/{id}")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response bookItinerary(@QueryParam("id") int customerID){
        
        if(!checkcustomer(customerID)){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Bad ID. ID not found").type(MediaType.TEXT_PLAIN).
                    build();
        }
        Itinerary itene = itineraries.get(customerID);
        List<FlightInformation> flights = itene.getFlights();
        List<FlightInformation> flightsBooked = new ArrayList<>();
        LameDuckService service = new LameDuckService();
        LameDuck port = service.getLameDuckBindingPort();
        boolean bookFailed = false;
        for(FlightInformation  f : flights){
            BookFlight bf = getBookFlight(f.getBookingNumber());
            
            try{
                if(port.bookFlight(bf)){
                    if(!itene.setFlightStatus(f.getBookingNumber(), "confirmed")){
                        Logger.getLogger(TravelGood.class.getName()).
                                log(Level.SEVERE, "Something wierd happened!");
                    }
                    flightsBooked.add(f);
                } //end if LameDuck port
                else{
                    bookFailed = true;
                    break;
                }
            }
            catch(BookingFailedFault e){
                bookFailed = true;
                break;
            }
        } //end for-loop flightInformation
        if(bookFailed){
            System.out.println(flightsBooked.size());
            if(!cancelFlightBookings(flightsBooked, itene)){ //If there is a fault i cancelling
                return Response.
                status(Response.Status.INTERNAL_SERVER_ERROR).
                entity("Some booking failed! Cancelled them, but not all was cancelled").
                type(MediaType.TEXT_PLAIN).
                build();
            }
            return Response.
                status(Response.Status.INTERNAL_SERVER_ERROR).
                entity("Some booking failed! Has cancelled those which got booked").
                type(MediaType.TEXT_PLAIN).
                build();
        }
        //Book hotels!
        List<HotelInformation> hotels = itene.getHotels();
        List<HotelInformation> hotelsBooked = new ArrayList<>();
        niceViewClient.NiceViewService niceViewService = new niceViewClient.NiceViewService();
        niceViewClient.NiceView niceViewport = niceViewService.getNiceViewBindingPort();
        for(HotelInformation h : hotels){
            BookHotelRequest bf = getBookHotelRequest(h.getBookingNumber());
            
            try{
                if(niceViewport.bookHotel(bf)){
                    if(!itene.setHotelStatus(h.getBookingNumber(), "confirmed")){
                        Logger.getLogger(TravelGood.class.getName()).
                                log(Level.SEVERE, "Something wierd happened! Hotel");
                    }
                    hotelsBooked.add(h);
                } //end if LameDuck port
                else{
                    bookFailed = true;
                    break;
                }
            }
            catch(BookingFailedMessage e){
                bookFailed = true;
                break;
            }
        } //end for-loop hotelInformation
        
        
        if(bookFailed){
            boolean resFlightCancel = cancelFlightBookings(flightsBooked, itene);
            String resHotelCancel = cancelHotelBookings(hotelsBooked, itene);
            if(!resHotelCancel.equals("Succeed")){ //If there is a fault i cancelling of hotels
                return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Some booking failed! Cancelled them, but not all was cancelled: "+resHotelCancel).
                    type(MediaType.TEXT_PLAIN).
                    build();
            }
            if(!resFlightCancel){
                return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Some booking failed! Cancelled them, but not all was cancelled").
                    type(MediaType.TEXT_PLAIN).
                    build();
            }
            return Response.
                status(Response.Status.INTERNAL_SERVER_ERROR).
                entity("Some booking failed! Has cancelled those which got booked").
                type(MediaType.TEXT_PLAIN).
                build();
        }
        else{
            itene.setBooked(true);
            itineraries.put(customerID, itene);
            return Response.
                status(Response.Status.OK).
                entity("Itinerary booked!").
                type(MediaType.TEXT_PLAIN).
                build(); 
        }
       /*
        if(!itine.isFlightInItinerary(bookingNumber)){ //If the flight is not in the itinerary
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Flight with booking-number '"+bookingNumber+
                            "' not found. Add the flight using getFlights and "
                            + "add, before booking").type(MediaType.TEXT_PLAIN).
                    build();
        }
        */
        
       
    }
    
    @Path("/cancelItinerary/{id}")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response cancelItinerary(@QueryParam("id") int customerID){
        if(!checkcustomer(customerID)){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Bad ID. ID not found").type(MediaType.TEXT_PLAIN).
                    build();
        }
        Itinerary itene = itineraries.get(customerID);
        XMLGregorianCalendar earlistDate = itene.getEarlistDate();
        XMLGregorianCalendar today = null;
        try{
        DatatypeFactory df = DatatypeFactory.newInstance();
        today = df.newXMLGregorianCalendar();
        }
        catch(DatatypeConfigurationException e){
            
        }
        if(today.compare(earlistDate)==1){
            return Response.
                status(Response.Status.FORBIDDEN).
                entity("You can't cancel the itinerary anymore do to the date").
                type(MediaType.TEXT_PLAIN).
                build();
        }
        List<FlightInformation> flights = itene.getFlights();
        
        LameDuckService service = new LameDuckService();
        LameDuck port = service.getLameDuckBindingPort();
        boolean cancelFailed = false;
        for(FlightInformation  f : flights){
            lameDuckClient.CancelFlight cf = new CancelFlight();
            cf.setBookingNumber(f.getBookingNumber());
            lameDuckClient.CreditCardInfoType credit = getCreditCardInfoFlight();
            cf.setCreditCardInfo(credit);
            try{
                if(port.cancelFlight(cf)){
                    if(!itene.setFlightStatus(f.getBookingNumber(), "cancelled")){
                        Logger.getLogger(TravelGood.class.getName()).
                                log(Level.SEVERE, "Something wierd happened!");
                    }
                } //end if LameDuck port
                else{
                    cancelFailed = true;
                }
            }
            catch(Exception e){
                cancelFailed = true;
            }
        } //end for-loop flightInformation
        
        //Cancelling hotels!
        List<HotelInformation> hotels = itene.getHotels();
        niceViewClient.NiceViewService niceViewService = new niceViewClient.NiceViewService();
        niceViewClient.NiceView niceViewport = niceViewService.getNiceViewBindingPort();
        for(HotelInformation h : hotels){
            niceViewClient.CancelHotelRequest chr = new niceViewClient.CancelHotelRequest();
            chr.setBookingNumber(h.getBookingNumber());
            try{
                if(niceViewport.cancelHotel(chr)){
                    if(!itene.setHotelStatus(h.getBookingNumber(), "cancelled")){
                        Logger.getLogger(TravelGood.class.getName()).
                                log(Level.SEVERE, "Something wierd happened! Hotel");
                    }
                } //end if LameDuck port
                else{
                    cancelFailed = true;
                }
            }
            catch(CancellingFailedMessage e){
                cancelFailed = true;
            }
        } //end for-loop flightInformation
        
        if(cancelFailed){
            return Response.
                status(Response.Status.INTERNAL_SERVER_ERROR).
                entity("Some cancelling failed!").
                type(MediaType.TEXT_PLAIN).
                build();
        }
        else{
            itene.setBooked(false);
            itineraries.put(customerID, itene);
            return Response.
                status(Response.Status.OK).
                entity("Itinerary cancelled!").
                type(MediaType.TEXT_PLAIN).
                build(); 
        }
       /*
        if(!itine.isFlightInItinerary(bookingNumber)){ //If the flight is not in the itinerary
            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Flight with booking-number '"+bookingNumber+
                            "' not found. Add the flight using getFlights and "
                            + "add, before booking").type(MediaType.TEXT_PLAIN).
                    build();
        }
        */
        
       
    }
    
    @Path("/getItinerary/{id}")
    @GET
    @Produces("application/json")
    public Response getItinerary(@QueryParam("id") int customerID){
        if(!checkcustomer(customerID)){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("Bad ID. ID not found").type(MediaType.TEXT_PLAIN).
                    build();
        }
        Itinerary itine = itineraries.get(customerID);
        itine.sortLists();
        System.out.println(itine.flights.size());
        GenericEntity<Itinerary> entity = 
            new GenericEntity<Itinerary>(itine){};
        
        return Response.ok(entity).build();
        /*
        return Response.
                    status(Response.Status.OK).
                    entity(itine).type(MediaType.APPLICATION_JSON_TYPE).
                    build();
        */
    }
    
   
    
   
    
    
    /*
    
    
    public void sortItenerary(){
        
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


    private FlightInformation findFlightinTemp(int bookingNumber) {
        for(FlightInformation f : flightsTemp){
            if(f.getBookingNumber() == bookingNumber){
                return f;
            }
        }
        return null;  
    }
    
    private HotelInformation findHotelinTemp(int bookingNumber) {
        for(HotelInformation h : hotelsTemp){
            if(h.getBookingNumber() == bookingNumber){
                return h;
            }
        }
        return null;  
    }
    private BookFlight getBookFlight(int bookingNumber){
        BookFlight bf = new BookFlight();
        bf.setBookingNumber(bookingNumber);
        lameDuckClient.CreditCardInfoType card = getCreditCardInfoFlight();
        
        bf.setCreditCardInfo(card);
        
        return bf;
    }
    
    private CreditCardInfoType getCreditCardInfoFlight(){
        lameDuckClient.CreditCardInfoType card = new lameDuckClient.CreditCardInfoType();
        card.setName("Anne Strandberg");
        card.setNumber("50408816");
        lameDuckClient.CreditCardInfoType.ExpirationDate date = 
                new lameDuckClient.CreditCardInfoType.ExpirationDate();
        date.setMonth(5);
        date.setYear(9);
        card.setExpirationDate(date);
        return card;
    }
    
    private BookHotelRequest getBookHotelRequest(int bookingNumber){
        BookHotelRequest hr = new BookHotelRequest();
                
        niceViewClient.CreditCardInfoType card = 
                new niceViewClient.CreditCardInfoType();
        card.setName("Anne Strandberg");
        card.setNumber("50408816");
        niceViewClient.CreditCardInfoType.ExpirationDate date = 
                new niceViewClient.CreditCardInfoType.ExpirationDate();
        date.setMonth(5);
        date.setYear(9);
        card.setExpirationDate(date);
        hr.setBookingNumber(bookingNumber);
        hr.setCreditCardInfo(card);
        
        
        return hr;
    }

    private boolean cancelFlightBookings(List<FlightInformation> flights, Itinerary itene){
        lameDuckClient.LameDuckService service = new lameDuckClient.LameDuckService();
        lameDuckClient.LameDuck port = service.getLameDuckBindingPort();
        
        boolean check = true;
        for(FlightInformation f : flights){
            lameDuckClient.CancelFlight cf = new CancelFlight();
            cf.setBookingNumber(f.getBookingNumber());
            lameDuckClient.CreditCardInfoType credit = getCreditCardInfoFlight();
            cf.setCreditCardInfo(credit);
            if(!port.cancelFlight(cf)){
                check = false;
            }
            else{
                itene.setFlightStatus(f.getBookingNumber(), "cancelled");
            }
            
        }
        return check;
    }

   private String cancelHotelBookings(List<HotelInformation> hotels, Itinerary itene){
        niceViewClient.NiceViewService niceViewService = new niceViewClient.NiceViewService();
        niceViewClient.NiceView niceViewport = niceViewService.getNiceViewBindingPort();
        
        String message = "Succeed";
        for(HotelInformation h : hotels){
            niceViewClient.CancelHotelRequest chr = new niceViewClient.CancelHotelRequest();
            chr.setBookingNumber(h.getBookingNumber());
            try{
                if(!niceViewport.cancelHotel(chr)){
                    message = "Fault!";
                }
                else{
                    itene.setHotelStatus(h.getBookingNumber(), "cancelled");
                }
            }
            catch(CancellingFailedMessage e){
                message = e.getFaultInfo().getMsg();
            }
        }
        return message;
    }
}

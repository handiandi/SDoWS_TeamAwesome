/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lameDuckClient.FlightInformation;
import niceViewClient.HotelInformation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tg.Itinerary;
/**
 *
 * @author Anders
 */
public class TravelGoodRESTtests {
    final String BASE = "http://localhost:8080/TGr/webresources/itineraries";
    Client client = ClientBuilder.newClient();
    WebTarget resource = client.target(BASE);
    
    Response bookItinerary(String id){
       WebTarget bookItinerary = resource.path("bookItinerary/"+id).queryParam("id", id);
       Invocation.Builder builder = bookItinerary.request();
       
       //Form form = new Form();
       //form.param("id", id);
       //Entity<String> entity = Entity.entity("entity", MediaType.WILDCARD_TYPE);
       return builder.put(Entity.entity("entity", MediaType.WILDCARD_TYPE));
    } 
    Response cancelItinerary(String id){
       WebTarget cancelItinerary = resource.path("cancelItinerary/"+id).queryParam("id", id);
       Invocation.Builder builder = cancelItinerary.request();
       return builder.put(Entity.entity("entity", MediaType.WILDCARD_TYPE));
    }            
    List<FlightInformation> getFlights(String start,String end, String date){
        WebTarget getFlights = resource.path("getFlights");
        return getFlights.queryParam("startLoc", start)
                .queryParam("endLoc",end)
                .queryParam("date", date)
                .request()
                .get(new GenericType<List<FlightInformation>>(){});
    }
    List<HotelInformation> getHotels(String arrival,String depart, String city){
        WebTarget getHotels = resource.path("getHotels");
        return getHotels.queryParam("arrival", arrival)
                .queryParam("depart",depart)
                .queryParam("city", city)
                .request()
                .get(new GenericType<List<HotelInformation>>(){});
    }
    Itinerary getItinerary(String id){
        WebTarget getItinerary = resource.path("getItinerary/"+id);
        return getItinerary.queryParam("id", id)
                .request()
                .get(new GenericType<Itinerary>(){});
    }
    int createItinerary(){
        
        Entity<String> entity = Entity.entity("entity", MediaType.TEXT_PLAIN);
        WebTarget createItinerary = resource.path("createItinerary");
        return Integer.parseInt(createItinerary.request().post(entity, String.class));
    }
    Response addFlight(String id,String bookno){
        GenericType<Response> generic = new GenericType<>(Response.class);
        Entity<String> entity = Entity.entity("entity", MediaType.WILDCARD_TYPE);
        WebTarget addFlight = resource.path("addFlight");
        return addFlight.queryParam("id", id).queryParam("bookno", bookno).request().post(entity, generic);
    }
    Response addHotel(String id,String bookno){
        GenericType<Response> generic = new GenericType<>(Response.class);
        Entity<String> entity = Entity.entity("entity", MediaType.WILDCARD_TYPE);
        WebTarget addHotel = resource.path("addHotel");
        return addHotel.queryParam("id", id).queryParam("bookno", bookno).request().post(entity, generic);
    }
    
    /*@Test
    public void testP1(){
        WebTarget r = 
                client.target("http://localhost:8080/TGr/webresources/itineraries/getFlights");
        
        String startLoc="Copenhagen";
        String endLoc = "Stockholm";
        String date = "2015-09-20T18:30:00";
        
        List<FlightInformation> res = r.queryParam("startLoc", startLoc).
                queryParam("endLoc", endLoc).
                queryParam("date", date).
                request().
                get(new GenericType<List<FlightInformation>>(){});
        
        //List<FlightInformation> res =r.request().get(new GenericType<List<FlightInformation>>(){});
        assertEquals(10, res.size());
        
         r = 
                client.target("http://localhost:8080/TGr/webresources/itineraries/addFlight");
        
        int id=0;
        int bookno=2;
        
        String res2 = r.
                request().
                post(Entity.entity(id, MediaType.WILDCARD_TYPE), Entity.entity(bookno, MediaType.WILDCARD_TYPE));
        
        assertEquals("Flight add", res2)
                
    }*/
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Before
    public void reset(){
        WebTarget reset = resource.path("reset");
        Invocation.Builder builder = reset.request();
        builder.put(Entity.entity("entity", MediaType.WILDCARD_TYPE));
    }
    
    @Test
    public void bookItineraryTest(){
        int id = createItinerary();
        Response r = bookItinerary(""+id);
        assertEquals(200,r.getStatus());
    }
    @Test
    public void cancelItineraryTest(){
        int id = createItinerary();
        bookItinerary(""+id);
        Response r = cancelItinerary(""+id);
        int stat = r.getStatus();
        assertEquals(200,stat);//
    }
    @Test
    public void getItineraryTest(){
        int bookno, id = createItinerary();
        List<FlightInformation> flights;
        FlightInformation fi;
        bookno = (fi = (flights = 
                getFlights("Copenhagen","Stockholm","2015-09-20T18:30:00"))
                .get(0))
                .getBookingNumber();
        assertFalse(flights.isEmpty());
        addFlight(""+id,""+bookno);
        //bookItinerary(""+id);
        Itinerary r = getItinerary(""+id);
        assertTrue(r.isFlightInItinerary(fi.getBookingNumber()));
        //assertEquals(fi.getAirlineReservationService(),r.getFlights().get(0).getAirlineReservationService());
    }
    @Test
    public void addFlightTest(){
        int bookno, id = createItinerary();
        List<FlightInformation> flights;
        FlightInformation fi;
        bookno = (fi = (flights = 
                getFlights("Copenhagen","Stockholm","2015-09-20T18:30:00"))
                .get(0))
                .getBookingNumber();
        Response r = addFlight(""+id,""+bookno);
        assertEquals(200,r.getStatus());
    }
    @Test
    public void addHotelTest(){
        int bookno, id = createItinerary();
        List<HotelInformation> hotels;
        HotelInformation hi;
        bookno = (hi = (hotels = 
                getHotels("2015-09-20T18:30:00","2015-09-23T18:30:00","København"))
                .get(0))
                .getBookingNumber();
        Response r = addFlight(""+id,""+bookno);
        assertEquals(200,r.getStatus());
    }
    @Test
    public void testP1(){
        int id = createItinerary();
        int bf1,bf2,bf3;
        int bh1,bh2;
        List<FlightInformation> flights = getFlights("Copenhagen","Stockholm","2015-09-20T18:30:00");
        addFlight(""+id,""+(bf1 = flights.get(1).getBookingNumber()));
        List<HotelInformation> hotels = getHotels("2015-09-20T18:30:00","2015-09-23T18:30:00","København");
        hotels.addAll(getHotels("2015-09-24T18:30:00","2015-09-25T18:30:00","Kgs. Lyngby"));
        addHotel(""+id,""+(bh1 = hotels.get(0).getBookingNumber()));
        addFlight(""+id,""+(bf2 = flights.get(2).getBookingNumber()));
        addFlight(""+id,""+(bf3 = flights.get(3).getBookingNumber()));
        addHotel(""+id,""+(bh2 = hotels.get(1).getBookingNumber()));
        Itinerary itin = getItinerary(""+id);
        assertEquals(3,itin.getFlights().size());
        assertTrue(itin.isFlightInItinerary(bf1));
        assertTrue(itin.isFlightInItinerary(bf2));
        assertTrue(itin.isFlightInItinerary(bf3));
        assertEquals(2,hotels.size());//itin.getHotels().size());
        assertNotSame(bh1,bh2);
        assertEquals(bh1,itin.getHotels().get(0).getBookingNumber());
        assertEquals(bh2,itin.getHotels().get(1).getBookingNumber());
        
        //asserts
    }
    @Test
    public void testP2(){
        //list of flight -> add -> cancel
        int id = createItinerary();
        List<FlightInformation> flights = getFlights("Copenhagen","Stockholm","2015-09-20T18:30:00");
        addFlight(""+id,""+flights.get(0).getBookingNumber());
        cancelItinerary(""+id);
        Itinerary i = getItinerary(""+id);
        assertEquals("unconfirmed",i.getFlights().get(0).getStatus());
        
    }
    @Test
    public void testB(){
        //make itin with 3 bookings -> get itin
        int id = createItinerary();
        List<FlightInformation> flights = getFlights("Copenhagen","Stockholm","2015-09-20T18:30:00");
        addFlight(""+id,""+flights.get(0).getBookingNumber());
        List<HotelInformation> hotels = getHotels("2015-09-20T18:30:00","2015-09-23T18:30:00","København");
        addHotel(""+id,""+hotels.get(0).getBookingNumber());
        addFlight(""+id,""+flights.get(1).getBookingNumber());
        Itinerary i = getItinerary(""+id);
        assertEquals("unconfirmed",i.getFlights().get(0).getStatus());
        assertEquals("unconfirmed",i.getFlights().get(1).getStatus());
        assertEquals("unconfirmed",i.getHotels().get(0).getStatus());
        bookItinerary(""+id);
        i = getItinerary(""+id);
        assertEquals("confirmed",i.getFlights().get(0).getStatus());
        assertEquals("confirmed",i.getFlights().get(1).getStatus());
        assertEquals("confirmed",i.getHotels().get(0).getStatus());
    }
    @Test
    public void testC1(){
        //book 3 stuff and cancel them
        int id = createItinerary();
        List<FlightInformation> flights = getFlights("Copenhagen","Stockholm","2015-09-20T18:30:00");
        addFlight(""+id,""+flights.get(0).getBookingNumber());
        List<HotelInformation> hotels = getHotels("2015-09-20T18:30:00","2015-09-23T18:30:00","København");
        addHotel(""+id,""+hotels.get(0).getBookingNumber());
        addFlight(""+id,""+flights.get(1).getBookingNumber());
        Itinerary i = getItinerary(""+id);
        assertEquals("unconfirmed",i.getFlights().get(0).getStatus());
        assertEquals("unconfirmed",i.getFlights().get(1).getStatus());
        assertEquals("unconfirmed",i.getHotels().get(0).getStatus());
        bookItinerary(""+id);
        i = getItinerary(""+id);
        assertEquals("confirmed",i.getFlights().get(0).getStatus());
        assertEquals("confirmed",i.getFlights().get(1).getStatus());
        assertEquals("confirmed",i.getHotels().get(0).getStatus());
        cancelItinerary(""+id);
        i = getItinerary(""+id);
        assertEquals("cancelled",i.getFlights().get(0).getStatus());
        assertEquals("cancelled",i.getFlights().get(1).getStatus());
        assertEquals("cancelled",i.getHotels().get(0).getStatus());
    }
    @Test
    public void testC2(){
        
    }
    /*
    @Test
    public void ComplexUCTest(){
        int id = createItinerary();
        Assert.assertNotNull(id);
        String start,end,date;
        int flightbookno = getFlights(start = "Copenhagen",end = "Stockholm",date = "2015-09-20T18:30:00").get(0).getBookingNumber();
        Assert.assertNotNull(flightbookno);
        Response r1 = addFlight(""+id,""+flightbookno);
        String avl,dpt,city;
        int hotelbookno = getHotels(avl = "2015-09-20T18:30:00",dpt = "2015-09-23T18:30:00",city = "København").get(0).getBookingNumber();
        Assert.assertNotNull(hotelbookno);
        Response r2 = addHotel(""+id,""+hotelbookno);
        Response r3 = bookItinerary(""+id);
        Response r4 = cancelItinerary(""+id);
        int afstat = r1.getStatus();
        int ahstat = r2.getStatus();
        int bistat = r3.getStatus();
        int cistat = r4.getStatus();
        
        assertEquals(200,afstat);
        assertEquals(200,ahstat);
        assertEquals(200,bistat);
        assertEquals(200,cistat);
    }  
    */
}

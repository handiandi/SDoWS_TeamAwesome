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
       WebTarget cancelItinerary = resource.path(BASE+"cancelItinerary/"+id).queryParam("id", id);
       Invocation.Builder builder = cancelItinerary.request();
       return builder.put(Entity.entity("entity", MediaType.WILDCARD_TYPE));
    }            
    List<FlightInformation> getFlights(String start,String end, String date){
        WebTarget getFlights = client.target(BASE+"/getFlights");
        return getFlights.queryParam("startLoc", start)
                .queryParam("endLoc",end)
                .queryParam("date", date)
                .request()
                .get(new GenericType<List<FlightInformation>>(){});
    }
    List<HotelInformation> getHotels(String arrival,String depart, String city){
        WebTarget getHotels = client.target(BASE+"/getHotels");
        return getHotels.queryParam("arrival", arrival)
                .queryParam("depart",depart)
                .queryParam("city", city)
                .request()
                .get(new GenericType<List<HotelInformation>>(){});
    }
    Itinerary getItinerary(String id){
        WebTarget getItinerary = client.target(BASE+"/getItinerary");
        return getItinerary.queryParam("id", id)
                .request()
                .get(new GenericType<Itinerary>(){});
    }
    int createItinerary(){
        
        Entity<String> entity = Entity.entity("entity", MediaType.TEXT_PLAIN);
        WebTarget createItinerary = client.target(BASE+"/createItinerary");
        return Integer.parseInt(createItinerary.request().post(entity, String.class));
    }
    Response addFlight(String id,String bookno){
        GenericType<Response> generic = new GenericType<>(Response.class);
        Entity<String> entity = Entity.entity("entity", MediaType.WILDCARD_TYPE);
        WebTarget addFlight = client.target(BASE+"/addFlight");
        return addFlight.queryParam("id", id).queryParam("bookno", bookno).request().post(entity, generic);
    }
    Response addHotel(String id,String bookno){
        GenericType<Response> generic = new GenericType<>(Response.class);
        Entity<String> entity = Entity.entity("entity", MediaType.WILDCARD_TYPE);
        WebTarget addHotel = client.target(BASE+"/addHotel");
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
    @Test
    public void bookItineraryTest(){
        int id = createItinerary();
        Response r = bookItinerary(""+id);
        assertEquals(200,r.getStatus());
    }
    @Test
    public void cancelItineraryTest(){
        int id = createItinerary();
        Response r = cancelItinerary(""+id);
        assertEquals(200,r.getStatus());
    }
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
}

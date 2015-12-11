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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import lameDuckClient.FlightInformation;
import org.junit.Test;
/**
 *
 * @author Anders
 */
public class TravelGoodRESTtests {
    String path = "http://localhost:8080/TGr/webresources/itineraries";
    Client client = ClientBuilder.newClient();
        WebTarget r = 
                client.target(path);
    
   
    
    @Test
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
        assertEquals(9, res.size());
        /*
         r = 
                client.target("http://localhost:8080/TGr/webresources/itineraries/addFlight");
        
        int id=0;
        int bookno=2;
        
        String res2 = r.
                request().
                post(Entity.entity(id, MediaType.WILDCARD_TYPE), Entity.entity(bookno, MediaType.WILDCARD_TYPE));
        
        assertEquals("Flight add", res2)
                */
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

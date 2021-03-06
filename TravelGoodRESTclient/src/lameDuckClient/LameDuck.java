
package lameDuckClient;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10-b140803.1500
 * Generated source version: 2.2
 * 
 */
@WebService(name = "LameDuck", targetNamespace = "http://LameDuck.ws")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface LameDuck {


    /**
     * 
     * @param date
     * @param startLocation
     * @param endLocation
     * @return
     *     returns java.util.List<lameDuckClient.FlightInformation>
     */
    @WebMethod
    @WebResult(name = "flightInformation", targetNamespace = "")
    @RequestWrapper(localName = "getFlights", targetNamespace = "http://LameDuck.ws", className = "lameDuckClient.GetFlights")
    @ResponseWrapper(localName = "getFlightsResponse", targetNamespace = "http://LameDuck.ws", className = "lameDuckClient.GetFlightsResponse")
    public List<FlightInformation> getFlights(
        @WebParam(name = "startLocation", targetNamespace = "")
        String startLocation,
        @WebParam(name = "endLocation", targetNamespace = "")
        String endLocation,
        @WebParam(name = "date", targetNamespace = "")
        XMLGregorianCalendar date);

    /**
     * 
     * @param request
     * @return
     *     returns boolean
     * @throws BookingFailedFault
     */
    @WebMethod
    @WebResult(name = "bookFlightResponse", targetNamespace = "http://LameDuck.ws", partName = "response")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public boolean bookFlight(
        @WebParam(name = "bookFlight", targetNamespace = "http://LameDuck.ws", partName = "request")
        BookFlight request)
        throws BookingFailedFault
    ;

    /**
     * 
     * @param request
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(name = "cancelFlightResponse", targetNamespace = "http://LameDuck.ws", partName = "response")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public boolean cancelFlight(
        @WebParam(name = "cancelFlight", targetNamespace = "http://LameDuck.ws", partName = "request")
        CancelFlight request);

}

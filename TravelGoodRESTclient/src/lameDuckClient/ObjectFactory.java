
package lameDuckClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the lameDuckClient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BookingFailedException_QNAME = new QName("http://LameDuck.ws", "bookingFailedException");
    private final static QName _CancelFlightResponse_QNAME = new QName("http://LameDuck.ws", "cancelFlightResponse");
    private final static QName _BookFlightResponse_QNAME = new QName("http://LameDuck.ws", "bookFlightResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: lameDuckClient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreditCardInfoType }
     * 
     */
    public CreditCardInfoType createCreditCardInfoType() {
        return new CreditCardInfoType();
    }

    /**
     * Create an instance of {@link GetFlights }
     * 
     */
    public GetFlights createGetFlights() {
        return new GetFlights();
    }

    /**
     * Create an instance of {@link BookFlight }
     * 
     */
    public BookFlight createBookFlight() {
        return new BookFlight();
    }

    /**
     * Create an instance of {@link CancelFlight }
     * 
     */
    public CancelFlight createCancelFlight() {
        return new CancelFlight();
    }

    /**
     * Create an instance of {@link BookingFailedException }
     * 
     */
    public BookingFailedException createBookingFailedException() {
        return new BookingFailedException();
    }

    /**
     * Create an instance of {@link GetFlightsResponse }
     * 
     */
    public GetFlightsResponse createGetFlightsResponse() {
        return new GetFlightsResponse();
    }

    /**
     * Create an instance of {@link FlightInformation }
     * 
     */
    public FlightInformation createFlightInformation() {
        return new FlightInformation();
    }

    /**
     * Create an instance of {@link Flight }
     * 
     */
    public Flight createFlight() {
        return new Flight();
    }

    /**
     * Create an instance of {@link AccountType }
     * 
     */
    public AccountType createAccountType() {
        return new AccountType();
    }

    /**
     * Create an instance of {@link CreditCardInfoType.ExpirationDate }
     * 
     */
    public CreditCardInfoType.ExpirationDate createCreditCardInfoTypeExpirationDate() {
        return new CreditCardInfoType.ExpirationDate();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookingFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LameDuck.ws", name = "bookingFailedException")
    public JAXBElement<BookingFailedException> createBookingFailedException(BookingFailedException value) {
        return new JAXBElement<BookingFailedException>(_BookingFailedException_QNAME, BookingFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LameDuck.ws", name = "cancelFlightResponse")
    public JAXBElement<Boolean> createCancelFlightResponse(Boolean value) {
        return new JAXBElement<Boolean>(_CancelFlightResponse_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LameDuck.ws", name = "bookFlightResponse")
    public JAXBElement<Boolean> createBookFlightResponse(Boolean value) {
        return new JAXBElement<Boolean>(_BookFlightResponse_QNAME, Boolean.class, null, value);
    }

}

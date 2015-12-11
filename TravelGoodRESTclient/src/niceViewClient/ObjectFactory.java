
package niceViewClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the niceViewClient package. 
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

    private final static QName _Success_QNAME = new QName("http://niceview.ws", "success");
    private final static QName _CancelHotelRequest_QNAME = new QName("http://niceview.ws", "cancelHotelRequest");
    private final static QName _BookHotelRequest_QNAME = new QName("http://niceview.ws", "bookHotelRequest");
    private final static QName _HotelList_QNAME = new QName("http://niceview.ws", "hotelList");
    private final static QName _GetHotelsRequest_QNAME = new QName("http://niceview.ws", "getHotelsRequest");
    private final static QName _CancellingFailedFault_QNAME = new QName("http://niceview.ws", "cancellingFailedFault");
    private final static QName _BookingFailedFault_QNAME = new QName("http://niceview.ws", "bookingFailedFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: niceViewClient
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
     * Create an instance of {@link BookHotelRequest }
     * 
     */
    public BookHotelRequest createBookHotelRequest() {
        return new BookHotelRequest();
    }

    /**
     * Create an instance of {@link HotelList }
     * 
     */
    public HotelList createHotelList() {
        return new HotelList();
    }

    /**
     * Create an instance of {@link GetHotelsRequest }
     * 
     */
    public GetHotelsRequest createGetHotelsRequest() {
        return new GetHotelsRequest();
    }

    /**
     * Create an instance of {@link CancellingFailedFaultType }
     * 
     */
    public CancellingFailedFaultType createCancellingFailedFaultType() {
        return new CancellingFailedFaultType();
    }

    /**
     * Create an instance of {@link CancelHotelRequest }
     * 
     */
    public CancelHotelRequest createCancelHotelRequest() {
        return new CancelHotelRequest();
    }

    /**
     * Create an instance of {@link BookingFailedFaultType }
     * 
     */
    public BookingFailedFaultType createBookingFailedFaultType() {
        return new BookingFailedFaultType();
    }

    /**
     * Create an instance of {@link HotelInformation }
     * 
     */
    public HotelInformation createHotelInformation() {
        return new HotelInformation();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Hotel }
     * 
     */
    public Hotel createHotel() {
        return new Hotel();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://niceview.ws", name = "success")
    public JAXBElement<Boolean> createSuccess(Boolean value) {
        return new JAXBElement<Boolean>(_Success_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelHotelRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://niceview.ws", name = "cancelHotelRequest")
    public JAXBElement<CancelHotelRequest> createCancelHotelRequest(CancelHotelRequest value) {
        return new JAXBElement<CancelHotelRequest>(_CancelHotelRequest_QNAME, CancelHotelRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookHotelRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://niceview.ws", name = "bookHotelRequest")
    public JAXBElement<BookHotelRequest> createBookHotelRequest(BookHotelRequest value) {
        return new JAXBElement<BookHotelRequest>(_BookHotelRequest_QNAME, BookHotelRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HotelList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://niceview.ws", name = "hotelList")
    public JAXBElement<HotelList> createHotelList(HotelList value) {
        return new JAXBElement<HotelList>(_HotelList_QNAME, HotelList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotelsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://niceview.ws", name = "getHotelsRequest")
    public JAXBElement<GetHotelsRequest> createGetHotelsRequest(GetHotelsRequest value) {
        return new JAXBElement<GetHotelsRequest>(_GetHotelsRequest_QNAME, GetHotelsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancellingFailedFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://niceview.ws", name = "cancellingFailedFault")
    public JAXBElement<CancellingFailedFaultType> createCancellingFailedFault(CancellingFailedFaultType value) {
        return new JAXBElement<CancellingFailedFaultType>(_CancellingFailedFault_QNAME, CancellingFailedFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookingFailedFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://niceview.ws", name = "bookingFailedFault")
    public JAXBElement<BookingFailedFaultType> createBookingFailedFault(BookingFailedFaultType value) {
        return new JAXBElement<BookingFailedFaultType>(_BookingFailedFault_QNAME, BookingFailedFaultType.class, null, value);
    }

}

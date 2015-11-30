/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niceview.ws;

import dk.dtu.imm.fastmoney.BankService;
import dk.dtu.imm.fastmoney.CreditCardFaultMessage;
import dk.dtu.imm.fastmoney.types.AccountType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;
import ws.niceview.BookingFailedMessage;
import ws.niceview.CancellingFailedMessage;
import ws.niceview.*;

/**
 *
 * @author jeppe
 */
@WebService(serviceName = "NiceViewService", portName = "NiceViewBindingPort", endpointInterface = "ws.niceview.NiceView", targetNamespace = "http://niceview.ws", wsdlLocation = "WEB-INF/wsdl/NiceView/NiceView.wsdl")
public class NiceView {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/BankService.wsdl")
    private BankService service;
    private static int bookingNumber;
    private static List<Hotel> allHotelsList;// = new List<>(); static i stedet
    private static List<HotelInformation> hotelsInAreaList = new ArrayList<>();
    
    private static Address createAddress(String country, String city, String street, int number, int postal){
        Address output = new Address();
        output.setCity(city);
        output.setCountry(country);
        output.setStreet(street);
        output.setNumber(number);
        output.setPostalCode(postal);
        return output;
    }
    private static Hotel createHotel(String name, Address address, boolean ccGuaranteeRequired, int price, String reservationService) {
        Hotel output = new Hotel();
        output.setName(name);
        output.setAddress(address);
        output.setGuaranteeRequired(ccGuaranteeRequired);
        output.setHotelReservationService(reservationService);
        output.setPrice(price);
        return output;
    }
    private static HotelInformation createHotelInformation(int bookingNumber,int price, Hotel hotel){
        HotelInformation output = new HotelInformation();
        output.setBookingNumber(bookingNumber);
        output.setPrice(price);
        output.setHotel(hotel);
        output.setStatus("available");
        return output;
    }
    private int bookRef = 0;

    private static AccountType ACCOUNT;
    private static final int GROUP = 14;
    private static final String ACCOUNT_NUMBER = "50308815";
    private static final String ACCOUNT_NAME = "NiceView";

    //private String[] arrivalDateArray;
    //private String[] departureDateArray;
    private ArrayList<HotelInformation> bookedHotelsList;

    static {
        //hotelsInAreaList = new List<>();
        ACCOUNT = new AccountType();
        ACCOUNT.setName(ACCOUNT_NAME);
        ACCOUNT.setNumber(ACCOUNT_NUMBER);
        allHotelsList = new ArrayList<>();
        allHotelsList.clear();
        bookingNumber = 0;
        //"Casa de Lyngby", "Lyngbyhovedgade 12", "Lyngby", true, 200, "reservationDOTdk", 0);
        //"Hotellet", "Nørre Alle 75", "København", false, 50, "StudentOffers", 0);
        Address a1 = createAddress("Denmark","Kgs. Lyngby","Lyngbyhovedgade",12,2800);
        Address a2 = createAddress("Denmark","København","Nørre Allé",75,007);
        Hotel CasaDeLyngby = createHotel("Casa de Lyngby",a1,true,200,"NiceView");
        Hotel Hotellet = createHotel("Hotellet",a2,false,50,"NiceView");
        
        allHotelsList.add(CasaDeLyngby);
        allHotelsList.add(Hotellet);
    }
    public ws.niceview.HotelList getHotels(ws.niceview.GetHotelsRequest request) {
        request.getCity();
        HotelList output = new HotelList();
        List<Hotel> list = new ArrayList<>();
        Date arrival = request.getArrival().toGregorianCalendar().getTime();
        Date departure = request.getDeparture().toGregorianCalendar().getTime();
        int days = (int) ((departure.getTime()-arrival.getTime())/(1000*60*60*24));
        for(Hotel h: allHotelsList){
            if(h.getAddress().getCity().equals(request.getCity())) 
                output.getHotel().add(createHotelInformation(bookingNumber++,h.getPrice()*days,h));
        }
        hotelsInAreaList.addAll(output.getHotel());
        return output;
        
    }

    public boolean bookHotel(ws.niceview.BookHotelRequest parameters) throws BookingFailedMessage {
        boolean valid = true;
        boolean paid = false;
        for(HotelInformation hi:hotelsInAreaList){
            if(parameters.getBookingNumber() == hi.getBookingNumber()){
                try {
                    if(hi.getHotel().isGuaranteeRequired())valid = validateCreditCard(GROUP,parameters.getCreditCardInfo(),hi.getPrice());
                    if(valid) chargeCreditCard(GROUP,parameters.getCreditCardInfo(),hi.getPrice(),ACCOUNT);
                    hotelsInAreaList.remove(hi);
                    bookedHotelsList.add(hi);
                    break;
                } catch (CreditCardFaultMessage ex) {throw new BookingFailedMessage("",null);}
                    
            }
        }
        return paid;
    }

    public boolean cancelHotel(ws.niceview.CancelHotelRequest parameters) throws CancellingFailedMessage {
        int bn = parameters.getBookingNumber();
        for(HotelInformation hi: bookedHotelsList){
            if(bn == hi.getBookingNumber()){
                hotelsInAreaList.add(hi);
                bookedHotelsList.remove(hi);
                break;
            }
        }
        return true;
    }

    private boolean chargeCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.fastmoney.types.AccountType account) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.chargeCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean validateCreditCard(int group, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }

    
    
}

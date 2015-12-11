
package niceViewClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Hotel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Hotel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hotelReservationService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="guaranteeRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="address" type="{http://niceview.ws}Address"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Hotel", propOrder = {
    "hotelReservationService",
    "guaranteeRequired",
    "address",
    "price",
    "name"
})
public class Hotel {

    @XmlElement(required = true)
    protected String hotelReservationService;
    protected boolean guaranteeRequired;
    @XmlElement(required = true)
    protected Address address;
    protected int price;
    @XmlElement(required = true)
    protected String name;

    /**
     * Gets the value of the hotelReservationService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelReservationService() {
        return hotelReservationService;
    }

    /**
     * Sets the value of the hotelReservationService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelReservationService(String value) {
        this.hotelReservationService = value;
    }

    /**
     * Gets the value of the guaranteeRequired property.
     * 
     */
    public boolean isGuaranteeRequired() {
        return guaranteeRequired;
    }

    /**
     * Sets the value of the guaranteeRequired property.
     * 
     */
    public void setGuaranteeRequired(boolean value) {
        this.guaranteeRequired = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(int value) {
        this.price = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}

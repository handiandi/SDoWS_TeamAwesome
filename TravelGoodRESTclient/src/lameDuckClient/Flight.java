
package lameDuckClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Flight complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Flight">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startAirport" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="endAirport" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="liftOffTime" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="landingTime" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="carrier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Flight", propOrder = {
    "startAirport",
    "endAirport",
    "liftOffTime",
    "landingTime",
    "carrier"
})
public class Flight {

    @XmlElement(required = true)
    protected String startAirport;
    @XmlElement(required = true)
    protected String endAirport;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar liftOffTime;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar landingTime;
    @XmlElement(required = true)
    protected String carrier;

    /**
     * Gets the value of the startAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartAirport() {
        return startAirport;
    }

    /**
     * Sets the value of the startAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartAirport(String value) {
        this.startAirport = value;
    }

    /**
     * Gets the value of the endAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndAirport() {
        return endAirport;
    }

    /**
     * Sets the value of the endAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndAirport(String value) {
        this.endAirport = value;
    }

    /**
     * Gets the value of the liftOffTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLiftOffTime() {
        return liftOffTime;
    }

    /**
     * Sets the value of the liftOffTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLiftOffTime(XMLGregorianCalendar value) {
        this.liftOffTime = value;
    }

    /**
     * Gets the value of the landingTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLandingTime() {
        return landingTime;
    }

    /**
     * Sets the value of the landingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLandingTime(XMLGregorianCalendar value) {
        this.landingTime = value;
    }

    /**
     * Gets the value of the carrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * Sets the value of the carrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrier(String value) {
        this.carrier = value;
    }

}

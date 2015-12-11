
package niceViewClient;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10-b140803.1500
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "cancellingFailedFault", targetNamespace = "http://niceview.ws")
public class CancellingFailedMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private CancellingFailedFaultType faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public CancellingFailedMessage(String message, CancellingFailedFaultType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public CancellingFailedMessage(String message, CancellingFailedFaultType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: niceViewClient.CancellingFailedFaultType
     */
    public CancellingFailedFaultType getFaultInfo() {
        return faultInfo;
    }

}


package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getServiceAddrResult" type="{http://www.openuri.org/}SrvAddEnqResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getServiceAddrResult"
})
@XmlRootElement(name = "getServiceAddrResponse")
public class GetServiceAddrResponse {

    protected SrvAddEnqResponse getServiceAddrResult;

    /**
     * Gets the value of the getServiceAddrResult property.
     * 
     * @return
     *     possible object is
     *     {@link SrvAddEnqResponse }
     *     
     */
    public SrvAddEnqResponse getGetServiceAddrResult() {
        return getServiceAddrResult;
    }

    /**
     * Sets the value of the getServiceAddrResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SrvAddEnqResponse }
     *     
     */
    public void setGetServiceAddrResult(SrvAddEnqResponse value) {
        this.getServiceAddrResult = value;
    }

}

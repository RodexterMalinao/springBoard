
package com.pccw.custProfileGateway.custProfile;

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
 *         &lt;element name="getCustNumEnquiryResult" type="{http://www.openuri.org/}CustNumEnqResponse" minOccurs="0"/>
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
    "getCustNumEnquiryResult"
})
@XmlRootElement(name = "getCustNumEnquiryResponse")
public class GetCustNumEnquiryResponse {

    protected CustNumEnqResponse getCustNumEnquiryResult;

    /**
     * Gets the value of the getCustNumEnquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link CustNumEnqResponse }
     *     
     */
    public CustNumEnqResponse getGetCustNumEnquiryResult() {
        return getCustNumEnquiryResult;
    }

    /**
     * Sets the value of the getCustNumEnquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustNumEnqResponse }
     *     
     */
    public void setGetCustNumEnquiryResult(CustNumEnqResponse value) {
        this.getCustNumEnquiryResult = value;
    }

}

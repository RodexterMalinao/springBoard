
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
 *         &lt;element name="getIddCallPlanEnquiryResult" type="{http://www.openuri.org/}CallPlanInfoEnqResponse" minOccurs="0"/>
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
    "getIddCallPlanEnquiryResult"
})
@XmlRootElement(name = "getIddCallPlanEnquiryResponse")
public class GetIddCallPlanEnquiryResponse {

    protected CallPlanInfoEnqResponse getIddCallPlanEnquiryResult;

    /**
     * Gets the value of the getIddCallPlanEnquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link CallPlanInfoEnqResponse }
     *     
     */
    public CallPlanInfoEnqResponse getGetIddCallPlanEnquiryResult() {
        return getIddCallPlanEnquiryResult;
    }

    /**
     * Sets the value of the getIddCallPlanEnquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallPlanInfoEnqResponse }
     *     
     */
    public void setGetIddCallPlanEnquiryResult(CallPlanInfoEnqResponse value) {
        this.getIddCallPlanEnquiryResult = value;
    }

}

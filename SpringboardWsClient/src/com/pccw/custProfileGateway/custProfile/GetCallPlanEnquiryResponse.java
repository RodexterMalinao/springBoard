
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
 *         &lt;element name="getCallPlanEnquiryResult" type="{http://www.openuri.org/}CallPlanEnqWrapperDTO" minOccurs="0"/>
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
    "getCallPlanEnquiryResult"
})
@XmlRootElement(name = "getCallPlanEnquiryResponse")
public class GetCallPlanEnquiryResponse {

    protected CallPlanEnqWrapperDTO getCallPlanEnquiryResult;

    /**
     * Gets the value of the getCallPlanEnquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link CallPlanEnqWrapperDTO }
     *     
     */
    public CallPlanEnqWrapperDTO getGetCallPlanEnquiryResult() {
        return getCallPlanEnquiryResult;
    }

    /**
     * Sets the value of the getCallPlanEnquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallPlanEnqWrapperDTO }
     *     
     */
    public void setGetCallPlanEnquiryResult(CallPlanEnqWrapperDTO value) {
        this.getCallPlanEnquiryResult = value;
    }

}

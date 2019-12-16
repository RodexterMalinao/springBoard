
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
 *         &lt;element name="getCrCallPlanEnquiryResult" type="{http://www.openuri.org/}CallPlanEnqWrapperDTO" minOccurs="0"/>
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
    "getCrCallPlanEnquiryResult"
})
@XmlRootElement(name = "getCrCallPlanEnquiryResponse")
public class GetCrCallPlanEnquiryResponse {

    protected CallPlanEnqWrapperDTO getCrCallPlanEnquiryResult;

    /**
     * Gets the value of the getCrCallPlanEnquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link CallPlanEnqWrapperDTO }
     *     
     */
    public CallPlanEnqWrapperDTO getGetCrCallPlanEnquiryResult() {
        return getCrCallPlanEnquiryResult;
    }

    /**
     * Sets the value of the getCrCallPlanEnquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallPlanEnqWrapperDTO }
     *     
     */
    public void setGetCrCallPlanEnquiryResult(CallPlanEnqWrapperDTO value) {
        this.getCrCallPlanEnquiryResult = value;
    }

}

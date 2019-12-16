
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
 *         &lt;element name="getCallPlanDtlEnquiryResult" type="{http://www.openuri.org/}CallPlanDtlEnqWrapperDTO" minOccurs="0"/>
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
    "getCallPlanDtlEnquiryResult"
})
@XmlRootElement(name = "getCallPlanDtlEnquiryResponse")
public class GetCallPlanDtlEnquiryResponse {

    protected CallPlanDtlEnqWrapperDTO getCallPlanDtlEnquiryResult;

    /**
     * Gets the value of the getCallPlanDtlEnquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link CallPlanDtlEnqWrapperDTO }
     *     
     */
    public CallPlanDtlEnqWrapperDTO getGetCallPlanDtlEnquiryResult() {
        return getCallPlanDtlEnquiryResult;
    }

    /**
     * Sets the value of the getCallPlanDtlEnquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallPlanDtlEnqWrapperDTO }
     *     
     */
    public void setGetCallPlanDtlEnquiryResult(CallPlanDtlEnqWrapperDTO value) {
        this.getCallPlanDtlEnquiryResult = value;
    }

}

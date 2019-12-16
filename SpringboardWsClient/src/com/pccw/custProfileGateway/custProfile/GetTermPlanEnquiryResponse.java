
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
 *         &lt;element name="getTermPlanEnquiryResult" type="{http://www.openuri.org/}TermPlanEnqWrapperDTO" minOccurs="0"/>
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
    "getTermPlanEnquiryResult"
})
@XmlRootElement(name = "getTermPlanEnquiryResponse")
public class GetTermPlanEnquiryResponse {

    protected TermPlanEnqWrapperDTO getTermPlanEnquiryResult;

    /**
     * Gets the value of the getTermPlanEnquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link TermPlanEnqWrapperDTO }
     *     
     */
    public TermPlanEnqWrapperDTO getGetTermPlanEnquiryResult() {
        return getTermPlanEnquiryResult;
    }

    /**
     * Sets the value of the getTermPlanEnquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermPlanEnqWrapperDTO }
     *     
     */
    public void setGetTermPlanEnquiryResult(TermPlanEnqWrapperDTO value) {
        this.getTermPlanEnquiryResult = value;
    }

}

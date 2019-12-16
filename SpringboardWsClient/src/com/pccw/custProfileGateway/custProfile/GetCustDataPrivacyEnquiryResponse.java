
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
 *         &lt;element name="getCustDataPrivacyEnquiryResult" type="{http://www.openuri.org/}ArrayOfDataPrivacyLtsDTO" minOccurs="0"/>
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
    "getCustDataPrivacyEnquiryResult"
})
@XmlRootElement(name = "getCustDataPrivacyEnquiryResponse")
public class GetCustDataPrivacyEnquiryResponse {

    protected ArrayOfDataPrivacyLtsDTO getCustDataPrivacyEnquiryResult;

    /**
     * Gets the value of the getCustDataPrivacyEnquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDataPrivacyLtsDTO }
     *     
     */
    public ArrayOfDataPrivacyLtsDTO getGetCustDataPrivacyEnquiryResult() {
        return getCustDataPrivacyEnquiryResult;
    }

    /**
     * Sets the value of the getCustDataPrivacyEnquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDataPrivacyLtsDTO }
     *     
     */
    public void setGetCustDataPrivacyEnquiryResult(ArrayOfDataPrivacyLtsDTO value) {
        this.getCustDataPrivacyEnquiryResult = value;
    }

}

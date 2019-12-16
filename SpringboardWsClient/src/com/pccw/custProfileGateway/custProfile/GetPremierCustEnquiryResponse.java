
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
 *         &lt;element name="getPremierCustEnquiryResult" type="{http://www.openuri.org/}CustTagEnqResponse" minOccurs="0"/>
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
    "getPremierCustEnquiryResult"
})
@XmlRootElement(name = "getPremierCustEnquiryResponse")
public class GetPremierCustEnquiryResponse {

    protected CustTagEnqResponse getPremierCustEnquiryResult;

    /**
     * Gets the value of the getPremierCustEnquiryResult property.
     * 
     * @return
     *     possible object is
     *     {@link CustTagEnqResponse }
     *     
     */
    public CustTagEnqResponse getGetPremierCustEnquiryResult() {
        return getPremierCustEnquiryResult;
    }

    /**
     * Sets the value of the getPremierCustEnquiryResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustTagEnqResponse }
     *     
     */
    public void setGetPremierCustEnquiryResult(CustTagEnqResponse value) {
        this.getPremierCustEnquiryResult = value;
    }

}


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
 *         &lt;element name="getPrimaryContactInfoResult" type="{http://www.openuri.org/}ContactInfoEnqResponse" minOccurs="0"/>
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
    "getPrimaryContactInfoResult"
})
@XmlRootElement(name = "getPrimaryContactInfoResponse")
public class GetPrimaryContactInfoResponse {

    protected ContactInfoEnqResponse getPrimaryContactInfoResult;

    /**
     * Gets the value of the getPrimaryContactInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoEnqResponse }
     *     
     */
    public ContactInfoEnqResponse getGetPrimaryContactInfoResult() {
        return getPrimaryContactInfoResult;
    }

    /**
     * Sets the value of the getPrimaryContactInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoEnqResponse }
     *     
     */
    public void setGetPrimaryContactInfoResult(ContactInfoEnqResponse value) {
        this.getPrimaryContactInfoResult = value;
    }

}

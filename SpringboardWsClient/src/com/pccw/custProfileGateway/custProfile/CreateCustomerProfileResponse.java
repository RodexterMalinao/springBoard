
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
 *         &lt;element name="createCustomerProfileResult" type="{http://www.openuri.org/}CustomerProfileResponse" minOccurs="0"/>
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
    "createCustomerProfileResult"
})
@XmlRootElement(name = "createCustomerProfileResponse")
public class CreateCustomerProfileResponse {

    protected CustomerProfileResponse createCustomerProfileResult;

    /**
     * Gets the value of the createCustomerProfileResult property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerProfileResponse }
     *     
     */
    public CustomerProfileResponse getCreateCustomerProfileResult() {
        return createCustomerProfileResult;
    }

    /**
     * Sets the value of the createCustomerProfileResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerProfileResponse }
     *     
     */
    public void setCreateCustomerProfileResult(CustomerProfileResponse value) {
        this.createCustomerProfileResult = value;
    }

}

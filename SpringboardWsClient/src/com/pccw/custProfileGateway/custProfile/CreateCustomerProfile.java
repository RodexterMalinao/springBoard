
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
 *         &lt;element name="customerProfileDTO" type="{http://www.openuri.org/}CustomerProfileDTO" minOccurs="0"/>
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
    "customerProfileDTO"
})
@XmlRootElement(name = "createCustomerProfile")
public class CreateCustomerProfile {

    protected CustomerProfileDTO customerProfileDTO;

    /**
     * Gets the value of the customerProfileDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerProfileDTO }
     *     
     */
    public CustomerProfileDTO getCustomerProfileDTO() {
        return customerProfileDTO;
    }

    /**
     * Sets the value of the customerProfileDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerProfileDTO }
     *     
     */
    public void setCustomerProfileDTO(CustomerProfileDTO value) {
        this.customerProfileDTO = value;
    }

}

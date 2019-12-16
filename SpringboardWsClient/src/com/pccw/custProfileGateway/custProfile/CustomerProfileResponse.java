
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerProfileResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerProfileResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="CustomerDTO" type="{http://www.openuri.org/}CustomerDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerProfileResponse", propOrder = {
    "customerDTO"
})
public class CustomerProfileResponse
    extends ServiceResponse
{

    @XmlElement(name = "CustomerDTO")
    protected CustomerDTO customerDTO;

    /**
     * Gets the value of the customerDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerDTO }
     *     
     */
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    /**
     * Sets the value of the customerDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerDTO }
     *     
     */
    public void setCustomerDTO(CustomerDTO value) {
        this.customerDTO = value;
    }

}

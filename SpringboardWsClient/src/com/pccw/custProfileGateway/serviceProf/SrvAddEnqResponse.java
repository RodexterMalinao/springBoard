
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SrvAddEnqResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SrvAddEnqResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="AddressDTO" type="{http://www.openuri.org/}AddressDTO" minOccurs="0"/>
 *         &lt;element name="FreeFmtAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SrvAddEnqResponse", propOrder = {
    "addressDTO",
    "freeFmtAddr"
})
public class SrvAddEnqResponse
    extends ServiceResponse
{

    @XmlElement(name = "AddressDTO")
    protected AddressDTO addressDTO;
    @XmlElement(name = "FreeFmtAddr")
    protected String freeFmtAddr;

    /**
     * Gets the value of the addressDTO property.
     * 
     * @return
     *     possible object is
     *     {@link AddressDTO }
     *     
     */
    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    /**
     * Sets the value of the addressDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressDTO }
     *     
     */
    public void setAddressDTO(AddressDTO value) {
        this.addressDTO = value;
    }

    /**
     * Gets the value of the freeFmtAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeFmtAddr() {
        return freeFmtAddr;
    }

    /**
     * Sets the value of the freeFmtAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeFmtAddr(String value) {
        this.freeFmtAddr = value;
    }

}

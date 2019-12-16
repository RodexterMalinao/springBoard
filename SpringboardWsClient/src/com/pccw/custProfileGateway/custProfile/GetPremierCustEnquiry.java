
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
 *         &lt;element name="custTagEnqBaseDTO" type="{http://www.openuri.org/}CustTagEnqBaseDTO" minOccurs="0"/>
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
    "custTagEnqBaseDTO"
})
@XmlRootElement(name = "getPremierCustEnquiry")
public class GetPremierCustEnquiry {

    protected CustTagEnqBaseDTO custTagEnqBaseDTO;

    /**
     * Gets the value of the custTagEnqBaseDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CustTagEnqBaseDTO }
     *     
     */
    public CustTagEnqBaseDTO getCustTagEnqBaseDTO() {
        return custTagEnqBaseDTO;
    }

    /**
     * Sets the value of the custTagEnqBaseDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustTagEnqBaseDTO }
     *     
     */
    public void setCustTagEnqBaseDTO(CustTagEnqBaseDTO value) {
        this.custTagEnqBaseDTO = value;
    }

}


package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateCustInfoEnqResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateCustInfoEnqResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="CustInfoMatched" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateCustInfoEnqResponse", propOrder = {
    "custInfoMatched"
})
public class ValidateCustInfoEnqResponse
    extends ServiceResponse
{

    @XmlElement(name = "CustInfoMatched")
    protected String custInfoMatched;

    /**
     * Gets the value of the custInfoMatched property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustInfoMatched() {
        return custInfoMatched;
    }

    /**
     * Sets the value of the custInfoMatched property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustInfoMatched(String value) {
        this.custInfoMatched = value;
    }

}

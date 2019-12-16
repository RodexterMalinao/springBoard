
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
 *         &lt;element name="validateCustInfoResult" type="{http://www.openuri.org/}ValidateCustInfoEnqResponse" minOccurs="0"/>
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
    "validateCustInfoResult"
})
@XmlRootElement(name = "validateCustInfoResponse")
public class ValidateCustInfoResponse {

    protected ValidateCustInfoEnqResponse validateCustInfoResult;

    /**
     * Gets the value of the validateCustInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link ValidateCustInfoEnqResponse }
     *     
     */
    public ValidateCustInfoEnqResponse getValidateCustInfoResult() {
        return validateCustInfoResult;
    }

    /**
     * Sets the value of the validateCustInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateCustInfoEnqResponse }
     *     
     */
    public void setValidateCustInfoResult(ValidateCustInfoEnqResponse value) {
        this.validateCustInfoResult = value;
    }

}

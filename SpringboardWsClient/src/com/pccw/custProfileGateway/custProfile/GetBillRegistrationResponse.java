
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
 *         &lt;element name="getBillRegistrationResult" type="{http://www.openuri.org/}BillRegWrapperDTO" minOccurs="0"/>
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
    "getBillRegistrationResult"
})
@XmlRootElement(name = "getBillRegistrationResponse")
public class GetBillRegistrationResponse {

    protected BillRegWrapperDTO getBillRegistrationResult;

    /**
     * Gets the value of the getBillRegistrationResult property.
     * 
     * @return
     *     possible object is
     *     {@link BillRegWrapperDTO }
     *     
     */
    public BillRegWrapperDTO getGetBillRegistrationResult() {
        return getBillRegistrationResult;
    }

    /**
     * Sets the value of the getBillRegistrationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillRegWrapperDTO }
     *     
     */
    public void setGetBillRegistrationResult(BillRegWrapperDTO value) {
        this.getBillRegistrationResult = value;
    }

}

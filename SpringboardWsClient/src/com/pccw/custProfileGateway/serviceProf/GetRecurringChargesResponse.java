
package com.pccw.custProfileGateway.serviceProf;

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
 *         &lt;element name="getRecurringChargesResult" type="{http://www.openuri.org/}ArrayOfOfferDetailRecurringChargesDTO" minOccurs="0"/>
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
    "getRecurringChargesResult"
})
@XmlRootElement(name = "getRecurringChargesResponse")
public class GetRecurringChargesResponse {

    protected ArrayOfOfferDetailRecurringChargesDTO getRecurringChargesResult;

    /**
     * Gets the value of the getRecurringChargesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOfferDetailRecurringChargesDTO }
     *     
     */
    public ArrayOfOfferDetailRecurringChargesDTO getGetRecurringChargesResult() {
        return getRecurringChargesResult;
    }

    /**
     * Sets the value of the getRecurringChargesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOfferDetailRecurringChargesDTO }
     *     
     */
    public void setGetRecurringChargesResult(ArrayOfOfferDetailRecurringChargesDTO value) {
        this.getRecurringChargesResult = value;
    }

}

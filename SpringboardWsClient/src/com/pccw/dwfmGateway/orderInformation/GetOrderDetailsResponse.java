
package com.pccw.dwfmGateway.orderInformation;

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
 *         &lt;element name="getOrderDetailsResult" type="{http://www.openuri.org/}OrderInformationResponse" minOccurs="0"/>
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
    "getOrderDetailsResult"
})
@XmlRootElement(name = "getOrderDetailsResponse")
public class GetOrderDetailsResponse {

    protected OrderInformationResponse getOrderDetailsResult;

    /**
     * Gets the value of the getOrderDetailsResult property.
     * 
     * @return
     *     possible object is
     *     {@link OrderInformationResponse }
     *     
     */
    public OrderInformationResponse getGetOrderDetailsResult() {
        return getOrderDetailsResult;
    }

    /**
     * Sets the value of the getOrderDetailsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderInformationResponse }
     *     
     */
    public void setGetOrderDetailsResult(OrderInformationResponse value) {
        this.getOrderDetailsResult = value;
    }

}

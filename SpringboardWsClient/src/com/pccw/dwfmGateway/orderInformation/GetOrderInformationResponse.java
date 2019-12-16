
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
 *         &lt;element name="getOrderInformationResult" type="{http://www.openuri.org/}OrderInformationResponse" minOccurs="0"/>
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
    "getOrderInformationResult"
})
@XmlRootElement(name = "getOrderInformationResponse")
public class GetOrderInformationResponse {

    protected OrderInformationResponse getOrderInformationResult;

    /**
     * Gets the value of the getOrderInformationResult property.
     * 
     * @return
     *     possible object is
     *     {@link OrderInformationResponse }
     *     
     */
    public OrderInformationResponse getGetOrderInformationResult() {
        return getOrderInformationResult;
    }

    /**
     * Sets the value of the getOrderInformationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderInformationResponse }
     *     
     */
    public void setGetOrderInformationResult(OrderInformationResponse value) {
        this.getOrderInformationResult = value;
    }

}

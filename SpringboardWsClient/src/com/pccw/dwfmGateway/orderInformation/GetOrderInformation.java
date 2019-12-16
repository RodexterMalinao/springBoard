
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
 *         &lt;element name="pOrderInfo" type="{http://www.openuri.org/}OrderInformationInput" minOccurs="0"/>
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
    "pOrderInfo"
})
@XmlRootElement(name = "getOrderInformation")
public class GetOrderInformation {

    protected OrderInformationInput pOrderInfo;

    /**
     * Gets the value of the pOrderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrderInformationInput }
     *     
     */
    public OrderInformationInput getPOrderInfo() {
        return pOrderInfo;
    }

    /**
     * Sets the value of the pOrderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderInformationInput }
     *     
     */
    public void setPOrderInfo(OrderInformationInput value) {
        this.pOrderInfo = value;
    }

}

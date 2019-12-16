
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
 *         &lt;element name="pOrderDetailsIn" type="{http://www.openuri.org/}ArrayOfOrderDetailsIn" minOccurs="0"/>
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
    "pOrderDetailsIn"
})
@XmlRootElement(name = "getOrderDetails")
public class GetOrderDetails {

    protected ArrayOfOrderDetailsIn pOrderDetailsIn;

    /**
     * Gets the value of the pOrderDetailsIn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderDetailsIn }
     *     
     */
    public ArrayOfOrderDetailsIn getPOrderDetailsIn() {
        return pOrderDetailsIn;
    }

    /**
     * Sets the value of the pOrderDetailsIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderDetailsIn }
     *     
     */
    public void setPOrderDetailsIn(ArrayOfOrderDetailsIn value) {
        this.pOrderDetailsIn = value;
    }

}

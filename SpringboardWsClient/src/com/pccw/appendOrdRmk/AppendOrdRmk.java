
package com.pccw.appendOrdRmk;

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
 *         &lt;element name="pOrderRemarkDTO" type="{http://www.openuri.org/}OrderRemarkDTO" minOccurs="0"/>
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
    "pOrderRemarkDTO"
})
@XmlRootElement(name = "appendOrdRmk")
public class AppendOrdRmk {

    protected OrderRemarkDTO pOrderRemarkDTO;

    /**
     * Gets the value of the pOrderRemarkDTO property.
     * 
     * @return
     *     possible object is
     *     {@link OrderRemarkDTO }
     *     
     */
    public OrderRemarkDTO getPOrderRemarkDTO() {
        return pOrderRemarkDTO;
    }

    /**
     * Sets the value of the pOrderRemarkDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderRemarkDTO }
     *     
     */
    public void setPOrderRemarkDTO(OrderRemarkDTO value) {
        this.pOrderRemarkDTO = value;
    }

}

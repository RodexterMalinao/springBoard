
package com.pccw.appendOrdRmk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderRemarkDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderRemarkDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceRequestDTO">
 *       &lt;sequence>
 *         &lt;element name="OrderRemark" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderRemarkDTO", propOrder = {
    "orderRemark"
})
public class OrderRemarkDTO
    extends ServiceRequestDTO
{

    @XmlElement(name = "OrderRemark")
    protected ArrayOfString orderRemark;

    /**
     * Gets the value of the orderRemark property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getOrderRemark() {
        return orderRemark;
    }

    /**
     * Sets the value of the orderRemark property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setOrderRemark(ArrayOfString value) {
        this.orderRemark = value;
    }

}

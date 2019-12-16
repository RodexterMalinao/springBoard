
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderDetailsOutList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderDetailsOutList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdDtlOutArray" type="{http://www.openuri.org/}ArrayOfOrderDetailsOut" minOccurs="0"/>
 *         &lt;element name="DrgRtnSts" type="{http://www.openuri.org/}DragonReturnStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderDetailsOutList", propOrder = {
    "ordDtlOutArray",
    "drgRtnSts"
})
public class OrderDetailsOutList {

    @XmlElement(name = "OrdDtlOutArray")
    protected ArrayOfOrderDetailsOut ordDtlOutArray;
    @XmlElement(name = "DrgRtnSts")
    protected DragonReturnStatus drgRtnSts;

    /**
     * Gets the value of the ordDtlOutArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderDetailsOut }
     *     
     */
    public ArrayOfOrderDetailsOut getOrdDtlOutArray() {
        return ordDtlOutArray;
    }

    /**
     * Sets the value of the ordDtlOutArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderDetailsOut }
     *     
     */
    public void setOrdDtlOutArray(ArrayOfOrderDetailsOut value) {
        this.ordDtlOutArray = value;
    }

    /**
     * Gets the value of the drgRtnSts property.
     * 
     * @return
     *     possible object is
     *     {@link DragonReturnStatus }
     *     
     */
    public DragonReturnStatus getDrgRtnSts() {
        return drgRtnSts;
    }

    /**
     * Sets the value of the drgRtnSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link DragonReturnStatus }
     *     
     */
    public void setDrgRtnSts(DragonReturnStatus value) {
        this.drgRtnSts = value;
    }

}

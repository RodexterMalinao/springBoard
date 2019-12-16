
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderDetailsOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderDetailsOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdDtlOutAArray" type="{http://www.openuri.org/}ArrayOfOrderDetailsOutA" minOccurs="0"/>
 *         &lt;element name="OrdDtlOutBArray" type="{http://www.openuri.org/}ArrayOfOrderDetailsOutB" minOccurs="0"/>
 *         &lt;element name="OrdDtlOutDArray" type="{http://www.openuri.org/}ArrayOfOrderDetailsOutD" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderDetailsOut", propOrder = {
    "ordDtlOutAArray",
    "ordDtlOutBArray",
    "ordDtlOutDArray"
})
public class OrderDetailsOut {

    @XmlElement(name = "OrdDtlOutAArray")
    protected ArrayOfOrderDetailsOutA ordDtlOutAArray;
    @XmlElement(name = "OrdDtlOutBArray")
    protected ArrayOfOrderDetailsOutB ordDtlOutBArray;
    @XmlElement(name = "OrdDtlOutDArray")
    protected ArrayOfOrderDetailsOutD ordDtlOutDArray;

    /**
     * Gets the value of the ordDtlOutAArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderDetailsOutA }
     *     
     */
    public ArrayOfOrderDetailsOutA getOrdDtlOutAArray() {
        return ordDtlOutAArray;
    }

    /**
     * Sets the value of the ordDtlOutAArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderDetailsOutA }
     *     
     */
    public void setOrdDtlOutAArray(ArrayOfOrderDetailsOutA value) {
        this.ordDtlOutAArray = value;
    }

    /**
     * Gets the value of the ordDtlOutBArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderDetailsOutB }
     *     
     */
    public ArrayOfOrderDetailsOutB getOrdDtlOutBArray() {
        return ordDtlOutBArray;
    }

    /**
     * Sets the value of the ordDtlOutBArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderDetailsOutB }
     *     
     */
    public void setOrdDtlOutBArray(ArrayOfOrderDetailsOutB value) {
        this.ordDtlOutBArray = value;
    }

    /**
     * Gets the value of the ordDtlOutDArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderDetailsOutD }
     *     
     */
    public ArrayOfOrderDetailsOutD getOrdDtlOutDArray() {
        return ordDtlOutDArray;
    }

    /**
     * Sets the value of the ordDtlOutDArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderDetailsOutD }
     *     
     */
    public void setOrdDtlOutDArray(ArrayOfOrderDetailsOutD value) {
        this.ordDtlOutDArray = value;
    }

}

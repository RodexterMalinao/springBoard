
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DragonReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DragonReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DragonReturnStatus" type="{http://www.openuri.org/}DragonReturnStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DragonReturn", propOrder = {
    "dragonReturnStatus"
})
@XmlSeeAlso({
    OrderInformationOutList.class
})
public class DragonReturn {

    @XmlElement(name = "DragonReturnStatus")
    protected DragonReturnStatus dragonReturnStatus;

    /**
     * Gets the value of the dragonReturnStatus property.
     * 
     * @return
     *     possible object is
     *     {@link DragonReturnStatus }
     *     
     */
    public DragonReturnStatus getDragonReturnStatus() {
        return dragonReturnStatus;
    }

    /**
     * Sets the value of the dragonReturnStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DragonReturnStatus }
     *     
     */
    public void setDragonReturnStatus(DragonReturnStatus value) {
        this.dragonReturnStatus = value;
    }

}

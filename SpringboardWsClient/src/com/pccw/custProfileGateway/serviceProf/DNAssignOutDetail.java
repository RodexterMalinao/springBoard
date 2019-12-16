
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DNAssignOutDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DNAssignOutDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GatewayNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InventSts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DNAssignOutDetail", propOrder = {
    "gatewayNum",
    "inventSts"
})
public class DNAssignOutDetail {

    @XmlElement(name = "GatewayNum")
    protected String gatewayNum;
    @XmlElement(name = "InventSts")
    protected String inventSts;

    /**
     * Gets the value of the gatewayNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatewayNum() {
        return gatewayNum;
    }

    /**
     * Sets the value of the gatewayNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatewayNum(String value) {
        this.gatewayNum = value;
    }

    /**
     * Gets the value of the inventSts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInventSts() {
        return inventSts;
    }

    /**
     * Sets the value of the inventSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInventSts(String value) {
        this.inventSts = value;
    }

}

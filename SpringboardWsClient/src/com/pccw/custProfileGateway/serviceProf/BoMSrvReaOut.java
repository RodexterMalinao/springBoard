
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BoMSrvReaOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BoMSrvReaOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OreaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OreaDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BoMSrvReaOut", propOrder = {
    "oreaCd",
    "oreaDesc"
})
public class BoMSrvReaOut {

    @XmlElement(name = "OreaCd")
    protected String oreaCd;
    @XmlElement(name = "OreaDesc")
    protected String oreaDesc;

    /**
     * Gets the value of the oreaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOreaCd() {
        return oreaCd;
    }

    /**
     * Sets the value of the oreaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOreaCd(String value) {
        this.oreaCd = value;
    }

    /**
     * Gets the value of the oreaDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOreaDesc() {
        return oreaDesc;
    }

    /**
     * Sets the value of the oreaDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOreaDesc(String value) {
        this.oreaDesc = value;
    }

}

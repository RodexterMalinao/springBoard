
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BoMSrvReqOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BoMSrvReqOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OreqId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BoMSrvReqOut", propOrder = {
    "oreqId"
})
public class BoMSrvReqOut {

    @XmlElement(name = "OreqId")
    protected String oreqId;

    /**
     * Gets the value of the oreqId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOreqId() {
        return oreqId;
    }

    /**
     * Sets the value of the oreqId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOreqId(String value) {
        this.oreqId = value;
    }

}

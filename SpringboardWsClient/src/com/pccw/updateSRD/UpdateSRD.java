
package com.pccw.updateSRD;

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
 *         &lt;element name="pSrdDTO" type="{http://www.openuri.org/}SrdDTO" minOccurs="0"/>
 *         &lt;element name="pWithAppointment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "pSrdDTO",
    "pWithAppointment"
})
@XmlRootElement(name = "UpdateSRD")
public class UpdateSRD {

    protected SrdDTO pSrdDTO;
    protected String pWithAppointment;

    /**
     * Gets the value of the pSrdDTO property.
     * 
     * @return
     *     possible object is
     *     {@link SrdDTO }
     *     
     */
    public SrdDTO getPSrdDTO() {
        return pSrdDTO;
    }

    /**
     * Sets the value of the pSrdDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link SrdDTO }
     *     
     */
    public void setPSrdDTO(SrdDTO value) {
        this.pSrdDTO = value;
    }

    /**
     * Gets the value of the pWithAppointment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPWithAppointment() {
        return pWithAppointment;
    }

    /**
     * Sets the value of the pWithAppointment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPWithAppointment(String value) {
        this.pWithAppointment = value;
    }

}

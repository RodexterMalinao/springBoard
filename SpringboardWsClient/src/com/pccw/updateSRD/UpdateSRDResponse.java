
package com.pccw.updateSRD;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="UpdateSRDResult" type="{http://www.openuri.org/}ServiceResponseDTO" minOccurs="0"/>
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
    "updateSRDResult"
})
@XmlRootElement(name = "UpdateSRDResponse")
public class UpdateSRDResponse {

    @XmlElement(name = "UpdateSRDResult")
    protected ServiceResponseDTO updateSRDResult;

    /**
     * Gets the value of the updateSRDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceResponseDTO }
     *     
     */
    public ServiceResponseDTO getUpdateSRDResult() {
        return updateSRDResult;
    }

    /**
     * Sets the value of the updateSRDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceResponseDTO }
     *     
     */
    public void setUpdateSRDResult(ServiceResponseDTO value) {
        this.updateSRDResult = value;
    }

}

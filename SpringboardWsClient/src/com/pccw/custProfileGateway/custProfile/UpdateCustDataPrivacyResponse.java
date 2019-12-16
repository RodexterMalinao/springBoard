
package com.pccw.custProfileGateway.custProfile;

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
 *         &lt;element name="updateCustDataPrivacyResult" type="{http://www.openuri.org/}DataPrivacyLtsDTO" minOccurs="0"/>
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
    "updateCustDataPrivacyResult"
})
@XmlRootElement(name = "updateCustDataPrivacyResponse")
public class UpdateCustDataPrivacyResponse {

    protected DataPrivacyLtsDTO updateCustDataPrivacyResult;

    /**
     * Gets the value of the updateCustDataPrivacyResult property.
     * 
     * @return
     *     possible object is
     *     {@link DataPrivacyLtsDTO }
     *     
     */
    public DataPrivacyLtsDTO getUpdateCustDataPrivacyResult() {
        return updateCustDataPrivacyResult;
    }

    /**
     * Sets the value of the updateCustDataPrivacyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataPrivacyLtsDTO }
     *     
     */
    public void setUpdateCustDataPrivacyResult(DataPrivacyLtsDTO value) {
        this.updateCustDataPrivacyResult = value;
    }

}

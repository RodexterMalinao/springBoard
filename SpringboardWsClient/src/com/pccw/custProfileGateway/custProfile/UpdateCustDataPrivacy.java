
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
 *         &lt;element name="dataPrivacyObj" type="{http://www.openuri.org/}ArrayOfDataPrivacyLtsDTO" minOccurs="0"/>
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
    "dataPrivacyObj"
})
@XmlRootElement(name = "updateCustDataPrivacy")
public class UpdateCustDataPrivacy {

    protected ArrayOfDataPrivacyLtsDTO dataPrivacyObj;

    /**
     * Gets the value of the dataPrivacyObj property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDataPrivacyLtsDTO }
     *     
     */
    public ArrayOfDataPrivacyLtsDTO getDataPrivacyObj() {
        return dataPrivacyObj;
    }

    /**
     * Sets the value of the dataPrivacyObj property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDataPrivacyLtsDTO }
     *     
     */
    public void setDataPrivacyObj(ArrayOfDataPrivacyLtsDTO value) {
        this.dataPrivacyObj = value;
    }

}

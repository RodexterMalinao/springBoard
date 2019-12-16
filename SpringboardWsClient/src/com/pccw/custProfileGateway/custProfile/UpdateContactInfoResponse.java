
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
 *         &lt;element name="updateContactInfoResult" type="{http://www.openuri.org/}ContactInfoUpdResponse" minOccurs="0"/>
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
    "updateContactInfoResult"
})
@XmlRootElement(name = "updateContactInfoResponse")
public class UpdateContactInfoResponse {

    protected ContactInfoUpdResponse updateContactInfoResult;

    /**
     * Gets the value of the updateContactInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoUpdResponse }
     *     
     */
    public ContactInfoUpdResponse getUpdateContactInfoResult() {
        return updateContactInfoResult;
    }

    /**
     * Sets the value of the updateContactInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoUpdResponse }
     *     
     */
    public void setUpdateContactInfoResult(ContactInfoUpdResponse value) {
        this.updateContactInfoResult = value;
    }

}


package com.pccw.custProfileGateway.custProfile;

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
 *         &lt;element name="AddContactInfoResult" type="{http://www.openuri.org/}ContactInfoUpdResponse" minOccurs="0"/>
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
    "addContactInfoResult"
})
@XmlRootElement(name = "AddContactInfoResponse")
public class AddContactInfoResponse {

    @XmlElement(name = "AddContactInfoResult")
    protected ContactInfoUpdResponse addContactInfoResult;

    /**
     * Gets the value of the addContactInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoUpdResponse }
     *     
     */
    public ContactInfoUpdResponse getAddContactInfoResult() {
        return addContactInfoResult;
    }

    /**
     * Sets the value of the addContactInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoUpdResponse }
     *     
     */
    public void setAddContactInfoResult(ContactInfoUpdResponse value) {
        this.addContactInfoResult = value;
    }

}

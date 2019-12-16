
package com.pccw.custProfileGateway.acctInfo;

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
 *         &lt;element name="createAccountInfoResult" type="{http://www.openuri.org/}AccountInfoResponse" minOccurs="0"/>
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
    "createAccountInfoResult"
})
@XmlRootElement(name = "createAccountInfoResponse")
public class CreateAccountInfoResponse {

    protected AccountInfoResponse createAccountInfoResult;

    /**
     * Gets the value of the createAccountInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link AccountInfoResponse }
     *     
     */
    public AccountInfoResponse getCreateAccountInfoResult() {
        return createAccountInfoResult;
    }

    /**
     * Sets the value of the createAccountInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountInfoResponse }
     *     
     */
    public void setCreateAccountInfoResult(AccountInfoResponse value) {
        this.createAccountInfoResult = value;
    }

}

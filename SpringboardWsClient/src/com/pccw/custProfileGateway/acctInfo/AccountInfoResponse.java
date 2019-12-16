
package com.pccw.custProfileGateway.acctInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountInfoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountInfoResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="AccountDTO" type="{http://www.openuri.org/}AccountDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountInfoResponse", propOrder = {
    "accountDTO"
})
public class AccountInfoResponse
    extends ServiceResponse
{

    @XmlElement(name = "AccountDTO")
    protected AccountDTO accountDTO;

    /**
     * Gets the value of the accountDTO property.
     * 
     * @return
     *     possible object is
     *     {@link AccountDTO }
     *     
     */
    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    /**
     * Sets the value of the accountDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountDTO }
     *     
     */
    public void setAccountDTO(AccountDTO value) {
        this.accountDTO = value;
    }

}

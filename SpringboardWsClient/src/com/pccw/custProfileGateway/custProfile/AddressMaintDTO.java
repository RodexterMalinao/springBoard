
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressMaintDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressMaintDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}AddressDTO">
 *       &lt;sequence>
 *         &lt;element name="AffectedAddrList" type="{http://www.openuri.org/}ArrayOfAddressDTO" minOccurs="0"/>
 *         &lt;element name="AffectedMAList" type="{http://www.openuri.org/}ArrayOfAddressDTO" minOccurs="0"/>
 *         &lt;element name="AffectedBAList" type="{http://www.openuri.org/}ArrayOfAddressDTO" minOccurs="0"/>
 *         &lt;element name="AffectedDAList" type="{http://www.openuri.org/}ArrayOfAddressDTO" minOccurs="0"/>
 *         &lt;element name="AffectedAddress" type="{http://www.openuri.org/}List" minOccurs="0"/>
 *         &lt;element name="MailingAddressList" type="{http://www.openuri.org/}List" minOccurs="0"/>
 *         &lt;element name="BillingAddressList" type="{http://www.openuri.org/}List" minOccurs="0"/>
 *         &lt;element name="DeliveryAddressList" type="{http://www.openuri.org/}List" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressMaintDTO", propOrder = {
    "affectedAddrList",
    "affectedMAList",
    "affectedBAList",
    "affectedDAList",
    "affectedAddress",
    "mailingAddressList",
    "billingAddressList",
    "deliveryAddressList"
})
public class AddressMaintDTO
    extends AddressDTO
{

    @XmlElement(name = "AffectedAddrList")
    protected ArrayOfAddressDTO affectedAddrList;
    @XmlElement(name = "AffectedMAList")
    protected ArrayOfAddressDTO affectedMAList;
    @XmlElement(name = "AffectedBAList")
    protected ArrayOfAddressDTO affectedBAList;
    @XmlElement(name = "AffectedDAList")
    protected ArrayOfAddressDTO affectedDAList;
    @XmlElement(name = "AffectedAddress")
    protected List affectedAddress;
    @XmlElement(name = "MailingAddressList")
    protected List mailingAddressList;
    @XmlElement(name = "BillingAddressList")
    protected List billingAddressList;
    @XmlElement(name = "DeliveryAddressList")
    protected List deliveryAddressList;

    /**
     * Gets the value of the affectedAddrList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAddressDTO }
     *     
     */
    public ArrayOfAddressDTO getAffectedAddrList() {
        return affectedAddrList;
    }

    /**
     * Sets the value of the affectedAddrList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAddressDTO }
     *     
     */
    public void setAffectedAddrList(ArrayOfAddressDTO value) {
        this.affectedAddrList = value;
    }

    /**
     * Gets the value of the affectedMAList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAddressDTO }
     *     
     */
    public ArrayOfAddressDTO getAffectedMAList() {
        return affectedMAList;
    }

    /**
     * Sets the value of the affectedMAList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAddressDTO }
     *     
     */
    public void setAffectedMAList(ArrayOfAddressDTO value) {
        this.affectedMAList = value;
    }

    /**
     * Gets the value of the affectedBAList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAddressDTO }
     *     
     */
    public ArrayOfAddressDTO getAffectedBAList() {
        return affectedBAList;
    }

    /**
     * Sets the value of the affectedBAList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAddressDTO }
     *     
     */
    public void setAffectedBAList(ArrayOfAddressDTO value) {
        this.affectedBAList = value;
    }

    /**
     * Gets the value of the affectedDAList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAddressDTO }
     *     
     */
    public ArrayOfAddressDTO getAffectedDAList() {
        return affectedDAList;
    }

    /**
     * Sets the value of the affectedDAList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAddressDTO }
     *     
     */
    public void setAffectedDAList(ArrayOfAddressDTO value) {
        this.affectedDAList = value;
    }

    /**
     * Gets the value of the affectedAddress property.
     * 
     * @return
     *     possible object is
     *     {@link List }
     *     
     */
    public List getAffectedAddress() {
        return affectedAddress;
    }

    /**
     * Sets the value of the affectedAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link List }
     *     
     */
    public void setAffectedAddress(List value) {
        this.affectedAddress = value;
    }

    /**
     * Gets the value of the mailingAddressList property.
     * 
     * @return
     *     possible object is
     *     {@link List }
     *     
     */
    public List getMailingAddressList() {
        return mailingAddressList;
    }

    /**
     * Sets the value of the mailingAddressList property.
     * 
     * @param value
     *     allowed object is
     *     {@link List }
     *     
     */
    public void setMailingAddressList(List value) {
        this.mailingAddressList = value;
    }

    /**
     * Gets the value of the billingAddressList property.
     * 
     * @return
     *     possible object is
     *     {@link List }
     *     
     */
    public List getBillingAddressList() {
        return billingAddressList;
    }

    /**
     * Sets the value of the billingAddressList property.
     * 
     * @param value
     *     allowed object is
     *     {@link List }
     *     
     */
    public void setBillingAddressList(List value) {
        this.billingAddressList = value;
    }

    /**
     * Gets the value of the deliveryAddressList property.
     * 
     * @return
     *     possible object is
     *     {@link List }
     *     
     */
    public List getDeliveryAddressList() {
        return deliveryAddressList;
    }

    /**
     * Sets the value of the deliveryAddressList property.
     * 
     * @param value
     *     allowed object is
     *     {@link List }
     *     
     */
    public void setDeliveryAddressList(List value) {
        this.deliveryAddressList = value;
    }

}

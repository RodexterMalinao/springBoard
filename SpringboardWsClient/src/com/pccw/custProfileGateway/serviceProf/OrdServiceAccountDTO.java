
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdServiceAccountDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdServiceAccountDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="AccountCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ChargeCategoryCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDetail" type="{http://www.openuri.org/}OrdDetailDTO" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdServiceAccountDTO" minOccurs="0"/>
 *         &lt;element name="OrderServiceUniqueKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerSystemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdServiceAccountDTO", propOrder = {
    "accountCode",
    "action",
    "chargeCategoryCode",
    "orderDetail",
    "profileImage",
    "orderServiceUniqueKey",
    "customerNum",
    "customerSystemID",
    "customerLevel"
})
public class OrdServiceAccountDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "AccountCode")
    protected String accountCode;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "ChargeCategoryCode")
    protected String chargeCategoryCode;
    @XmlElement(name = "OrderDetail")
    protected OrdDetailDTO orderDetail;
    @XmlElement(name = "ProfileImage")
    protected OrdServiceAccountDTO profileImage;
    @XmlElement(name = "OrderServiceUniqueKey")
    protected String orderServiceUniqueKey;
    @XmlElement(name = "CustomerNum")
    protected String customerNum;
    @XmlElement(name = "CustomerSystemID")
    protected String customerSystemID;
    @XmlElement(name = "CustomerLevel")
    protected int customerLevel;

    /**
     * Gets the value of the accountCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * Sets the value of the accountCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountCode(String value) {
        this.accountCode = value;
    }

    /**
     * Gets the value of the action property.
     * 
     */
    public int getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     */
    public void setAction(int value) {
        this.action = value;
    }

    /**
     * Gets the value of the chargeCategoryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeCategoryCode() {
        return chargeCategoryCode;
    }

    /**
     * Sets the value of the chargeCategoryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeCategoryCode(String value) {
        this.chargeCategoryCode = value;
    }

    /**
     * Gets the value of the orderDetail property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailDTO }
     *     
     */
    public OrdDetailDTO getOrderDetail() {
        return orderDetail;
    }

    /**
     * Sets the value of the orderDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailDTO }
     *     
     */
    public void setOrderDetail(OrdDetailDTO value) {
        this.orderDetail = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdServiceAccountDTO }
     *     
     */
    public OrdServiceAccountDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdServiceAccountDTO }
     *     
     */
    public void setProfileImage(OrdServiceAccountDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the orderServiceUniqueKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderServiceUniqueKey() {
        return orderServiceUniqueKey;
    }

    /**
     * Sets the value of the orderServiceUniqueKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderServiceUniqueKey(String value) {
        this.orderServiceUniqueKey = value;
    }

    /**
     * Gets the value of the customerNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerNum() {
        return customerNum;
    }

    /**
     * Sets the value of the customerNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerNum(String value) {
        this.customerNum = value;
    }

    /**
     * Gets the value of the customerSystemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerSystemID() {
        return customerSystemID;
    }

    /**
     * Sets the value of the customerSystemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerSystemID(String value) {
        this.customerSystemID = value;
    }

    /**
     * Gets the value of the customerLevel property.
     * 
     */
    public int getCustomerLevel() {
        return customerLevel;
    }

    /**
     * Sets the value of the customerLevel property.
     * 
     */
    public void setCustomerLevel(int value) {
        this.customerLevel = value;
    }

}

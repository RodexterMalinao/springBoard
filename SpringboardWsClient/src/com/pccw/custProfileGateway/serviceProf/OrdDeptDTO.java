
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdDeptDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdDeptDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChargeCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDetail" type="{http://www.openuri.org/}OrdDetailDTO" minOccurs="0"/>
 *         &lt;element name="ItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewDeptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewDeptId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OldDeptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderServiceAccount" type="{http://www.openuri.org/}OrdServiceAccountDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdDeptDTO", propOrder = {
    "accountNumber",
    "chargeCategory",
    "customerNumber",
    "orderDetail",
    "itemNumber",
    "newDeptName",
    "newDeptId",
    "oldDeptName",
    "serviceNumber",
    "action",
    "orderServiceAccount"
})
public class OrdDeptDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "AccountNumber")
    protected String accountNumber;
    @XmlElement(name = "ChargeCategory")
    protected String chargeCategory;
    @XmlElement(name = "CustomerNumber")
    protected String customerNumber;
    @XmlElement(name = "OrderDetail")
    protected OrdDetailDTO orderDetail;
    @XmlElement(name = "ItemNumber")
    protected String itemNumber;
    @XmlElement(name = "NewDeptName")
    protected String newDeptName;
    @XmlElement(name = "NewDeptId")
    protected String newDeptId;
    @XmlElement(name = "OldDeptName")
    protected String oldDeptName;
    @XmlElement(name = "ServiceNumber")
    protected String serviceNumber;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderServiceAccount")
    protected OrdServiceAccountDTO orderServiceAccount;

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the chargeCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeCategory() {
        return chargeCategory;
    }

    /**
     * Sets the value of the chargeCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeCategory(String value) {
        this.chargeCategory = value;
    }

    /**
     * Gets the value of the customerNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     * Sets the value of the customerNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerNumber(String value) {
        this.customerNumber = value;
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
     * Gets the value of the itemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemNumber() {
        return itemNumber;
    }

    /**
     * Sets the value of the itemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemNumber(String value) {
        this.itemNumber = value;
    }

    /**
     * Gets the value of the newDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDeptName() {
        return newDeptName;
    }

    /**
     * Sets the value of the newDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDeptName(String value) {
        this.newDeptName = value;
    }

    /**
     * Gets the value of the newDeptId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDeptId() {
        return newDeptId;
    }

    /**
     * Sets the value of the newDeptId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDeptId(String value) {
        this.newDeptId = value;
    }

    /**
     * Gets the value of the oldDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldDeptName() {
        return oldDeptName;
    }

    /**
     * Sets the value of the oldDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldDeptName(String value) {
        this.oldDeptName = value;
    }

    /**
     * Gets the value of the serviceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceNumber() {
        return serviceNumber;
    }

    /**
     * Sets the value of the serviceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceNumber(String value) {
        this.serviceNumber = value;
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
     * Gets the value of the orderServiceAccount property.
     * 
     * @return
     *     possible object is
     *     {@link OrdServiceAccountDTO }
     *     
     */
    public OrdServiceAccountDTO getOrderServiceAccount() {
        return orderServiceAccount;
    }

    /**
     * Sets the value of the orderServiceAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdServiceAccountDTO }
     *     
     */
    public void setOrderServiceAccount(OrdServiceAccountDTO value) {
        this.orderServiceAccount = value;
    }

}

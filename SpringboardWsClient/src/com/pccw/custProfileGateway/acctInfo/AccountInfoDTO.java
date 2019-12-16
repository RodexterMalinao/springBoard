
package com.pccw.custProfileGateway.acctInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountInfoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustCatg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctSubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillFreq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillLang" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NetvigatorInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustAddressDTO" type="{http://www.openuri.org/}CustAddressDTO" minOccurs="0"/>
 *         &lt;element name="CustFuturePaymentMethodDTO" type="{http://www.openuri.org/}CustFuturePaymentMethodDTO" minOccurs="0"/>
 *         &lt;element name="EmailAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountInfoDTO", propOrder = {
    "custNum",
    "custCatg",
    "systemId",
    "acctName",
    "acctType",
    "acctSubType",
    "billFreq",
    "billLang",
    "effStartDate",
    "effEndDate",
    "lastUpdBy",
    "netvigatorInd",
    "custAddressDTO",
    "custFuturePaymentMethodDTO",
    "emailAddr"
})
public class AccountInfoDTO {

    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "CustCatg")
    protected String custCatg;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "AcctName")
    protected String acctName;
    @XmlElement(name = "AcctType")
    protected String acctType;
    @XmlElement(name = "AcctSubType")
    protected String acctSubType;
    @XmlElement(name = "BillFreq")
    protected String billFreq;
    @XmlElement(name = "BillLang")
    protected String billLang;
    @XmlElement(name = "EffStartDate")
    protected String effStartDate;
    @XmlElement(name = "EffEndDate")
    protected String effEndDate;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "NetvigatorInd")
    protected String netvigatorInd;
    @XmlElement(name = "CustAddressDTO")
    protected CustAddressDTO custAddressDTO;
    @XmlElement(name = "CustFuturePaymentMethodDTO")
    protected CustFuturePaymentMethodDTO custFuturePaymentMethodDTO;
    @XmlElement(name = "EmailAddr")
    protected String emailAddr;

    /**
     * Gets the value of the custNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustNum() {
        return custNum;
    }

    /**
     * Sets the value of the custNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustNum(String value) {
        this.custNum = value;
    }

    /**
     * Gets the value of the custCatg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustCatg() {
        return custCatg;
    }

    /**
     * Sets the value of the custCatg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustCatg(String value) {
        this.custCatg = value;
    }

    /**
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * Gets the value of the acctName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctName() {
        return acctName;
    }

    /**
     * Sets the value of the acctName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctName(String value) {
        this.acctName = value;
    }

    /**
     * Gets the value of the acctType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctType() {
        return acctType;
    }

    /**
     * Sets the value of the acctType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctType(String value) {
        this.acctType = value;
    }

    /**
     * Gets the value of the acctSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctSubType() {
        return acctSubType;
    }

    /**
     * Sets the value of the acctSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctSubType(String value) {
        this.acctSubType = value;
    }

    /**
     * Gets the value of the billFreq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillFreq() {
        return billFreq;
    }

    /**
     * Sets the value of the billFreq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillFreq(String value) {
        this.billFreq = value;
    }

    /**
     * Gets the value of the billLang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillLang() {
        return billLang;
    }

    /**
     * Sets the value of the billLang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillLang(String value) {
        this.billLang = value;
    }

    /**
     * Gets the value of the effStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffStartDate() {
        return effStartDate;
    }

    /**
     * Sets the value of the effStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffStartDate(String value) {
        this.effStartDate = value;
    }

    /**
     * Gets the value of the effEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffEndDate() {
        return effEndDate;
    }

    /**
     * Sets the value of the effEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffEndDate(String value) {
        this.effEndDate = value;
    }

    /**
     * Gets the value of the lastUpdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdBy() {
        return lastUpdBy;
    }

    /**
     * Sets the value of the lastUpdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdBy(String value) {
        this.lastUpdBy = value;
    }

    /**
     * Gets the value of the netvigatorInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetvigatorInd() {
        return netvigatorInd;
    }

    /**
     * Sets the value of the netvigatorInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetvigatorInd(String value) {
        this.netvigatorInd = value;
    }

    /**
     * Gets the value of the custAddressDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CustAddressDTO }
     *     
     */
    public CustAddressDTO getCustAddressDTO() {
        return custAddressDTO;
    }

    /**
     * Sets the value of the custAddressDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustAddressDTO }
     *     
     */
    public void setCustAddressDTO(CustAddressDTO value) {
        this.custAddressDTO = value;
    }

    /**
     * Gets the value of the custFuturePaymentMethodDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CustFuturePaymentMethodDTO }
     *     
     */
    public CustFuturePaymentMethodDTO getCustFuturePaymentMethodDTO() {
        return custFuturePaymentMethodDTO;
    }

    /**
     * Sets the value of the custFuturePaymentMethodDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustFuturePaymentMethodDTO }
     *     
     */
    public void setCustFuturePaymentMethodDTO(CustFuturePaymentMethodDTO value) {
        this.custFuturePaymentMethodDTO = value;
    }

    /**
     * Gets the value of the emailAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddr() {
        return emailAddr;
    }

    /**
     * Sets the value of the emailAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddr(String value) {
        this.emailAddr = value;
    }

}

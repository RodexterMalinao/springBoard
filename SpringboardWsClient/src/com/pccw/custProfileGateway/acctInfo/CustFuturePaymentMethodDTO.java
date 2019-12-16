
package com.pccw.custProfileGateway.acctInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustFuturePaymentMethodDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustFuturePaymentMethodDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AutopayClearingBankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AutopayClearingBankCodem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SerialNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AssRowId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AssRowSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthzReportDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AutopayApplDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AutopayStatementInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankAcctHolderIdNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankAcctHolderIdType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankAcctHolderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankAcctNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillFmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillMedia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BranchCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CccNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcCreateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClearingBankCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardExpiryDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardHolderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardIdDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardIdDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardIssueBank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardSerialNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditCardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdateProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PayMethodKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PayMethodType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RejDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RowId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RowSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AutopayUpperLimitAmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ThirdPartyInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShopCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DomainType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WaivePaperInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WaivePaperRemarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustFuturePaymentMethodDTO", propOrder = {
    "autopayClearingBankCode",
    "autopayClearingBankCodem",
    "serialNum",
    "acctNum",
    "assRowId",
    "assRowSeqNum",
    "authzReportDate",
    "autopayApplDate",
    "autopayStatementInd",
    "bankAcctHolderIdNum",
    "bankAcctHolderIdType",
    "bankAcctHolderName",
    "bankAcctNum",
    "bankCode",
    "bankName",
    "billFmt",
    "billMedia",
    "branchCode",
    "cccNum",
    "ccCreateDate",
    "clearingBankCd",
    "creditCardExpiryDate",
    "creditCardHolderName",
    "creditCardIdDocNum",
    "creditCardIdDocType",
    "creditCardIssueBank",
    "creditCardNum",
    "creditCardSerialNum",
    "creditCardType",
    "custNum",
    "effEndDate",
    "effStartDate",
    "iIndicator",
    "lastUpdateProcess",
    "payMethodKey",
    "payMethodType",
    "rejDesc",
    "rowId",
    "rowSeqNum",
    "systemId",
    "termCd",
    "userId",
    "autopayUpperLimitAmt",
    "thirdPartyInd",
    "status",
    "shopCode",
    "domainType",
    "waivePaperInd",
    "waivePaperRemarks"
})
public class CustFuturePaymentMethodDTO {

    @XmlElement(name = "AutopayClearingBankCode")
    protected String autopayClearingBankCode;
    @XmlElement(name = "AutopayClearingBankCodem")
    protected String autopayClearingBankCodem;
    @XmlElement(name = "SerialNum")
    protected String serialNum;
    @XmlElement(name = "AcctNum")
    protected String acctNum;
    @XmlElement(name = "AssRowId")
    protected String assRowId;
    @XmlElement(name = "AssRowSeqNum")
    protected String assRowSeqNum;
    @XmlElement(name = "AuthzReportDate")
    protected String authzReportDate;
    @XmlElement(name = "AutopayApplDate")
    protected String autopayApplDate;
    @XmlElement(name = "AutopayStatementInd")
    protected String autopayStatementInd;
    @XmlElement(name = "BankAcctHolderIdNum")
    protected String bankAcctHolderIdNum;
    @XmlElement(name = "BankAcctHolderIdType")
    protected String bankAcctHolderIdType;
    @XmlElement(name = "BankAcctHolderName")
    protected String bankAcctHolderName;
    @XmlElement(name = "BankAcctNum")
    protected String bankAcctNum;
    @XmlElement(name = "BankCode")
    protected String bankCode;
    @XmlElement(name = "BankName")
    protected String bankName;
    @XmlElement(name = "BillFmt")
    protected String billFmt;
    @XmlElement(name = "BillMedia")
    protected String billMedia;
    @XmlElement(name = "BranchCode")
    protected String branchCode;
    @XmlElement(name = "CccNum")
    protected String cccNum;
    @XmlElement(name = "CcCreateDate")
    protected String ccCreateDate;
    @XmlElement(name = "ClearingBankCd")
    protected String clearingBankCd;
    @XmlElement(name = "CreditCardExpiryDate")
    protected String creditCardExpiryDate;
    @XmlElement(name = "CreditCardHolderName")
    protected String creditCardHolderName;
    @XmlElement(name = "CreditCardIdDocNum")
    protected String creditCardIdDocNum;
    @XmlElement(name = "CreditCardIdDocType")
    protected String creditCardIdDocType;
    @XmlElement(name = "CreditCardIssueBank")
    protected String creditCardIssueBank;
    @XmlElement(name = "CreditCardNum")
    protected String creditCardNum;
    @XmlElement(name = "CreditCardSerialNum")
    protected String creditCardSerialNum;
    @XmlElement(name = "CreditCardType")
    protected String creditCardType;
    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "EffEndDate")
    protected String effEndDate;
    @XmlElement(name = "EffStartDate")
    protected String effStartDate;
    @XmlElement(name = "IIndicator")
    protected String iIndicator;
    @XmlElement(name = "LastUpdateProcess")
    protected String lastUpdateProcess;
    @XmlElement(name = "PayMethodKey")
    protected String payMethodKey;
    @XmlElement(name = "PayMethodType")
    protected String payMethodType;
    @XmlElement(name = "RejDesc")
    protected String rejDesc;
    @XmlElement(name = "RowId")
    protected String rowId;
    @XmlElement(name = "RowSeqNum")
    protected String rowSeqNum;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "TermCd")
    protected String termCd;
    @XmlElement(name = "UserId")
    protected String userId;
    @XmlElement(name = "AutopayUpperLimitAmt")
    protected String autopayUpperLimitAmt;
    @XmlElement(name = "ThirdPartyInd")
    protected String thirdPartyInd;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "ShopCode")
    protected String shopCode;
    @XmlElement(name = "DomainType")
    protected String domainType;
    @XmlElement(name = "WaivePaperInd")
    protected String waivePaperInd;
    @XmlElement(name = "WaivePaperRemarks")
    protected String waivePaperRemarks;

    /**
     * Gets the value of the autopayClearingBankCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutopayClearingBankCode() {
        return autopayClearingBankCode;
    }

    /**
     * Sets the value of the autopayClearingBankCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutopayClearingBankCode(String value) {
        this.autopayClearingBankCode = value;
    }

    /**
     * Gets the value of the autopayClearingBankCodem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutopayClearingBankCodem() {
        return autopayClearingBankCodem;
    }

    /**
     * Sets the value of the autopayClearingBankCodem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutopayClearingBankCodem(String value) {
        this.autopayClearingBankCodem = value;
    }

    /**
     * Gets the value of the serialNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * Sets the value of the serialNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNum(String value) {
        this.serialNum = value;
    }

    /**
     * Gets the value of the acctNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctNum() {
        return acctNum;
    }

    /**
     * Sets the value of the acctNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctNum(String value) {
        this.acctNum = value;
    }

    /**
     * Gets the value of the assRowId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssRowId() {
        return assRowId;
    }

    /**
     * Sets the value of the assRowId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssRowId(String value) {
        this.assRowId = value;
    }

    /**
     * Gets the value of the assRowSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssRowSeqNum() {
        return assRowSeqNum;
    }

    /**
     * Sets the value of the assRowSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssRowSeqNum(String value) {
        this.assRowSeqNum = value;
    }

    /**
     * Gets the value of the authzReportDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthzReportDate() {
        return authzReportDate;
    }

    /**
     * Sets the value of the authzReportDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthzReportDate(String value) {
        this.authzReportDate = value;
    }

    /**
     * Gets the value of the autopayApplDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutopayApplDate() {
        return autopayApplDate;
    }

    /**
     * Sets the value of the autopayApplDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutopayApplDate(String value) {
        this.autopayApplDate = value;
    }

    /**
     * Gets the value of the autopayStatementInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutopayStatementInd() {
        return autopayStatementInd;
    }

    /**
     * Sets the value of the autopayStatementInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutopayStatementInd(String value) {
        this.autopayStatementInd = value;
    }

    /**
     * Gets the value of the bankAcctHolderIdNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAcctHolderIdNum() {
        return bankAcctHolderIdNum;
    }

    /**
     * Sets the value of the bankAcctHolderIdNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAcctHolderIdNum(String value) {
        this.bankAcctHolderIdNum = value;
    }

    /**
     * Gets the value of the bankAcctHolderIdType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAcctHolderIdType() {
        return bankAcctHolderIdType;
    }

    /**
     * Sets the value of the bankAcctHolderIdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAcctHolderIdType(String value) {
        this.bankAcctHolderIdType = value;
    }

    /**
     * Gets the value of the bankAcctHolderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAcctHolderName() {
        return bankAcctHolderName;
    }

    /**
     * Sets the value of the bankAcctHolderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAcctHolderName(String value) {
        this.bankAcctHolderName = value;
    }

    /**
     * Gets the value of the bankAcctNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAcctNum() {
        return bankAcctNum;
    }

    /**
     * Sets the value of the bankAcctNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAcctNum(String value) {
        this.bankAcctNum = value;
    }

    /**
     * Gets the value of the bankCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Sets the value of the bankCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    /**
     * Gets the value of the bankName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Sets the value of the bankName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    /**
     * Gets the value of the billFmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillFmt() {
        return billFmt;
    }

    /**
     * Sets the value of the billFmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillFmt(String value) {
        this.billFmt = value;
    }

    /**
     * Gets the value of the billMedia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillMedia() {
        return billMedia;
    }

    /**
     * Sets the value of the billMedia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillMedia(String value) {
        this.billMedia = value;
    }

    /**
     * Gets the value of the branchCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * Sets the value of the branchCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchCode(String value) {
        this.branchCode = value;
    }

    /**
     * Gets the value of the cccNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCccNum() {
        return cccNum;
    }

    /**
     * Sets the value of the cccNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCccNum(String value) {
        this.cccNum = value;
    }

    /**
     * Gets the value of the ccCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcCreateDate() {
        return ccCreateDate;
    }

    /**
     * Sets the value of the ccCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcCreateDate(String value) {
        this.ccCreateDate = value;
    }

    /**
     * Gets the value of the clearingBankCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClearingBankCd() {
        return clearingBankCd;
    }

    /**
     * Sets the value of the clearingBankCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClearingBankCd(String value) {
        this.clearingBankCd = value;
    }

    /**
     * Gets the value of the creditCardExpiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardExpiryDate() {
        return creditCardExpiryDate;
    }

    /**
     * Sets the value of the creditCardExpiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardExpiryDate(String value) {
        this.creditCardExpiryDate = value;
    }

    /**
     * Gets the value of the creditCardHolderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardHolderName() {
        return creditCardHolderName;
    }

    /**
     * Sets the value of the creditCardHolderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardHolderName(String value) {
        this.creditCardHolderName = value;
    }

    /**
     * Gets the value of the creditCardIdDocNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardIdDocNum() {
        return creditCardIdDocNum;
    }

    /**
     * Sets the value of the creditCardIdDocNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardIdDocNum(String value) {
        this.creditCardIdDocNum = value;
    }

    /**
     * Gets the value of the creditCardIdDocType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardIdDocType() {
        return creditCardIdDocType;
    }

    /**
     * Sets the value of the creditCardIdDocType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardIdDocType(String value) {
        this.creditCardIdDocType = value;
    }

    /**
     * Gets the value of the creditCardIssueBank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardIssueBank() {
        return creditCardIssueBank;
    }

    /**
     * Sets the value of the creditCardIssueBank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardIssueBank(String value) {
        this.creditCardIssueBank = value;
    }

    /**
     * Gets the value of the creditCardNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardNum() {
        return creditCardNum;
    }

    /**
     * Sets the value of the creditCardNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardNum(String value) {
        this.creditCardNum = value;
    }

    /**
     * Gets the value of the creditCardSerialNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardSerialNum() {
        return creditCardSerialNum;
    }

    /**
     * Sets the value of the creditCardSerialNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardSerialNum(String value) {
        this.creditCardSerialNum = value;
    }

    /**
     * Gets the value of the creditCardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardType() {
        return creditCardType;
    }

    /**
     * Sets the value of the creditCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardType(String value) {
        this.creditCardType = value;
    }

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
     * Gets the value of the iIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIIndicator() {
        return iIndicator;
    }

    /**
     * Sets the value of the iIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIIndicator(String value) {
        this.iIndicator = value;
    }

    /**
     * Gets the value of the lastUpdateProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdateProcess() {
        return lastUpdateProcess;
    }

    /**
     * Sets the value of the lastUpdateProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdateProcess(String value) {
        this.lastUpdateProcess = value;
    }

    /**
     * Gets the value of the payMethodKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayMethodKey() {
        return payMethodKey;
    }

    /**
     * Sets the value of the payMethodKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayMethodKey(String value) {
        this.payMethodKey = value;
    }

    /**
     * Gets the value of the payMethodType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayMethodType() {
        return payMethodType;
    }

    /**
     * Sets the value of the payMethodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayMethodType(String value) {
        this.payMethodType = value;
    }

    /**
     * Gets the value of the rejDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRejDesc() {
        return rejDesc;
    }

    /**
     * Sets the value of the rejDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRejDesc(String value) {
        this.rejDesc = value;
    }

    /**
     * Gets the value of the rowId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowId() {
        return rowId;
    }

    /**
     * Sets the value of the rowId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowId(String value) {
        this.rowId = value;
    }

    /**
     * Gets the value of the rowSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowSeqNum() {
        return rowSeqNum;
    }

    /**
     * Sets the value of the rowSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowSeqNum(String value) {
        this.rowSeqNum = value;
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
     * Gets the value of the termCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermCd() {
        return termCd;
    }

    /**
     * Sets the value of the termCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermCd(String value) {
        this.termCd = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the autopayUpperLimitAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutopayUpperLimitAmt() {
        return autopayUpperLimitAmt;
    }

    /**
     * Sets the value of the autopayUpperLimitAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutopayUpperLimitAmt(String value) {
        this.autopayUpperLimitAmt = value;
    }

    /**
     * Gets the value of the thirdPartyInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyInd() {
        return thirdPartyInd;
    }

    /**
     * Sets the value of the thirdPartyInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyInd(String value) {
        this.thirdPartyInd = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the shopCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * Sets the value of the shopCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShopCode(String value) {
        this.shopCode = value;
    }

    /**
     * Gets the value of the domainType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainType() {
        return domainType;
    }

    /**
     * Sets the value of the domainType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainType(String value) {
        this.domainType = value;
    }

    /**
     * Gets the value of the waivePaperInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaivePaperInd() {
        return waivePaperInd;
    }

    /**
     * Sets the value of the waivePaperInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaivePaperInd(String value) {
        this.waivePaperInd = value;
    }

    /**
     * Gets the value of the waivePaperRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaivePaperRemarks() {
        return waivePaperRemarks;
    }

    /**
     * Sets the value of the waivePaperRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaivePaperRemarks(String value) {
        this.waivePaperRemarks = value;
    }

}

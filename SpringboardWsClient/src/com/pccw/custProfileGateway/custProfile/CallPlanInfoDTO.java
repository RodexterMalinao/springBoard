
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CallPlanInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CallPlanInfoDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="PlanType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanTypeSubCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanIndx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanFixedFreeChrg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FreeMinsEntitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanCreditFreq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuyBackInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegEffDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegTermnDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanHolderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanHolderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanSrvSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContractStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContractEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContractDuratn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MethodOfPenalty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WaivePenaltyInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FreeMinUsed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CharingAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CallPlanInfoDTO", propOrder = {
    "planType",
    "planTypeSubCd",
    "planCd",
    "planIndx",
    "planFixedFreeChrg",
    "freeMinsEntitle",
    "planCreditFreq",
    "buyBackInd",
    "regEffDate",
    "regTermnDate",
    "planHolderType",
    "planHolderId",
    "planSrvSeq",
    "contractStartDate",
    "contractEndDate",
    "contractDuratn",
    "methodOfPenalty",
    "waivePenaltyInd",
    "fromDate",
    "toDate",
    "freeMinUsed",
    "charingAccount"
})
public class CallPlanInfoDTO
    extends ValueObject
{

    @XmlElement(name = "PlanType")
    protected String planType;
    @XmlElement(name = "PlanTypeSubCd")
    protected String planTypeSubCd;
    @XmlElement(name = "PlanCd")
    protected String planCd;
    @XmlElement(name = "PlanIndx")
    protected String planIndx;
    @XmlElement(name = "PlanFixedFreeChrg")
    protected String planFixedFreeChrg;
    @XmlElement(name = "FreeMinsEntitle")
    protected String freeMinsEntitle;
    @XmlElement(name = "PlanCreditFreq")
    protected String planCreditFreq;
    @XmlElement(name = "BuyBackInd")
    protected String buyBackInd;
    @XmlElement(name = "RegEffDate")
    protected String regEffDate;
    @XmlElement(name = "RegTermnDate")
    protected String regTermnDate;
    @XmlElement(name = "PlanHolderType")
    protected String planHolderType;
    @XmlElement(name = "PlanHolderId")
    protected String planHolderId;
    @XmlElement(name = "PlanSrvSeq")
    protected String planSrvSeq;
    @XmlElement(name = "ContractStartDate")
    protected String contractStartDate;
    @XmlElement(name = "ContractEndDate")
    protected String contractEndDate;
    @XmlElement(name = "ContractDuratn")
    protected String contractDuratn;
    @XmlElement(name = "MethodOfPenalty")
    protected String methodOfPenalty;
    @XmlElement(name = "WaivePenaltyInd")
    protected String waivePenaltyInd;
    @XmlElement(name = "FromDate")
    protected String fromDate;
    @XmlElement(name = "ToDate")
    protected String toDate;
    @XmlElement(name = "FreeMinUsed")
    protected String freeMinUsed;
    @XmlElement(name = "CharingAccount")
    protected String charingAccount;

    /**
     * Gets the value of the planType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanType() {
        return planType;
    }

    /**
     * Sets the value of the planType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanType(String value) {
        this.planType = value;
    }

    /**
     * Gets the value of the planTypeSubCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanTypeSubCd() {
        return planTypeSubCd;
    }

    /**
     * Sets the value of the planTypeSubCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanTypeSubCd(String value) {
        this.planTypeSubCd = value;
    }

    /**
     * Gets the value of the planCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanCd() {
        return planCd;
    }

    /**
     * Sets the value of the planCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanCd(String value) {
        this.planCd = value;
    }

    /**
     * Gets the value of the planIndx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanIndx() {
        return planIndx;
    }

    /**
     * Sets the value of the planIndx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanIndx(String value) {
        this.planIndx = value;
    }

    /**
     * Gets the value of the planFixedFreeChrg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanFixedFreeChrg() {
        return planFixedFreeChrg;
    }

    /**
     * Sets the value of the planFixedFreeChrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanFixedFreeChrg(String value) {
        this.planFixedFreeChrg = value;
    }

    /**
     * Gets the value of the freeMinsEntitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeMinsEntitle() {
        return freeMinsEntitle;
    }

    /**
     * Sets the value of the freeMinsEntitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeMinsEntitle(String value) {
        this.freeMinsEntitle = value;
    }

    /**
     * Gets the value of the planCreditFreq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanCreditFreq() {
        return planCreditFreq;
    }

    /**
     * Sets the value of the planCreditFreq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanCreditFreq(String value) {
        this.planCreditFreq = value;
    }

    /**
     * Gets the value of the buyBackInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyBackInd() {
        return buyBackInd;
    }

    /**
     * Sets the value of the buyBackInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyBackInd(String value) {
        this.buyBackInd = value;
    }

    /**
     * Gets the value of the regEffDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegEffDate() {
        return regEffDate;
    }

    /**
     * Sets the value of the regEffDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegEffDate(String value) {
        this.regEffDate = value;
    }

    /**
     * Gets the value of the regTermnDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegTermnDate() {
        return regTermnDate;
    }

    /**
     * Sets the value of the regTermnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegTermnDate(String value) {
        this.regTermnDate = value;
    }

    /**
     * Gets the value of the planHolderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanHolderType() {
        return planHolderType;
    }

    /**
     * Sets the value of the planHolderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanHolderType(String value) {
        this.planHolderType = value;
    }

    /**
     * Gets the value of the planHolderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanHolderId() {
        return planHolderId;
    }

    /**
     * Sets the value of the planHolderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanHolderId(String value) {
        this.planHolderId = value;
    }

    /**
     * Gets the value of the planSrvSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanSrvSeq() {
        return planSrvSeq;
    }

    /**
     * Sets the value of the planSrvSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanSrvSeq(String value) {
        this.planSrvSeq = value;
    }

    /**
     * Gets the value of the contractStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractStartDate() {
        return contractStartDate;
    }

    /**
     * Sets the value of the contractStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractStartDate(String value) {
        this.contractStartDate = value;
    }

    /**
     * Gets the value of the contractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractEndDate() {
        return contractEndDate;
    }

    /**
     * Sets the value of the contractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractEndDate(String value) {
        this.contractEndDate = value;
    }

    /**
     * Gets the value of the contractDuratn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractDuratn() {
        return contractDuratn;
    }

    /**
     * Sets the value of the contractDuratn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractDuratn(String value) {
        this.contractDuratn = value;
    }

    /**
     * Gets the value of the methodOfPenalty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethodOfPenalty() {
        return methodOfPenalty;
    }

    /**
     * Sets the value of the methodOfPenalty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodOfPenalty(String value) {
        this.methodOfPenalty = value;
    }

    /**
     * Gets the value of the waivePenaltyInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaivePenaltyInd() {
        return waivePenaltyInd;
    }

    /**
     * Sets the value of the waivePenaltyInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaivePenaltyInd(String value) {
        this.waivePenaltyInd = value;
    }

    /**
     * Gets the value of the fromDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Sets the value of the fromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromDate(String value) {
        this.fromDate = value;
    }

    /**
     * Gets the value of the toDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * Sets the value of the toDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToDate(String value) {
        this.toDate = value;
    }

    /**
     * Gets the value of the freeMinUsed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeMinUsed() {
        return freeMinUsed;
    }

    /**
     * Sets the value of the freeMinUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeMinUsed(String value) {
        this.freeMinUsed = value;
    }

    /**
     * Gets the value of the charingAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharingAccount() {
        return charingAccount;
    }

    /**
     * Sets the value of the charingAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharingAccount(String value) {
        this.charingAccount = value;
    }

}

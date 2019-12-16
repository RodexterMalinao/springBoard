
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CallPlanEnqDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CallPlanEnqDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="PlanType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanSubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanIndex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanFixCharge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanEntitleMin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanCreditFreq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanBuyBackInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegEffDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegTermDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanHolderKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanSrvSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CallPlanEnqDTO", propOrder = {
    "planType",
    "planSubType",
    "planCode",
    "planIndex",
    "planFixCharge",
    "planEntitleMin",
    "planCreditFreq",
    "planBuyBackInd",
    "regEffDate",
    "regTermDate",
    "planHolderKey",
    "planSrvSeq"
})
public class CallPlanEnqDTO
    extends ValueObject
{

    @XmlElement(name = "PlanType")
    protected String planType;
    @XmlElement(name = "PlanSubType")
    protected String planSubType;
    @XmlElement(name = "PlanCode")
    protected String planCode;
    @XmlElement(name = "PlanIndex")
    protected String planIndex;
    @XmlElement(name = "PlanFixCharge")
    protected String planFixCharge;
    @XmlElement(name = "PlanEntitleMin")
    protected String planEntitleMin;
    @XmlElement(name = "PlanCreditFreq")
    protected String planCreditFreq;
    @XmlElement(name = "PlanBuyBackInd")
    protected String planBuyBackInd;
    @XmlElement(name = "RegEffDate")
    protected String regEffDate;
    @XmlElement(name = "RegTermDate")
    protected String regTermDate;
    @XmlElement(name = "PlanHolderKey")
    protected String planHolderKey;
    @XmlElement(name = "PlanSrvSeq")
    protected String planSrvSeq;

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
     * Gets the value of the planSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanSubType() {
        return planSubType;
    }

    /**
     * Sets the value of the planSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanSubType(String value) {
        this.planSubType = value;
    }

    /**
     * Gets the value of the planCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanCode() {
        return planCode;
    }

    /**
     * Sets the value of the planCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanCode(String value) {
        this.planCode = value;
    }

    /**
     * Gets the value of the planIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanIndex() {
        return planIndex;
    }

    /**
     * Sets the value of the planIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanIndex(String value) {
        this.planIndex = value;
    }

    /**
     * Gets the value of the planFixCharge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanFixCharge() {
        return planFixCharge;
    }

    /**
     * Sets the value of the planFixCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanFixCharge(String value) {
        this.planFixCharge = value;
    }

    /**
     * Gets the value of the planEntitleMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanEntitleMin() {
        return planEntitleMin;
    }

    /**
     * Sets the value of the planEntitleMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanEntitleMin(String value) {
        this.planEntitleMin = value;
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
     * Gets the value of the planBuyBackInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanBuyBackInd() {
        return planBuyBackInd;
    }

    /**
     * Sets the value of the planBuyBackInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanBuyBackInd(String value) {
        this.planBuyBackInd = value;
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
     * Gets the value of the regTermDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegTermDate() {
        return regTermDate;
    }

    /**
     * Sets the value of the regTermDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegTermDate(String value) {
        this.regTermDate = value;
    }

    /**
     * Gets the value of the planHolderKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanHolderKey() {
        return planHolderKey;
    }

    /**
     * Sets the value of the planHolderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanHolderKey(String value) {
        this.planHolderKey = value;
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

}

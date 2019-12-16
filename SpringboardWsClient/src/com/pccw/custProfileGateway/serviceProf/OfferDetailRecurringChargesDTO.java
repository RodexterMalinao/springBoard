
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OfferDetailRecurringChargesDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OfferDetailRecurringChargesDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="ServiceNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfferCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChargeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChargeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DefaultAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChargeAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TerminateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContractOrPromotionStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContractOrPromotionEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReturnMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReturnCd" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfferDetailRecurringChargesDTO", propOrder = {
    "serviceNum",
    "offerCode",
    "productId",
    "productDesc",
    "chargeCode",
    "chargeDesc",
    "defaultAmount",
    "chargeAmount",
    "effectiveDate",
    "terminateDate",
    "contractOrPromotionStartDate",
    "contractOrPromotionEndDate",
    "returnMsg",
    "returnCd"
})
public class OfferDetailRecurringChargesDTO
    extends ServiceResponse
{

    @XmlElement(name = "ServiceNum")
    protected String serviceNum;
    @XmlElement(name = "OfferCode")
    protected String offerCode;
    @XmlElement(name = "ProductId")
    protected String productId;
    @XmlElement(name = "ProductDesc")
    protected String productDesc;
    @XmlElement(name = "ChargeCode")
    protected String chargeCode;
    @XmlElement(name = "ChargeDesc")
    protected String chargeDesc;
    @XmlElement(name = "DefaultAmount")
    protected String defaultAmount;
    @XmlElement(name = "ChargeAmount")
    protected String chargeAmount;
    @XmlElement(name = "EffectiveDate")
    protected String effectiveDate;
    @XmlElement(name = "TerminateDate")
    protected String terminateDate;
    @XmlElement(name = "ContractOrPromotionStartDate")
    protected String contractOrPromotionStartDate;
    @XmlElement(name = "ContractOrPromotionEndDate")
    protected String contractOrPromotionEndDate;
    @XmlElement(name = "ReturnMsg")
    protected String returnMsg;
    @XmlElement(name = "ReturnCd")
    protected int returnCd;

    /**
     * Gets the value of the serviceNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceNum() {
        return serviceNum;
    }

    /**
     * Sets the value of the serviceNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceNum(String value) {
        this.serviceNum = value;
    }

    /**
     * Gets the value of the offerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferCode() {
        return offerCode;
    }

    /**
     * Sets the value of the offerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferCode(String value) {
        this.offerCode = value;
    }

    /**
     * Gets the value of the productId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductId(String value) {
        this.productId = value;
    }

    /**
     * Gets the value of the productDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * Sets the value of the productDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductDesc(String value) {
        this.productDesc = value;
    }

    /**
     * Gets the value of the chargeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeCode() {
        return chargeCode;
    }

    /**
     * Sets the value of the chargeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeCode(String value) {
        this.chargeCode = value;
    }

    /**
     * Gets the value of the chargeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeDesc() {
        return chargeDesc;
    }

    /**
     * Sets the value of the chargeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeDesc(String value) {
        this.chargeDesc = value;
    }

    /**
     * Gets the value of the defaultAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultAmount() {
        return defaultAmount;
    }

    /**
     * Sets the value of the defaultAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultAmount(String value) {
        this.defaultAmount = value;
    }

    /**
     * Gets the value of the chargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeAmount() {
        return chargeAmount;
    }

    /**
     * Sets the value of the chargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeAmount(String value) {
        this.chargeAmount = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveDate(String value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the terminateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminateDate() {
        return terminateDate;
    }

    /**
     * Sets the value of the terminateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminateDate(String value) {
        this.terminateDate = value;
    }

    /**
     * Gets the value of the contractOrPromotionStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractOrPromotionStartDate() {
        return contractOrPromotionStartDate;
    }

    /**
     * Sets the value of the contractOrPromotionStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractOrPromotionStartDate(String value) {
        this.contractOrPromotionStartDate = value;
    }

    /**
     * Gets the value of the contractOrPromotionEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractOrPromotionEndDate() {
        return contractOrPromotionEndDate;
    }

    /**
     * Sets the value of the contractOrPromotionEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractOrPromotionEndDate(String value) {
        this.contractOrPromotionEndDate = value;
    }

    /**
     * Gets the value of the returnMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnMsg() {
        return returnMsg;
    }

    /**
     * Sets the value of the returnMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnMsg(String value) {
        this.returnMsg = value;
    }

    /**
     * Gets the value of the returnCd property.
     * 
     */
    public int getReturnCd() {
        return returnCd;
    }

    /**
     * Sets the value of the returnCd property.
     * 
     */
    public void setReturnCd(int value) {
        this.returnCd = value;
    }

}

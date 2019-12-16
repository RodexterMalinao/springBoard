
package com.pccw.custProfileGateway.acctInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MOBBillMediaDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MOBBillMediaDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="AcctNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillMedium" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillFmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfferSubKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfferId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillMediumMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Period" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrgPeriodFrPortal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToPortalAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcCreateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OcOrderId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChargeAmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WaiveInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MOBBillMediaDTO", propOrder = {
    "acctNum",
    "billMedium",
    "billFmt",
    "offerSubKey",
    "offerId",
    "effStartDate",
    "billMediumMode",
    "period",
    "orgPeriodFrPortal",
    "effEndDate",
    "toPortalAction",
    "lastUpdDate",
    "lastUpdBy",
    "ccCreateDate",
    "lastUpdProcess",
    "rowseqnb",
    "ocOrderId",
    "actionInd",
    "chargeAmt",
    "waiveInd"
})
public class MOBBillMediaDTO
    extends ValueObject
{

    @XmlElement(name = "AcctNum")
    protected String acctNum;
    @XmlElement(name = "BillMedium")
    protected String billMedium;
    @XmlElement(name = "BillFmt")
    protected String billFmt;
    @XmlElement(name = "OfferSubKey")
    protected String offerSubKey;
    @XmlElement(name = "OfferId")
    protected String offerId;
    @XmlElement(name = "EffStartDate")
    protected String effStartDate;
    @XmlElement(name = "BillMediumMode")
    protected String billMediumMode;
    @XmlElement(name = "Period")
    protected String period;
    @XmlElement(name = "OrgPeriodFrPortal")
    protected String orgPeriodFrPortal;
    @XmlElement(name = "EffEndDate")
    protected String effEndDate;
    @XmlElement(name = "ToPortalAction")
    protected String toPortalAction;
    @XmlElement(name = "LastUpdDate")
    protected String lastUpdDate;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "CcCreateDate")
    protected String ccCreateDate;
    @XmlElement(name = "LastUpdProcess")
    protected String lastUpdProcess;
    @XmlElement(name = "Rowseqnb")
    protected int rowseqnb;
    @XmlElement(name = "OcOrderId")
    protected int ocOrderId;
    @XmlElement(name = "ActionInd")
    protected String actionInd;
    @XmlElement(name = "ChargeAmt")
    protected String chargeAmt;
    @XmlElement(name = "WaiveInd")
    protected String waiveInd;

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
     * Gets the value of the billMedium property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillMedium() {
        return billMedium;
    }

    /**
     * Sets the value of the billMedium property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillMedium(String value) {
        this.billMedium = value;
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
     * Gets the value of the offerSubKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferSubKey() {
        return offerSubKey;
    }

    /**
     * Sets the value of the offerSubKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferSubKey(String value) {
        this.offerSubKey = value;
    }

    /**
     * Gets the value of the offerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferId() {
        return offerId;
    }

    /**
     * Sets the value of the offerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferId(String value) {
        this.offerId = value;
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
     * Gets the value of the billMediumMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillMediumMode() {
        return billMediumMode;
    }

    /**
     * Sets the value of the billMediumMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillMediumMode(String value) {
        this.billMediumMode = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriod(String value) {
        this.period = value;
    }

    /**
     * Gets the value of the orgPeriodFrPortal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgPeriodFrPortal() {
        return orgPeriodFrPortal;
    }

    /**
     * Sets the value of the orgPeriodFrPortal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgPeriodFrPortal(String value) {
        this.orgPeriodFrPortal = value;
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
     * Gets the value of the toPortalAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToPortalAction() {
        return toPortalAction;
    }

    /**
     * Sets the value of the toPortalAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToPortalAction(String value) {
        this.toPortalAction = value;
    }

    /**
     * Gets the value of the lastUpdDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdDate() {
        return lastUpdDate;
    }

    /**
     * Sets the value of the lastUpdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdDate(String value) {
        this.lastUpdDate = value;
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
     * Gets the value of the lastUpdProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdProcess() {
        return lastUpdProcess;
    }

    /**
     * Sets the value of the lastUpdProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdProcess(String value) {
        this.lastUpdProcess = value;
    }

    /**
     * Gets the value of the rowseqnb property.
     * 
     */
    public int getRowseqnb() {
        return rowseqnb;
    }

    /**
     * Sets the value of the rowseqnb property.
     * 
     */
    public void setRowseqnb(int value) {
        this.rowseqnb = value;
    }

    /**
     * Gets the value of the ocOrderId property.
     * 
     */
    public int getOcOrderId() {
        return ocOrderId;
    }

    /**
     * Sets the value of the ocOrderId property.
     * 
     */
    public void setOcOrderId(int value) {
        this.ocOrderId = value;
    }

    /**
     * Gets the value of the actionInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionInd() {
        return actionInd;
    }

    /**
     * Sets the value of the actionInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionInd(String value) {
        this.actionInd = value;
    }

    /**
     * Gets the value of the chargeAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeAmt() {
        return chargeAmt;
    }

    /**
     * Sets the value of the chargeAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeAmt(String value) {
        this.chargeAmt = value;
    }

    /**
     * Gets the value of the waiveInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaiveInd() {
        return waiveInd;
    }

    /**
     * Sets the value of the waiveInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaiveInd(String value) {
        this.waiveInd = value;
    }

}

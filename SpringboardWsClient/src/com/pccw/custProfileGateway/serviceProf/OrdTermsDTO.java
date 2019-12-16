
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdTermsDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdTermsDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="WaiveReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderPenaltyMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustomizeManualDelete" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomizeQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffectiveDependancy" type="{http://www.openuri.org/}OrdTermsDTO" minOccurs="0"/>
 *         &lt;element name="ExpectedEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderTermGroup" type="{http://www.openuri.org/}OrdTermsGroupDTO" minOccurs="0"/>
 *         &lt;element name="TermsID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CsCustomizedDisplay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="CreditProductID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermGroupID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdTermsDTO" minOccurs="0"/>
 *         &lt;element name="DomainType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MeasureUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartMonth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TerminateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderPenalties" type="{http://www.openuri.org/}ArrayOfOrdPenaltyDTO" minOccurs="0"/>
 *         &lt;element name="CommitmentOption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffectiveEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffectiveStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PenaltyAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EarlyTermDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcOfferSubKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdTermsDTO", propOrder = {
    "waiveReason",
    "orderPenaltyMap",
    "action",
    "customizeManualDelete",
    "customizeQuantity",
    "effectiveDependancy",
    "expectedEndDate",
    "orderTermGroup",
    "termsID",
    "csCustomizedDisplay",
    "orderOfferAssignment",
    "creditProductID",
    "termGroupID",
    "profileImage",
    "domainType",
    "duration",
    "measureUnit",
    "startMonth",
    "terminateDate",
    "orderPenalties",
    "commitmentOption",
    "effectiveEndDate",
    "effectiveStartDate",
    "penaltyAmount",
    "serviceOrderNumber",
    "earlyTermDate",
    "lastUpdBy",
    "lastUpdDate",
    "ccOfferSubKey"
})
public class OrdTermsDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "WaiveReason")
    protected String waiveReason;
    @XmlElement(name = "OrderPenaltyMap")
    protected Map orderPenaltyMap;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "CustomizeManualDelete")
    protected String customizeManualDelete;
    @XmlElement(name = "CustomizeQuantity")
    protected String customizeQuantity;
    @XmlElement(name = "EffectiveDependancy")
    protected OrdTermsDTO effectiveDependancy;
    @XmlElement(name = "ExpectedEndDate")
    protected String expectedEndDate;
    @XmlElement(name = "OrderTermGroup")
    protected OrdTermsGroupDTO orderTermGroup;
    @XmlElement(name = "TermsID")
    protected String termsID;
    @XmlElement(name = "CsCustomizedDisplay")
    protected String csCustomizedDisplay;
    @XmlElement(name = "OrderOfferAssignment")
    protected OrdOfferAssignmentDTO orderOfferAssignment;
    @XmlElement(name = "CreditProductID")
    protected String creditProductID;
    @XmlElement(name = "TermGroupID")
    protected String termGroupID;
    @XmlElement(name = "ProfileImage")
    protected OrdTermsDTO profileImage;
    @XmlElement(name = "DomainType")
    protected String domainType;
    @XmlElement(name = "Duration")
    protected Integer duration;
    @XmlElement(name = "MeasureUnit")
    protected String measureUnit;
    @XmlElement(name = "StartMonth")
    protected int startMonth;
    @XmlElement(name = "TerminateDate")
    protected String terminateDate;
    @XmlElement(name = "OrderPenalties")
    protected ArrayOfOrdPenaltyDTO orderPenalties;
    @XmlElement(name = "CommitmentOption")
    protected String commitmentOption;
    @XmlElement(name = "EffectiveEndDate")
    protected String effectiveEndDate;
    @XmlElement(name = "EffectiveStartDate")
    protected String effectiveStartDate;
    @XmlElement(name = "PenaltyAmount")
    protected String penaltyAmount;
    @XmlElement(name = "ServiceOrderNumber")
    protected String serviceOrderNumber;
    @XmlElement(name = "EarlyTermDate")
    protected String earlyTermDate;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "LastUpdDate")
    protected String lastUpdDate;
    @XmlElement(name = "CcOfferSubKey")
    protected String ccOfferSubKey;

    /**
     * Gets the value of the waiveReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaiveReason() {
        return waiveReason;
    }

    /**
     * Sets the value of the waiveReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaiveReason(String value) {
        this.waiveReason = value;
    }

    /**
     * Gets the value of the orderPenaltyMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderPenaltyMap() {
        return orderPenaltyMap;
    }

    /**
     * Sets the value of the orderPenaltyMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderPenaltyMap(Map value) {
        this.orderPenaltyMap = value;
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
     * Gets the value of the customizeManualDelete property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomizeManualDelete() {
        return customizeManualDelete;
    }

    /**
     * Sets the value of the customizeManualDelete property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomizeManualDelete(String value) {
        this.customizeManualDelete = value;
    }

    /**
     * Gets the value of the customizeQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomizeQuantity() {
        return customizeQuantity;
    }

    /**
     * Sets the value of the customizeQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomizeQuantity(String value) {
        this.customizeQuantity = value;
    }

    /**
     * Gets the value of the effectiveDependancy property.
     * 
     * @return
     *     possible object is
     *     {@link OrdTermsDTO }
     *     
     */
    public OrdTermsDTO getEffectiveDependancy() {
        return effectiveDependancy;
    }

    /**
     * Sets the value of the effectiveDependancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdTermsDTO }
     *     
     */
    public void setEffectiveDependancy(OrdTermsDTO value) {
        this.effectiveDependancy = value;
    }

    /**
     * Gets the value of the expectedEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    /**
     * Sets the value of the expectedEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedEndDate(String value) {
        this.expectedEndDate = value;
    }

    /**
     * Gets the value of the orderTermGroup property.
     * 
     * @return
     *     possible object is
     *     {@link OrdTermsGroupDTO }
     *     
     */
    public OrdTermsGroupDTO getOrderTermGroup() {
        return orderTermGroup;
    }

    /**
     * Sets the value of the orderTermGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdTermsGroupDTO }
     *     
     */
    public void setOrderTermGroup(OrdTermsGroupDTO value) {
        this.orderTermGroup = value;
    }

    /**
     * Gets the value of the termsID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermsID() {
        return termsID;
    }

    /**
     * Sets the value of the termsID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermsID(String value) {
        this.termsID = value;
    }

    /**
     * Gets the value of the csCustomizedDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCsCustomizedDisplay() {
        return csCustomizedDisplay;
    }

    /**
     * Sets the value of the csCustomizedDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCsCustomizedDisplay(String value) {
        this.csCustomizedDisplay = value;
    }

    /**
     * Gets the value of the orderOfferAssignment property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferAssignmentDTO }
     *     
     */
    public OrdOfferAssignmentDTO getOrderOfferAssignment() {
        return orderOfferAssignment;
    }

    /**
     * Sets the value of the orderOfferAssignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferAssignmentDTO }
     *     
     */
    public void setOrderOfferAssignment(OrdOfferAssignmentDTO value) {
        this.orderOfferAssignment = value;
    }

    /**
     * Gets the value of the creditProductID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditProductID() {
        return creditProductID;
    }

    /**
     * Sets the value of the creditProductID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditProductID(String value) {
        this.creditProductID = value;
    }

    /**
     * Gets the value of the termGroupID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermGroupID() {
        return termGroupID;
    }

    /**
     * Sets the value of the termGroupID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermGroupID(String value) {
        this.termGroupID = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdTermsDTO }
     *     
     */
    public OrdTermsDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdTermsDTO }
     *     
     */
    public void setProfileImage(OrdTermsDTO value) {
        this.profileImage = value;
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
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDuration(Integer value) {
        this.duration = value;
    }

    /**
     * Gets the value of the measureUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasureUnit() {
        return measureUnit;
    }

    /**
     * Sets the value of the measureUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasureUnit(String value) {
        this.measureUnit = value;
    }

    /**
     * Gets the value of the startMonth property.
     * 
     */
    public int getStartMonth() {
        return startMonth;
    }

    /**
     * Sets the value of the startMonth property.
     * 
     */
    public void setStartMonth(int value) {
        this.startMonth = value;
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
     * Gets the value of the orderPenalties property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdPenaltyDTO }
     *     
     */
    public ArrayOfOrdPenaltyDTO getOrderPenalties() {
        return orderPenalties;
    }

    /**
     * Sets the value of the orderPenalties property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdPenaltyDTO }
     *     
     */
    public void setOrderPenalties(ArrayOfOrdPenaltyDTO value) {
        this.orderPenalties = value;
    }

    /**
     * Gets the value of the commitmentOption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommitmentOption() {
        return commitmentOption;
    }

    /**
     * Sets the value of the commitmentOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommitmentOption(String value) {
        this.commitmentOption = value;
    }

    /**
     * Gets the value of the effectiveEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveEndDate() {
        return effectiveEndDate;
    }

    /**
     * Sets the value of the effectiveEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveEndDate(String value) {
        this.effectiveEndDate = value;
    }

    /**
     * Gets the value of the effectiveStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveStartDate() {
        return effectiveStartDate;
    }

    /**
     * Sets the value of the effectiveStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveStartDate(String value) {
        this.effectiveStartDate = value;
    }

    /**
     * Gets the value of the penaltyAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPenaltyAmount() {
        return penaltyAmount;
    }

    /**
     * Sets the value of the penaltyAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPenaltyAmount(String value) {
        this.penaltyAmount = value;
    }

    /**
     * Gets the value of the serviceOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceOrderNumber() {
        return serviceOrderNumber;
    }

    /**
     * Sets the value of the serviceOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceOrderNumber(String value) {
        this.serviceOrderNumber = value;
    }

    /**
     * Gets the value of the earlyTermDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarlyTermDate() {
        return earlyTermDate;
    }

    /**
     * Sets the value of the earlyTermDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarlyTermDate(String value) {
        this.earlyTermDate = value;
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
     * Gets the value of the ccOfferSubKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcOfferSubKey() {
        return ccOfferSubKey;
    }

    /**
     * Sets the value of the ccOfferSubKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcOfferSubKey(String value) {
        this.ccOfferSubKey = value;
    }

}

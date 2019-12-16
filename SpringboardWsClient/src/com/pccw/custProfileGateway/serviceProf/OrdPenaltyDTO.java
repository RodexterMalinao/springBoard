
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdPenaltyDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdPenaltyDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderPenaltyTierMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="PenaltyCustomizedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PenaltyID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Waive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GenerateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderPenaltyAssignment" type="{http://www.openuri.org/}OrdPenaltyAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OrderTerms" type="{http://www.openuri.org/}OrdTermsDTO" minOccurs="0"/>
 *         &lt;element name="PenaltyCustomizedPercentage" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="PenaltyEstimatedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemainingQty" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="UsedQty" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="WaiveReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdPenaltyDTO" minOccurs="0"/>
 *         &lt;element name="OrderPenaltyTiers" type="{http://www.openuri.org/}ArrayOfOrdPenaltyTierDTO" minOccurs="0"/>
 *         &lt;element name="PenaltyPsefCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdPenaltyDTO", propOrder = {
    "orderPenaltyTierMap",
    "penaltyCustomizedAmount",
    "penaltyID",
    "waive",
    "action",
    "generateDate",
    "orderPenaltyAssignment",
    "orderTerms",
    "penaltyCustomizedPercentage",
    "penaltyEstimatedAmount",
    "remainingQty",
    "usedQty",
    "waiveReason",
    "profileImage",
    "orderPenaltyTiers",
    "penaltyPsefCode"
})
public class OrdPenaltyDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderPenaltyTierMap")
    protected Map orderPenaltyTierMap;
    @XmlElement(name = "PenaltyCustomizedAmount")
    protected String penaltyCustomizedAmount;
    @XmlElement(name = "PenaltyID")
    protected String penaltyID;
    @XmlElement(name = "Waive")
    protected String waive;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "GenerateDate")
    protected String generateDate;
    @XmlElement(name = "OrderPenaltyAssignment")
    protected OrdPenaltyAssignmentDTO orderPenaltyAssignment;
    @XmlElement(name = "OrderTerms")
    protected OrdTermsDTO orderTerms;
    @XmlElement(name = "PenaltyCustomizedPercentage")
    protected float penaltyCustomizedPercentage;
    @XmlElement(name = "PenaltyEstimatedAmount")
    protected String penaltyEstimatedAmount;
    @XmlElement(name = "RemainingQty")
    protected float remainingQty;
    @XmlElement(name = "UsedQty")
    protected float usedQty;
    @XmlElement(name = "WaiveReason")
    protected String waiveReason;
    @XmlElement(name = "ProfileImage")
    protected OrdPenaltyDTO profileImage;
    @XmlElement(name = "OrderPenaltyTiers")
    protected ArrayOfOrdPenaltyTierDTO orderPenaltyTiers;
    @XmlElement(name = "PenaltyPsefCode")
    protected String penaltyPsefCode;

    /**
     * Gets the value of the orderPenaltyTierMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderPenaltyTierMap() {
        return orderPenaltyTierMap;
    }

    /**
     * Sets the value of the orderPenaltyTierMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderPenaltyTierMap(Map value) {
        this.orderPenaltyTierMap = value;
    }

    /**
     * Gets the value of the penaltyCustomizedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPenaltyCustomizedAmount() {
        return penaltyCustomizedAmount;
    }

    /**
     * Sets the value of the penaltyCustomizedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPenaltyCustomizedAmount(String value) {
        this.penaltyCustomizedAmount = value;
    }

    /**
     * Gets the value of the penaltyID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPenaltyID() {
        return penaltyID;
    }

    /**
     * Sets the value of the penaltyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPenaltyID(String value) {
        this.penaltyID = value;
    }

    /**
     * Gets the value of the waive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaive() {
        return waive;
    }

    /**
     * Sets the value of the waive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaive(String value) {
        this.waive = value;
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
     * Gets the value of the generateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenerateDate() {
        return generateDate;
    }

    /**
     * Sets the value of the generateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenerateDate(String value) {
        this.generateDate = value;
    }

    /**
     * Gets the value of the orderPenaltyAssignment property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPenaltyAssignmentDTO }
     *     
     */
    public OrdPenaltyAssignmentDTO getOrderPenaltyAssignment() {
        return orderPenaltyAssignment;
    }

    /**
     * Sets the value of the orderPenaltyAssignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPenaltyAssignmentDTO }
     *     
     */
    public void setOrderPenaltyAssignment(OrdPenaltyAssignmentDTO value) {
        this.orderPenaltyAssignment = value;
    }

    /**
     * Gets the value of the orderTerms property.
     * 
     * @return
     *     possible object is
     *     {@link OrdTermsDTO }
     *     
     */
    public OrdTermsDTO getOrderTerms() {
        return orderTerms;
    }

    /**
     * Sets the value of the orderTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdTermsDTO }
     *     
     */
    public void setOrderTerms(OrdTermsDTO value) {
        this.orderTerms = value;
    }

    /**
     * Gets the value of the penaltyCustomizedPercentage property.
     * 
     */
    public float getPenaltyCustomizedPercentage() {
        return penaltyCustomizedPercentage;
    }

    /**
     * Sets the value of the penaltyCustomizedPercentage property.
     * 
     */
    public void setPenaltyCustomizedPercentage(float value) {
        this.penaltyCustomizedPercentage = value;
    }

    /**
     * Gets the value of the penaltyEstimatedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPenaltyEstimatedAmount() {
        return penaltyEstimatedAmount;
    }

    /**
     * Sets the value of the penaltyEstimatedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPenaltyEstimatedAmount(String value) {
        this.penaltyEstimatedAmount = value;
    }

    /**
     * Gets the value of the remainingQty property.
     * 
     */
    public float getRemainingQty() {
        return remainingQty;
    }

    /**
     * Sets the value of the remainingQty property.
     * 
     */
    public void setRemainingQty(float value) {
        this.remainingQty = value;
    }

    /**
     * Gets the value of the usedQty property.
     * 
     */
    public float getUsedQty() {
        return usedQty;
    }

    /**
     * Sets the value of the usedQty property.
     * 
     */
    public void setUsedQty(float value) {
        this.usedQty = value;
    }

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
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPenaltyDTO }
     *     
     */
    public OrdPenaltyDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPenaltyDTO }
     *     
     */
    public void setProfileImage(OrdPenaltyDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the orderPenaltyTiers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdPenaltyTierDTO }
     *     
     */
    public ArrayOfOrdPenaltyTierDTO getOrderPenaltyTiers() {
        return orderPenaltyTiers;
    }

    /**
     * Sets the value of the orderPenaltyTiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdPenaltyTierDTO }
     *     
     */
    public void setOrderPenaltyTiers(ArrayOfOrdPenaltyTierDTO value) {
        this.orderPenaltyTiers = value;
    }

    /**
     * Gets the value of the penaltyPsefCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPenaltyPsefCode() {
        return penaltyPsefCode;
    }

    /**
     * Sets the value of the penaltyPsefCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPenaltyPsefCode(String value) {
        this.penaltyPsefCode = value;
    }

}

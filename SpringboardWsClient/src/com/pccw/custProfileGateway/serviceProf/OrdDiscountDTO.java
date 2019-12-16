
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdDiscountDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdDiscountDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DiscountID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDiscountAssignment" type="{http://www.openuri.org/}OrdDiscountAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OrderOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="CreditProductID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DiscountGroupID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdDiscountDTO" minOccurs="0"/>
 *         &lt;element name="Remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SentDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TerminationDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserCreateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcOfferSubKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RebatePsefCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdDiscountDTO", propOrder = {
    "action",
    "discountID",
    "orderDiscountAssignment",
    "orderOfferAssignment",
    "creditProductID",
    "discountGroupID",
    "profileImage",
    "remark",
    "effectiveDate",
    "orderNo",
    "sentDate",
    "terminationDate",
    "userCreateDate",
    "ccOfferSubKey",
    "duration",
    "rebatePsefCode"
})
public class OrdDiscountDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "DiscountID")
    protected String discountID;
    @XmlElement(name = "OrderDiscountAssignment")
    protected OrdDiscountAssignmentDTO orderDiscountAssignment;
    @XmlElement(name = "OrderOfferAssignment")
    protected OrdOfferAssignmentDTO orderOfferAssignment;
    @XmlElement(name = "CreditProductID")
    protected String creditProductID;
    @XmlElement(name = "DiscountGroupID")
    protected String discountGroupID;
    @XmlElement(name = "ProfileImage")
    protected OrdDiscountDTO profileImage;
    @XmlElement(name = "Remark")
    protected String remark;
    @XmlElement(name = "EffectiveDate")
    protected String effectiveDate;
    @XmlElement(name = "OrderNo")
    protected String orderNo;
    @XmlElement(name = "SentDate")
    protected String sentDate;
    @XmlElement(name = "TerminationDate")
    protected String terminationDate;
    @XmlElement(name = "UserCreateDate")
    protected String userCreateDate;
    @XmlElement(name = "CcOfferSubKey")
    protected String ccOfferSubKey;
    @XmlElement(name = "Duration")
    protected String duration;
    @XmlElement(name = "RebatePsefCode")
    protected String rebatePsefCode;

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
     * Gets the value of the discountID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscountID() {
        return discountID;
    }

    /**
     * Sets the value of the discountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscountID(String value) {
        this.discountID = value;
    }

    /**
     * Gets the value of the orderDiscountAssignment property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDiscountAssignmentDTO }
     *     
     */
    public OrdDiscountAssignmentDTO getOrderDiscountAssignment() {
        return orderDiscountAssignment;
    }

    /**
     * Sets the value of the orderDiscountAssignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDiscountAssignmentDTO }
     *     
     */
    public void setOrderDiscountAssignment(OrdDiscountAssignmentDTO value) {
        this.orderDiscountAssignment = value;
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
     * Gets the value of the discountGroupID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscountGroupID() {
        return discountGroupID;
    }

    /**
     * Sets the value of the discountGroupID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscountGroupID(String value) {
        this.discountGroupID = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDiscountDTO }
     *     
     */
    public OrdDiscountDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDiscountDTO }
     *     
     */
    public void setProfileImage(OrdDiscountDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
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
     * Gets the value of the orderNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * Sets the value of the orderNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNo(String value) {
        this.orderNo = value;
    }

    /**
     * Gets the value of the sentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSentDate() {
        return sentDate;
    }

    /**
     * Sets the value of the sentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSentDate(String value) {
        this.sentDate = value;
    }

    /**
     * Gets the value of the terminationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminationDate() {
        return terminationDate;
    }

    /**
     * Sets the value of the terminationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminationDate(String value) {
        this.terminationDate = value;
    }

    /**
     * Gets the value of the userCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCreateDate() {
        return userCreateDate;
    }

    /**
     * Sets the value of the userCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCreateDate(String value) {
        this.userCreateDate = value;
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

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * Gets the value of the rebatePsefCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRebatePsefCode() {
        return rebatePsefCode;
    }

    /**
     * Sets the value of the rebatePsefCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRebatePsefCode(String value) {
        this.rebatePsefCode = value;
    }

}


package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdPricingTierDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdPricingTierDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderPricingScheme" type="{http://www.openuri.org/}OrdPricingSchemeDTO" minOccurs="0"/>
 *         &lt;element name="CustomizePercentage" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="PricingTierID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdPricingTierDTO" minOccurs="0"/>
 *         &lt;element name="CustomizeAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistingAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdPricingTierDTO", propOrder = {
    "orderPricingScheme",
    "customizePercentage",
    "pricingTierID",
    "action",
    "profileImage",
    "customizeAmount",
    "existingAmount"
})
public class OrdPricingTierDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderPricingScheme")
    protected OrdPricingSchemeDTO orderPricingScheme;
    @XmlElement(name = "CustomizePercentage")
    protected float customizePercentage;
    @XmlElement(name = "PricingTierID")
    protected String pricingTierID;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "ProfileImage")
    protected OrdPricingTierDTO profileImage;
    @XmlElement(name = "CustomizeAmount")
    protected String customizeAmount;
    @XmlElement(name = "ExistingAmount")
    protected String existingAmount;

    /**
     * Gets the value of the orderPricingScheme property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPricingSchemeDTO }
     *     
     */
    public OrdPricingSchemeDTO getOrderPricingScheme() {
        return orderPricingScheme;
    }

    /**
     * Sets the value of the orderPricingScheme property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPricingSchemeDTO }
     *     
     */
    public void setOrderPricingScheme(OrdPricingSchemeDTO value) {
        this.orderPricingScheme = value;
    }

    /**
     * Gets the value of the customizePercentage property.
     * 
     */
    public float getCustomizePercentage() {
        return customizePercentage;
    }

    /**
     * Sets the value of the customizePercentage property.
     * 
     */
    public void setCustomizePercentage(float value) {
        this.customizePercentage = value;
    }

    /**
     * Gets the value of the pricingTierID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingTierID() {
        return pricingTierID;
    }

    /**
     * Sets the value of the pricingTierID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingTierID(String value) {
        this.pricingTierID = value;
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
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPricingTierDTO }
     *     
     */
    public OrdPricingTierDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPricingTierDTO }
     *     
     */
    public void setProfileImage(OrdPricingTierDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the customizeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomizeAmount() {
        return customizeAmount;
    }

    /**
     * Sets the value of the customizeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomizeAmount(String value) {
        this.customizeAmount = value;
    }

    /**
     * Gets the value of the existingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistingAmount() {
        return existingAmount;
    }

    /**
     * Sets the value of the existingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistingAmount(String value) {
        this.existingAmount = value;
    }

}

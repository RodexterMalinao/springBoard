
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdPenaltyTierDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdPenaltyTierDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="PenaltyTierID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdPenaltyTierDTO" minOccurs="0"/>
 *         &lt;element name="OrderPenalty" type="{http://www.openuri.org/}OrdPenaltyDTO" minOccurs="0"/>
 *         &lt;element name="Month" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdPenaltyTierDTO", propOrder = {
    "penaltyTierID",
    "action",
    "profileImage",
    "orderPenalty",
    "month"
})
public class OrdPenaltyTierDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "PenaltyTierID")
    protected String penaltyTierID;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "ProfileImage")
    protected OrdPenaltyTierDTO profileImage;
    @XmlElement(name = "OrderPenalty")
    protected OrdPenaltyDTO orderPenalty;
    @XmlElement(name = "Month")
    protected String month;

    /**
     * Gets the value of the penaltyTierID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPenaltyTierID() {
        return penaltyTierID;
    }

    /**
     * Sets the value of the penaltyTierID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPenaltyTierID(String value) {
        this.penaltyTierID = value;
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
     *     {@link OrdPenaltyTierDTO }
     *     
     */
    public OrdPenaltyTierDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPenaltyTierDTO }
     *     
     */
    public void setProfileImage(OrdPenaltyTierDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the orderPenalty property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPenaltyDTO }
     *     
     */
    public OrdPenaltyDTO getOrderPenalty() {
        return orderPenalty;
    }

    /**
     * Sets the value of the orderPenalty property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPenaltyDTO }
     *     
     */
    public void setOrderPenalty(OrdPenaltyDTO value) {
        this.orderPenalty = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonth(String value) {
        this.month = value;
    }

}

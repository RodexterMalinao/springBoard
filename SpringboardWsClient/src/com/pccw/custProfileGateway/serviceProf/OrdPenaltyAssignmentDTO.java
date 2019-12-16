
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdPenaltyAssignmentDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdPenaltyAssignmentDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderPenaltyMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="PenaltyGroupID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="PenalizeOrderTerm" type="{http://www.openuri.org/}OrdTermsDTO" minOccurs="0"/>
 *         &lt;element name="OrderPenalties" type="{http://www.openuri.org/}ArrayOfOrdPenaltyDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdPenaltyAssignmentDTO", propOrder = {
    "orderPenaltyMap",
    "penaltyGroupID",
    "action",
    "orderOfferAssignment",
    "penalizeOrderTerm",
    "orderPenalties"
})
public class OrdPenaltyAssignmentDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderPenaltyMap")
    protected Map orderPenaltyMap;
    @XmlElement(name = "PenaltyGroupID")
    protected String penaltyGroupID;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderOfferAssignment")
    protected OrdOfferAssignmentDTO orderOfferAssignment;
    @XmlElement(name = "PenalizeOrderTerm")
    protected OrdTermsDTO penalizeOrderTerm;
    @XmlElement(name = "OrderPenalties")
    protected ArrayOfOrdPenaltyDTO orderPenalties;

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
     * Gets the value of the penaltyGroupID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPenaltyGroupID() {
        return penaltyGroupID;
    }

    /**
     * Sets the value of the penaltyGroupID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPenaltyGroupID(String value) {
        this.penaltyGroupID = value;
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
     * Gets the value of the penalizeOrderTerm property.
     * 
     * @return
     *     possible object is
     *     {@link OrdTermsDTO }
     *     
     */
    public OrdTermsDTO getPenalizeOrderTerm() {
        return penalizeOrderTerm;
    }

    /**
     * Sets the value of the penalizeOrderTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdTermsDTO }
     *     
     */
    public void setPenalizeOrderTerm(OrdTermsDTO value) {
        this.penalizeOrderTerm = value;
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

}

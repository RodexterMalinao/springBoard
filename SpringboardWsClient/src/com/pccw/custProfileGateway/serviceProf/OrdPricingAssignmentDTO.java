
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdPricingAssignmentDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdPricingAssignmentDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderPricingSchemeMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="PricingAssignmentID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OrderPricingSchemes" type="{http://www.openuri.org/}ArrayOfOrdPricingSchemeDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdPricingAssignmentDTO", propOrder = {
    "orderPricingSchemeMap",
    "pricingAssignmentID",
    "action",
    "orderOfferAssignment",
    "orderPricingSchemes"
})
public class OrdPricingAssignmentDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderPricingSchemeMap")
    protected Map orderPricingSchemeMap;
    @XmlElement(name = "PricingAssignmentID")
    protected String pricingAssignmentID;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderOfferAssignment")
    protected OrdOfferAssignmentDTO orderOfferAssignment;
    @XmlElement(name = "OrderPricingSchemes")
    protected ArrayOfOrdPricingSchemeDTO orderPricingSchemes;

    /**
     * Gets the value of the orderPricingSchemeMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderPricingSchemeMap() {
        return orderPricingSchemeMap;
    }

    /**
     * Sets the value of the orderPricingSchemeMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderPricingSchemeMap(Map value) {
        this.orderPricingSchemeMap = value;
    }

    /**
     * Gets the value of the pricingAssignmentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingAssignmentID() {
        return pricingAssignmentID;
    }

    /**
     * Sets the value of the pricingAssignmentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingAssignmentID(String value) {
        this.pricingAssignmentID = value;
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
     * Gets the value of the orderPricingSchemes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdPricingSchemeDTO }
     *     
     */
    public ArrayOfOrdPricingSchemeDTO getOrderPricingSchemes() {
        return orderPricingSchemes;
    }

    /**
     * Sets the value of the orderPricingSchemes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdPricingSchemeDTO }
     *     
     */
    public void setOrderPricingSchemes(ArrayOfOrdPricingSchemeDTO value) {
        this.orderPricingSchemes = value;
    }

}

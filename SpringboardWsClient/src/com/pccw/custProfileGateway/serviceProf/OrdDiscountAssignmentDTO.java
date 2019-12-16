
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdDiscountAssignmentDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdDiscountAssignmentDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderDiscountMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DiscountAssignmentID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OrderDiscounts" type="{http://www.openuri.org/}ArrayOfOrdDiscountDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdDiscountAssignmentDTO", propOrder = {
    "orderDiscountMap",
    "action",
    "discountAssignmentID",
    "orderOfferAssignment",
    "orderDiscounts"
})
public class OrdDiscountAssignmentDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderDiscountMap")
    protected Map orderDiscountMap;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "DiscountAssignmentID")
    protected String discountAssignmentID;
    @XmlElement(name = "OrderOfferAssignment")
    protected OrdOfferAssignmentDTO orderOfferAssignment;
    @XmlElement(name = "OrderDiscounts")
    protected ArrayOfOrdDiscountDTO orderDiscounts;

    /**
     * Gets the value of the orderDiscountMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderDiscountMap() {
        return orderDiscountMap;
    }

    /**
     * Sets the value of the orderDiscountMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderDiscountMap(Map value) {
        this.orderDiscountMap = value;
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
     * Gets the value of the discountAssignmentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscountAssignmentID() {
        return discountAssignmentID;
    }

    /**
     * Sets the value of the discountAssignmentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscountAssignmentID(String value) {
        this.discountAssignmentID = value;
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
     * Gets the value of the orderDiscounts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdDiscountDTO }
     *     
     */
    public ArrayOfOrdDiscountDTO getOrderDiscounts() {
        return orderDiscounts;
    }

    /**
     * Sets the value of the orderDiscounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdDiscountDTO }
     *     
     */
    public void setOrderDiscounts(ArrayOfOrdDiscountDTO value) {
        this.orderDiscounts = value;
    }

}

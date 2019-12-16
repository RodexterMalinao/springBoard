
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdIncentiveAssignmentDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdIncentiveAssignmentDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderIncentiveMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OrderIncentives" type="{http://www.openuri.org/}ArrayOfOrdIncentiveDTO" minOccurs="0"/>
 *         &lt;element name="IncentiveAssignmentID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdIncentiveAssignmentDTO", propOrder = {
    "orderIncentiveMap",
    "action",
    "orderOfferAssignment",
    "orderIncentives",
    "incentiveAssignmentID"
})
public class OrdIncentiveAssignmentDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderIncentiveMap")
    protected Map orderIncentiveMap;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderOfferAssignment")
    protected OrdOfferAssignmentDTO orderOfferAssignment;
    @XmlElement(name = "OrderIncentives")
    protected ArrayOfOrdIncentiveDTO orderIncentives;
    @XmlElement(name = "IncentiveAssignmentID")
    protected String incentiveAssignmentID;

    /**
     * Gets the value of the orderIncentiveMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderIncentiveMap() {
        return orderIncentiveMap;
    }

    /**
     * Sets the value of the orderIncentiveMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderIncentiveMap(Map value) {
        this.orderIncentiveMap = value;
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
     * Gets the value of the orderIncentives property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdIncentiveDTO }
     *     
     */
    public ArrayOfOrdIncentiveDTO getOrderIncentives() {
        return orderIncentives;
    }

    /**
     * Sets the value of the orderIncentives property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdIncentiveDTO }
     *     
     */
    public void setOrderIncentives(ArrayOfOrdIncentiveDTO value) {
        this.orderIncentives = value;
    }

    /**
     * Gets the value of the incentiveAssignmentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncentiveAssignmentID() {
        return incentiveAssignmentID;
    }

    /**
     * Sets the value of the incentiveAssignmentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncentiveAssignmentID(String value) {
        this.incentiveAssignmentID = value;
    }

}

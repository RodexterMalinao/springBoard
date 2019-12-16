
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdTermsGroupDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdTermsGroupDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderTermMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="CustomizedInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConditionGroupID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OrderTerms" type="{http://www.openuri.org/}ArrayOfOrdTermsDTO" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdTermsGroupDTO", propOrder = {
    "orderTermMap",
    "customizedInd",
    "conditionGroupID",
    "orderOfferAssignment",
    "orderTerms",
    "action"
})
public class OrdTermsGroupDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderTermMap")
    protected Map orderTermMap;
    @XmlElement(name = "CustomizedInd")
    protected String customizedInd;
    @XmlElement(name = "ConditionGroupID")
    protected String conditionGroupID;
    @XmlElement(name = "OrderOfferAssignment")
    protected OrdOfferAssignmentDTO orderOfferAssignment;
    @XmlElement(name = "OrderTerms")
    protected ArrayOfOrdTermsDTO orderTerms;
    @XmlElement(name = "Action")
    protected int action;

    /**
     * Gets the value of the orderTermMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderTermMap() {
        return orderTermMap;
    }

    /**
     * Sets the value of the orderTermMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderTermMap(Map value) {
        this.orderTermMap = value;
    }

    /**
     * Gets the value of the customizedInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomizedInd() {
        return customizedInd;
    }

    /**
     * Sets the value of the customizedInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomizedInd(String value) {
        this.customizedInd = value;
    }

    /**
     * Gets the value of the conditionGroupID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionGroupID() {
        return conditionGroupID;
    }

    /**
     * Sets the value of the conditionGroupID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionGroupID(String value) {
        this.conditionGroupID = value;
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
     * Gets the value of the orderTerms property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdTermsDTO }
     *     
     */
    public ArrayOfOrdTermsDTO getOrderTerms() {
        return orderTerms;
    }

    /**
     * Sets the value of the orderTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdTermsDTO }
     *     
     */
    public void setOrderTerms(ArrayOfOrdTermsDTO value) {
        this.orderTerms = value;
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

}


package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdGroupFeatureDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdGroupFeatureDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActionQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ComponentID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistingQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderDetailGroup" type="{http://www.openuri.org/}OrdDetailGroupDTO" minOccurs="0"/>
 *         &lt;element name="OrderGroupFeatureSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PsefCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PsefInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdGroupFeatureDTO" minOccurs="0"/>
 *         &lt;element name="OrderDetailGroupSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderGroupFeatureAttributes" type="{http://www.openuri.org/}ArrayOfOrdGroupFeatureAttributeDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdGroupFeatureDTO", propOrder = {
    "action",
    "actionCode",
    "actionQuantity",
    "componentID",
    "existingQuantity",
    "orderDetailGroup",
    "orderGroupFeatureSeq",
    "psefCode",
    "psefInd",
    "quantity",
    "profileImage",
    "orderDetailGroupSequence",
    "orderGroupFeatureAttributes"
})
public class OrdGroupFeatureDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "ActionCode")
    protected String actionCode;
    @XmlElement(name = "ActionQuantity")
    protected int actionQuantity;
    @XmlElement(name = "ComponentID")
    protected String componentID;
    @XmlElement(name = "ExistingQuantity")
    protected int existingQuantity;
    @XmlElement(name = "OrderDetailGroup")
    protected OrdDetailGroupDTO orderDetailGroup;
    @XmlElement(name = "OrderGroupFeatureSeq")
    protected String orderGroupFeatureSeq;
    @XmlElement(name = "PsefCode")
    protected String psefCode;
    @XmlElement(name = "PsefInd")
    protected String psefInd;
    @XmlElement(name = "Quantity")
    protected int quantity;
    @XmlElement(name = "ProfileImage")
    protected OrdGroupFeatureDTO profileImage;
    @XmlElement(name = "OrderDetailGroupSequence")
    protected String orderDetailGroupSequence;
    @XmlElement(name = "OrderGroupFeatureAttributes")
    protected ArrayOfOrdGroupFeatureAttributeDTO orderGroupFeatureAttributes;

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
     * Gets the value of the actionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * Sets the value of the actionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionCode(String value) {
        this.actionCode = value;
    }

    /**
     * Gets the value of the actionQuantity property.
     * 
     */
    public int getActionQuantity() {
        return actionQuantity;
    }

    /**
     * Sets the value of the actionQuantity property.
     * 
     */
    public void setActionQuantity(int value) {
        this.actionQuantity = value;
    }

    /**
     * Gets the value of the componentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComponentID() {
        return componentID;
    }

    /**
     * Sets the value of the componentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComponentID(String value) {
        this.componentID = value;
    }

    /**
     * Gets the value of the existingQuantity property.
     * 
     */
    public int getExistingQuantity() {
        return existingQuantity;
    }

    /**
     * Sets the value of the existingQuantity property.
     * 
     */
    public void setExistingQuantity(int value) {
        this.existingQuantity = value;
    }

    /**
     * Gets the value of the orderDetailGroup property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public OrdDetailGroupDTO getOrderDetailGroup() {
        return orderDetailGroup;
    }

    /**
     * Sets the value of the orderDetailGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public void setOrderDetailGroup(OrdDetailGroupDTO value) {
        this.orderDetailGroup = value;
    }

    /**
     * Gets the value of the orderGroupFeatureSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderGroupFeatureSeq() {
        return orderGroupFeatureSeq;
    }

    /**
     * Sets the value of the orderGroupFeatureSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderGroupFeatureSeq(String value) {
        this.orderGroupFeatureSeq = value;
    }

    /**
     * Gets the value of the psefCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsefCode() {
        return psefCode;
    }

    /**
     * Sets the value of the psefCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsefCode(String value) {
        this.psefCode = value;
    }

    /**
     * Gets the value of the psefInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsefInd() {
        return psefInd;
    }

    /**
     * Sets the value of the psefInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsefInd(String value) {
        this.psefInd = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(int value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdGroupFeatureDTO }
     *     
     */
    public OrdGroupFeatureDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdGroupFeatureDTO }
     *     
     */
    public void setProfileImage(OrdGroupFeatureDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the orderDetailGroupSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDetailGroupSequence() {
        return orderDetailGroupSequence;
    }

    /**
     * Sets the value of the orderDetailGroupSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDetailGroupSequence(String value) {
        this.orderDetailGroupSequence = value;
    }

    /**
     * Gets the value of the orderGroupFeatureAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdGroupFeatureAttributeDTO }
     *     
     */
    public ArrayOfOrdGroupFeatureAttributeDTO getOrderGroupFeatureAttributes() {
        return orderGroupFeatureAttributes;
    }

    /**
     * Sets the value of the orderGroupFeatureAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdGroupFeatureAttributeDTO }
     *     
     */
    public void setOrderGroupFeatureAttributes(ArrayOfOrdGroupFeatureAttributeDTO value) {
        this.orderGroupFeatureAttributes = value;
    }

}

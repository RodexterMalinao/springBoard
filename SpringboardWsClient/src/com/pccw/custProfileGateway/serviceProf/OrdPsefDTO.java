
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdPsefDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdPsefDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="ActionQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AttachTo" type="{http://www.openuri.org/}OrdServiceDTO" minOccurs="0"/>
 *         &lt;element name="ChargeCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ColorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistingQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GenerateFrom" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="OneTimeChargeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderPsefSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PsefCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PsefInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChildPsefs" type="{http://www.openuri.org/}ArrayOfOrdPsefDTO" minOccurs="0"/>
 *         &lt;element name="ParentPsef" type="{http://www.openuri.org/}OrdPsefDTO" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OneTimeChargeAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrimaryInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MeasureUnitQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdPsefDTO", propOrder = {
    "actionQuantity",
    "attachTo",
    "chargeCategory",
    "colorCode",
    "existingQuantity",
    "generateFrom",
    "oneTimeChargeType",
    "orderPsefSequence",
    "psefCode",
    "psefInd",
    "actionCode",
    "childPsefs",
    "parentPsef",
    "action",
    "oneTimeChargeAmount",
    "primaryInd",
    "measureUnitQty"
})
public class OrdPsefDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "ActionQuantity")
    protected int actionQuantity;
    @XmlElement(name = "AttachTo")
    protected OrdServiceDTO attachTo;
    @XmlElement(name = "ChargeCategory")
    protected String chargeCategory;
    @XmlElement(name = "ColorCode")
    protected String colorCode;
    @XmlElement(name = "ExistingQuantity")
    protected int existingQuantity;
    @XmlElement(name = "GenerateFrom")
    protected Object generateFrom;
    @XmlElement(name = "OneTimeChargeType")
    protected String oneTimeChargeType;
    @XmlElement(name = "OrderPsefSequence")
    protected String orderPsefSequence;
    @XmlElement(name = "PsefCode")
    protected String psefCode;
    @XmlElement(name = "PsefInd")
    protected String psefInd;
    @XmlElement(name = "ActionCode")
    protected String actionCode;
    @XmlElement(name = "ChildPsefs")
    protected ArrayOfOrdPsefDTO childPsefs;
    @XmlElement(name = "ParentPsef")
    protected OrdPsefDTO parentPsef;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OneTimeChargeAmount")
    protected String oneTimeChargeAmount;
    @XmlElement(name = "PrimaryInd")
    protected String primaryInd;
    @XmlElement(name = "MeasureUnitQty")
    protected String measureUnitQty;

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
     * Gets the value of the attachTo property.
     * 
     * @return
     *     possible object is
     *     {@link OrdServiceDTO }
     *     
     */
    public OrdServiceDTO getAttachTo() {
        return attachTo;
    }

    /**
     * Sets the value of the attachTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdServiceDTO }
     *     
     */
    public void setAttachTo(OrdServiceDTO value) {
        this.attachTo = value;
    }

    /**
     * Gets the value of the chargeCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeCategory() {
        return chargeCategory;
    }

    /**
     * Sets the value of the chargeCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeCategory(String value) {
        this.chargeCategory = value;
    }

    /**
     * Gets the value of the colorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets the value of the colorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColorCode(String value) {
        this.colorCode = value;
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
     * Gets the value of the generateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getGenerateFrom() {
        return generateFrom;
    }

    /**
     * Sets the value of the generateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setGenerateFrom(Object value) {
        this.generateFrom = value;
    }

    /**
     * Gets the value of the oneTimeChargeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOneTimeChargeType() {
        return oneTimeChargeType;
    }

    /**
     * Sets the value of the oneTimeChargeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOneTimeChargeType(String value) {
        this.oneTimeChargeType = value;
    }

    /**
     * Gets the value of the orderPsefSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderPsefSequence() {
        return orderPsefSequence;
    }

    /**
     * Sets the value of the orderPsefSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderPsefSequence(String value) {
        this.orderPsefSequence = value;
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
     * Gets the value of the childPsefs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdPsefDTO }
     *     
     */
    public ArrayOfOrdPsefDTO getChildPsefs() {
        return childPsefs;
    }

    /**
     * Sets the value of the childPsefs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdPsefDTO }
     *     
     */
    public void setChildPsefs(ArrayOfOrdPsefDTO value) {
        this.childPsefs = value;
    }

    /**
     * Gets the value of the parentPsef property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPsefDTO }
     *     
     */
    public OrdPsefDTO getParentPsef() {
        return parentPsef;
    }

    /**
     * Sets the value of the parentPsef property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPsefDTO }
     *     
     */
    public void setParentPsef(OrdPsefDTO value) {
        this.parentPsef = value;
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
     * Gets the value of the oneTimeChargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOneTimeChargeAmount() {
        return oneTimeChargeAmount;
    }

    /**
     * Sets the value of the oneTimeChargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOneTimeChargeAmount(String value) {
        this.oneTimeChargeAmount = value;
    }

    /**
     * Gets the value of the primaryInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryInd() {
        return primaryInd;
    }

    /**
     * Sets the value of the primaryInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryInd(String value) {
        this.primaryInd = value;
    }

    /**
     * Gets the value of the measureUnitQty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasureUnitQty() {
        return measureUnitQty;
    }

    /**
     * Sets the value of the measureUnitQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasureUnitQty(String value) {
        this.measureUnitQty = value;
    }

}

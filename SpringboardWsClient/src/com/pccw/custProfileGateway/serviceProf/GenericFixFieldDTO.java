
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GenericFixFieldDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenericFixFieldDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="MandatoryInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemarkSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RemarkType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Value1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Value2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericFixFieldDTO", propOrder = {
    "mandatoryInd",
    "action",
    "actionType",
    "remarkSequence",
    "remarkType",
    "value1",
    "value2"
})
public class GenericFixFieldDTO
    extends ValueObject
{

    @XmlElement(name = "MandatoryInd")
    protected String mandatoryInd;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "ActionType")
    protected String actionType;
    @XmlElement(name = "RemarkSequence")
    protected int remarkSequence;
    @XmlElement(name = "RemarkType")
    protected String remarkType;
    @XmlElement(name = "Value1")
    protected String value1;
    @XmlElement(name = "Value2")
    protected String value2;

    /**
     * Gets the value of the mandatoryInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMandatoryInd() {
        return mandatoryInd;
    }

    /**
     * Sets the value of the mandatoryInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMandatoryInd(String value) {
        this.mandatoryInd = value;
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
     * Gets the value of the actionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Sets the value of the actionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionType(String value) {
        this.actionType = value;
    }

    /**
     * Gets the value of the remarkSequence property.
     * 
     */
    public int getRemarkSequence() {
        return remarkSequence;
    }

    /**
     * Sets the value of the remarkSequence property.
     * 
     */
    public void setRemarkSequence(int value) {
        this.remarkSequence = value;
    }

    /**
     * Gets the value of the remarkType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarkType() {
        return remarkType;
    }

    /**
     * Sets the value of the remarkType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarkType(String value) {
        this.remarkType = value;
    }

    /**
     * Gets the value of the value1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue1() {
        return value1;
    }

    /**
     * Sets the value of the value1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue1(String value) {
        this.value1 = value;
    }

    /**
     * Gets the value of the value2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue2() {
        return value2;
    }

    /**
     * Sets the value of the value2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue2(String value) {
        this.value2 = value;
    }

}

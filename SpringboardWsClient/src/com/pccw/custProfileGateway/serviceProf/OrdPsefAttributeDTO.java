
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdPsefAttributeDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdPsefAttributeDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="AttributeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderComponent" type="{http://www.openuri.org/}OrdOfferProductComponentDTO" minOccurs="0"/>
 *         &lt;element name="ParamCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ParamValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdPsefAttributeDTO" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderPsefAttributeSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdPsefAttributeDTO", propOrder = {
    "attributeID",
    "orderComponent",
    "paramCode",
    "paramValue",
    "profileImage",
    "action",
    "orderPsefAttributeSequence"
})
public class OrdPsefAttributeDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "AttributeID")
    protected String attributeID;
    @XmlElement(name = "OrderComponent")
    protected OrdOfferProductComponentDTO orderComponent;
    @XmlElement(name = "ParamCode")
    protected String paramCode;
    @XmlElement(name = "ParamValue")
    protected String paramValue;
    @XmlElement(name = "ProfileImage")
    protected OrdPsefAttributeDTO profileImage;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderPsefAttributeSequence")
    protected String orderPsefAttributeSequence;

    /**
     * Gets the value of the attributeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeID() {
        return attributeID;
    }

    /**
     * Sets the value of the attributeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeID(String value) {
        this.attributeID = value;
    }

    /**
     * Gets the value of the orderComponent property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferProductComponentDTO }
     *     
     */
    public OrdOfferProductComponentDTO getOrderComponent() {
        return orderComponent;
    }

    /**
     * Sets the value of the orderComponent property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferProductComponentDTO }
     *     
     */
    public void setOrderComponent(OrdOfferProductComponentDTO value) {
        this.orderComponent = value;
    }

    /**
     * Gets the value of the paramCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamCode() {
        return paramCode;
    }

    /**
     * Sets the value of the paramCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamCode(String value) {
        this.paramCode = value;
    }

    /**
     * Gets the value of the paramValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * Sets the value of the paramValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamValue(String value) {
        this.paramValue = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPsefAttributeDTO }
     *     
     */
    public OrdPsefAttributeDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPsefAttributeDTO }
     *     
     */
    public void setProfileImage(OrdPsefAttributeDTO value) {
        this.profileImage = value;
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
     * Gets the value of the orderPsefAttributeSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderPsefAttributeSequence() {
        return orderPsefAttributeSequence;
    }

    /**
     * Sets the value of the orderPsefAttributeSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderPsefAttributeSequence(String value) {
        this.orderPsefAttributeSequence = value;
    }

}

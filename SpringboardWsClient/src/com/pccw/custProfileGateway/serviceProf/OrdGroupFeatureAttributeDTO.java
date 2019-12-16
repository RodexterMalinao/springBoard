
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdGroupFeatureAttributeDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdGroupFeatureAttributeDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="AttributeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderGroupFeature" type="{http://www.openuri.org/}OrdGroupFeatureDTO" minOccurs="0"/>
 *         &lt;element name="ParamCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ParamValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdGroupFeatureAttributeDTO" minOccurs="0"/>
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
@XmlType(name = "OrdGroupFeatureAttributeDTO", propOrder = {
    "attributeID",
    "orderGroupFeature",
    "paramCode",
    "paramValue",
    "profileImage",
    "action"
})
public class OrdGroupFeatureAttributeDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "AttributeID")
    protected String attributeID;
    @XmlElement(name = "OrderGroupFeature")
    protected OrdGroupFeatureDTO orderGroupFeature;
    @XmlElement(name = "ParamCode")
    protected String paramCode;
    @XmlElement(name = "ParamValue")
    protected String paramValue;
    @XmlElement(name = "ProfileImage")
    protected OrdGroupFeatureAttributeDTO profileImage;
    @XmlElement(name = "Action")
    protected int action;

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
     * Gets the value of the orderGroupFeature property.
     * 
     * @return
     *     possible object is
     *     {@link OrdGroupFeatureDTO }
     *     
     */
    public OrdGroupFeatureDTO getOrderGroupFeature() {
        return orderGroupFeature;
    }

    /**
     * Sets the value of the orderGroupFeature property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdGroupFeatureDTO }
     *     
     */
    public void setOrderGroupFeature(OrdGroupFeatureDTO value) {
        this.orderGroupFeature = value;
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
     *     {@link OrdGroupFeatureAttributeDTO }
     *     
     */
    public OrdGroupFeatureAttributeDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdGroupFeatureAttributeDTO }
     *     
     */
    public void setProfileImage(OrdGroupFeatureAttributeDTO value) {
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

}

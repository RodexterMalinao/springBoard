
package com.pccw.updateSRD;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValueObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValueObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValidationRulesID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObjectAction" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ObjectUniqueID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModifiedFields" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueObject", propOrder = {
    "validationRulesID",
    "objectAction",
    "objectUniqueID",
    "modifiedFields"
})
@XmlSeeAlso({
    ServiceResponseDTO.class,
    ServiceRequestDTO.class
})
public class ValueObject {

    @XmlElement(name = "ValidationRulesID")
    protected String validationRulesID;
    @XmlElement(name = "ObjectAction")
    protected int objectAction;
    @XmlElement(name = "ObjectUniqueID")
    protected String objectUniqueID;
    @XmlElement(name = "ModifiedFields")
    protected ArrayOfString modifiedFields;

    /**
     * Gets the value of the validationRulesID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationRulesID() {
        return validationRulesID;
    }

    /**
     * Sets the value of the validationRulesID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationRulesID(String value) {
        this.validationRulesID = value;
    }

    /**
     * Gets the value of the objectAction property.
     * 
     */
    public int getObjectAction() {
        return objectAction;
    }

    /**
     * Sets the value of the objectAction property.
     * 
     */
    public void setObjectAction(int value) {
        this.objectAction = value;
    }

    /**
     * Gets the value of the objectUniqueID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectUniqueID() {
        return objectUniqueID;
    }

    /**
     * Sets the value of the objectUniqueID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectUniqueID(String value) {
        this.objectUniqueID = value;
    }

    /**
     * Gets the value of the modifiedFields property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getModifiedFields() {
        return modifiedFields;
    }

    /**
     * Sets the value of the modifiedFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setModifiedFields(ArrayOfString value) {
        this.modifiedFields = value;
    }

}

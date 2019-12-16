
package com.pccw.updateSRD;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SrdDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SrdDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceRequestDTO">
 *       &lt;sequence>
 *         &lt;element name="FrApptDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrApptType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrVisitInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToApptDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToApptType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToVisitInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SrdDTO", propOrder = {
    "frApptDate",
    "frApptType",
    "frEndTime",
    "frReasonCode",
    "frStartTime",
    "frVisitInd",
    "toApptDate",
    "toApptType",
    "toEndTime",
    "toReasonCode",
    "toStartTime",
    "toVisitInd"
})
public class SrdDTO
    extends ServiceRequestDTO
{

    @XmlElement(name = "FrApptDate")
    protected String frApptDate;
    @XmlElement(name = "FrApptType")
    protected String frApptType;
    @XmlElement(name = "FrEndTime")
    protected String frEndTime;
    @XmlElement(name = "FrReasonCode")
    protected String frReasonCode;
    @XmlElement(name = "FrStartTime")
    protected String frStartTime;
    @XmlElement(name = "FrVisitInd")
    protected String frVisitInd;
    @XmlElement(name = "ToApptDate")
    protected String toApptDate;
    @XmlElement(name = "ToApptType")
    protected String toApptType;
    @XmlElement(name = "ToEndTime")
    protected String toEndTime;
    @XmlElement(name = "ToReasonCode")
    protected String toReasonCode;
    @XmlElement(name = "ToStartTime")
    protected String toStartTime;
    @XmlElement(name = "ToVisitInd")
    protected String toVisitInd;

    /**
     * Gets the value of the frApptDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrApptDate() {
        return frApptDate;
    }

    /**
     * Sets the value of the frApptDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrApptDate(String value) {
        this.frApptDate = value;
    }

    /**
     * Gets the value of the frApptType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrApptType() {
        return frApptType;
    }

    /**
     * Sets the value of the frApptType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrApptType(String value) {
        this.frApptType = value;
    }

    /**
     * Gets the value of the frEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrEndTime() {
        return frEndTime;
    }

    /**
     * Sets the value of the frEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrEndTime(String value) {
        this.frEndTime = value;
    }

    /**
     * Gets the value of the frReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrReasonCode() {
        return frReasonCode;
    }

    /**
     * Sets the value of the frReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrReasonCode(String value) {
        this.frReasonCode = value;
    }

    /**
     * Gets the value of the frStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrStartTime() {
        return frStartTime;
    }

    /**
     * Sets the value of the frStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrStartTime(String value) {
        this.frStartTime = value;
    }

    /**
     * Gets the value of the frVisitInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrVisitInd() {
        return frVisitInd;
    }

    /**
     * Sets the value of the frVisitInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrVisitInd(String value) {
        this.frVisitInd = value;
    }

    /**
     * Gets the value of the toApptDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToApptDate() {
        return toApptDate;
    }

    /**
     * Sets the value of the toApptDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToApptDate(String value) {
        this.toApptDate = value;
    }

    /**
     * Gets the value of the toApptType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToApptType() {
        return toApptType;
    }

    /**
     * Sets the value of the toApptType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToApptType(String value) {
        this.toApptType = value;
    }

    /**
     * Gets the value of the toEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToEndTime() {
        return toEndTime;
    }

    /**
     * Sets the value of the toEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToEndTime(String value) {
        this.toEndTime = value;
    }

    /**
     * Gets the value of the toReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToReasonCode() {
        return toReasonCode;
    }

    /**
     * Sets the value of the toReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToReasonCode(String value) {
        this.toReasonCode = value;
    }

    /**
     * Gets the value of the toStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToStartTime() {
        return toStartTime;
    }

    /**
     * Sets the value of the toStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToStartTime(String value) {
        this.toStartTime = value;
    }

    /**
     * Gets the value of the toVisitInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToVisitInd() {
        return toVisitInd;
    }

    /**
     * Sets the value of the toVisitInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToVisitInd(String value) {
        this.toVisitInd = value;
    }

}

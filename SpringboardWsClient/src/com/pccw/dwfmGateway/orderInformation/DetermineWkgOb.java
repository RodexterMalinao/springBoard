
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetermineWkgOb complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetermineWkgOb">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObAddDelInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObDp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObExch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObGridId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObIssDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObIssTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObNotifLev" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObOrdSts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObOrdType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObUnitId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObWrkActn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObWorkgrp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetermineWkgOb", propOrder = {
    "obAddDelInd",
    "obDp",
    "obExch",
    "obGridId",
    "obIssDate",
    "obIssTime",
    "obNotifLev",
    "obOrdSts",
    "obOrdType",
    "obUnitId",
    "obWrkActn",
    "obWorkgrp"
})
public class DetermineWkgOb {

    @XmlElement(name = "ObAddDelInd")
    protected String obAddDelInd;
    @XmlElement(name = "ObDp")
    protected String obDp;
    @XmlElement(name = "ObExch")
    protected String obExch;
    @XmlElement(name = "ObGridId")
    protected String obGridId;
    @XmlElement(name = "ObIssDate")
    protected String obIssDate;
    @XmlElement(name = "ObIssTime")
    protected String obIssTime;
    @XmlElement(name = "ObNotifLev")
    protected String obNotifLev;
    @XmlElement(name = "ObOrdSts")
    protected String obOrdSts;
    @XmlElement(name = "ObOrdType")
    protected String obOrdType;
    @XmlElement(name = "ObUnitId")
    protected String obUnitId;
    @XmlElement(name = "ObWrkActn")
    protected String obWrkActn;
    @XmlElement(name = "ObWorkgrp")
    protected String obWorkgrp;

    /**
     * Gets the value of the obAddDelInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObAddDelInd() {
        return obAddDelInd;
    }

    /**
     * Sets the value of the obAddDelInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObAddDelInd(String value) {
        this.obAddDelInd = value;
    }

    /**
     * Gets the value of the obDp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObDp() {
        return obDp;
    }

    /**
     * Sets the value of the obDp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObDp(String value) {
        this.obDp = value;
    }

    /**
     * Gets the value of the obExch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObExch() {
        return obExch;
    }

    /**
     * Sets the value of the obExch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObExch(String value) {
        this.obExch = value;
    }

    /**
     * Gets the value of the obGridId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObGridId() {
        return obGridId;
    }

    /**
     * Sets the value of the obGridId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObGridId(String value) {
        this.obGridId = value;
    }

    /**
     * Gets the value of the obIssDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObIssDate() {
        return obIssDate;
    }

    /**
     * Sets the value of the obIssDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObIssDate(String value) {
        this.obIssDate = value;
    }

    /**
     * Gets the value of the obIssTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObIssTime() {
        return obIssTime;
    }

    /**
     * Sets the value of the obIssTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObIssTime(String value) {
        this.obIssTime = value;
    }

    /**
     * Gets the value of the obNotifLev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObNotifLev() {
        return obNotifLev;
    }

    /**
     * Sets the value of the obNotifLev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObNotifLev(String value) {
        this.obNotifLev = value;
    }

    /**
     * Gets the value of the obOrdSts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObOrdSts() {
        return obOrdSts;
    }

    /**
     * Sets the value of the obOrdSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObOrdSts(String value) {
        this.obOrdSts = value;
    }

    /**
     * Gets the value of the obOrdType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObOrdType() {
        return obOrdType;
    }

    /**
     * Sets the value of the obOrdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObOrdType(String value) {
        this.obOrdType = value;
    }

    /**
     * Gets the value of the obUnitId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObUnitId() {
        return obUnitId;
    }

    /**
     * Sets the value of the obUnitId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObUnitId(String value) {
        this.obUnitId = value;
    }

    /**
     * Gets the value of the obWrkActn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObWrkActn() {
        return obWrkActn;
    }

    /**
     * Sets the value of the obWrkActn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObWrkActn(String value) {
        this.obWrkActn = value;
    }

    /**
     * Gets the value of the obWorkgrp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObWorkgrp() {
        return obWorkgrp;
    }

    /**
     * Sets the value of the obWorkgrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObWorkgrp(String value) {
        this.obWorkgrp = value;
    }

}

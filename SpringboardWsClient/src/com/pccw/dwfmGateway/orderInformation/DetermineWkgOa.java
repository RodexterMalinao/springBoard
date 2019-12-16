
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetermineWkgOa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetermineWkgOa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OaApptType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaCd2312Ind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaCd7adaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaDatCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaDpCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaEnChngInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaExchId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaFrDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaFrDueTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaFrReaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaFrSbNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaFrVisitInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaGrid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaIntlInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaOrdSts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaOrdTypCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaSbNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaSpMarker_b" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaSpMarkerD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaSubNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaTentativeInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaToDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaToDueTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaToReaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaToSbNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaToVisitInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaTypOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaVisitReaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaWeatherCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaWkgContNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaWrkgrp_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OaWrkgrp_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetermineWkgOa", propOrder = {
    "oaApptType",
    "oaCd2312Ind",
    "oaCd7AdaCd",
    "oaDatCd",
    "oaDpCd",
    "oaEnChngInd",
    "oaExchId",
    "oaFrDueDate",
    "oaFrDueTime",
    "oaFrReaCd",
    "oaFrSbNum",
    "oaFrVisitInd",
    "oaGrid",
    "oaIntlInd",
    "oaOrdSts",
    "oaOrdTypCd",
    "oaSbNum",
    "oaSpMarkerB",
    "oaSpMarkerD",
    "oaSubNum",
    "oaTentativeInd",
    "oaToDueDate",
    "oaToDueTime",
    "oaToReaCd",
    "oaToSbNum",
    "oaToVisitInd",
    "oaTypOfSrv",
    "oaUserId",
    "oaVisitReaCd",
    "oaWeatherCd",
    "oaWkgContNum",
    "oaWrkgrp1",
    "oaWrkgrp2"
})
public class DetermineWkgOa {

    @XmlElement(name = "OaApptType")
    protected String oaApptType;
    @XmlElement(name = "OaCd2312Ind")
    protected String oaCd2312Ind;
    @XmlElement(name = "OaCd7adaCd")
    protected String oaCd7AdaCd;
    @XmlElement(name = "OaDatCd")
    protected String oaDatCd;
    @XmlElement(name = "OaDpCd")
    protected String oaDpCd;
    @XmlElement(name = "OaEnChngInd")
    protected String oaEnChngInd;
    @XmlElement(name = "OaExchId")
    protected String oaExchId;
    @XmlElement(name = "OaFrDueDate")
    protected String oaFrDueDate;
    @XmlElement(name = "OaFrDueTime")
    protected String oaFrDueTime;
    @XmlElement(name = "OaFrReaCd")
    protected String oaFrReaCd;
    @XmlElement(name = "OaFrSbNum")
    protected String oaFrSbNum;
    @XmlElement(name = "OaFrVisitInd")
    protected String oaFrVisitInd;
    @XmlElement(name = "OaGrid")
    protected String oaGrid;
    @XmlElement(name = "OaIntlInd")
    protected String oaIntlInd;
    @XmlElement(name = "OaOrdSts")
    protected String oaOrdSts;
    @XmlElement(name = "OaOrdTypCd")
    protected String oaOrdTypCd;
    @XmlElement(name = "OaSbNum")
    protected String oaSbNum;
    @XmlElement(name = "OaSpMarker_b")
    protected String oaSpMarkerB;
    @XmlElement(name = "OaSpMarkerD")
    protected String oaSpMarkerD;
    @XmlElement(name = "OaSubNum")
    protected String oaSubNum;
    @XmlElement(name = "OaTentativeInd")
    protected String oaTentativeInd;
    @XmlElement(name = "OaToDueDate")
    protected String oaToDueDate;
    @XmlElement(name = "OaToDueTime")
    protected String oaToDueTime;
    @XmlElement(name = "OaToReaCd")
    protected String oaToReaCd;
    @XmlElement(name = "OaToSbNum")
    protected String oaToSbNum;
    @XmlElement(name = "OaToVisitInd")
    protected String oaToVisitInd;
    @XmlElement(name = "OaTypOfSrv")
    protected String oaTypOfSrv;
    @XmlElement(name = "OaUserId")
    protected String oaUserId;
    @XmlElement(name = "OaVisitReaCd")
    protected String oaVisitReaCd;
    @XmlElement(name = "OaWeatherCd")
    protected String oaWeatherCd;
    @XmlElement(name = "OaWkgContNum")
    protected String oaWkgContNum;
    @XmlElement(name = "OaWrkgrp_1")
    protected String oaWrkgrp1;
    @XmlElement(name = "OaWrkgrp_2")
    protected String oaWrkgrp2;

    /**
     * Gets the value of the oaApptType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaApptType() {
        return oaApptType;
    }

    /**
     * Sets the value of the oaApptType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaApptType(String value) {
        this.oaApptType = value;
    }

    /**
     * Gets the value of the oaCd2312Ind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaCd2312Ind() {
        return oaCd2312Ind;
    }

    /**
     * Sets the value of the oaCd2312Ind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaCd2312Ind(String value) {
        this.oaCd2312Ind = value;
    }

    /**
     * Gets the value of the oaCd7AdaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaCd7AdaCd() {
        return oaCd7AdaCd;
    }

    /**
     * Sets the value of the oaCd7AdaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaCd7AdaCd(String value) {
        this.oaCd7AdaCd = value;
    }

    /**
     * Gets the value of the oaDatCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaDatCd() {
        return oaDatCd;
    }

    /**
     * Sets the value of the oaDatCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaDatCd(String value) {
        this.oaDatCd = value;
    }

    /**
     * Gets the value of the oaDpCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaDpCd() {
        return oaDpCd;
    }

    /**
     * Sets the value of the oaDpCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaDpCd(String value) {
        this.oaDpCd = value;
    }

    /**
     * Gets the value of the oaEnChngInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaEnChngInd() {
        return oaEnChngInd;
    }

    /**
     * Sets the value of the oaEnChngInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaEnChngInd(String value) {
        this.oaEnChngInd = value;
    }

    /**
     * Gets the value of the oaExchId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaExchId() {
        return oaExchId;
    }

    /**
     * Sets the value of the oaExchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaExchId(String value) {
        this.oaExchId = value;
    }

    /**
     * Gets the value of the oaFrDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaFrDueDate() {
        return oaFrDueDate;
    }

    /**
     * Sets the value of the oaFrDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaFrDueDate(String value) {
        this.oaFrDueDate = value;
    }

    /**
     * Gets the value of the oaFrDueTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaFrDueTime() {
        return oaFrDueTime;
    }

    /**
     * Sets the value of the oaFrDueTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaFrDueTime(String value) {
        this.oaFrDueTime = value;
    }

    /**
     * Gets the value of the oaFrReaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaFrReaCd() {
        return oaFrReaCd;
    }

    /**
     * Sets the value of the oaFrReaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaFrReaCd(String value) {
        this.oaFrReaCd = value;
    }

    /**
     * Gets the value of the oaFrSbNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaFrSbNum() {
        return oaFrSbNum;
    }

    /**
     * Sets the value of the oaFrSbNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaFrSbNum(String value) {
        this.oaFrSbNum = value;
    }

    /**
     * Gets the value of the oaFrVisitInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaFrVisitInd() {
        return oaFrVisitInd;
    }

    /**
     * Sets the value of the oaFrVisitInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaFrVisitInd(String value) {
        this.oaFrVisitInd = value;
    }

    /**
     * Gets the value of the oaGrid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaGrid() {
        return oaGrid;
    }

    /**
     * Sets the value of the oaGrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaGrid(String value) {
        this.oaGrid = value;
    }

    /**
     * Gets the value of the oaIntlInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaIntlInd() {
        return oaIntlInd;
    }

    /**
     * Sets the value of the oaIntlInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaIntlInd(String value) {
        this.oaIntlInd = value;
    }

    /**
     * Gets the value of the oaOrdSts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaOrdSts() {
        return oaOrdSts;
    }

    /**
     * Sets the value of the oaOrdSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaOrdSts(String value) {
        this.oaOrdSts = value;
    }

    /**
     * Gets the value of the oaOrdTypCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaOrdTypCd() {
        return oaOrdTypCd;
    }

    /**
     * Sets the value of the oaOrdTypCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaOrdTypCd(String value) {
        this.oaOrdTypCd = value;
    }

    /**
     * Gets the value of the oaSbNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaSbNum() {
        return oaSbNum;
    }

    /**
     * Sets the value of the oaSbNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaSbNum(String value) {
        this.oaSbNum = value;
    }

    /**
     * Gets the value of the oaSpMarkerB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaSpMarkerB() {
        return oaSpMarkerB;
    }

    /**
     * Sets the value of the oaSpMarkerB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaSpMarkerB(String value) {
        this.oaSpMarkerB = value;
    }

    /**
     * Gets the value of the oaSpMarkerD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaSpMarkerD() {
        return oaSpMarkerD;
    }

    /**
     * Sets the value of the oaSpMarkerD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaSpMarkerD(String value) {
        this.oaSpMarkerD = value;
    }

    /**
     * Gets the value of the oaSubNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaSubNum() {
        return oaSubNum;
    }

    /**
     * Sets the value of the oaSubNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaSubNum(String value) {
        this.oaSubNum = value;
    }

    /**
     * Gets the value of the oaTentativeInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaTentativeInd() {
        return oaTentativeInd;
    }

    /**
     * Sets the value of the oaTentativeInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaTentativeInd(String value) {
        this.oaTentativeInd = value;
    }

    /**
     * Gets the value of the oaToDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaToDueDate() {
        return oaToDueDate;
    }

    /**
     * Sets the value of the oaToDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaToDueDate(String value) {
        this.oaToDueDate = value;
    }

    /**
     * Gets the value of the oaToDueTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaToDueTime() {
        return oaToDueTime;
    }

    /**
     * Sets the value of the oaToDueTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaToDueTime(String value) {
        this.oaToDueTime = value;
    }

    /**
     * Gets the value of the oaToReaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaToReaCd() {
        return oaToReaCd;
    }

    /**
     * Sets the value of the oaToReaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaToReaCd(String value) {
        this.oaToReaCd = value;
    }

    /**
     * Gets the value of the oaToSbNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaToSbNum() {
        return oaToSbNum;
    }

    /**
     * Sets the value of the oaToSbNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaToSbNum(String value) {
        this.oaToSbNum = value;
    }

    /**
     * Gets the value of the oaToVisitInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaToVisitInd() {
        return oaToVisitInd;
    }

    /**
     * Sets the value of the oaToVisitInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaToVisitInd(String value) {
        this.oaToVisitInd = value;
    }

    /**
     * Gets the value of the oaTypOfSrv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaTypOfSrv() {
        return oaTypOfSrv;
    }

    /**
     * Sets the value of the oaTypOfSrv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaTypOfSrv(String value) {
        this.oaTypOfSrv = value;
    }

    /**
     * Gets the value of the oaUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaUserId() {
        return oaUserId;
    }

    /**
     * Sets the value of the oaUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaUserId(String value) {
        this.oaUserId = value;
    }

    /**
     * Gets the value of the oaVisitReaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaVisitReaCd() {
        return oaVisitReaCd;
    }

    /**
     * Sets the value of the oaVisitReaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaVisitReaCd(String value) {
        this.oaVisitReaCd = value;
    }

    /**
     * Gets the value of the oaWeatherCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaWeatherCd() {
        return oaWeatherCd;
    }

    /**
     * Sets the value of the oaWeatherCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaWeatherCd(String value) {
        this.oaWeatherCd = value;
    }

    /**
     * Gets the value of the oaWkgContNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaWkgContNum() {
        return oaWkgContNum;
    }

    /**
     * Sets the value of the oaWkgContNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaWkgContNum(String value) {
        this.oaWkgContNum = value;
    }

    /**
     * Gets the value of the oaWrkgrp1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaWrkgrp1() {
        return oaWrkgrp1;
    }

    /**
     * Sets the value of the oaWrkgrp1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaWrkgrp1(String value) {
        this.oaWrkgrp1 = value;
    }

    /**
     * Gets the value of the oaWrkgrp2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOaWrkgrp2() {
        return oaWrkgrp2;
    }

    /**
     * Sets the value of the oaWrkgrp2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOaWrkgrp2(String value) {
        this.oaWrkgrp2 = value;
    }

}

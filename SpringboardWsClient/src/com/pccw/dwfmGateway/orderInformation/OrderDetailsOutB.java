
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderDetailsOutB complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderDetailsOutB">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OldNewInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PilotAuxInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DuplexNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DuplexType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NnNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NnExchngId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LineClsCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TariffCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConcsCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VendorCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CoeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExDirInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HuntSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PayphonId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GatewayNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FreeCallAllwnc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MinChrgAmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RevnShrRatio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VoipInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderDetailsOutB", propOrder = {
    "oldNewInd",
    "typeOfSrv",
    "datCd",
    "pilotAuxInd",
    "srvNum",
    "duplexNum",
    "duplexType",
    "nnNum",
    "nnExchngId",
    "lineClsCd",
    "tariffCd",
    "concsCd",
    "vendorCd",
    "coeCd",
    "exDirInd",
    "exUserName",
    "huntSeq",
    "payphonId",
    "gatewayNum",
    "freeCallAllwnc",
    "minChrgAmt",
    "revnShrRatio",
    "voipInd"
})
public class OrderDetailsOutB {

    @XmlElement(name = "OldNewInd")
    protected String oldNewInd;
    @XmlElement(name = "TypeOfSrv")
    protected String typeOfSrv;
    @XmlElement(name = "DatCd")
    protected String datCd;
    @XmlElement(name = "PilotAuxInd")
    protected String pilotAuxInd;
    @XmlElement(name = "SrvNum")
    protected String srvNum;
    @XmlElement(name = "DuplexNum")
    protected String duplexNum;
    @XmlElement(name = "DuplexType")
    protected String duplexType;
    @XmlElement(name = "NnNum")
    protected String nnNum;
    @XmlElement(name = "NnExchngId")
    protected String nnExchngId;
    @XmlElement(name = "LineClsCd")
    protected String lineClsCd;
    @XmlElement(name = "TariffCd")
    protected String tariffCd;
    @XmlElement(name = "ConcsCd")
    protected String concsCd;
    @XmlElement(name = "VendorCd")
    protected String vendorCd;
    @XmlElement(name = "CoeCd")
    protected String coeCd;
    @XmlElement(name = "ExDirInd")
    protected String exDirInd;
    @XmlElement(name = "ExUserName")
    protected String exUserName;
    @XmlElement(name = "HuntSeq")
    protected String huntSeq;
    @XmlElement(name = "PayphonId")
    protected String payphonId;
    @XmlElement(name = "GatewayNum")
    protected String gatewayNum;
    @XmlElement(name = "FreeCallAllwnc")
    protected String freeCallAllwnc;
    @XmlElement(name = "MinChrgAmt")
    protected String minChrgAmt;
    @XmlElement(name = "RevnShrRatio")
    protected String revnShrRatio;
    @XmlElement(name = "VoipInd")
    protected String voipInd;

    /**
     * Gets the value of the oldNewInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldNewInd() {
        return oldNewInd;
    }

    /**
     * Sets the value of the oldNewInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldNewInd(String value) {
        this.oldNewInd = value;
    }

    /**
     * Gets the value of the typeOfSrv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfSrv() {
        return typeOfSrv;
    }

    /**
     * Sets the value of the typeOfSrv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfSrv(String value) {
        this.typeOfSrv = value;
    }

    /**
     * Gets the value of the datCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatCd() {
        return datCd;
    }

    /**
     * Sets the value of the datCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatCd(String value) {
        this.datCd = value;
    }

    /**
     * Gets the value of the pilotAuxInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPilotAuxInd() {
        return pilotAuxInd;
    }

    /**
     * Sets the value of the pilotAuxInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPilotAuxInd(String value) {
        this.pilotAuxInd = value;
    }

    /**
     * Gets the value of the srvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvNum() {
        return srvNum;
    }

    /**
     * Sets the value of the srvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvNum(String value) {
        this.srvNum = value;
    }

    /**
     * Gets the value of the duplexNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplexNum() {
        return duplexNum;
    }

    /**
     * Sets the value of the duplexNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplexNum(String value) {
        this.duplexNum = value;
    }

    /**
     * Gets the value of the duplexType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplexType() {
        return duplexType;
    }

    /**
     * Sets the value of the duplexType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplexType(String value) {
        this.duplexType = value;
    }

    /**
     * Gets the value of the nnNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNnNum() {
        return nnNum;
    }

    /**
     * Sets the value of the nnNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNnNum(String value) {
        this.nnNum = value;
    }

    /**
     * Gets the value of the nnExchngId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNnExchngId() {
        return nnExchngId;
    }

    /**
     * Sets the value of the nnExchngId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNnExchngId(String value) {
        this.nnExchngId = value;
    }

    /**
     * Gets the value of the lineClsCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineClsCd() {
        return lineClsCd;
    }

    /**
     * Sets the value of the lineClsCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineClsCd(String value) {
        this.lineClsCd = value;
    }

    /**
     * Gets the value of the tariffCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTariffCd() {
        return tariffCd;
    }

    /**
     * Sets the value of the tariffCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTariffCd(String value) {
        this.tariffCd = value;
    }

    /**
     * Gets the value of the concsCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConcsCd() {
        return concsCd;
    }

    /**
     * Sets the value of the concsCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConcsCd(String value) {
        this.concsCd = value;
    }

    /**
     * Gets the value of the vendorCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorCd() {
        return vendorCd;
    }

    /**
     * Sets the value of the vendorCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorCd(String value) {
        this.vendorCd = value;
    }

    /**
     * Gets the value of the coeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoeCd() {
        return coeCd;
    }

    /**
     * Sets the value of the coeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoeCd(String value) {
        this.coeCd = value;
    }

    /**
     * Gets the value of the exDirInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExDirInd() {
        return exDirInd;
    }

    /**
     * Sets the value of the exDirInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExDirInd(String value) {
        this.exDirInd = value;
    }

    /**
     * Gets the value of the exUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExUserName() {
        return exUserName;
    }

    /**
     * Sets the value of the exUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExUserName(String value) {
        this.exUserName = value;
    }

    /**
     * Gets the value of the huntSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHuntSeq() {
        return huntSeq;
    }

    /**
     * Sets the value of the huntSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHuntSeq(String value) {
        this.huntSeq = value;
    }

    /**
     * Gets the value of the payphonId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayphonId() {
        return payphonId;
    }

    /**
     * Sets the value of the payphonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayphonId(String value) {
        this.payphonId = value;
    }

    /**
     * Gets the value of the gatewayNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatewayNum() {
        return gatewayNum;
    }

    /**
     * Sets the value of the gatewayNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatewayNum(String value) {
        this.gatewayNum = value;
    }

    /**
     * Gets the value of the freeCallAllwnc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeCallAllwnc() {
        return freeCallAllwnc;
    }

    /**
     * Sets the value of the freeCallAllwnc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeCallAllwnc(String value) {
        this.freeCallAllwnc = value;
    }

    /**
     * Gets the value of the minChrgAmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinChrgAmt() {
        return minChrgAmt;
    }

    /**
     * Sets the value of the minChrgAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinChrgAmt(String value) {
        this.minChrgAmt = value;
    }

    /**
     * Gets the value of the revnShrRatio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevnShrRatio() {
        return revnShrRatio;
    }

    /**
     * Sets the value of the revnShrRatio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevnShrRatio(String value) {
        this.revnShrRatio = value;
    }

    /**
     * Gets the value of the voipInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoipInd() {
        return voipInd;
    }

    /**
     * Sets the value of the voipInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoipInd(String value) {
        this.voipInd = value;
    }

}

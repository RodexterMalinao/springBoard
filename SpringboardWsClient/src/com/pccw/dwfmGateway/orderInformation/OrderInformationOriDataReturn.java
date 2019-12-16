
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationOriDataReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationOriDataReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdslBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdslDpsdfPairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AftCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttenuatnAt1600HzQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttenuatnAt800HzQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BlkWirePairNumPr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BlkWirePairNumPr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CabinetSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CableCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CablePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CdslBwSharedInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpsdfBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpsdfCd19" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpsdfCd23" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpsdfPairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DsidePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnExchange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EsidePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistLineCls" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrL2Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrProdId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HorTypeCdPr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HorTypeCdPr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JmpInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LcTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfbarSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NidenNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NodeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PinNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwDpsdfCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwPairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwVt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemoteMdfSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RestncQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rpid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RpidDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RtDpCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RtDpSubCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RtDslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SbiInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermntrCdPr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermntrCdPr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VertTypeCdPr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VertTypeCdPr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VoiceBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VoiceEn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "OrderInformationOriDataReturn", propOrder = {
    "adslBuildId",
    "adslDpsdfPairNum",
    "adslInd",
    "aftCd",
    "attenuatnAt1600HzQty",
    "attenuatnAt800HzQty",
    "blkWirePairNumPr1",
    "blkWirePairNumPr2",
    "buildId",
    "cabinetSeqNum",
    "cableCd",
    "cablePairNum",
    "cdslBwSharedInd",
    "dpsdfBuildId",
    "dpsdfCd19",
    "dpsdfCd23",
    "dpsdfPairNum",
    "dsidePairNum",
    "enExchange",
    "esidePairNum",
    "existLineCls",
    "frL2Id",
    "frProdId",
    "horTypeCdPr1",
    "horTypeCdPr2",
    "jmpInd",
    "lcTypeCd",
    "mdfbarSeqNum",
    "mdfBuildId",
    "mdfNum",
    "mdfSeqNum",
    "nidenNum",
    "nodeId",
    "orderId",
    "pinNum",
    "relBwBuildId",
    "relBwDpsdfCd",
    "relBwPairNum",
    "relBwVt",
    "remoteMdfSeqNum",
    "restncQty",
    "rpid",
    "rpidDesc",
    "rtDpCat",
    "rtDpSubCat",
    "rtDslInd",
    "sbiInd",
    "termntrCdPr1",
    "termntrCdPr2",
    "tid",
    "vertTypeCdPr1",
    "vertTypeCdPr2",
    "voiceBuildId",
    "voiceEn",
    "voipInd"
})
public class OrderInformationOriDataReturn {

    @XmlElement(name = "AdslBuildId")
    protected String adslBuildId;
    @XmlElement(name = "AdslDpsdfPairNum")
    protected String adslDpsdfPairNum;
    @XmlElement(name = "AdslInd")
    protected String adslInd;
    @XmlElement(name = "AftCd")
    protected String aftCd;
    @XmlElement(name = "AttenuatnAt1600HzQty")
    protected String attenuatnAt1600HzQty;
    @XmlElement(name = "AttenuatnAt800HzQty")
    protected String attenuatnAt800HzQty;
    @XmlElement(name = "BlkWirePairNumPr1")
    protected String blkWirePairNumPr1;
    @XmlElement(name = "BlkWirePairNumPr2")
    protected String blkWirePairNumPr2;
    @XmlElement(name = "BuildId")
    protected String buildId;
    @XmlElement(name = "CabinetSeqNum")
    protected String cabinetSeqNum;
    @XmlElement(name = "CableCd")
    protected String cableCd;
    @XmlElement(name = "CablePairNum")
    protected String cablePairNum;
    @XmlElement(name = "CdslBwSharedInd")
    protected String cdslBwSharedInd;
    @XmlElement(name = "DpsdfBuildId")
    protected String dpsdfBuildId;
    @XmlElement(name = "DpsdfCd19")
    protected String dpsdfCd19;
    @XmlElement(name = "DpsdfCd23")
    protected String dpsdfCd23;
    @XmlElement(name = "DpsdfPairNum")
    protected String dpsdfPairNum;
    @XmlElement(name = "DsidePairNum")
    protected String dsidePairNum;
    @XmlElement(name = "EnExchange")
    protected String enExchange;
    @XmlElement(name = "EsidePairNum")
    protected String esidePairNum;
    @XmlElement(name = "ExistLineCls")
    protected String existLineCls;
    @XmlElement(name = "FrL2Id")
    protected String frL2Id;
    @XmlElement(name = "FrProdId")
    protected String frProdId;
    @XmlElement(name = "HorTypeCdPr1")
    protected String horTypeCdPr1;
    @XmlElement(name = "HorTypeCdPr2")
    protected String horTypeCdPr2;
    @XmlElement(name = "JmpInd")
    protected String jmpInd;
    @XmlElement(name = "LcTypeCd")
    protected String lcTypeCd;
    @XmlElement(name = "MdfbarSeqNum")
    protected String mdfbarSeqNum;
    @XmlElement(name = "MdfBuildId")
    protected String mdfBuildId;
    @XmlElement(name = "MdfNum")
    protected String mdfNum;
    @XmlElement(name = "MdfSeqNum")
    protected String mdfSeqNum;
    @XmlElement(name = "NidenNum")
    protected String nidenNum;
    @XmlElement(name = "NodeId")
    protected String nodeId;
    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "PinNum")
    protected String pinNum;
    @XmlElement(name = "RelBwBuildId")
    protected String relBwBuildId;
    @XmlElement(name = "RelBwDpsdfCd")
    protected String relBwDpsdfCd;
    @XmlElement(name = "RelBwPairNum")
    protected String relBwPairNum;
    @XmlElement(name = "RelBwVt")
    protected String relBwVt;
    @XmlElement(name = "RemoteMdfSeqNum")
    protected String remoteMdfSeqNum;
    @XmlElement(name = "RestncQty")
    protected String restncQty;
    @XmlElement(name = "Rpid")
    protected String rpid;
    @XmlElement(name = "RpidDesc")
    protected String rpidDesc;
    @XmlElement(name = "RtDpCat")
    protected String rtDpCat;
    @XmlElement(name = "RtDpSubCat")
    protected String rtDpSubCat;
    @XmlElement(name = "RtDslInd")
    protected String rtDslInd;
    @XmlElement(name = "SbiInd")
    protected String sbiInd;
    @XmlElement(name = "TermntrCdPr1")
    protected String termntrCdPr1;
    @XmlElement(name = "TermntrCdPr2")
    protected String termntrCdPr2;
    @XmlElement(name = "Tid")
    protected String tid;
    @XmlElement(name = "VertTypeCdPr1")
    protected String vertTypeCdPr1;
    @XmlElement(name = "VertTypeCdPr2")
    protected String vertTypeCdPr2;
    @XmlElement(name = "VoiceBuildId")
    protected String voiceBuildId;
    @XmlElement(name = "VoiceEn")
    protected String voiceEn;
    @XmlElement(name = "VoipInd")
    protected String voipInd;

    /**
     * Gets the value of the adslBuildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdslBuildId() {
        return adslBuildId;
    }

    /**
     * Sets the value of the adslBuildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdslBuildId(String value) {
        this.adslBuildId = value;
    }

    /**
     * Gets the value of the adslDpsdfPairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdslDpsdfPairNum() {
        return adslDpsdfPairNum;
    }

    /**
     * Sets the value of the adslDpsdfPairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdslDpsdfPairNum(String value) {
        this.adslDpsdfPairNum = value;
    }

    /**
     * Gets the value of the adslInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdslInd() {
        return adslInd;
    }

    /**
     * Sets the value of the adslInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdslInd(String value) {
        this.adslInd = value;
    }

    /**
     * Gets the value of the aftCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAftCd() {
        return aftCd;
    }

    /**
     * Sets the value of the aftCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAftCd(String value) {
        this.aftCd = value;
    }

    /**
     * Gets the value of the attenuatnAt1600HzQty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttenuatnAt1600HzQty() {
        return attenuatnAt1600HzQty;
    }

    /**
     * Sets the value of the attenuatnAt1600HzQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttenuatnAt1600HzQty(String value) {
        this.attenuatnAt1600HzQty = value;
    }

    /**
     * Gets the value of the attenuatnAt800HzQty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttenuatnAt800HzQty() {
        return attenuatnAt800HzQty;
    }

    /**
     * Sets the value of the attenuatnAt800HzQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttenuatnAt800HzQty(String value) {
        this.attenuatnAt800HzQty = value;
    }

    /**
     * Gets the value of the blkWirePairNumPr1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlkWirePairNumPr1() {
        return blkWirePairNumPr1;
    }

    /**
     * Sets the value of the blkWirePairNumPr1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlkWirePairNumPr1(String value) {
        this.blkWirePairNumPr1 = value;
    }

    /**
     * Gets the value of the blkWirePairNumPr2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlkWirePairNumPr2() {
        return blkWirePairNumPr2;
    }

    /**
     * Sets the value of the blkWirePairNumPr2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlkWirePairNumPr2(String value) {
        this.blkWirePairNumPr2 = value;
    }

    /**
     * Gets the value of the buildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildId() {
        return buildId;
    }

    /**
     * Sets the value of the buildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildId(String value) {
        this.buildId = value;
    }

    /**
     * Gets the value of the cabinetSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCabinetSeqNum() {
        return cabinetSeqNum;
    }

    /**
     * Sets the value of the cabinetSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCabinetSeqNum(String value) {
        this.cabinetSeqNum = value;
    }

    /**
     * Gets the value of the cableCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCableCd() {
        return cableCd;
    }

    /**
     * Sets the value of the cableCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCableCd(String value) {
        this.cableCd = value;
    }

    /**
     * Gets the value of the cablePairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCablePairNum() {
        return cablePairNum;
    }

    /**
     * Sets the value of the cablePairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCablePairNum(String value) {
        this.cablePairNum = value;
    }

    /**
     * Gets the value of the cdslBwSharedInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCdslBwSharedInd() {
        return cdslBwSharedInd;
    }

    /**
     * Sets the value of the cdslBwSharedInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCdslBwSharedInd(String value) {
        this.cdslBwSharedInd = value;
    }

    /**
     * Gets the value of the dpsdfBuildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpsdfBuildId() {
        return dpsdfBuildId;
    }

    /**
     * Sets the value of the dpsdfBuildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpsdfBuildId(String value) {
        this.dpsdfBuildId = value;
    }

    /**
     * Gets the value of the dpsdfCd19 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpsdfCd19() {
        return dpsdfCd19;
    }

    /**
     * Sets the value of the dpsdfCd19 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpsdfCd19(String value) {
        this.dpsdfCd19 = value;
    }

    /**
     * Gets the value of the dpsdfCd23 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpsdfCd23() {
        return dpsdfCd23;
    }

    /**
     * Sets the value of the dpsdfCd23 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpsdfCd23(String value) {
        this.dpsdfCd23 = value;
    }

    /**
     * Gets the value of the dpsdfPairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpsdfPairNum() {
        return dpsdfPairNum;
    }

    /**
     * Sets the value of the dpsdfPairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpsdfPairNum(String value) {
        this.dpsdfPairNum = value;
    }

    /**
     * Gets the value of the dsidePairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsidePairNum() {
        return dsidePairNum;
    }

    /**
     * Sets the value of the dsidePairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsidePairNum(String value) {
        this.dsidePairNum = value;
    }

    /**
     * Gets the value of the enExchange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnExchange() {
        return enExchange;
    }

    /**
     * Sets the value of the enExchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnExchange(String value) {
        this.enExchange = value;
    }

    /**
     * Gets the value of the esidePairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsidePairNum() {
        return esidePairNum;
    }

    /**
     * Sets the value of the esidePairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsidePairNum(String value) {
        this.esidePairNum = value;
    }

    /**
     * Gets the value of the existLineCls property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistLineCls() {
        return existLineCls;
    }

    /**
     * Sets the value of the existLineCls property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistLineCls(String value) {
        this.existLineCls = value;
    }

    /**
     * Gets the value of the frL2Id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrL2Id() {
        return frL2Id;
    }

    /**
     * Sets the value of the frL2Id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrL2Id(String value) {
        this.frL2Id = value;
    }

    /**
     * Gets the value of the frProdId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrProdId() {
        return frProdId;
    }

    /**
     * Sets the value of the frProdId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrProdId(String value) {
        this.frProdId = value;
    }

    /**
     * Gets the value of the horTypeCdPr1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHorTypeCdPr1() {
        return horTypeCdPr1;
    }

    /**
     * Sets the value of the horTypeCdPr1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHorTypeCdPr1(String value) {
        this.horTypeCdPr1 = value;
    }

    /**
     * Gets the value of the horTypeCdPr2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHorTypeCdPr2() {
        return horTypeCdPr2;
    }

    /**
     * Sets the value of the horTypeCdPr2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHorTypeCdPr2(String value) {
        this.horTypeCdPr2 = value;
    }

    /**
     * Gets the value of the jmpInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJmpInd() {
        return jmpInd;
    }

    /**
     * Sets the value of the jmpInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJmpInd(String value) {
        this.jmpInd = value;
    }

    /**
     * Gets the value of the lcTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLcTypeCd() {
        return lcTypeCd;
    }

    /**
     * Sets the value of the lcTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLcTypeCd(String value) {
        this.lcTypeCd = value;
    }

    /**
     * Gets the value of the mdfbarSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMdfbarSeqNum() {
        return mdfbarSeqNum;
    }

    /**
     * Sets the value of the mdfbarSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMdfbarSeqNum(String value) {
        this.mdfbarSeqNum = value;
    }

    /**
     * Gets the value of the mdfBuildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMdfBuildId() {
        return mdfBuildId;
    }

    /**
     * Sets the value of the mdfBuildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMdfBuildId(String value) {
        this.mdfBuildId = value;
    }

    /**
     * Gets the value of the mdfNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMdfNum() {
        return mdfNum;
    }

    /**
     * Sets the value of the mdfNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMdfNum(String value) {
        this.mdfNum = value;
    }

    /**
     * Gets the value of the mdfSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMdfSeqNum() {
        return mdfSeqNum;
    }

    /**
     * Sets the value of the mdfSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMdfSeqNum(String value) {
        this.mdfSeqNum = value;
    }

    /**
     * Gets the value of the nidenNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNidenNum() {
        return nidenNum;
    }

    /**
     * Sets the value of the nidenNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNidenNum(String value) {
        this.nidenNum = value;
    }

    /**
     * Gets the value of the nodeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Sets the value of the nodeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeId(String value) {
        this.nodeId = value;
    }

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the pinNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPinNum() {
        return pinNum;
    }

    /**
     * Sets the value of the pinNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPinNum(String value) {
        this.pinNum = value;
    }

    /**
     * Gets the value of the relBwBuildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelBwBuildId() {
        return relBwBuildId;
    }

    /**
     * Sets the value of the relBwBuildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelBwBuildId(String value) {
        this.relBwBuildId = value;
    }

    /**
     * Gets the value of the relBwDpsdfCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelBwDpsdfCd() {
        return relBwDpsdfCd;
    }

    /**
     * Sets the value of the relBwDpsdfCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelBwDpsdfCd(String value) {
        this.relBwDpsdfCd = value;
    }

    /**
     * Gets the value of the relBwPairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelBwPairNum() {
        return relBwPairNum;
    }

    /**
     * Sets the value of the relBwPairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelBwPairNum(String value) {
        this.relBwPairNum = value;
    }

    /**
     * Gets the value of the relBwVt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelBwVt() {
        return relBwVt;
    }

    /**
     * Sets the value of the relBwVt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelBwVt(String value) {
        this.relBwVt = value;
    }

    /**
     * Gets the value of the remoteMdfSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteMdfSeqNum() {
        return remoteMdfSeqNum;
    }

    /**
     * Sets the value of the remoteMdfSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteMdfSeqNum(String value) {
        this.remoteMdfSeqNum = value;
    }

    /**
     * Gets the value of the restncQty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestncQty() {
        return restncQty;
    }

    /**
     * Sets the value of the restncQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestncQty(String value) {
        this.restncQty = value;
    }

    /**
     * Gets the value of the rpid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRpid() {
        return rpid;
    }

    /**
     * Sets the value of the rpid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRpid(String value) {
        this.rpid = value;
    }

    /**
     * Gets the value of the rpidDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRpidDesc() {
        return rpidDesc;
    }

    /**
     * Sets the value of the rpidDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRpidDesc(String value) {
        this.rpidDesc = value;
    }

    /**
     * Gets the value of the rtDpCat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtDpCat() {
        return rtDpCat;
    }

    /**
     * Sets the value of the rtDpCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtDpCat(String value) {
        this.rtDpCat = value;
    }

    /**
     * Gets the value of the rtDpSubCat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtDpSubCat() {
        return rtDpSubCat;
    }

    /**
     * Sets the value of the rtDpSubCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtDpSubCat(String value) {
        this.rtDpSubCat = value;
    }

    /**
     * Gets the value of the rtDslInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtDslInd() {
        return rtDslInd;
    }

    /**
     * Sets the value of the rtDslInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtDslInd(String value) {
        this.rtDslInd = value;
    }

    /**
     * Gets the value of the sbiInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSbiInd() {
        return sbiInd;
    }

    /**
     * Sets the value of the sbiInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSbiInd(String value) {
        this.sbiInd = value;
    }

    /**
     * Gets the value of the termntrCdPr1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermntrCdPr1() {
        return termntrCdPr1;
    }

    /**
     * Sets the value of the termntrCdPr1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermntrCdPr1(String value) {
        this.termntrCdPr1 = value;
    }

    /**
     * Gets the value of the termntrCdPr2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermntrCdPr2() {
        return termntrCdPr2;
    }

    /**
     * Sets the value of the termntrCdPr2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermntrCdPr2(String value) {
        this.termntrCdPr2 = value;
    }

    /**
     * Gets the value of the tid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTid() {
        return tid;
    }

    /**
     * Sets the value of the tid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTid(String value) {
        this.tid = value;
    }

    /**
     * Gets the value of the vertTypeCdPr1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVertTypeCdPr1() {
        return vertTypeCdPr1;
    }

    /**
     * Sets the value of the vertTypeCdPr1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVertTypeCdPr1(String value) {
        this.vertTypeCdPr1 = value;
    }

    /**
     * Gets the value of the vertTypeCdPr2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVertTypeCdPr2() {
        return vertTypeCdPr2;
    }

    /**
     * Sets the value of the vertTypeCdPr2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVertTypeCdPr2(String value) {
        this.vertTypeCdPr2 = value;
    }

    /**
     * Gets the value of the voiceBuildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoiceBuildId() {
        return voiceBuildId;
    }

    /**
     * Sets the value of the voiceBuildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoiceBuildId(String value) {
        this.voiceBuildId = value;
    }

    /**
     * Gets the value of the voiceEn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoiceEn() {
        return voiceEn;
    }

    /**
     * Sets the value of the voiceEn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoiceEn(String value) {
        this.voiceEn = value;
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

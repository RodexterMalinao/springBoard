
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationTgtDataReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationTgtDataReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdslBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdslDpPairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdslDpsdfCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AftCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttenuatnAt1600HzQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttenuatnAt800HzQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BlkWirePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CabinetBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CabinetSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CableCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CablePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CdslBwSharedInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DsidePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EsidePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExchngId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HorTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JmpInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LcTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfbarSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewNidNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NidenNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NodeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdactvSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *         &lt;element name="RtBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RtDpCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RtDpPairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RtDpsdfCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RtDpSubCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RtDslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SbiInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermntrTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToL2Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToProdId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VertTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "OrderInformationTgtDataReturn", propOrder = {
    "adslBuildId",
    "adslDpPairNum",
    "adslDpsdfCd",
    "adslInd",
    "aftCd",
    "attenuatnAt1600HzQty",
    "attenuatnAt800HzQty",
    "blkWirePairNum",
    "cabinetBuildId",
    "cabinetSeqNum",
    "cableCd",
    "cablePairNum",
    "cdslBwSharedInd",
    "dsidePairNum",
    "esidePairNum",
    "exchngId",
    "horTypeCd",
    "jmpInd",
    "lcTypeCd",
    "mdfbarSeqNum",
    "mdfBuildId",
    "mdfSeqNum",
    "newNidNum",
    "nidenNum",
    "nodeId",
    "ordactvSeqNum",
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
    "rtBuildId",
    "rtDpCat",
    "rtDpPairNum",
    "rtDpsdfCd",
    "rtDpSubCat",
    "rtDslInd",
    "sbiInd",
    "termntrTypeCd",
    "tid",
    "toL2Id",
    "toProdId",
    "vertTypeCd",
    "voiceEn",
    "voipInd"
})
public class OrderInformationTgtDataReturn {

    @XmlElement(name = "AdslBuildId")
    protected String adslBuildId;
    @XmlElement(name = "AdslDpPairNum")
    protected String adslDpPairNum;
    @XmlElement(name = "AdslDpsdfCd")
    protected String adslDpsdfCd;
    @XmlElement(name = "AdslInd")
    protected String adslInd;
    @XmlElement(name = "AftCd")
    protected String aftCd;
    @XmlElement(name = "AttenuatnAt1600HzQty")
    protected String attenuatnAt1600HzQty;
    @XmlElement(name = "AttenuatnAt800HzQty")
    protected String attenuatnAt800HzQty;
    @XmlElement(name = "BlkWirePairNum")
    protected String blkWirePairNum;
    @XmlElement(name = "CabinetBuildId")
    protected String cabinetBuildId;
    @XmlElement(name = "CabinetSeqNum")
    protected String cabinetSeqNum;
    @XmlElement(name = "CableCd")
    protected String cableCd;
    @XmlElement(name = "CablePairNum")
    protected String cablePairNum;
    @XmlElement(name = "CdslBwSharedInd")
    protected String cdslBwSharedInd;
    @XmlElement(name = "DsidePairNum")
    protected String dsidePairNum;
    @XmlElement(name = "EsidePairNum")
    protected String esidePairNum;
    @XmlElement(name = "ExchngId")
    protected String exchngId;
    @XmlElement(name = "HorTypeCd")
    protected String horTypeCd;
    @XmlElement(name = "JmpInd")
    protected String jmpInd;
    @XmlElement(name = "LcTypeCd")
    protected String lcTypeCd;
    @XmlElement(name = "MdfbarSeqNum")
    protected String mdfbarSeqNum;
    @XmlElement(name = "MdfBuildId")
    protected String mdfBuildId;
    @XmlElement(name = "MdfSeqNum")
    protected String mdfSeqNum;
    @XmlElement(name = "NewNidNum")
    protected String newNidNum;
    @XmlElement(name = "NidenNum")
    protected String nidenNum;
    @XmlElement(name = "NodeId")
    protected String nodeId;
    @XmlElement(name = "OrdactvSeqNum")
    protected String ordactvSeqNum;
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
    @XmlElement(name = "RtBuildId")
    protected String rtBuildId;
    @XmlElement(name = "RtDpCat")
    protected String rtDpCat;
    @XmlElement(name = "RtDpPairNum")
    protected String rtDpPairNum;
    @XmlElement(name = "RtDpsdfCd")
    protected String rtDpsdfCd;
    @XmlElement(name = "RtDpSubCat")
    protected String rtDpSubCat;
    @XmlElement(name = "RtDslInd")
    protected String rtDslInd;
    @XmlElement(name = "SbiInd")
    protected String sbiInd;
    @XmlElement(name = "TermntrTypeCd")
    protected String termntrTypeCd;
    @XmlElement(name = "Tid")
    protected String tid;
    @XmlElement(name = "ToL2Id")
    protected String toL2Id;
    @XmlElement(name = "ToProdId")
    protected String toProdId;
    @XmlElement(name = "VertTypeCd")
    protected String vertTypeCd;
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
     * Gets the value of the adslDpPairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdslDpPairNum() {
        return adslDpPairNum;
    }

    /**
     * Sets the value of the adslDpPairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdslDpPairNum(String value) {
        this.adslDpPairNum = value;
    }

    /**
     * Gets the value of the adslDpsdfCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdslDpsdfCd() {
        return adslDpsdfCd;
    }

    /**
     * Sets the value of the adslDpsdfCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdslDpsdfCd(String value) {
        this.adslDpsdfCd = value;
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
     * Gets the value of the blkWirePairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlkWirePairNum() {
        return blkWirePairNum;
    }

    /**
     * Sets the value of the blkWirePairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlkWirePairNum(String value) {
        this.blkWirePairNum = value;
    }

    /**
     * Gets the value of the cabinetBuildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCabinetBuildId() {
        return cabinetBuildId;
    }

    /**
     * Sets the value of the cabinetBuildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCabinetBuildId(String value) {
        this.cabinetBuildId = value;
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
     * Gets the value of the exchngId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchngId() {
        return exchngId;
    }

    /**
     * Sets the value of the exchngId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchngId(String value) {
        this.exchngId = value;
    }

    /**
     * Gets the value of the horTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHorTypeCd() {
        return horTypeCd;
    }

    /**
     * Sets the value of the horTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHorTypeCd(String value) {
        this.horTypeCd = value;
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
     * Gets the value of the newNidNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewNidNum() {
        return newNidNum;
    }

    /**
     * Sets the value of the newNidNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewNidNum(String value) {
        this.newNidNum = value;
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
     * Gets the value of the ordactvSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdactvSeqNum() {
        return ordactvSeqNum;
    }

    /**
     * Sets the value of the ordactvSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdactvSeqNum(String value) {
        this.ordactvSeqNum = value;
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
     * Gets the value of the rtBuildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtBuildId() {
        return rtBuildId;
    }

    /**
     * Sets the value of the rtBuildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtBuildId(String value) {
        this.rtBuildId = value;
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
     * Gets the value of the rtDpPairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtDpPairNum() {
        return rtDpPairNum;
    }

    /**
     * Sets the value of the rtDpPairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtDpPairNum(String value) {
        this.rtDpPairNum = value;
    }

    /**
     * Gets the value of the rtDpsdfCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtDpsdfCd() {
        return rtDpsdfCd;
    }

    /**
     * Sets the value of the rtDpsdfCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtDpsdfCd(String value) {
        this.rtDpsdfCd = value;
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
     * Gets the value of the termntrTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermntrTypeCd() {
        return termntrTypeCd;
    }

    /**
     * Sets the value of the termntrTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermntrTypeCd(String value) {
        this.termntrTypeCd = value;
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
     * Gets the value of the toL2Id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToL2Id() {
        return toL2Id;
    }

    /**
     * Sets the value of the toL2Id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToL2Id(String value) {
        this.toL2Id = value;
    }

    /**
     * Gets the value of the toProdId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToProdId() {
        return toProdId;
    }

    /**
     * Sets the value of the toProdId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToProdId(String value) {
        this.toProdId = value;
    }

    /**
     * Gets the value of the vertTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVertTypeCd() {
        return vertTypeCd;
    }

    /**
     * Sets the value of the vertTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVertTypeCd(String value) {
        this.vertTypeCd = value;
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

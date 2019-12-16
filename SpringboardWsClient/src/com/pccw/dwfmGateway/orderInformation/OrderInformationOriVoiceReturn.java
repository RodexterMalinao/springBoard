
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationOriVoiceReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationOriVoiceReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AftCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AgNodeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AgTid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AnExchng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AnInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AnPortNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttenuatnAt1600HzQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttenuatnAt800HzQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AudioCarrInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BlkWirePairNumPr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BlkWirePairNumPr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CabinetSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CableCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CablePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChannelNum1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChannelNum2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DivrCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DnPnExchngeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpsdfCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpsdfPairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpSubCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DsidePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnExchange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EsidePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistLineCls" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromAdslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromBsn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromL2Prov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromLcCrType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromNiden" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromProdId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromRtId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromRtPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromStbType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HorTypeCdPr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HorTypeCdPr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HostEn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HostExchng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JmpInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="L3Address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LineEqmtNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfbarSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OltNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PcmId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PinNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PnNnExchange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PnNnInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RefPnNn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwDpsdfCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwPairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwVt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelNid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelRtDpCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelRtDpSubCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelRtDslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemoteMdfSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RestncQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SbiInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermntrCdPr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermntrCdPr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VdslBwSharedInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VertTypeCdPr1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VertTypeCdPr2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VfId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "OrderInformationOriVoiceReturn", propOrder = {
    "aftCd",
    "agNodeId",
    "agTid",
    "anExchng",
    "anInd",
    "anPortNum",
    "attenuatnAt1600HzQty",
    "attenuatnAt800HzQty",
    "audioCarrInd",
    "blkWirePairNumPr1",
    "blkWirePairNumPr2",
    "buildId",
    "cabinetSeqNum",
    "cableCd",
    "cablePairNum",
    "channelNum1",
    "channelNum2",
    "divrCd",
    "dnPnExchngeId",
    "dpCat",
    "dpsdfCd",
    "dpsdfPairNum",
    "dpSubCat",
    "dsidePairNum",
    "dslInd",
    "enExchange",
    "esidePairNum",
    "existLineCls",
    "existSrvNum",
    "fromAdslInd",
    "fromBsn",
    "fromL2Prov",
    "fromLcCrType",
    "fromNiden",
    "fromProdId",
    "fromRtId",
    "fromRtPort",
    "fromStbType",
    "horTypeCdPr1",
    "horTypeCdPr2",
    "hostEn",
    "hostExchng",
    "jmpInd",
    "l3Address",
    "lineEqmtNum",
    "mdfbarSeqNum",
    "mdfBuildId",
    "mdfSeqNum",
    "oltNum",
    "orderId",
    "pcmId",
    "pinNum",
    "pnNnExchange",
    "pnNnInd",
    "refPnNn",
    "relBwBuildId",
    "relBwDpsdfCd",
    "relBwPairNum",
    "relBwVt",
    "relNid",
    "relRtDpCat",
    "relRtDpSubCat",
    "relRtDslInd",
    "remoteMdfSeqNum",
    "restncQty",
    "sbiInd",
    "termntrCdPr1",
    "termntrCdPr2",
    "vdslBwSharedInd",
    "vertTypeCdPr1",
    "vertTypeCdPr2",
    "vfId",
    "voipInd"
})
public class OrderInformationOriVoiceReturn {

    @XmlElement(name = "AftCd")
    protected String aftCd;
    @XmlElement(name = "AgNodeId")
    protected String agNodeId;
    @XmlElement(name = "AgTid")
    protected String agTid;
    @XmlElement(name = "AnExchng")
    protected String anExchng;
    @XmlElement(name = "AnInd")
    protected String anInd;
    @XmlElement(name = "AnPortNum")
    protected String anPortNum;
    @XmlElement(name = "AttenuatnAt1600HzQty")
    protected String attenuatnAt1600HzQty;
    @XmlElement(name = "AttenuatnAt800HzQty")
    protected String attenuatnAt800HzQty;
    @XmlElement(name = "AudioCarrInd")
    protected String audioCarrInd;
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
    @XmlElement(name = "ChannelNum1")
    protected String channelNum1;
    @XmlElement(name = "ChannelNum2")
    protected String channelNum2;
    @XmlElement(name = "DivrCd")
    protected String divrCd;
    @XmlElement(name = "DnPnExchngeId")
    protected String dnPnExchngeId;
    @XmlElement(name = "DpCat")
    protected String dpCat;
    @XmlElement(name = "DpsdfCd")
    protected String dpsdfCd;
    @XmlElement(name = "DpsdfPairNum")
    protected String dpsdfPairNum;
    @XmlElement(name = "DpSubCat")
    protected String dpSubCat;
    @XmlElement(name = "DsidePairNum")
    protected String dsidePairNum;
    @XmlElement(name = "DslInd")
    protected String dslInd;
    @XmlElement(name = "EnExchange")
    protected String enExchange;
    @XmlElement(name = "EsidePairNum")
    protected String esidePairNum;
    @XmlElement(name = "ExistLineCls")
    protected String existLineCls;
    @XmlElement(name = "ExistSrvNum")
    protected String existSrvNum;
    @XmlElement(name = "FromAdslInd")
    protected String fromAdslInd;
    @XmlElement(name = "FromBsn")
    protected String fromBsn;
    @XmlElement(name = "FromL2Prov")
    protected String fromL2Prov;
    @XmlElement(name = "FromLcCrType")
    protected String fromLcCrType;
    @XmlElement(name = "FromNiden")
    protected String fromNiden;
    @XmlElement(name = "FromProdId")
    protected String fromProdId;
    @XmlElement(name = "FromRtId")
    protected String fromRtId;
    @XmlElement(name = "FromRtPort")
    protected String fromRtPort;
    @XmlElement(name = "FromStbType")
    protected String fromStbType;
    @XmlElement(name = "HorTypeCdPr1")
    protected String horTypeCdPr1;
    @XmlElement(name = "HorTypeCdPr2")
    protected String horTypeCdPr2;
    @XmlElement(name = "HostEn")
    protected String hostEn;
    @XmlElement(name = "HostExchng")
    protected String hostExchng;
    @XmlElement(name = "JmpInd")
    protected String jmpInd;
    @XmlElement(name = "L3Address")
    protected String l3Address;
    @XmlElement(name = "LineEqmtNum")
    protected String lineEqmtNum;
    @XmlElement(name = "MdfbarSeqNum")
    protected String mdfbarSeqNum;
    @XmlElement(name = "MdfBuildId")
    protected String mdfBuildId;
    @XmlElement(name = "MdfSeqNum")
    protected String mdfSeqNum;
    @XmlElement(name = "OltNum")
    protected String oltNum;
    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "PcmId")
    protected String pcmId;
    @XmlElement(name = "PinNum")
    protected String pinNum;
    @XmlElement(name = "PnNnExchange")
    protected String pnNnExchange;
    @XmlElement(name = "PnNnInd")
    protected String pnNnInd;
    @XmlElement(name = "RefPnNn")
    protected String refPnNn;
    @XmlElement(name = "RelBwBuildId")
    protected String relBwBuildId;
    @XmlElement(name = "RelBwDpsdfCd")
    protected String relBwDpsdfCd;
    @XmlElement(name = "RelBwPairNum")
    protected String relBwPairNum;
    @XmlElement(name = "RelBwVt")
    protected String relBwVt;
    @XmlElement(name = "RelNid")
    protected String relNid;
    @XmlElement(name = "RelRtDpCat")
    protected String relRtDpCat;
    @XmlElement(name = "RelRtDpSubCat")
    protected String relRtDpSubCat;
    @XmlElement(name = "RelRtDslInd")
    protected String relRtDslInd;
    @XmlElement(name = "RemoteMdfSeqNum")
    protected String remoteMdfSeqNum;
    @XmlElement(name = "RestncQty")
    protected String restncQty;
    @XmlElement(name = "SbiInd")
    protected String sbiInd;
    @XmlElement(name = "TermntrCdPr1")
    protected String termntrCdPr1;
    @XmlElement(name = "TermntrCdPr2")
    protected String termntrCdPr2;
    @XmlElement(name = "VdslBwSharedInd")
    protected String vdslBwSharedInd;
    @XmlElement(name = "VertTypeCdPr1")
    protected String vertTypeCdPr1;
    @XmlElement(name = "VertTypeCdPr2")
    protected String vertTypeCdPr2;
    @XmlElement(name = "VfId")
    protected String vfId;
    @XmlElement(name = "VoipInd")
    protected String voipInd;

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
     * Gets the value of the agNodeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgNodeId() {
        return agNodeId;
    }

    /**
     * Sets the value of the agNodeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgNodeId(String value) {
        this.agNodeId = value;
    }

    /**
     * Gets the value of the agTid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgTid() {
        return agTid;
    }

    /**
     * Sets the value of the agTid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgTid(String value) {
        this.agTid = value;
    }

    /**
     * Gets the value of the anExchng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnExchng() {
        return anExchng;
    }

    /**
     * Sets the value of the anExchng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnExchng(String value) {
        this.anExchng = value;
    }

    /**
     * Gets the value of the anInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnInd() {
        return anInd;
    }

    /**
     * Sets the value of the anInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnInd(String value) {
        this.anInd = value;
    }

    /**
     * Gets the value of the anPortNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnPortNum() {
        return anPortNum;
    }

    /**
     * Sets the value of the anPortNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnPortNum(String value) {
        this.anPortNum = value;
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
     * Gets the value of the audioCarrInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudioCarrInd() {
        return audioCarrInd;
    }

    /**
     * Sets the value of the audioCarrInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudioCarrInd(String value) {
        this.audioCarrInd = value;
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
     * Gets the value of the channelNum1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelNum1() {
        return channelNum1;
    }

    /**
     * Sets the value of the channelNum1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelNum1(String value) {
        this.channelNum1 = value;
    }

    /**
     * Gets the value of the channelNum2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelNum2() {
        return channelNum2;
    }

    /**
     * Sets the value of the channelNum2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelNum2(String value) {
        this.channelNum2 = value;
    }

    /**
     * Gets the value of the divrCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDivrCd() {
        return divrCd;
    }

    /**
     * Sets the value of the divrCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDivrCd(String value) {
        this.divrCd = value;
    }

    /**
     * Gets the value of the dnPnExchngeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDnPnExchngeId() {
        return dnPnExchngeId;
    }

    /**
     * Sets the value of the dnPnExchngeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDnPnExchngeId(String value) {
        this.dnPnExchngeId = value;
    }

    /**
     * Gets the value of the dpCat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpCat() {
        return dpCat;
    }

    /**
     * Sets the value of the dpCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpCat(String value) {
        this.dpCat = value;
    }

    /**
     * Gets the value of the dpsdfCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpsdfCd() {
        return dpsdfCd;
    }

    /**
     * Sets the value of the dpsdfCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpsdfCd(String value) {
        this.dpsdfCd = value;
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
     * Gets the value of the dpSubCat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpSubCat() {
        return dpSubCat;
    }

    /**
     * Sets the value of the dpSubCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpSubCat(String value) {
        this.dpSubCat = value;
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
     * Gets the value of the dslInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDslInd() {
        return dslInd;
    }

    /**
     * Sets the value of the dslInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDslInd(String value) {
        this.dslInd = value;
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
     * Gets the value of the existSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistSrvNum() {
        return existSrvNum;
    }

    /**
     * Sets the value of the existSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistSrvNum(String value) {
        this.existSrvNum = value;
    }

    /**
     * Gets the value of the fromAdslInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromAdslInd() {
        return fromAdslInd;
    }

    /**
     * Sets the value of the fromAdslInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromAdslInd(String value) {
        this.fromAdslInd = value;
    }

    /**
     * Gets the value of the fromBsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromBsn() {
        return fromBsn;
    }

    /**
     * Sets the value of the fromBsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromBsn(String value) {
        this.fromBsn = value;
    }

    /**
     * Gets the value of the fromL2Prov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromL2Prov() {
        return fromL2Prov;
    }

    /**
     * Sets the value of the fromL2Prov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromL2Prov(String value) {
        this.fromL2Prov = value;
    }

    /**
     * Gets the value of the fromLcCrType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromLcCrType() {
        return fromLcCrType;
    }

    /**
     * Sets the value of the fromLcCrType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromLcCrType(String value) {
        this.fromLcCrType = value;
    }

    /**
     * Gets the value of the fromNiden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromNiden() {
        return fromNiden;
    }

    /**
     * Sets the value of the fromNiden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromNiden(String value) {
        this.fromNiden = value;
    }

    /**
     * Gets the value of the fromProdId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromProdId() {
        return fromProdId;
    }

    /**
     * Sets the value of the fromProdId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromProdId(String value) {
        this.fromProdId = value;
    }

    /**
     * Gets the value of the fromRtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromRtId() {
        return fromRtId;
    }

    /**
     * Sets the value of the fromRtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromRtId(String value) {
        this.fromRtId = value;
    }

    /**
     * Gets the value of the fromRtPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromRtPort() {
        return fromRtPort;
    }

    /**
     * Sets the value of the fromRtPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromRtPort(String value) {
        this.fromRtPort = value;
    }

    /**
     * Gets the value of the fromStbType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromStbType() {
        return fromStbType;
    }

    /**
     * Sets the value of the fromStbType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromStbType(String value) {
        this.fromStbType = value;
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
     * Gets the value of the hostEn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostEn() {
        return hostEn;
    }

    /**
     * Sets the value of the hostEn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostEn(String value) {
        this.hostEn = value;
    }

    /**
     * Gets the value of the hostExchng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostExchng() {
        return hostExchng;
    }

    /**
     * Sets the value of the hostExchng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostExchng(String value) {
        this.hostExchng = value;
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
     * Gets the value of the l3Address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL3Address() {
        return l3Address;
    }

    /**
     * Sets the value of the l3Address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL3Address(String value) {
        this.l3Address = value;
    }

    /**
     * Gets the value of the lineEqmtNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineEqmtNum() {
        return lineEqmtNum;
    }

    /**
     * Sets the value of the lineEqmtNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineEqmtNum(String value) {
        this.lineEqmtNum = value;
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
     * Gets the value of the oltNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOltNum() {
        return oltNum;
    }

    /**
     * Sets the value of the oltNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOltNum(String value) {
        this.oltNum = value;
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
     * Gets the value of the pcmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPcmId() {
        return pcmId;
    }

    /**
     * Sets the value of the pcmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPcmId(String value) {
        this.pcmId = value;
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
     * Gets the value of the pnNnExchange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPnNnExchange() {
        return pnNnExchange;
    }

    /**
     * Sets the value of the pnNnExchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPnNnExchange(String value) {
        this.pnNnExchange = value;
    }

    /**
     * Gets the value of the pnNnInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPnNnInd() {
        return pnNnInd;
    }

    /**
     * Sets the value of the pnNnInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPnNnInd(String value) {
        this.pnNnInd = value;
    }

    /**
     * Gets the value of the refPnNn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefPnNn() {
        return refPnNn;
    }

    /**
     * Sets the value of the refPnNn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefPnNn(String value) {
        this.refPnNn = value;
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
     * Gets the value of the relNid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelNid() {
        return relNid;
    }

    /**
     * Sets the value of the relNid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelNid(String value) {
        this.relNid = value;
    }

    /**
     * Gets the value of the relRtDpCat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelRtDpCat() {
        return relRtDpCat;
    }

    /**
     * Sets the value of the relRtDpCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelRtDpCat(String value) {
        this.relRtDpCat = value;
    }

    /**
     * Gets the value of the relRtDpSubCat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelRtDpSubCat() {
        return relRtDpSubCat;
    }

    /**
     * Sets the value of the relRtDpSubCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelRtDpSubCat(String value) {
        this.relRtDpSubCat = value;
    }

    /**
     * Gets the value of the relRtDslInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelRtDslInd() {
        return relRtDslInd;
    }

    /**
     * Sets the value of the relRtDslInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelRtDslInd(String value) {
        this.relRtDslInd = value;
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
     * Gets the value of the vdslBwSharedInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVdslBwSharedInd() {
        return vdslBwSharedInd;
    }

    /**
     * Sets the value of the vdslBwSharedInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVdslBwSharedInd(String value) {
        this.vdslBwSharedInd = value;
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
     * Gets the value of the vfId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVfId() {
        return vfId;
    }

    /**
     * Sets the value of the vfId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVfId(String value) {
        this.vfId = value;
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

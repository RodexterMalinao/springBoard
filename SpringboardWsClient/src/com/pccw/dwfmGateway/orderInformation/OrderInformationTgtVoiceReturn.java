
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationTgtVoiceReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationTgtVoiceReturn">
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
 *         &lt;element name="BlkWirePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CabinetSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CableCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CablePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CarrierInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *         &lt;element name="HorTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HostEn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HostExchng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IntraOfficeInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JmpInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="L3Address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LineEqmtNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfbarSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MdfSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OltNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdactvSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PcmId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PinNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PnNnExchange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwdpsdfCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwPairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBwVt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelNid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelRtDpCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelRtDpSubCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelRtDslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemoteMdfSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RestncQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SbiInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvNn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermntrTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToAdslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToBsn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToL2Prov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToLcCrType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToNiden" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToProdId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToRtId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToRtPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToStbType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VdslBwSharedInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VertTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "OrderInformationTgtVoiceReturn", propOrder = {
    "aftCd",
    "agNodeId",
    "agTid",
    "anExchng",
    "anInd",
    "anPortNum",
    "attenuatnAt1600HzQty",
    "attenuatnAt800HzQty",
    "blkWirePairNum",
    "buildId",
    "cabinetSeqNum",
    "cableCd",
    "cablePairNum",
    "carrierInd",
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
    "horTypeCd",
    "hostEn",
    "hostExchng",
    "intraOfficeInd",
    "jmpInd",
    "l3Address",
    "lineEqmtNum",
    "mdfbarSeqNum",
    "mdfBuildId",
    "mdfSeqNum",
    "newSrvNum",
    "oltNum",
    "ordactvSeqNum",
    "orderId",
    "pcmId",
    "pinNum",
    "pnNnExchange",
    "relBwBuildId",
    "relBwdpsdfCd",
    "relBwPairNum",
    "relBwVt",
    "relNid",
    "relRtDpCat",
    "relRtDpSubCat",
    "relRtDslInd",
    "remoteMdfSeqNum",
    "restncQty",
    "sbiInd",
    "srvNn",
    "termntrTypeCd",
    "toAdslInd",
    "toBsn",
    "toL2Prov",
    "toLcCrType",
    "toNiden",
    "toProdId",
    "toRtId",
    "toRtPort",
    "toStbType",
    "vdslBwSharedInd",
    "vertTypeCd",
    "vfId",
    "voipInd"
})
public class OrderInformationTgtVoiceReturn {

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
    @XmlElement(name = "BlkWirePairNum")
    protected String blkWirePairNum;
    @XmlElement(name = "BuildId")
    protected String buildId;
    @XmlElement(name = "CabinetSeqNum")
    protected String cabinetSeqNum;
    @XmlElement(name = "CableCd")
    protected String cableCd;
    @XmlElement(name = "CablePairNum")
    protected String cablePairNum;
    @XmlElement(name = "CarrierInd")
    protected String carrierInd;
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
    @XmlElement(name = "HorTypeCd")
    protected String horTypeCd;
    @XmlElement(name = "HostEn")
    protected String hostEn;
    @XmlElement(name = "HostExchng")
    protected String hostExchng;
    @XmlElement(name = "IntraOfficeInd")
    protected String intraOfficeInd;
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
    @XmlElement(name = "NewSrvNum")
    protected String newSrvNum;
    @XmlElement(name = "OltNum")
    protected String oltNum;
    @XmlElement(name = "OrdactvSeqNum")
    protected String ordactvSeqNum;
    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "PcmId")
    protected String pcmId;
    @XmlElement(name = "PinNum")
    protected String pinNum;
    @XmlElement(name = "PnNnExchange")
    protected String pnNnExchange;
    @XmlElement(name = "RelBwBuildId")
    protected String relBwBuildId;
    @XmlElement(name = "RelBwdpsdfCd")
    protected String relBwdpsdfCd;
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
    @XmlElement(name = "SrvNn")
    protected String srvNn;
    @XmlElement(name = "TermntrTypeCd")
    protected String termntrTypeCd;
    @XmlElement(name = "ToAdslInd")
    protected String toAdslInd;
    @XmlElement(name = "ToBsn")
    protected String toBsn;
    @XmlElement(name = "ToL2Prov")
    protected String toL2Prov;
    @XmlElement(name = "ToLcCrType")
    protected String toLcCrType;
    @XmlElement(name = "ToNiden")
    protected String toNiden;
    @XmlElement(name = "ToProdId")
    protected String toProdId;
    @XmlElement(name = "ToRtId")
    protected String toRtId;
    @XmlElement(name = "ToRtPort")
    protected String toRtPort;
    @XmlElement(name = "ToStbType")
    protected String toStbType;
    @XmlElement(name = "VdslBwSharedInd")
    protected String vdslBwSharedInd;
    @XmlElement(name = "VertTypeCd")
    protected String vertTypeCd;
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
     * Gets the value of the carrierInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierInd() {
        return carrierInd;
    }

    /**
     * Sets the value of the carrierInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierInd(String value) {
        this.carrierInd = value;
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
     * Gets the value of the intraOfficeInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntraOfficeInd() {
        return intraOfficeInd;
    }

    /**
     * Sets the value of the intraOfficeInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntraOfficeInd(String value) {
        this.intraOfficeInd = value;
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
     * Gets the value of the newSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewSrvNum() {
        return newSrvNum;
    }

    /**
     * Sets the value of the newSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewSrvNum(String value) {
        this.newSrvNum = value;
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
     * Gets the value of the relBwdpsdfCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelBwdpsdfCd() {
        return relBwdpsdfCd;
    }

    /**
     * Sets the value of the relBwdpsdfCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelBwdpsdfCd(String value) {
        this.relBwdpsdfCd = value;
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
     * Gets the value of the srvNn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvNn() {
        return srvNn;
    }

    /**
     * Sets the value of the srvNn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvNn(String value) {
        this.srvNn = value;
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
     * Gets the value of the toAdslInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToAdslInd() {
        return toAdslInd;
    }

    /**
     * Sets the value of the toAdslInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToAdslInd(String value) {
        this.toAdslInd = value;
    }

    /**
     * Gets the value of the toBsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToBsn() {
        return toBsn;
    }

    /**
     * Sets the value of the toBsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToBsn(String value) {
        this.toBsn = value;
    }

    /**
     * Gets the value of the toL2Prov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToL2Prov() {
        return toL2Prov;
    }

    /**
     * Sets the value of the toL2Prov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToL2Prov(String value) {
        this.toL2Prov = value;
    }

    /**
     * Gets the value of the toLcCrType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToLcCrType() {
        return toLcCrType;
    }

    /**
     * Sets the value of the toLcCrType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToLcCrType(String value) {
        this.toLcCrType = value;
    }

    /**
     * Gets the value of the toNiden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToNiden() {
        return toNiden;
    }

    /**
     * Sets the value of the toNiden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToNiden(String value) {
        this.toNiden = value;
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
     * Gets the value of the toRtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRtId() {
        return toRtId;
    }

    /**
     * Sets the value of the toRtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRtId(String value) {
        this.toRtId = value;
    }

    /**
     * Gets the value of the toRtPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRtPort() {
        return toRtPort;
    }

    /**
     * Sets the value of the toRtPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRtPort(String value) {
        this.toRtPort = value;
    }

    /**
     * Gets the value of the toStbType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToStbType() {
        return toStbType;
    }

    /**
     * Sets the value of the toStbType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToStbType(String value) {
        this.toStbType = value;
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

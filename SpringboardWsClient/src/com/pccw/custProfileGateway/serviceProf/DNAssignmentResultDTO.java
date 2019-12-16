
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DNAssignmentResultDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DNAssignmentResultDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="OrdServiceDTO" type="{http://www.openuri.org/}OrdServiceDTO" minOccurs="0"/>
 *         &lt;element name="OrdDetailDTO" type="{http://www.openuri.org/}OrdDetailDTO" minOccurs="0"/>
 *         &lt;element name="PrimaryOrderDtID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrimaryOrderServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PilotSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SelectedCustomer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SelectedHostExchangeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CableBuildID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CableMdfSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CableMdfBarSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CableCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CablePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CableDpsdfCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LvlNewSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnTargetExchangeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvestigateIND" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Investigate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Gp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GpID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Methods" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Overwrite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IntercomNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Assign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrMnmonic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrMachineID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrSeverity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActvSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AftInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AvailableDNs" type="{http://www.openuri.org/}ArrayOfDnDTO" minOccurs="0"/>
 *         &lt;element name="CcnRLU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcnBuildID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcnCustomer" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="CcnHostExchangeId" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="CcnInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DnAssignmentOption" type="{http://www.openuri.org/}DNAssignmentOptionDTO" minOccurs="0"/>
 *         &lt;element name="DuplexInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExchangeIDIn" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="ExchangeIDOut" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="ExtServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FeaCD" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="FeaChangeInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FeaQty" type="{http://www.openuri.org/}ArrayOfint" minOccurs="0"/>
 *         &lt;element name="GroupFeaCd" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="GroupFeaQty" type="{http://www.openuri.org/}ArrayOfint" minOccurs="0"/>
 *         &lt;element name="GroupLinesQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="InventSTSCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InventSTSCDIn" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="InventSTSCDOut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastSearchDN" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LegacyOrderNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxSearchLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Message" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="MoreInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NetworkID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewDatCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewDatQualCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewServiceIDIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OcID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDtID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderModifyInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderStsCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderTypeCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SelectedDN" type="{http://www.openuri.org/}DnDTO" minOccurs="0"/>
 *         &lt;element name="ServiceNumIn" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="ServiceNumOut" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="SrvReqID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvReqIDOut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TargetSoftswitch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VoipInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GatewayNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GatewayNumIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContinueLvlID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DragonReasonCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DragonReasonDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReservationAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReservationBoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReservationProject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartRange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndRange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExchangeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BbgGroupID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImsDtlID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImsDtlSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImsOcID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RetainSrvId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DNAssignmentResultDTO", propOrder = {
    "ordServiceDTO",
    "ordDetailDTO",
    "primaryOrderDtID",
    "primaryOrderServiceID",
    "pilotSrvNum",
    "selectedCustomer",
    "selectedHostExchangeID",
    "cableBuildID",
    "cableMdfSeqNum",
    "cableMdfBarSeqNum",
    "cableCD",
    "cablePairNum",
    "cableDpsdfCD",
    "lvlNewSrvNum",
    "enTargetExchangeID",
    "investigateIND",
    "investigate",
    "gp",
    "gpID",
    "serviceAddress",
    "methods",
    "overwrite",
    "intercomNo",
    "assign",
    "errDetail",
    "errMnmonic",
    "errMachineID",
    "errDesc",
    "errSeverity",
    "actvSeqNum",
    "aftInd",
    "availableDNs",
    "ccnRLU",
    "ccnBuildID",
    "ccnCustomer",
    "ccnHostExchangeId",
    "ccnInd",
    "datCD",
    "dnAssignmentOption",
    "duplexInd",
    "exchangeIDIn",
    "exchangeIDOut",
    "extServiceID",
    "feaCD",
    "feaChangeInd",
    "feaQty",
    "groupFeaCd",
    "groupFeaQty",
    "groupLinesQty",
    "inventSTSCD",
    "inventSTSCDIn",
    "inventSTSCDOut",
    "lastSearchDN",
    "legacyOrderNum",
    "maxSearchLimit",
    "message",
    "moreInd",
    "networkID",
    "newDatCD",
    "newDatQualCD",
    "newServiceID",
    "newServiceIDIN",
    "ocID",
    "orderDtID",
    "orderModifyInd",
    "orderServiceID",
    "orderStsCD",
    "orderTypeCD",
    "selectedDN",
    "serviceNumIn",
    "serviceNumOut",
    "srvReqID",
    "srvReqIDOut",
    "targetSoftswitch",
    "typeOfSrv",
    "voipInd",
    "gatewayNum",
    "gatewayNumIN",
    "continueLvlID",
    "dragonReasonCd",
    "dragonReasonDesc",
    "reservationAccount",
    "reservationBoc",
    "reservationProject",
    "startRange",
    "endRange",
    "exchangeID",
    "bbgGroupID",
    "imsDtlID",
    "imsDtlSeq",
    "imsOcID",
    "retainSrvId"
})
public class DNAssignmentResultDTO
    extends ValueObject
{

    @XmlElement(name = "OrdServiceDTO")
    protected OrdServiceDTO ordServiceDTO;
    @XmlElement(name = "OrdDetailDTO")
    protected OrdDetailDTO ordDetailDTO;
    @XmlElement(name = "PrimaryOrderDtID")
    protected String primaryOrderDtID;
    @XmlElement(name = "PrimaryOrderServiceID")
    protected String primaryOrderServiceID;
    @XmlElement(name = "PilotSrvNum")
    protected String pilotSrvNum;
    @XmlElement(name = "SelectedCustomer")
    protected String selectedCustomer;
    @XmlElement(name = "SelectedHostExchangeID")
    protected String selectedHostExchangeID;
    @XmlElement(name = "CableBuildID")
    protected String cableBuildID;
    @XmlElement(name = "CableMdfSeqNum")
    protected String cableMdfSeqNum;
    @XmlElement(name = "CableMdfBarSeqNum")
    protected String cableMdfBarSeqNum;
    @XmlElement(name = "CableCD")
    protected String cableCD;
    @XmlElement(name = "CablePairNum")
    protected String cablePairNum;
    @XmlElement(name = "CableDpsdfCD")
    protected String cableDpsdfCD;
    @XmlElement(name = "LvlNewSrvNum")
    protected String lvlNewSrvNum;
    @XmlElement(name = "EnTargetExchangeID")
    protected String enTargetExchangeID;
    @XmlElement(name = "InvestigateIND")
    protected boolean investigateIND;
    @XmlElement(name = "Investigate")
    protected String investigate;
    @XmlElement(name = "Gp")
    protected String gp;
    @XmlElement(name = "GpID")
    protected String gpID;
    @XmlElement(name = "ServiceAddress")
    protected String serviceAddress;
    @XmlElement(name = "Methods")
    protected String methods;
    @XmlElement(name = "Overwrite")
    protected String overwrite;
    @XmlElement(name = "IntercomNo")
    protected String intercomNo;
    @XmlElement(name = "Assign")
    protected String assign;
    @XmlElement(name = "ErrDetail")
    protected String errDetail;
    @XmlElement(name = "ErrMnmonic")
    protected String errMnmonic;
    @XmlElement(name = "ErrMachineID")
    protected String errMachineID;
    @XmlElement(name = "ErrDesc")
    protected String errDesc;
    @XmlElement(name = "ErrSeverity")
    protected String errSeverity;
    @XmlElement(name = "ActvSeqNum")
    protected String actvSeqNum;
    @XmlElement(name = "AftInd")
    protected String aftInd;
    @XmlElement(name = "AvailableDNs")
    protected ArrayOfDnDTO availableDNs;
    @XmlElement(name = "CcnRLU")
    protected String ccnRLU;
    @XmlElement(name = "CcnBuildID")
    protected String ccnBuildID;
    @XmlElement(name = "CcnCustomer")
    protected ArrayOfString ccnCustomer;
    @XmlElement(name = "CcnHostExchangeId")
    protected ArrayOfString ccnHostExchangeId;
    @XmlElement(name = "CcnInd")
    protected String ccnInd;
    @XmlElement(name = "DatCD")
    protected String datCD;
    @XmlElement(name = "DnAssignmentOption")
    protected DNAssignmentOptionDTO dnAssignmentOption;
    @XmlElement(name = "DuplexInd")
    protected String duplexInd;
    @XmlElement(name = "ExchangeIDIn")
    protected ArrayOfString exchangeIDIn;
    @XmlElement(name = "ExchangeIDOut")
    protected ArrayOfString exchangeIDOut;
    @XmlElement(name = "ExtServiceID")
    protected String extServiceID;
    @XmlElement(name = "FeaCD")
    protected ArrayOfString feaCD;
    @XmlElement(name = "FeaChangeInd")
    protected String feaChangeInd;
    @XmlElement(name = "FeaQty")
    protected ArrayOfint feaQty;
    @XmlElement(name = "GroupFeaCd")
    protected ArrayOfString groupFeaCd;
    @XmlElement(name = "GroupFeaQty")
    protected ArrayOfint groupFeaQty;
    @XmlElement(name = "GroupLinesQty")
    protected int groupLinesQty;
    @XmlElement(name = "InventSTSCD")
    protected String inventSTSCD;
    @XmlElement(name = "InventSTSCDIn")
    protected ArrayOfString inventSTSCDIn;
    @XmlElement(name = "InventSTSCDOut")
    protected String inventSTSCDOut;
    @XmlElement(name = "LastSearchDN")
    protected int lastSearchDN;
    @XmlElement(name = "LegacyOrderNum")
    protected String legacyOrderNum;
    @XmlElement(name = "MaxSearchLimit")
    protected int maxSearchLimit;
    @XmlElement(name = "Message")
    protected ArrayOfString message;
    @XmlElement(name = "MoreInd")
    protected String moreInd;
    @XmlElement(name = "NetworkID")
    protected String networkID;
    @XmlElement(name = "NewDatCD")
    protected String newDatCD;
    @XmlElement(name = "NewDatQualCD")
    protected String newDatQualCD;
    @XmlElement(name = "NewServiceID")
    protected String newServiceID;
    @XmlElement(name = "NewServiceIDIN")
    protected String newServiceIDIN;
    @XmlElement(name = "OcID")
    protected String ocID;
    @XmlElement(name = "OrderDtID")
    protected String orderDtID;
    @XmlElement(name = "OrderModifyInd")
    protected String orderModifyInd;
    @XmlElement(name = "OrderServiceID")
    protected String orderServiceID;
    @XmlElement(name = "OrderStsCD")
    protected String orderStsCD;
    @XmlElement(name = "OrderTypeCD")
    protected String orderTypeCD;
    @XmlElement(name = "SelectedDN")
    protected DnDTO selectedDN;
    @XmlElement(name = "ServiceNumIn")
    protected ArrayOfString serviceNumIn;
    @XmlElement(name = "ServiceNumOut")
    protected ArrayOfString serviceNumOut;
    @XmlElement(name = "SrvReqID")
    protected String srvReqID;
    @XmlElement(name = "SrvReqIDOut")
    protected String srvReqIDOut;
    @XmlElement(name = "TargetSoftswitch")
    protected String targetSoftswitch;
    @XmlElement(name = "TypeOfSrv")
    protected String typeOfSrv;
    @XmlElement(name = "VoipInd")
    protected String voipInd;
    @XmlElement(name = "GatewayNum")
    protected String gatewayNum;
    @XmlElement(name = "GatewayNumIN")
    protected String gatewayNumIN;
    @XmlElement(name = "ContinueLvlID")
    protected String continueLvlID;
    @XmlElement(name = "DragonReasonCd")
    protected String dragonReasonCd;
    @XmlElement(name = "DragonReasonDesc")
    protected String dragonReasonDesc;
    @XmlElement(name = "ReservationAccount")
    protected String reservationAccount;
    @XmlElement(name = "ReservationBoc")
    protected String reservationBoc;
    @XmlElement(name = "ReservationProject")
    protected String reservationProject;
    @XmlElement(name = "StartRange")
    protected String startRange;
    @XmlElement(name = "EndRange")
    protected String endRange;
    @XmlElement(name = "ExchangeID")
    protected String exchangeID;
    @XmlElement(name = "BbgGroupID")
    protected String bbgGroupID;
    @XmlElement(name = "ImsDtlID")
    protected String imsDtlID;
    @XmlElement(name = "ImsDtlSeq")
    protected String imsDtlSeq;
    @XmlElement(name = "ImsOcID")
    protected String imsOcID;
    @XmlElement(name = "RetainSrvId")
    protected String retainSrvId;

    /**
     * Gets the value of the ordServiceDTO property.
     * 
     * @return
     *     possible object is
     *     {@link OrdServiceDTO }
     *     
     */
    public OrdServiceDTO getOrdServiceDTO() {
        return ordServiceDTO;
    }

    /**
     * Sets the value of the ordServiceDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdServiceDTO }
     *     
     */
    public void setOrdServiceDTO(OrdServiceDTO value) {
        this.ordServiceDTO = value;
    }

    /**
     * Gets the value of the ordDetailDTO property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailDTO }
     *     
     */
    public OrdDetailDTO getOrdDetailDTO() {
        return ordDetailDTO;
    }

    /**
     * Sets the value of the ordDetailDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailDTO }
     *     
     */
    public void setOrdDetailDTO(OrdDetailDTO value) {
        this.ordDetailDTO = value;
    }

    /**
     * Gets the value of the primaryOrderDtID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryOrderDtID() {
        return primaryOrderDtID;
    }

    /**
     * Sets the value of the primaryOrderDtID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryOrderDtID(String value) {
        this.primaryOrderDtID = value;
    }

    /**
     * Gets the value of the primaryOrderServiceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryOrderServiceID() {
        return primaryOrderServiceID;
    }

    /**
     * Sets the value of the primaryOrderServiceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryOrderServiceID(String value) {
        this.primaryOrderServiceID = value;
    }

    /**
     * Gets the value of the pilotSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPilotSrvNum() {
        return pilotSrvNum;
    }

    /**
     * Sets the value of the pilotSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPilotSrvNum(String value) {
        this.pilotSrvNum = value;
    }

    /**
     * Gets the value of the selectedCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * Sets the value of the selectedCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectedCustomer(String value) {
        this.selectedCustomer = value;
    }

    /**
     * Gets the value of the selectedHostExchangeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectedHostExchangeID() {
        return selectedHostExchangeID;
    }

    /**
     * Sets the value of the selectedHostExchangeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectedHostExchangeID(String value) {
        this.selectedHostExchangeID = value;
    }

    /**
     * Gets the value of the cableBuildID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCableBuildID() {
        return cableBuildID;
    }

    /**
     * Sets the value of the cableBuildID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCableBuildID(String value) {
        this.cableBuildID = value;
    }

    /**
     * Gets the value of the cableMdfSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCableMdfSeqNum() {
        return cableMdfSeqNum;
    }

    /**
     * Sets the value of the cableMdfSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCableMdfSeqNum(String value) {
        this.cableMdfSeqNum = value;
    }

    /**
     * Gets the value of the cableMdfBarSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCableMdfBarSeqNum() {
        return cableMdfBarSeqNum;
    }

    /**
     * Sets the value of the cableMdfBarSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCableMdfBarSeqNum(String value) {
        this.cableMdfBarSeqNum = value;
    }

    /**
     * Gets the value of the cableCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCableCD() {
        return cableCD;
    }

    /**
     * Sets the value of the cableCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCableCD(String value) {
        this.cableCD = value;
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
     * Gets the value of the cableDpsdfCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCableDpsdfCD() {
        return cableDpsdfCD;
    }

    /**
     * Sets the value of the cableDpsdfCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCableDpsdfCD(String value) {
        this.cableDpsdfCD = value;
    }

    /**
     * Gets the value of the lvlNewSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLvlNewSrvNum() {
        return lvlNewSrvNum;
    }

    /**
     * Sets the value of the lvlNewSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLvlNewSrvNum(String value) {
        this.lvlNewSrvNum = value;
    }

    /**
     * Gets the value of the enTargetExchangeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnTargetExchangeID() {
        return enTargetExchangeID;
    }

    /**
     * Sets the value of the enTargetExchangeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnTargetExchangeID(String value) {
        this.enTargetExchangeID = value;
    }

    /**
     * Gets the value of the investigateIND property.
     * 
     */
    public boolean isInvestigateIND() {
        return investigateIND;
    }

    /**
     * Sets the value of the investigateIND property.
     * 
     */
    public void setInvestigateIND(boolean value) {
        this.investigateIND = value;
    }

    /**
     * Gets the value of the investigate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvestigate() {
        return investigate;
    }

    /**
     * Sets the value of the investigate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvestigate(String value) {
        this.investigate = value;
    }

    /**
     * Gets the value of the gp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGp() {
        return gp;
    }

    /**
     * Sets the value of the gp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGp(String value) {
        this.gp = value;
    }

    /**
     * Gets the value of the gpID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGpID() {
        return gpID;
    }

    /**
     * Sets the value of the gpID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGpID(String value) {
        this.gpID = value;
    }

    /**
     * Gets the value of the serviceAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceAddress() {
        return serviceAddress;
    }

    /**
     * Sets the value of the serviceAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceAddress(String value) {
        this.serviceAddress = value;
    }

    /**
     * Gets the value of the methods property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethods() {
        return methods;
    }

    /**
     * Sets the value of the methods property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethods(String value) {
        this.methods = value;
    }

    /**
     * Gets the value of the overwrite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverwrite() {
        return overwrite;
    }

    /**
     * Sets the value of the overwrite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverwrite(String value) {
        this.overwrite = value;
    }

    /**
     * Gets the value of the intercomNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntercomNo() {
        return intercomNo;
    }

    /**
     * Sets the value of the intercomNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntercomNo(String value) {
        this.intercomNo = value;
    }

    /**
     * Gets the value of the assign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssign() {
        return assign;
    }

    /**
     * Sets the value of the assign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssign(String value) {
        this.assign = value;
    }

    /**
     * Gets the value of the errDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrDetail() {
        return errDetail;
    }

    /**
     * Sets the value of the errDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrDetail(String value) {
        this.errDetail = value;
    }

    /**
     * Gets the value of the errMnmonic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrMnmonic() {
        return errMnmonic;
    }

    /**
     * Sets the value of the errMnmonic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrMnmonic(String value) {
        this.errMnmonic = value;
    }

    /**
     * Gets the value of the errMachineID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrMachineID() {
        return errMachineID;
    }

    /**
     * Sets the value of the errMachineID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrMachineID(String value) {
        this.errMachineID = value;
    }

    /**
     * Gets the value of the errDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrDesc() {
        return errDesc;
    }

    /**
     * Sets the value of the errDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrDesc(String value) {
        this.errDesc = value;
    }

    /**
     * Gets the value of the errSeverity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrSeverity() {
        return errSeverity;
    }

    /**
     * Sets the value of the errSeverity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrSeverity(String value) {
        this.errSeverity = value;
    }

    /**
     * Gets the value of the actvSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActvSeqNum() {
        return actvSeqNum;
    }

    /**
     * Sets the value of the actvSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActvSeqNum(String value) {
        this.actvSeqNum = value;
    }

    /**
     * Gets the value of the aftInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAftInd() {
        return aftInd;
    }

    /**
     * Sets the value of the aftInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAftInd(String value) {
        this.aftInd = value;
    }

    /**
     * Gets the value of the availableDNs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDnDTO }
     *     
     */
    public ArrayOfDnDTO getAvailableDNs() {
        return availableDNs;
    }

    /**
     * Sets the value of the availableDNs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDnDTO }
     *     
     */
    public void setAvailableDNs(ArrayOfDnDTO value) {
        this.availableDNs = value;
    }

    /**
     * Gets the value of the ccnRLU property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcnRLU() {
        return ccnRLU;
    }

    /**
     * Sets the value of the ccnRLU property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcnRLU(String value) {
        this.ccnRLU = value;
    }

    /**
     * Gets the value of the ccnBuildID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcnBuildID() {
        return ccnBuildID;
    }

    /**
     * Sets the value of the ccnBuildID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcnBuildID(String value) {
        this.ccnBuildID = value;
    }

    /**
     * Gets the value of the ccnCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getCcnCustomer() {
        return ccnCustomer;
    }

    /**
     * Sets the value of the ccnCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setCcnCustomer(ArrayOfString value) {
        this.ccnCustomer = value;
    }

    /**
     * Gets the value of the ccnHostExchangeId property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getCcnHostExchangeId() {
        return ccnHostExchangeId;
    }

    /**
     * Sets the value of the ccnHostExchangeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setCcnHostExchangeId(ArrayOfString value) {
        this.ccnHostExchangeId = value;
    }

    /**
     * Gets the value of the ccnInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcnInd() {
        return ccnInd;
    }

    /**
     * Sets the value of the ccnInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcnInd(String value) {
        this.ccnInd = value;
    }

    /**
     * Gets the value of the datCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatCD() {
        return datCD;
    }

    /**
     * Sets the value of the datCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatCD(String value) {
        this.datCD = value;
    }

    /**
     * Gets the value of the dnAssignmentOption property.
     * 
     * @return
     *     possible object is
     *     {@link DNAssignmentOptionDTO }
     *     
     */
    public DNAssignmentOptionDTO getDnAssignmentOption() {
        return dnAssignmentOption;
    }

    /**
     * Sets the value of the dnAssignmentOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link DNAssignmentOptionDTO }
     *     
     */
    public void setDnAssignmentOption(DNAssignmentOptionDTO value) {
        this.dnAssignmentOption = value;
    }

    /**
     * Gets the value of the duplexInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplexInd() {
        return duplexInd;
    }

    /**
     * Sets the value of the duplexInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplexInd(String value) {
        this.duplexInd = value;
    }

    /**
     * Gets the value of the exchangeIDIn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getExchangeIDIn() {
        return exchangeIDIn;
    }

    /**
     * Sets the value of the exchangeIDIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setExchangeIDIn(ArrayOfString value) {
        this.exchangeIDIn = value;
    }

    /**
     * Gets the value of the exchangeIDOut property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getExchangeIDOut() {
        return exchangeIDOut;
    }

    /**
     * Sets the value of the exchangeIDOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setExchangeIDOut(ArrayOfString value) {
        this.exchangeIDOut = value;
    }

    /**
     * Gets the value of the extServiceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtServiceID() {
        return extServiceID;
    }

    /**
     * Sets the value of the extServiceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtServiceID(String value) {
        this.extServiceID = value;
    }

    /**
     * Gets the value of the feaCD property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getFeaCD() {
        return feaCD;
    }

    /**
     * Sets the value of the feaCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setFeaCD(ArrayOfString value) {
        this.feaCD = value;
    }

    /**
     * Gets the value of the feaChangeInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeaChangeInd() {
        return feaChangeInd;
    }

    /**
     * Sets the value of the feaChangeInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeaChangeInd(String value) {
        this.feaChangeInd = value;
    }

    /**
     * Gets the value of the feaQty property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfint }
     *     
     */
    public ArrayOfint getFeaQty() {
        return feaQty;
    }

    /**
     * Sets the value of the feaQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfint }
     *     
     */
    public void setFeaQty(ArrayOfint value) {
        this.feaQty = value;
    }

    /**
     * Gets the value of the groupFeaCd property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getGroupFeaCd() {
        return groupFeaCd;
    }

    /**
     * Sets the value of the groupFeaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setGroupFeaCd(ArrayOfString value) {
        this.groupFeaCd = value;
    }

    /**
     * Gets the value of the groupFeaQty property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfint }
     *     
     */
    public ArrayOfint getGroupFeaQty() {
        return groupFeaQty;
    }

    /**
     * Sets the value of the groupFeaQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfint }
     *     
     */
    public void setGroupFeaQty(ArrayOfint value) {
        this.groupFeaQty = value;
    }

    /**
     * Gets the value of the groupLinesQty property.
     * 
     */
    public int getGroupLinesQty() {
        return groupLinesQty;
    }

    /**
     * Sets the value of the groupLinesQty property.
     * 
     */
    public void setGroupLinesQty(int value) {
        this.groupLinesQty = value;
    }

    /**
     * Gets the value of the inventSTSCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInventSTSCD() {
        return inventSTSCD;
    }

    /**
     * Sets the value of the inventSTSCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInventSTSCD(String value) {
        this.inventSTSCD = value;
    }

    /**
     * Gets the value of the inventSTSCDIn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getInventSTSCDIn() {
        return inventSTSCDIn;
    }

    /**
     * Sets the value of the inventSTSCDIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setInventSTSCDIn(ArrayOfString value) {
        this.inventSTSCDIn = value;
    }

    /**
     * Gets the value of the inventSTSCDOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInventSTSCDOut() {
        return inventSTSCDOut;
    }

    /**
     * Sets the value of the inventSTSCDOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInventSTSCDOut(String value) {
        this.inventSTSCDOut = value;
    }

    /**
     * Gets the value of the lastSearchDN property.
     * 
     */
    public int getLastSearchDN() {
        return lastSearchDN;
    }

    /**
     * Sets the value of the lastSearchDN property.
     * 
     */
    public void setLastSearchDN(int value) {
        this.lastSearchDN = value;
    }

    /**
     * Gets the value of the legacyOrderNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyOrderNum() {
        return legacyOrderNum;
    }

    /**
     * Sets the value of the legacyOrderNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyOrderNum(String value) {
        this.legacyOrderNum = value;
    }

    /**
     * Gets the value of the maxSearchLimit property.
     * 
     */
    public int getMaxSearchLimit() {
        return maxSearchLimit;
    }

    /**
     * Sets the value of the maxSearchLimit property.
     * 
     */
    public void setMaxSearchLimit(int value) {
        this.maxSearchLimit = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setMessage(ArrayOfString value) {
        this.message = value;
    }

    /**
     * Gets the value of the moreInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoreInd() {
        return moreInd;
    }

    /**
     * Sets the value of the moreInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoreInd(String value) {
        this.moreInd = value;
    }

    /**
     * Gets the value of the networkID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetworkID() {
        return networkID;
    }

    /**
     * Sets the value of the networkID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetworkID(String value) {
        this.networkID = value;
    }

    /**
     * Gets the value of the newDatCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDatCD() {
        return newDatCD;
    }

    /**
     * Sets the value of the newDatCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDatCD(String value) {
        this.newDatCD = value;
    }

    /**
     * Gets the value of the newDatQualCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDatQualCD() {
        return newDatQualCD;
    }

    /**
     * Sets the value of the newDatQualCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDatQualCD(String value) {
        this.newDatQualCD = value;
    }

    /**
     * Gets the value of the newServiceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewServiceID() {
        return newServiceID;
    }

    /**
     * Sets the value of the newServiceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewServiceID(String value) {
        this.newServiceID = value;
    }

    /**
     * Gets the value of the newServiceIDIN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewServiceIDIN() {
        return newServiceIDIN;
    }

    /**
     * Sets the value of the newServiceIDIN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewServiceIDIN(String value) {
        this.newServiceIDIN = value;
    }

    /**
     * Gets the value of the ocID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOcID() {
        return ocID;
    }

    /**
     * Sets the value of the ocID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOcID(String value) {
        this.ocID = value;
    }

    /**
     * Gets the value of the orderDtID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDtID() {
        return orderDtID;
    }

    /**
     * Sets the value of the orderDtID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDtID(String value) {
        this.orderDtID = value;
    }

    /**
     * Gets the value of the orderModifyInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderModifyInd() {
        return orderModifyInd;
    }

    /**
     * Sets the value of the orderModifyInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderModifyInd(String value) {
        this.orderModifyInd = value;
    }

    /**
     * Gets the value of the orderServiceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderServiceID() {
        return orderServiceID;
    }

    /**
     * Sets the value of the orderServiceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderServiceID(String value) {
        this.orderServiceID = value;
    }

    /**
     * Gets the value of the orderStsCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderStsCD() {
        return orderStsCD;
    }

    /**
     * Sets the value of the orderStsCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderStsCD(String value) {
        this.orderStsCD = value;
    }

    /**
     * Gets the value of the orderTypeCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderTypeCD() {
        return orderTypeCD;
    }

    /**
     * Sets the value of the orderTypeCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderTypeCD(String value) {
        this.orderTypeCD = value;
    }

    /**
     * Gets the value of the selectedDN property.
     * 
     * @return
     *     possible object is
     *     {@link DnDTO }
     *     
     */
    public DnDTO getSelectedDN() {
        return selectedDN;
    }

    /**
     * Sets the value of the selectedDN property.
     * 
     * @param value
     *     allowed object is
     *     {@link DnDTO }
     *     
     */
    public void setSelectedDN(DnDTO value) {
        this.selectedDN = value;
    }

    /**
     * Gets the value of the serviceNumIn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getServiceNumIn() {
        return serviceNumIn;
    }

    /**
     * Sets the value of the serviceNumIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setServiceNumIn(ArrayOfString value) {
        this.serviceNumIn = value;
    }

    /**
     * Gets the value of the serviceNumOut property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getServiceNumOut() {
        return serviceNumOut;
    }

    /**
     * Sets the value of the serviceNumOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setServiceNumOut(ArrayOfString value) {
        this.serviceNumOut = value;
    }

    /**
     * Gets the value of the srvReqID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvReqID() {
        return srvReqID;
    }

    /**
     * Sets the value of the srvReqID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvReqID(String value) {
        this.srvReqID = value;
    }

    /**
     * Gets the value of the srvReqIDOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvReqIDOut() {
        return srvReqIDOut;
    }

    /**
     * Sets the value of the srvReqIDOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvReqIDOut(String value) {
        this.srvReqIDOut = value;
    }

    /**
     * Gets the value of the targetSoftswitch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetSoftswitch() {
        return targetSoftswitch;
    }

    /**
     * Sets the value of the targetSoftswitch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetSoftswitch(String value) {
        this.targetSoftswitch = value;
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
     * Gets the value of the gatewayNumIN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatewayNumIN() {
        return gatewayNumIN;
    }

    /**
     * Sets the value of the gatewayNumIN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatewayNumIN(String value) {
        this.gatewayNumIN = value;
    }

    /**
     * Gets the value of the continueLvlID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContinueLvlID() {
        return continueLvlID;
    }

    /**
     * Sets the value of the continueLvlID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContinueLvlID(String value) {
        this.continueLvlID = value;
    }

    /**
     * Gets the value of the dragonReasonCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDragonReasonCd() {
        return dragonReasonCd;
    }

    /**
     * Sets the value of the dragonReasonCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDragonReasonCd(String value) {
        this.dragonReasonCd = value;
    }

    /**
     * Gets the value of the dragonReasonDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDragonReasonDesc() {
        return dragonReasonDesc;
    }

    /**
     * Sets the value of the dragonReasonDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDragonReasonDesc(String value) {
        this.dragonReasonDesc = value;
    }

    /**
     * Gets the value of the reservationAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationAccount() {
        return reservationAccount;
    }

    /**
     * Sets the value of the reservationAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationAccount(String value) {
        this.reservationAccount = value;
    }

    /**
     * Gets the value of the reservationBoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationBoc() {
        return reservationBoc;
    }

    /**
     * Sets the value of the reservationBoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationBoc(String value) {
        this.reservationBoc = value;
    }

    /**
     * Gets the value of the reservationProject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationProject() {
        return reservationProject;
    }

    /**
     * Sets the value of the reservationProject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationProject(String value) {
        this.reservationProject = value;
    }

    /**
     * Gets the value of the startRange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartRange() {
        return startRange;
    }

    /**
     * Sets the value of the startRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartRange(String value) {
        this.startRange = value;
    }

    /**
     * Gets the value of the endRange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndRange() {
        return endRange;
    }

    /**
     * Sets the value of the endRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndRange(String value) {
        this.endRange = value;
    }

    /**
     * Gets the value of the exchangeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeID() {
        return exchangeID;
    }

    /**
     * Sets the value of the exchangeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeID(String value) {
        this.exchangeID = value;
    }

    /**
     * Gets the value of the bbgGroupID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBbgGroupID() {
        return bbgGroupID;
    }

    /**
     * Sets the value of the bbgGroupID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBbgGroupID(String value) {
        this.bbgGroupID = value;
    }

    /**
     * Gets the value of the imsDtlID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImsDtlID() {
        return imsDtlID;
    }

    /**
     * Sets the value of the imsDtlID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImsDtlID(String value) {
        this.imsDtlID = value;
    }

    /**
     * Gets the value of the imsDtlSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImsDtlSeq() {
        return imsDtlSeq;
    }

    /**
     * Sets the value of the imsDtlSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImsDtlSeq(String value) {
        this.imsDtlSeq = value;
    }

    /**
     * Gets the value of the imsOcID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImsOcID() {
        return imsOcID;
    }

    /**
     * Sets the value of the imsOcID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImsOcID(String value) {
        this.imsOcID = value;
    }

    /**
     * Gets the value of the retainSrvId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetainSrvId() {
        return retainSrvId;
    }

    /**
     * Sets the value of the retainSrvId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetainSrvId(String value) {
        this.retainSrvId = value;
    }

}

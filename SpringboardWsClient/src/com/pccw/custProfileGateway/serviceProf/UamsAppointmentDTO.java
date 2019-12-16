
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UamsAppointmentDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UamsAppointmentDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="AftInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertFieldCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlertFieldRemark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AreaCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CusterNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CxsOrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DelayReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DistrictCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExchID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ForcedDelayInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromApptEndDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromApptStartDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromApptType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromVisitInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Grid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OcID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OcProducts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProdType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SbiInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SerialNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Srd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tariff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TidInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToApptEndDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToApptStartDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToApptType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToVisitInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CpdInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerInsistedInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TentativeInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VisitReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDtlID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResultCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResultMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TranID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InstantFallbackInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SmsIndex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SmsLanguage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SmsMobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UamsAppointmentDTO", propOrder = {
    "aftInd",
    "alertFieldCode",
    "alertFieldRemark",
    "areaCode",
    "custerNum",
    "cxsOrderId",
    "delayReasonCode",
    "districtCode",
    "exchID",
    "forcedDelayInd",
    "fromApptEndDT",
    "fromApptStartDT",
    "fromApptType",
    "fromVisitInd",
    "grid",
    "ocID",
    "ocProducts",
    "orderStatus",
    "orderTypeCode",
    "prodType",
    "sbiInd",
    "serialNum",
    "srd",
    "srvNum",
    "srvType",
    "tariff",
    "tidInd",
    "toApptEndDT",
    "toApptStartDT",
    "toApptType",
    "toVisitInd",
    "userID",
    "serviceLevel",
    "cpdInd",
    "customerInsistedInd",
    "tentativeInd",
    "visitReasonCode",
    "orderDtlID",
    "resultCd",
    "resultMsg",
    "addressID",
    "tranID",
    "instantFallbackInd",
    "smsIndex",
    "smsLanguage",
    "smsMobile",
    "docType"
})
public class UamsAppointmentDTO
    extends ValueObject
{

    @XmlElement(name = "AftInd")
    protected String aftInd;
    @XmlElement(name = "AlertFieldCode")
    protected String alertFieldCode;
    @XmlElement(name = "AlertFieldRemark")
    protected String alertFieldRemark;
    @XmlElement(name = "AreaCode")
    protected String areaCode;
    @XmlElement(name = "CusterNum")
    protected String custerNum;
    @XmlElement(name = "CxsOrderId")
    protected String cxsOrderId;
    @XmlElement(name = "DelayReasonCode")
    protected String delayReasonCode;
    @XmlElement(name = "DistrictCode")
    protected String districtCode;
    @XmlElement(name = "ExchID")
    protected String exchID;
    @XmlElement(name = "ForcedDelayInd")
    protected String forcedDelayInd;
    @XmlElement(name = "FromApptEndDT")
    protected String fromApptEndDT;
    @XmlElement(name = "FromApptStartDT")
    protected String fromApptStartDT;
    @XmlElement(name = "FromApptType")
    protected String fromApptType;
    @XmlElement(name = "FromVisitInd")
    protected String fromVisitInd;
    @XmlElement(name = "Grid")
    protected String grid;
    @XmlElement(name = "OcID")
    protected String ocID;
    @XmlElement(name = "OcProducts")
    protected String ocProducts;
    @XmlElement(name = "OrderStatus")
    protected String orderStatus;
    @XmlElement(name = "OrderTypeCode")
    protected String orderTypeCode;
    @XmlElement(name = "ProdType")
    protected String prodType;
    @XmlElement(name = "SbiInd")
    protected String sbiInd;
    @XmlElement(name = "SerialNum")
    protected String serialNum;
    @XmlElement(name = "Srd")
    protected String srd;
    @XmlElement(name = "SrvNum")
    protected String srvNum;
    @XmlElement(name = "SrvType")
    protected String srvType;
    @XmlElement(name = "Tariff")
    protected String tariff;
    @XmlElement(name = "TidInd")
    protected String tidInd;
    @XmlElement(name = "ToApptEndDT")
    protected String toApptEndDT;
    @XmlElement(name = "ToApptStartDT")
    protected String toApptStartDT;
    @XmlElement(name = "ToApptType")
    protected String toApptType;
    @XmlElement(name = "ToVisitInd")
    protected String toVisitInd;
    @XmlElement(name = "UserID")
    protected String userID;
    @XmlElement(name = "ServiceLevel")
    protected String serviceLevel;
    @XmlElement(name = "CpdInd")
    protected String cpdInd;
    @XmlElement(name = "CustomerInsistedInd")
    protected String customerInsistedInd;
    @XmlElement(name = "TentativeInd")
    protected String tentativeInd;
    @XmlElement(name = "VisitReasonCode")
    protected String visitReasonCode;
    @XmlElement(name = "OrderDtlID")
    protected String orderDtlID;
    @XmlElement(name = "ResultCd")
    protected String resultCd;
    @XmlElement(name = "ResultMsg")
    protected String resultMsg;
    @XmlElement(name = "AddressID")
    protected String addressID;
    @XmlElement(name = "TranID")
    protected String tranID;
    @XmlElement(name = "InstantFallbackInd")
    protected String instantFallbackInd;
    @XmlElement(name = "SmsIndex")
    protected String smsIndex;
    @XmlElement(name = "SmsLanguage")
    protected String smsLanguage;
    @XmlElement(name = "SmsMobile")
    protected String smsMobile;
    @XmlElement(name = "DocType")
    protected String docType;

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
     * Gets the value of the alertFieldCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertFieldCode() {
        return alertFieldCode;
    }

    /**
     * Sets the value of the alertFieldCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertFieldCode(String value) {
        this.alertFieldCode = value;
    }

    /**
     * Gets the value of the alertFieldRemark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertFieldRemark() {
        return alertFieldRemark;
    }

    /**
     * Sets the value of the alertFieldRemark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertFieldRemark(String value) {
        this.alertFieldRemark = value;
    }

    /**
     * Gets the value of the areaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets the value of the areaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaCode(String value) {
        this.areaCode = value;
    }

    /**
     * Gets the value of the custerNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCusterNum() {
        return custerNum;
    }

    /**
     * Sets the value of the custerNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCusterNum(String value) {
        this.custerNum = value;
    }

    /**
     * Gets the value of the cxsOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCxsOrderId() {
        return cxsOrderId;
    }

    /**
     * Sets the value of the cxsOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCxsOrderId(String value) {
        this.cxsOrderId = value;
    }

    /**
     * Gets the value of the delayReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelayReasonCode() {
        return delayReasonCode;
    }

    /**
     * Sets the value of the delayReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelayReasonCode(String value) {
        this.delayReasonCode = value;
    }

    /**
     * Gets the value of the districtCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * Sets the value of the districtCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictCode(String value) {
        this.districtCode = value;
    }

    /**
     * Gets the value of the exchID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchID() {
        return exchID;
    }

    /**
     * Sets the value of the exchID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchID(String value) {
        this.exchID = value;
    }

    /**
     * Gets the value of the forcedDelayInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForcedDelayInd() {
        return forcedDelayInd;
    }

    /**
     * Sets the value of the forcedDelayInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForcedDelayInd(String value) {
        this.forcedDelayInd = value;
    }

    /**
     * Gets the value of the fromApptEndDT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromApptEndDT() {
        return fromApptEndDT;
    }

    /**
     * Sets the value of the fromApptEndDT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromApptEndDT(String value) {
        this.fromApptEndDT = value;
    }

    /**
     * Gets the value of the fromApptStartDT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromApptStartDT() {
        return fromApptStartDT;
    }

    /**
     * Sets the value of the fromApptStartDT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromApptStartDT(String value) {
        this.fromApptStartDT = value;
    }

    /**
     * Gets the value of the fromApptType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromApptType() {
        return fromApptType;
    }

    /**
     * Sets the value of the fromApptType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromApptType(String value) {
        this.fromApptType = value;
    }

    /**
     * Gets the value of the fromVisitInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromVisitInd() {
        return fromVisitInd;
    }

    /**
     * Sets the value of the fromVisitInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromVisitInd(String value) {
        this.fromVisitInd = value;
    }

    /**
     * Gets the value of the grid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrid() {
        return grid;
    }

    /**
     * Sets the value of the grid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrid(String value) {
        this.grid = value;
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
     * Gets the value of the ocProducts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOcProducts() {
        return ocProducts;
    }

    /**
     * Sets the value of the ocProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOcProducts(String value) {
        this.ocProducts = value;
    }

    /**
     * Gets the value of the orderStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the value of the orderStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderStatus(String value) {
        this.orderStatus = value;
    }

    /**
     * Gets the value of the orderTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderTypeCode() {
        return orderTypeCode;
    }

    /**
     * Sets the value of the orderTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderTypeCode(String value) {
        this.orderTypeCode = value;
    }

    /**
     * Gets the value of the prodType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdType() {
        return prodType;
    }

    /**
     * Sets the value of the prodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdType(String value) {
        this.prodType = value;
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
     * Gets the value of the serialNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * Sets the value of the serialNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNum(String value) {
        this.serialNum = value;
    }

    /**
     * Gets the value of the srd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrd() {
        return srd;
    }

    /**
     * Sets the value of the srd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrd(String value) {
        this.srd = value;
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
     * Gets the value of the srvType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvType() {
        return srvType;
    }

    /**
     * Sets the value of the srvType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvType(String value) {
        this.srvType = value;
    }

    /**
     * Gets the value of the tariff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTariff() {
        return tariff;
    }

    /**
     * Sets the value of the tariff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTariff(String value) {
        this.tariff = value;
    }

    /**
     * Gets the value of the tidInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTidInd() {
        return tidInd;
    }

    /**
     * Sets the value of the tidInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTidInd(String value) {
        this.tidInd = value;
    }

    /**
     * Gets the value of the toApptEndDT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToApptEndDT() {
        return toApptEndDT;
    }

    /**
     * Sets the value of the toApptEndDT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToApptEndDT(String value) {
        this.toApptEndDT = value;
    }

    /**
     * Gets the value of the toApptStartDT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToApptStartDT() {
        return toApptStartDT;
    }

    /**
     * Sets the value of the toApptStartDT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToApptStartDT(String value) {
        this.toApptStartDT = value;
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

    /**
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Gets the value of the serviceLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceLevel() {
        return serviceLevel;
    }

    /**
     * Sets the value of the serviceLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceLevel(String value) {
        this.serviceLevel = value;
    }

    /**
     * Gets the value of the cpdInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpdInd() {
        return cpdInd;
    }

    /**
     * Sets the value of the cpdInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpdInd(String value) {
        this.cpdInd = value;
    }

    /**
     * Gets the value of the customerInsistedInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerInsistedInd() {
        return customerInsistedInd;
    }

    /**
     * Sets the value of the customerInsistedInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerInsistedInd(String value) {
        this.customerInsistedInd = value;
    }

    /**
     * Gets the value of the tentativeInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTentativeInd() {
        return tentativeInd;
    }

    /**
     * Sets the value of the tentativeInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTentativeInd(String value) {
        this.tentativeInd = value;
    }

    /**
     * Gets the value of the visitReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisitReasonCode() {
        return visitReasonCode;
    }

    /**
     * Sets the value of the visitReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisitReasonCode(String value) {
        this.visitReasonCode = value;
    }

    /**
     * Gets the value of the orderDtlID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDtlID() {
        return orderDtlID;
    }

    /**
     * Sets the value of the orderDtlID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDtlID(String value) {
        this.orderDtlID = value;
    }

    /**
     * Gets the value of the resultCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultCd() {
        return resultCd;
    }

    /**
     * Sets the value of the resultCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultCd(String value) {
        this.resultCd = value;
    }

    /**
     * Gets the value of the resultMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultMsg() {
        return resultMsg;
    }

    /**
     * Sets the value of the resultMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultMsg(String value) {
        this.resultMsg = value;
    }

    /**
     * Gets the value of the addressID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressID() {
        return addressID;
    }

    /**
     * Sets the value of the addressID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressID(String value) {
        this.addressID = value;
    }

    /**
     * Gets the value of the tranID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranID() {
        return tranID;
    }

    /**
     * Sets the value of the tranID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranID(String value) {
        this.tranID = value;
    }

    /**
     * Gets the value of the instantFallbackInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstantFallbackInd() {
        return instantFallbackInd;
    }

    /**
     * Sets the value of the instantFallbackInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstantFallbackInd(String value) {
        this.instantFallbackInd = value;
    }

    /**
     * Gets the value of the smsIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsIndex() {
        return smsIndex;
    }

    /**
     * Sets the value of the smsIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsIndex(String value) {
        this.smsIndex = value;
    }

    /**
     * Gets the value of the smsLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsLanguage() {
        return smsLanguage;
    }

    /**
     * Sets the value of the smsLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsLanguage(String value) {
        this.smsLanguage = value;
    }

    /**
     * Gets the value of the smsMobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsMobile() {
        return smsMobile;
    }

    /**
     * Sets the value of the smsMobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsMobile(String value) {
        this.smsMobile = value;
    }

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
    }

}

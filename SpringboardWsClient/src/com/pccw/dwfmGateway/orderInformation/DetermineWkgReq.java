
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetermineWkgReq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetermineWkgReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cxsparm" type="{http://www.openuri.org/}CommonCxSparm" minOccurs="0"/>
 *         &lt;element name="AppCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApplDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillAdjustInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CallCardInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CitnetAgentCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClsOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrpId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrpType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InOutBoundInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LineCls" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobileNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NoOfCaPrs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrjCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalemanRefId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesStaff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesStaffInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SocInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpSrvCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvNum1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvNum2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TelNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetermineWkgReq", propOrder = {
    "cxsparm",
    "appCode",
    "applDate",
    "billAdjustInd",
    "callCardInd",
    "citnetAgentCd",
    "clsOfSrv",
    "datCd",
    "dueDate",
    "endInd",
    "grpId",
    "grpType",
    "inOutBoundInd",
    "lineCls",
    "mobileNum",
    "noOfCaPrs",
    "ordType",
    "prjCd",
    "salemanRefId",
    "salesStaff",
    "salesStaffInd",
    "socInd",
    "spSrvCode",
    "srvNum1",
    "srvNum2",
    "telNum",
    "typeOfSrv"
})
public class DetermineWkgReq {

    @XmlElement(name = "Cxsparm")
    protected CommonCxSparm cxsparm;
    @XmlElement(name = "AppCode")
    protected String appCode;
    @XmlElement(name = "ApplDate")
    protected String applDate;
    @XmlElement(name = "BillAdjustInd")
    protected String billAdjustInd;
    @XmlElement(name = "CallCardInd")
    protected String callCardInd;
    @XmlElement(name = "CitnetAgentCd")
    protected String citnetAgentCd;
    @XmlElement(name = "ClsOfSrv")
    protected String clsOfSrv;
    @XmlElement(name = "DatCd")
    protected String datCd;
    @XmlElement(name = "DueDate")
    protected String dueDate;
    @XmlElement(name = "EndInd")
    protected String endInd;
    @XmlElement(name = "GrpId")
    protected String grpId;
    @XmlElement(name = "GrpType")
    protected String grpType;
    @XmlElement(name = "InOutBoundInd")
    protected String inOutBoundInd;
    @XmlElement(name = "LineCls")
    protected String lineCls;
    @XmlElement(name = "MobileNum")
    protected String mobileNum;
    @XmlElement(name = "NoOfCaPrs")
    protected String noOfCaPrs;
    @XmlElement(name = "OrdType")
    protected String ordType;
    @XmlElement(name = "PrjCd")
    protected String prjCd;
    @XmlElement(name = "SalemanRefId")
    protected String salemanRefId;
    @XmlElement(name = "SalesStaff")
    protected String salesStaff;
    @XmlElement(name = "SalesStaffInd")
    protected String salesStaffInd;
    @XmlElement(name = "SocInd")
    protected String socInd;
    @XmlElement(name = "SpSrvCode")
    protected String spSrvCode;
    @XmlElement(name = "SrvNum1")
    protected String srvNum1;
    @XmlElement(name = "SrvNum2")
    protected String srvNum2;
    @XmlElement(name = "TelNum")
    protected String telNum;
    @XmlElement(name = "TypeOfSrv")
    protected String typeOfSrv;

    /**
     * Gets the value of the cxsparm property.
     * 
     * @return
     *     possible object is
     *     {@link CommonCxSparm }
     *     
     */
    public CommonCxSparm getCxsparm() {
        return cxsparm;
    }

    /**
     * Sets the value of the cxsparm property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonCxSparm }
     *     
     */
    public void setCxsparm(CommonCxSparm value) {
        this.cxsparm = value;
    }

    /**
     * Gets the value of the appCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * Sets the value of the appCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppCode(String value) {
        this.appCode = value;
    }

    /**
     * Gets the value of the applDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplDate() {
        return applDate;
    }

    /**
     * Sets the value of the applDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplDate(String value) {
        this.applDate = value;
    }

    /**
     * Gets the value of the billAdjustInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillAdjustInd() {
        return billAdjustInd;
    }

    /**
     * Sets the value of the billAdjustInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillAdjustInd(String value) {
        this.billAdjustInd = value;
    }

    /**
     * Gets the value of the callCardInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallCardInd() {
        return callCardInd;
    }

    /**
     * Sets the value of the callCardInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallCardInd(String value) {
        this.callCardInd = value;
    }

    /**
     * Gets the value of the citnetAgentCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitnetAgentCd() {
        return citnetAgentCd;
    }

    /**
     * Sets the value of the citnetAgentCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitnetAgentCd(String value) {
        this.citnetAgentCd = value;
    }

    /**
     * Gets the value of the clsOfSrv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClsOfSrv() {
        return clsOfSrv;
    }

    /**
     * Sets the value of the clsOfSrv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClsOfSrv(String value) {
        this.clsOfSrv = value;
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
     * Gets the value of the dueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the value of the dueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDueDate(String value) {
        this.dueDate = value;
    }

    /**
     * Gets the value of the endInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndInd() {
        return endInd;
    }

    /**
     * Sets the value of the endInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndInd(String value) {
        this.endInd = value;
    }

    /**
     * Gets the value of the grpId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrpId() {
        return grpId;
    }

    /**
     * Sets the value of the grpId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrpId(String value) {
        this.grpId = value;
    }

    /**
     * Gets the value of the grpType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrpType() {
        return grpType;
    }

    /**
     * Sets the value of the grpType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrpType(String value) {
        this.grpType = value;
    }

    /**
     * Gets the value of the inOutBoundInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInOutBoundInd() {
        return inOutBoundInd;
    }

    /**
     * Sets the value of the inOutBoundInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInOutBoundInd(String value) {
        this.inOutBoundInd = value;
    }

    /**
     * Gets the value of the lineCls property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineCls() {
        return lineCls;
    }

    /**
     * Sets the value of the lineCls property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineCls(String value) {
        this.lineCls = value;
    }

    /**
     * Gets the value of the mobileNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileNum() {
        return mobileNum;
    }

    /**
     * Sets the value of the mobileNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileNum(String value) {
        this.mobileNum = value;
    }

    /**
     * Gets the value of the noOfCaPrs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoOfCaPrs() {
        return noOfCaPrs;
    }

    /**
     * Sets the value of the noOfCaPrs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoOfCaPrs(String value) {
        this.noOfCaPrs = value;
    }

    /**
     * Gets the value of the ordType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdType() {
        return ordType;
    }

    /**
     * Sets the value of the ordType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdType(String value) {
        this.ordType = value;
    }

    /**
     * Gets the value of the prjCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrjCd() {
        return prjCd;
    }

    /**
     * Sets the value of the prjCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrjCd(String value) {
        this.prjCd = value;
    }

    /**
     * Gets the value of the salemanRefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalemanRefId() {
        return salemanRefId;
    }

    /**
     * Sets the value of the salemanRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalemanRefId(String value) {
        this.salemanRefId = value;
    }

    /**
     * Gets the value of the salesStaff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesStaff() {
        return salesStaff;
    }

    /**
     * Sets the value of the salesStaff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesStaff(String value) {
        this.salesStaff = value;
    }

    /**
     * Gets the value of the salesStaffInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesStaffInd() {
        return salesStaffInd;
    }

    /**
     * Sets the value of the salesStaffInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesStaffInd(String value) {
        this.salesStaffInd = value;
    }

    /**
     * Gets the value of the socInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocInd() {
        return socInd;
    }

    /**
     * Sets the value of the socInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocInd(String value) {
        this.socInd = value;
    }

    /**
     * Gets the value of the spSrvCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpSrvCode() {
        return spSrvCode;
    }

    /**
     * Sets the value of the spSrvCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpSrvCode(String value) {
        this.spSrvCode = value;
    }

    /**
     * Gets the value of the srvNum1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvNum1() {
        return srvNum1;
    }

    /**
     * Sets the value of the srvNum1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvNum1(String value) {
        this.srvNum1 = value;
    }

    /**
     * Gets the value of the srvNum2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvNum2() {
        return srvNum2;
    }

    /**
     * Sets the value of the srvNum2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvNum2(String value) {
        this.srvNum2 = value;
    }

    /**
     * Gets the value of the telNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelNum() {
        return telNum;
    }

    /**
     * Sets the value of the telNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelNum(String value) {
        this.telNum = value;
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

}

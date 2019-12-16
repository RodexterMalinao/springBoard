
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AddressDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="Acctnb" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="AddrChi1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrChi2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrChi3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AddrIDStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrLine3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrLine4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrLine5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrLine6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddrUsage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AffectAccount" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="AffectCust" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="AffectFSA" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="AreaCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AreaDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuildNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custnb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="District" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DistrictNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FloorNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ForeignAddrFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Hit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HlLotNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LdLotNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewAssignEquipment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PoBoxNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PostalRegion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Section" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SectionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Serialno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Street" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StreetCatCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StreetCatCodeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StreetName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StreetNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Subsrlno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UnitNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttnBillName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Province" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecBillName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PbLastUpdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PbLastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PbSecLastUpdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PbSecLastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fsa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillingAcctDtl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvBdryNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SbiInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AftInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SysId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TerminateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShopCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftContactName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GiftContactNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WeeklyReportIND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsExistPendingAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modify" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressDTO", propOrder = {
    "acctnb",
    "addrChi1",
    "addrChi2",
    "addrChi3",
    "addrID",
    "addrIDStr",
    "addrLine1",
    "addrLine2",
    "addrLine3",
    "addrLine4",
    "addrLine5",
    "addrLine6",
    "addrType",
    "addrUsage",
    "affectAccount",
    "affectCust",
    "affectFSA",
    "areaCode",
    "areaDesc",
    "buildNum",
    "custnb",
    "district",
    "districtNum",
    "floorNum",
    "foreignAddrFlag",
    "hit",
    "hlLotNum",
    "ldLotNum",
    "newAssignEquipment",
    "poBoxNum",
    "postalCode",
    "postalRegion",
    "section",
    "sectionCode",
    "serialno",
    "street",
    "streetCatCode",
    "streetCatCodeDesc",
    "streetName",
    "streetNum",
    "subsrlno",
    "unitNum",
    "attnBillName",
    "city",
    "country",
    "province",
    "secBillName",
    "pbLastUpdDate",
    "pbLastUpdBy",
    "pbSecLastUpdDate",
    "pbSecLastUpdBy",
    "state",
    "fsa",
    "billingAcctDtl",
    "srvId",
    "srvNum",
    "srvBdryNum",
    "sbiInd",
    "aftInd",
    "lastUpdBy",
    "sysId",
    "effDate",
    "terminateDate",
    "shopCode",
    "giftContactName",
    "giftContactNumber",
    "weeklyReportIND",
    "isExistPendingAddr",
    "modify"
})
public class AddressDTO
    extends ValueObject
{

    @XmlElement(name = "Acctnb")
    protected long acctnb;
    @XmlElement(name = "AddrChi1")
    protected String addrChi1;
    @XmlElement(name = "AddrChi2")
    protected String addrChi2;
    @XmlElement(name = "AddrChi3")
    protected String addrChi3;
    @XmlElement(name = "AddrID")
    protected int addrID;
    @XmlElement(name = "AddrIDStr")
    protected String addrIDStr;
    @XmlElement(name = "AddrLine1")
    protected String addrLine1;
    @XmlElement(name = "AddrLine2")
    protected String addrLine2;
    @XmlElement(name = "AddrLine3")
    protected String addrLine3;
    @XmlElement(name = "AddrLine4")
    protected String addrLine4;
    @XmlElement(name = "AddrLine5")
    protected String addrLine5;
    @XmlElement(name = "AddrLine6")
    protected String addrLine6;
    @XmlElement(name = "AddrType")
    protected String addrType;
    @XmlElement(name = "AddrUsage")
    protected String addrUsage;
    @XmlElement(name = "AffectAccount")
    protected ArrayOfString affectAccount;
    @XmlElement(name = "AffectCust")
    protected ArrayOfString affectCust;
    @XmlElement(name = "AffectFSA")
    protected ArrayOfString affectFSA;
    @XmlElement(name = "AreaCode")
    protected String areaCode;
    @XmlElement(name = "AreaDesc")
    protected String areaDesc;
    @XmlElement(name = "BuildNum")
    protected String buildNum;
    @XmlElement(name = "Custnb")
    protected String custnb;
    @XmlElement(name = "District")
    protected String district;
    @XmlElement(name = "DistrictNum")
    protected String districtNum;
    @XmlElement(name = "FloorNum")
    protected String floorNum;
    @XmlElement(name = "ForeignAddrFlag")
    protected String foreignAddrFlag;
    @XmlElement(name = "Hit")
    protected String hit;
    @XmlElement(name = "HlLotNum")
    protected String hlLotNum;
    @XmlElement(name = "LdLotNum")
    protected String ldLotNum;
    @XmlElement(name = "NewAssignEquipment")
    protected String newAssignEquipment;
    @XmlElement(name = "PoBoxNum")
    protected String poBoxNum;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "PostalRegion")
    protected String postalRegion;
    @XmlElement(name = "Section")
    protected String section;
    @XmlElement(name = "SectionCode")
    protected String sectionCode;
    @XmlElement(name = "Serialno")
    protected String serialno;
    @XmlElement(name = "Street")
    protected String street;
    @XmlElement(name = "StreetCatCode")
    protected String streetCatCode;
    @XmlElement(name = "StreetCatCodeDesc")
    protected String streetCatCodeDesc;
    @XmlElement(name = "StreetName")
    protected String streetName;
    @XmlElement(name = "StreetNum")
    protected String streetNum;
    @XmlElement(name = "Subsrlno")
    protected int subsrlno;
    @XmlElement(name = "UnitNum")
    protected String unitNum;
    @XmlElement(name = "AttnBillName")
    protected String attnBillName;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "Province")
    protected String province;
    @XmlElement(name = "SecBillName")
    protected String secBillName;
    @XmlElement(name = "PbLastUpdDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar pbLastUpdDate;
    @XmlElement(name = "PbLastUpdBy")
    protected String pbLastUpdBy;
    @XmlElement(name = "PbSecLastUpdDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar pbSecLastUpdDate;
    @XmlElement(name = "PbSecLastUpdBy")
    protected String pbSecLastUpdBy;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "Fsa")
    protected String fsa;
    @XmlElement(name = "BillingAcctDtl")
    protected String billingAcctDtl;
    @XmlElement(name = "SrvId")
    protected String srvId;
    @XmlElement(name = "SrvNum")
    protected String srvNum;
    @XmlElement(name = "SrvBdryNum")
    protected String srvBdryNum;
    @XmlElement(name = "SbiInd")
    protected String sbiInd;
    @XmlElement(name = "AftInd")
    protected String aftInd;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "SysId")
    protected String sysId;
    @XmlElement(name = "EffDate")
    protected String effDate;
    @XmlElement(name = "TerminateDate")
    protected String terminateDate;
    @XmlElement(name = "ShopCode")
    protected String shopCode;
    @XmlElement(name = "GiftContactName")
    protected String giftContactName;
    @XmlElement(name = "GiftContactNumber")
    protected String giftContactNumber;
    @XmlElement(name = "WeeklyReportIND")
    protected String weeklyReportIND;
    @XmlElement(name = "IsExistPendingAddr")
    protected String isExistPendingAddr;
    protected boolean modify;

    /**
     * Gets the value of the acctnb property.
     * 
     */
    public long getAcctnb() {
        return acctnb;
    }

    /**
     * Sets the value of the acctnb property.
     * 
     */
    public void setAcctnb(long value) {
        this.acctnb = value;
    }

    /**
     * Gets the value of the addrChi1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrChi1() {
        return addrChi1;
    }

    /**
     * Sets the value of the addrChi1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrChi1(String value) {
        this.addrChi1 = value;
    }

    /**
     * Gets the value of the addrChi2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrChi2() {
        return addrChi2;
    }

    /**
     * Sets the value of the addrChi2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrChi2(String value) {
        this.addrChi2 = value;
    }

    /**
     * Gets the value of the addrChi3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrChi3() {
        return addrChi3;
    }

    /**
     * Sets the value of the addrChi3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrChi3(String value) {
        this.addrChi3 = value;
    }

    /**
     * Gets the value of the addrID property.
     * 
     */
    public int getAddrID() {
        return addrID;
    }

    /**
     * Sets the value of the addrID property.
     * 
     */
    public void setAddrID(int value) {
        this.addrID = value;
    }

    /**
     * Gets the value of the addrIDStr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrIDStr() {
        return addrIDStr;
    }

    /**
     * Sets the value of the addrIDStr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrIDStr(String value) {
        this.addrIDStr = value;
    }

    /**
     * Gets the value of the addrLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrLine1() {
        return addrLine1;
    }

    /**
     * Sets the value of the addrLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrLine1(String value) {
        this.addrLine1 = value;
    }

    /**
     * Gets the value of the addrLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrLine2() {
        return addrLine2;
    }

    /**
     * Sets the value of the addrLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrLine2(String value) {
        this.addrLine2 = value;
    }

    /**
     * Gets the value of the addrLine3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrLine3() {
        return addrLine3;
    }

    /**
     * Sets the value of the addrLine3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrLine3(String value) {
        this.addrLine3 = value;
    }

    /**
     * Gets the value of the addrLine4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrLine4() {
        return addrLine4;
    }

    /**
     * Sets the value of the addrLine4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrLine4(String value) {
        this.addrLine4 = value;
    }

    /**
     * Gets the value of the addrLine5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrLine5() {
        return addrLine5;
    }

    /**
     * Sets the value of the addrLine5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrLine5(String value) {
        this.addrLine5 = value;
    }

    /**
     * Gets the value of the addrLine6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrLine6() {
        return addrLine6;
    }

    /**
     * Sets the value of the addrLine6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrLine6(String value) {
        this.addrLine6 = value;
    }

    /**
     * Gets the value of the addrType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrType() {
        return addrType;
    }

    /**
     * Sets the value of the addrType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrType(String value) {
        this.addrType = value;
    }

    /**
     * Gets the value of the addrUsage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrUsage() {
        return addrUsage;
    }

    /**
     * Sets the value of the addrUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrUsage(String value) {
        this.addrUsage = value;
    }

    /**
     * Gets the value of the affectAccount property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getAffectAccount() {
        return affectAccount;
    }

    /**
     * Sets the value of the affectAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setAffectAccount(ArrayOfString value) {
        this.affectAccount = value;
    }

    /**
     * Gets the value of the affectCust property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getAffectCust() {
        return affectCust;
    }

    /**
     * Sets the value of the affectCust property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setAffectCust(ArrayOfString value) {
        this.affectCust = value;
    }

    /**
     * Gets the value of the affectFSA property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getAffectFSA() {
        return affectFSA;
    }

    /**
     * Sets the value of the affectFSA property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setAffectFSA(ArrayOfString value) {
        this.affectFSA = value;
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
     * Gets the value of the areaDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaDesc() {
        return areaDesc;
    }

    /**
     * Sets the value of the areaDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaDesc(String value) {
        this.areaDesc = value;
    }

    /**
     * Gets the value of the buildNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildNum() {
        return buildNum;
    }

    /**
     * Sets the value of the buildNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildNum(String value) {
        this.buildNum = value;
    }

    /**
     * Gets the value of the custnb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustnb() {
        return custnb;
    }

    /**
     * Sets the value of the custnb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustnb(String value) {
        this.custnb = value;
    }

    /**
     * Gets the value of the district property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the value of the district property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrict(String value) {
        this.district = value;
    }

    /**
     * Gets the value of the districtNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictNum() {
        return districtNum;
    }

    /**
     * Sets the value of the districtNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictNum(String value) {
        this.districtNum = value;
    }

    /**
     * Gets the value of the floorNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloorNum() {
        return floorNum;
    }

    /**
     * Sets the value of the floorNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloorNum(String value) {
        this.floorNum = value;
    }

    /**
     * Gets the value of the foreignAddrFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignAddrFlag() {
        return foreignAddrFlag;
    }

    /**
     * Sets the value of the foreignAddrFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignAddrFlag(String value) {
        this.foreignAddrFlag = value;
    }

    /**
     * Gets the value of the hit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHit() {
        return hit;
    }

    /**
     * Sets the value of the hit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHit(String value) {
        this.hit = value;
    }

    /**
     * Gets the value of the hlLotNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHlLotNum() {
        return hlLotNum;
    }

    /**
     * Sets the value of the hlLotNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHlLotNum(String value) {
        this.hlLotNum = value;
    }

    /**
     * Gets the value of the ldLotNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLdLotNum() {
        return ldLotNum;
    }

    /**
     * Sets the value of the ldLotNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLdLotNum(String value) {
        this.ldLotNum = value;
    }

    /**
     * Gets the value of the newAssignEquipment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewAssignEquipment() {
        return newAssignEquipment;
    }

    /**
     * Sets the value of the newAssignEquipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewAssignEquipment(String value) {
        this.newAssignEquipment = value;
    }

    /**
     * Gets the value of the poBoxNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoBoxNum() {
        return poBoxNum;
    }

    /**
     * Sets the value of the poBoxNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoBoxNum(String value) {
        this.poBoxNum = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the postalRegion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalRegion() {
        return postalRegion;
    }

    /**
     * Sets the value of the postalRegion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalRegion(String value) {
        this.postalRegion = value;
    }

    /**
     * Gets the value of the section property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSection() {
        return section;
    }

    /**
     * Sets the value of the section property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSection(String value) {
        this.section = value;
    }

    /**
     * Gets the value of the sectionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * Sets the value of the sectionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectionCode(String value) {
        this.sectionCode = value;
    }

    /**
     * Gets the value of the serialno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialno() {
        return serialno;
    }

    /**
     * Sets the value of the serialno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialno(String value) {
        this.serialno = value;
    }

    /**
     * Gets the value of the street property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreet(String value) {
        this.street = value;
    }

    /**
     * Gets the value of the streetCatCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetCatCode() {
        return streetCatCode;
    }

    /**
     * Sets the value of the streetCatCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetCatCode(String value) {
        this.streetCatCode = value;
    }

    /**
     * Gets the value of the streetCatCodeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetCatCodeDesc() {
        return streetCatCodeDesc;
    }

    /**
     * Sets the value of the streetCatCodeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetCatCodeDesc(String value) {
        this.streetCatCodeDesc = value;
    }

    /**
     * Gets the value of the streetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the value of the streetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetName(String value) {
        this.streetName = value;
    }

    /**
     * Gets the value of the streetNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetNum() {
        return streetNum;
    }

    /**
     * Sets the value of the streetNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetNum(String value) {
        this.streetNum = value;
    }

    /**
     * Gets the value of the subsrlno property.
     * 
     */
    public int getSubsrlno() {
        return subsrlno;
    }

    /**
     * Sets the value of the subsrlno property.
     * 
     */
    public void setSubsrlno(int value) {
        this.subsrlno = value;
    }

    /**
     * Gets the value of the unitNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitNum() {
        return unitNum;
    }

    /**
     * Sets the value of the unitNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitNum(String value) {
        this.unitNum = value;
    }

    /**
     * Gets the value of the attnBillName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttnBillName() {
        return attnBillName;
    }

    /**
     * Sets the value of the attnBillName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttnBillName(String value) {
        this.attnBillName = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the province property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the value of the province property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvince(String value) {
        this.province = value;
    }

    /**
     * Gets the value of the secBillName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecBillName() {
        return secBillName;
    }

    /**
     * Sets the value of the secBillName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecBillName(String value) {
        this.secBillName = value;
    }

    /**
     * Gets the value of the pbLastUpdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPbLastUpdDate() {
        return pbLastUpdDate;
    }

    /**
     * Sets the value of the pbLastUpdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPbLastUpdDate(XMLGregorianCalendar value) {
        this.pbLastUpdDate = value;
    }

    /**
     * Gets the value of the pbLastUpdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPbLastUpdBy() {
        return pbLastUpdBy;
    }

    /**
     * Sets the value of the pbLastUpdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPbLastUpdBy(String value) {
        this.pbLastUpdBy = value;
    }

    /**
     * Gets the value of the pbSecLastUpdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPbSecLastUpdDate() {
        return pbSecLastUpdDate;
    }

    /**
     * Sets the value of the pbSecLastUpdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPbSecLastUpdDate(XMLGregorianCalendar value) {
        this.pbSecLastUpdDate = value;
    }

    /**
     * Gets the value of the pbSecLastUpdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPbSecLastUpdBy() {
        return pbSecLastUpdBy;
    }

    /**
     * Sets the value of the pbSecLastUpdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPbSecLastUpdBy(String value) {
        this.pbSecLastUpdBy = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the fsa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFsa() {
        return fsa;
    }

    /**
     * Sets the value of the fsa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFsa(String value) {
        this.fsa = value;
    }

    /**
     * Gets the value of the billingAcctDtl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingAcctDtl() {
        return billingAcctDtl;
    }

    /**
     * Sets the value of the billingAcctDtl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingAcctDtl(String value) {
        this.billingAcctDtl = value;
    }

    /**
     * Gets the value of the srvId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvId() {
        return srvId;
    }

    /**
     * Sets the value of the srvId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvId(String value) {
        this.srvId = value;
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
     * Gets the value of the srvBdryNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvBdryNum() {
        return srvBdryNum;
    }

    /**
     * Sets the value of the srvBdryNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvBdryNum(String value) {
        this.srvBdryNum = value;
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
     * Gets the value of the lastUpdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdBy() {
        return lastUpdBy;
    }

    /**
     * Sets the value of the lastUpdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdBy(String value) {
        this.lastUpdBy = value;
    }

    /**
     * Gets the value of the sysId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysId() {
        return sysId;
    }

    /**
     * Sets the value of the sysId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysId(String value) {
        this.sysId = value;
    }

    /**
     * Gets the value of the effDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffDate() {
        return effDate;
    }

    /**
     * Sets the value of the effDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDate(String value) {
        this.effDate = value;
    }

    /**
     * Gets the value of the terminateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminateDate() {
        return terminateDate;
    }

    /**
     * Sets the value of the terminateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminateDate(String value) {
        this.terminateDate = value;
    }

    /**
     * Gets the value of the shopCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * Sets the value of the shopCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShopCode(String value) {
        this.shopCode = value;
    }

    /**
     * Gets the value of the giftContactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiftContactName() {
        return giftContactName;
    }

    /**
     * Sets the value of the giftContactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiftContactName(String value) {
        this.giftContactName = value;
    }

    /**
     * Gets the value of the giftContactNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiftContactNumber() {
        return giftContactNumber;
    }

    /**
     * Sets the value of the giftContactNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiftContactNumber(String value) {
        this.giftContactNumber = value;
    }

    /**
     * Gets the value of the weeklyReportIND property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeeklyReportIND() {
        return weeklyReportIND;
    }

    /**
     * Sets the value of the weeklyReportIND property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeeklyReportIND(String value) {
        this.weeklyReportIND = value;
    }

    /**
     * Gets the value of the isExistPendingAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsExistPendingAddr() {
        return isExistPendingAddr;
    }

    /**
     * Sets the value of the isExistPendingAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsExistPendingAddr(String value) {
        this.isExistPendingAddr = value;
    }

    /**
     * Gets the value of the modify property.
     * 
     */
    public boolean isModify() {
        return modify;
    }

    /**
     * Sets the value of the modify property.
     * 
     */
    public void setModify(boolean value) {
        this.modify = value;
    }

}


package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AcctNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressLine3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressLine4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DueTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistBbgExchng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistBbgNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistMbgNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistPilotNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromDueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromDueTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromPermit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromReaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromVisitInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FsaNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewAddressLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewAddressLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewAddressLine3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewAddressLine4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewBbgExchng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewBbgNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewDatCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewMbgNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewPilotNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ocid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderStsCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelBsnNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelLtwGp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelOrdId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelOrdSt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelOrdTc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceLevelCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubCatCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToPermit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VisitInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Yy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformationReturn", propOrder = {
    "acctNum",
    "addressLine1",
    "addressLine2",
    "addressLine3",
    "addressLine4",
    "datCd",
    "dueDate",
    "dueTime",
    "existBbgExchng",
    "existBbgNum",
    "existMbgNum",
    "existPilotNum",
    "fromDueDate",
    "fromDueTime",
    "fromPermit",
    "fromReaCd",
    "fromVisitInd",
    "fsaNum",
    "issDate",
    "issTime",
    "newAddressLine1",
    "newAddressLine2",
    "newAddressLine3",
    "newAddressLine4",
    "newBbgExchng",
    "newBbgNum",
    "newDatCd",
    "newMbgNum",
    "newPilotNum",
    "ocid",
    "orderStsCd",
    "orderTypeCd",
    "reaCd",
    "relBsnNum",
    "relLtwGp",
    "relOrdId",
    "relOrdSt",
    "relOrdTc",
    "serviceLevelCd",
    "subCatCd",
    "subName",
    "subSeqNum",
    "toPermit",
    "typeOfSrv",
    "visitInd",
    "yy",
    "orderId"
})
public class OrderInformationReturn {

    @XmlElement(name = "AcctNum")
    protected String acctNum;
    @XmlElement(name = "AddressLine1")
    protected String addressLine1;
    @XmlElement(name = "AddressLine2")
    protected String addressLine2;
    @XmlElement(name = "AddressLine3")
    protected String addressLine3;
    @XmlElement(name = "AddressLine4")
    protected String addressLine4;
    @XmlElement(name = "DatCd")
    protected String datCd;
    @XmlElement(name = "DueDate")
    protected String dueDate;
    @XmlElement(name = "DueTime")
    protected String dueTime;
    @XmlElement(name = "ExistBbgExchng")
    protected String existBbgExchng;
    @XmlElement(name = "ExistBbgNum")
    protected String existBbgNum;
    @XmlElement(name = "ExistMbgNum")
    protected String existMbgNum;
    @XmlElement(name = "ExistPilotNum")
    protected String existPilotNum;
    @XmlElement(name = "FromDueDate")
    protected String fromDueDate;
    @XmlElement(name = "FromDueTime")
    protected String fromDueTime;
    @XmlElement(name = "FromPermit")
    protected String fromPermit;
    @XmlElement(name = "FromReaCd")
    protected String fromReaCd;
    @XmlElement(name = "FromVisitInd")
    protected String fromVisitInd;
    @XmlElement(name = "FsaNum")
    protected String fsaNum;
    @XmlElement(name = "IssDate")
    protected String issDate;
    @XmlElement(name = "IssTime")
    protected String issTime;
    @XmlElement(name = "NewAddressLine1")
    protected String newAddressLine1;
    @XmlElement(name = "NewAddressLine2")
    protected String newAddressLine2;
    @XmlElement(name = "NewAddressLine3")
    protected String newAddressLine3;
    @XmlElement(name = "NewAddressLine4")
    protected String newAddressLine4;
    @XmlElement(name = "NewBbgExchng")
    protected String newBbgExchng;
    @XmlElement(name = "NewBbgNum")
    protected String newBbgNum;
    @XmlElement(name = "NewDatCd")
    protected String newDatCd;
    @XmlElement(name = "NewMbgNum")
    protected String newMbgNum;
    @XmlElement(name = "NewPilotNum")
    protected String newPilotNum;
    @XmlElement(name = "Ocid")
    protected String ocid;
    @XmlElement(name = "OrderStsCd")
    protected String orderStsCd;
    @XmlElement(name = "OrderTypeCd")
    protected String orderTypeCd;
    @XmlElement(name = "ReaCd")
    protected String reaCd;
    @XmlElement(name = "RelBsnNum")
    protected String relBsnNum;
    @XmlElement(name = "RelLtwGp")
    protected String relLtwGp;
    @XmlElement(name = "RelOrdId")
    protected String relOrdId;
    @XmlElement(name = "RelOrdSt")
    protected String relOrdSt;
    @XmlElement(name = "RelOrdTc")
    protected String relOrdTc;
    @XmlElement(name = "ServiceLevelCd")
    protected String serviceLevelCd;
    @XmlElement(name = "SubCatCd")
    protected String subCatCd;
    @XmlElement(name = "SubName")
    protected String subName;
    @XmlElement(name = "SubSeqNum")
    protected String subSeqNum;
    @XmlElement(name = "ToPermit")
    protected String toPermit;
    @XmlElement(name = "TypeOfSrv")
    protected String typeOfSrv;
    @XmlElement(name = "VisitInd")
    protected String visitInd;
    @XmlElement(name = "Yy")
    protected String yy;
    @XmlElement(name = "OrderId")
    protected String orderId;

    /**
     * Gets the value of the acctNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctNum() {
        return acctNum;
    }

    /**
     * Sets the value of the acctNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctNum(String value) {
        this.acctNum = value;
    }

    /**
     * Gets the value of the addressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine1(String value) {
        this.addressLine1 = value;
    }

    /**
     * Gets the value of the addressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the value of the addressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine2(String value) {
        this.addressLine2 = value;
    }

    /**
     * Gets the value of the addressLine3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * Sets the value of the addressLine3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine3(String value) {
        this.addressLine3 = value;
    }

    /**
     * Gets the value of the addressLine4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine4() {
        return addressLine4;
    }

    /**
     * Sets the value of the addressLine4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine4(String value) {
        this.addressLine4 = value;
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
     * Gets the value of the dueTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDueTime() {
        return dueTime;
    }

    /**
     * Sets the value of the dueTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDueTime(String value) {
        this.dueTime = value;
    }

    /**
     * Gets the value of the existBbgExchng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistBbgExchng() {
        return existBbgExchng;
    }

    /**
     * Sets the value of the existBbgExchng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistBbgExchng(String value) {
        this.existBbgExchng = value;
    }

    /**
     * Gets the value of the existBbgNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistBbgNum() {
        return existBbgNum;
    }

    /**
     * Sets the value of the existBbgNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistBbgNum(String value) {
        this.existBbgNum = value;
    }

    /**
     * Gets the value of the existMbgNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistMbgNum() {
        return existMbgNum;
    }

    /**
     * Sets the value of the existMbgNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistMbgNum(String value) {
        this.existMbgNum = value;
    }

    /**
     * Gets the value of the existPilotNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistPilotNum() {
        return existPilotNum;
    }

    /**
     * Sets the value of the existPilotNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistPilotNum(String value) {
        this.existPilotNum = value;
    }

    /**
     * Gets the value of the fromDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromDueDate() {
        return fromDueDate;
    }

    /**
     * Sets the value of the fromDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromDueDate(String value) {
        this.fromDueDate = value;
    }

    /**
     * Gets the value of the fromDueTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromDueTime() {
        return fromDueTime;
    }

    /**
     * Sets the value of the fromDueTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromDueTime(String value) {
        this.fromDueTime = value;
    }

    /**
     * Gets the value of the fromPermit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromPermit() {
        return fromPermit;
    }

    /**
     * Sets the value of the fromPermit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromPermit(String value) {
        this.fromPermit = value;
    }

    /**
     * Gets the value of the fromReaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromReaCd() {
        return fromReaCd;
    }

    /**
     * Sets the value of the fromReaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromReaCd(String value) {
        this.fromReaCd = value;
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
     * Gets the value of the fsaNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFsaNum() {
        return fsaNum;
    }

    /**
     * Sets the value of the fsaNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFsaNum(String value) {
        this.fsaNum = value;
    }

    /**
     * Gets the value of the issDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssDate() {
        return issDate;
    }

    /**
     * Sets the value of the issDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssDate(String value) {
        this.issDate = value;
    }

    /**
     * Gets the value of the issTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssTime() {
        return issTime;
    }

    /**
     * Sets the value of the issTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssTime(String value) {
        this.issTime = value;
    }

    /**
     * Gets the value of the newAddressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewAddressLine1() {
        return newAddressLine1;
    }

    /**
     * Sets the value of the newAddressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewAddressLine1(String value) {
        this.newAddressLine1 = value;
    }

    /**
     * Gets the value of the newAddressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewAddressLine2() {
        return newAddressLine2;
    }

    /**
     * Sets the value of the newAddressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewAddressLine2(String value) {
        this.newAddressLine2 = value;
    }

    /**
     * Gets the value of the newAddressLine3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewAddressLine3() {
        return newAddressLine3;
    }

    /**
     * Sets the value of the newAddressLine3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewAddressLine3(String value) {
        this.newAddressLine3 = value;
    }

    /**
     * Gets the value of the newAddressLine4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewAddressLine4() {
        return newAddressLine4;
    }

    /**
     * Sets the value of the newAddressLine4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewAddressLine4(String value) {
        this.newAddressLine4 = value;
    }

    /**
     * Gets the value of the newBbgExchng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewBbgExchng() {
        return newBbgExchng;
    }

    /**
     * Sets the value of the newBbgExchng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewBbgExchng(String value) {
        this.newBbgExchng = value;
    }

    /**
     * Gets the value of the newBbgNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewBbgNum() {
        return newBbgNum;
    }

    /**
     * Sets the value of the newBbgNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewBbgNum(String value) {
        this.newBbgNum = value;
    }

    /**
     * Gets the value of the newDatCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDatCd() {
        return newDatCd;
    }

    /**
     * Sets the value of the newDatCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDatCd(String value) {
        this.newDatCd = value;
    }

    /**
     * Gets the value of the newMbgNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewMbgNum() {
        return newMbgNum;
    }

    /**
     * Sets the value of the newMbgNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewMbgNum(String value) {
        this.newMbgNum = value;
    }

    /**
     * Gets the value of the newPilotNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPilotNum() {
        return newPilotNum;
    }

    /**
     * Sets the value of the newPilotNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPilotNum(String value) {
        this.newPilotNum = value;
    }

    /**
     * Gets the value of the ocid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOcid() {
        return ocid;
    }

    /**
     * Sets the value of the ocid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOcid(String value) {
        this.ocid = value;
    }

    /**
     * Gets the value of the orderStsCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderStsCd() {
        return orderStsCd;
    }

    /**
     * Sets the value of the orderStsCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderStsCd(String value) {
        this.orderStsCd = value;
    }

    /**
     * Gets the value of the orderTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderTypeCd() {
        return orderTypeCd;
    }

    /**
     * Sets the value of the orderTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderTypeCd(String value) {
        this.orderTypeCd = value;
    }

    /**
     * Gets the value of the reaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReaCd() {
        return reaCd;
    }

    /**
     * Sets the value of the reaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReaCd(String value) {
        this.reaCd = value;
    }

    /**
     * Gets the value of the relBsnNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelBsnNum() {
        return relBsnNum;
    }

    /**
     * Sets the value of the relBsnNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelBsnNum(String value) {
        this.relBsnNum = value;
    }

    /**
     * Gets the value of the relLtwGp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelLtwGp() {
        return relLtwGp;
    }

    /**
     * Sets the value of the relLtwGp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelLtwGp(String value) {
        this.relLtwGp = value;
    }

    /**
     * Gets the value of the relOrdId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelOrdId() {
        return relOrdId;
    }

    /**
     * Sets the value of the relOrdId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelOrdId(String value) {
        this.relOrdId = value;
    }

    /**
     * Gets the value of the relOrdSt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelOrdSt() {
        return relOrdSt;
    }

    /**
     * Sets the value of the relOrdSt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelOrdSt(String value) {
        this.relOrdSt = value;
    }

    /**
     * Gets the value of the relOrdTc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelOrdTc() {
        return relOrdTc;
    }

    /**
     * Sets the value of the relOrdTc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelOrdTc(String value) {
        this.relOrdTc = value;
    }

    /**
     * Gets the value of the serviceLevelCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceLevelCd() {
        return serviceLevelCd;
    }

    /**
     * Sets the value of the serviceLevelCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceLevelCd(String value) {
        this.serviceLevelCd = value;
    }

    /**
     * Gets the value of the subCatCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubCatCd() {
        return subCatCd;
    }

    /**
     * Sets the value of the subCatCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubCatCd(String value) {
        this.subCatCd = value;
    }

    /**
     * Gets the value of the subName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubName() {
        return subName;
    }

    /**
     * Sets the value of the subName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubName(String value) {
        this.subName = value;
    }

    /**
     * Gets the value of the subSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubSeqNum() {
        return subSeqNum;
    }

    /**
     * Sets the value of the subSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubSeqNum(String value) {
        this.subSeqNum = value;
    }

    /**
     * Gets the value of the toPermit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToPermit() {
        return toPermit;
    }

    /**
     * Sets the value of the toPermit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToPermit(String value) {
        this.toPermit = value;
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
     * Gets the value of the visitInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisitInd() {
        return visitInd;
    }

    /**
     * Sets the value of the visitInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisitInd(String value) {
        this.visitInd = value;
    }

    /**
     * Gets the value of the yy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYy() {
        return yy;
    }

    /**
     * Sets the value of the yy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYy(String value) {
        this.yy = value;
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

}

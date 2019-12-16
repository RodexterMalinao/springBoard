
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DNAssignmentOptionDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DNAssignmentOptionDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="ExtSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SelectedCustomer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SelectedHostExchangeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcnZoneNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcnDnRange" type="{http://www.openuri.org/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="CcnDPNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OcID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDtID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcnBuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcnRlu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsecutiveInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DnSelectionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LevelID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OoaInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestDN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestDNInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestDNQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ReservationAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReservationBoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReservationProject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecialSrvGrp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecialSrvName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TargetExchangeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReservationBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvReqID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestDN2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DNAssignmentOptionDTO", propOrder = {
    "extSrvNum",
    "selectedCustomer",
    "selectedHostExchangeID",
    "ccnZoneNum",
    "ccnDnRange",
    "ccnDPNum",
    "ocID",
    "orderDtID",
    "orderServiceID",
    "ccnBuildId",
    "ccnRlu",
    "consecutiveInd",
    "dnSelectionInd",
    "levelID",
    "ooaInd",
    "requestDN",
    "requestDNInd",
    "requestDNQty",
    "reservationAccount",
    "reservationBoc",
    "reservationProject",
    "specialSrvGrp",
    "specialSrvName",
    "targetExchangeID",
    "remarks",
    "reservationBy",
    "srvReqID",
    "requestDN2"
})
public class DNAssignmentOptionDTO
    extends ValueObject
{

    @XmlElement(name = "ExtSrvNum")
    protected String extSrvNum;
    @XmlElement(name = "SelectedCustomer")
    protected String selectedCustomer;
    @XmlElement(name = "SelectedHostExchangeID")
    protected String selectedHostExchangeID;
    @XmlElement(name = "CcnZoneNum")
    protected String ccnZoneNum;
    @XmlElement(name = "CcnDnRange")
    protected ArrayOfString ccnDnRange;
    @XmlElement(name = "CcnDPNum")
    protected String ccnDPNum;
    @XmlElement(name = "OcID")
    protected String ocID;
    @XmlElement(name = "OrderDtID")
    protected String orderDtID;
    @XmlElement(name = "OrderServiceID")
    protected String orderServiceID;
    @XmlElement(name = "CcnBuildId")
    protected String ccnBuildId;
    @XmlElement(name = "CcnRlu")
    protected String ccnRlu;
    @XmlElement(name = "ConsecutiveInd")
    protected String consecutiveInd;
    @XmlElement(name = "DnSelectionInd")
    protected String dnSelectionInd;
    @XmlElement(name = "LevelID")
    protected String levelID;
    @XmlElement(name = "OoaInd")
    protected String ooaInd;
    @XmlElement(name = "RequestDN")
    protected String requestDN;
    @XmlElement(name = "RequestDNInd")
    protected String requestDNInd;
    @XmlElement(name = "RequestDNQty")
    protected int requestDNQty;
    @XmlElement(name = "ReservationAccount")
    protected String reservationAccount;
    @XmlElement(name = "ReservationBoc")
    protected String reservationBoc;
    @XmlElement(name = "ReservationProject")
    protected String reservationProject;
    @XmlElement(name = "SpecialSrvGrp")
    protected String specialSrvGrp;
    @XmlElement(name = "SpecialSrvName")
    protected String specialSrvName;
    @XmlElement(name = "TargetExchangeID")
    protected String targetExchangeID;
    @XmlElement(name = "Remarks")
    protected String remarks;
    @XmlElement(name = "ReservationBy")
    protected String reservationBy;
    @XmlElement(name = "SrvReqID")
    protected String srvReqID;
    @XmlElement(name = "RequestDN2")
    protected String requestDN2;

    /**
     * Gets the value of the extSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtSrvNum() {
        return extSrvNum;
    }

    /**
     * Sets the value of the extSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtSrvNum(String value) {
        this.extSrvNum = value;
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
     * Gets the value of the ccnZoneNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcnZoneNum() {
        return ccnZoneNum;
    }

    /**
     * Sets the value of the ccnZoneNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcnZoneNum(String value) {
        this.ccnZoneNum = value;
    }

    /**
     * Gets the value of the ccnDnRange property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getCcnDnRange() {
        return ccnDnRange;
    }

    /**
     * Sets the value of the ccnDnRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setCcnDnRange(ArrayOfString value) {
        this.ccnDnRange = value;
    }

    /**
     * Gets the value of the ccnDPNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcnDPNum() {
        return ccnDPNum;
    }

    /**
     * Sets the value of the ccnDPNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcnDPNum(String value) {
        this.ccnDPNum = value;
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
     * Gets the value of the ccnBuildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcnBuildId() {
        return ccnBuildId;
    }

    /**
     * Sets the value of the ccnBuildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcnBuildId(String value) {
        this.ccnBuildId = value;
    }

    /**
     * Gets the value of the ccnRlu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcnRlu() {
        return ccnRlu;
    }

    /**
     * Sets the value of the ccnRlu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcnRlu(String value) {
        this.ccnRlu = value;
    }

    /**
     * Gets the value of the consecutiveInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsecutiveInd() {
        return consecutiveInd;
    }

    /**
     * Sets the value of the consecutiveInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsecutiveInd(String value) {
        this.consecutiveInd = value;
    }

    /**
     * Gets the value of the dnSelectionInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDnSelectionInd() {
        return dnSelectionInd;
    }

    /**
     * Sets the value of the dnSelectionInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDnSelectionInd(String value) {
        this.dnSelectionInd = value;
    }

    /**
     * Gets the value of the levelID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevelID() {
        return levelID;
    }

    /**
     * Sets the value of the levelID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevelID(String value) {
        this.levelID = value;
    }

    /**
     * Gets the value of the ooaInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOoaInd() {
        return ooaInd;
    }

    /**
     * Sets the value of the ooaInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOoaInd(String value) {
        this.ooaInd = value;
    }

    /**
     * Gets the value of the requestDN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestDN() {
        return requestDN;
    }

    /**
     * Sets the value of the requestDN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestDN(String value) {
        this.requestDN = value;
    }

    /**
     * Gets the value of the requestDNInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestDNInd() {
        return requestDNInd;
    }

    /**
     * Sets the value of the requestDNInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestDNInd(String value) {
        this.requestDNInd = value;
    }

    /**
     * Gets the value of the requestDNQty property.
     * 
     */
    public int getRequestDNQty() {
        return requestDNQty;
    }

    /**
     * Sets the value of the requestDNQty property.
     * 
     */
    public void setRequestDNQty(int value) {
        this.requestDNQty = value;
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
     * Gets the value of the specialSrvGrp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialSrvGrp() {
        return specialSrvGrp;
    }

    /**
     * Sets the value of the specialSrvGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialSrvGrp(String value) {
        this.specialSrvGrp = value;
    }

    /**
     * Gets the value of the specialSrvName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialSrvName() {
        return specialSrvName;
    }

    /**
     * Sets the value of the specialSrvName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialSrvName(String value) {
        this.specialSrvName = value;
    }

    /**
     * Gets the value of the targetExchangeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetExchangeID() {
        return targetExchangeID;
    }

    /**
     * Sets the value of the targetExchangeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetExchangeID(String value) {
        this.targetExchangeID = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * Gets the value of the reservationBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationBy() {
        return reservationBy;
    }

    /**
     * Sets the value of the reservationBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationBy(String value) {
        this.reservationBy = value;
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
     * Gets the value of the requestDN2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestDN2() {
        return requestDN2;
    }

    /**
     * Sets the value of the requestDN2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestDN2(String value) {
        this.requestDN2 = value;
    }

}

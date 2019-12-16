
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdDetailDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdDetailDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="UserID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DiscDayPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DiscFaxNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DiscNightPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DiscDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DiscReaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewDatCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyOrderNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TpDat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyOrderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyOrderStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrimaryLegacyOrderActivityStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderProducts" type="{http://www.openuri.org/}ArrayOfOrdOfferProductDTO" minOccurs="0"/>
 *         &lt;element name="OrderOfferAssignments" type="{http://www.openuri.org/}ArrayOfOrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OrderServiceAccounts" type="{http://www.openuri.org/}ArrayOfOrdServiceAccountDTO" minOccurs="0"/>
 *         &lt;element name="OrderServices" type="{http://www.openuri.org/}ArrayOfOrdServiceDTO" minOccurs="0"/>
 *         &lt;element name="ApplicationDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AppointmentDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CeaseRentalDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InstallationAddress" type="{http://www.openuri.org/}OrdAddressDTO" minOccurs="0"/>
 *         &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDtlID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceRequestDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfService" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InventoryReqd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToVisitReqd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromVisitReqd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesInformation" type="{http://www.openuri.org/}OrdSalesInfoDTO" minOccurs="0"/>
 *         &lt;element name="CustomerNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Domain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Appointment" type="{http://www.openuri.org/}UamsAppointmentDTO" minOccurs="0"/>
 *         &lt;element name="BomStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDetailGroup" type="{http://www.openuri.org/}OrdDetailGroupDTO" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdDetailDTO" minOccurs="0"/>
 *         &lt;element name="MaxOrderServiceID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustomerLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustomerSystemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDetailRemarks" type="{http://www.openuri.org/}ArrayOfOrdDetailRemarkDTO" minOccurs="0"/>
 *         &lt;element name="CcSeviceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TempLineInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TargetDisconnectDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrimaryOrderService" type="{http://www.openuri.org/}OrdServiceDTO" minOccurs="0"/>
 *         &lt;element name="DuplexGroupID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDepartments" type="{http://www.openuri.org/}ArrayOfOrdDeptDTO" minOccurs="0"/>
 *         &lt;element name="SecondaryOrderServices" type="{http://www.openuri.org/}ArrayOfOrdServiceDTO" minOccurs="0"/>
 *         &lt;element name="SslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AssignedStaffId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Boc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InstallExpansionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InitialDepositInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceRequestTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ManualBillInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApplicationTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PseudoOrdInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PenaltyHandleInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AgreementNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErChargeMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserSbIdInput" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdDetailDTO", propOrder = {
    "userID",
    "reasonCode",
    "discDayPhone",
    "discFaxNum",
    "discNightPhone",
    "discDate",
    "discReaCd",
    "newDatCode",
    "serviceID",
    "legacyOrderNum",
    "tpDat",
    "legacyOrderType",
    "legacyOrderStatus",
    "primaryLegacyOrderActivityStatus",
    "action",
    "orderProducts",
    "orderOfferAssignments",
    "orderServiceAccounts",
    "orderServices",
    "applicationDate",
    "appointmentDate",
    "ceaseRentalDate",
    "datCode",
    "installationAddress",
    "issueDate",
    "orderDtlID",
    "serviceRequestDate",
    "typeOfService",
    "lob",
    "inventoryReqd",
    "toVisitReqd",
    "fromVisitReqd",
    "salesInformation",
    "customerNum",
    "domain",
    "appointment",
    "bomStatus",
    "orderDetailGroup",
    "profileImage",
    "maxOrderServiceID",
    "customerLevel",
    "customerSystemID",
    "orderDetailRemarks",
    "ccSeviceID",
    "siteContact",
    "tempLineInd",
    "targetDisconnectDate",
    "primaryOrderService",
    "duplexGroupID",
    "orderDepartments",
    "secondaryOrderServices",
    "sslInd",
    "assignedStaffId",
    "boc",
    "installExpansionInd",
    "initialDepositInd",
    "serviceRequestTime",
    "manualBillInd",
    "applicationTime",
    "pseudoOrdInd",
    "penaltyHandleInd",
    "agreementNum",
    "erChargeMethod",
    "userSbIdInput"
})
public class OrdDetailDTO
    extends ValueObject
{

    @XmlElement(name = "UserID")
    protected String userID;
    @XmlElement(name = "ReasonCode")
    protected String reasonCode;
    @XmlElement(name = "DiscDayPhone")
    protected String discDayPhone;
    @XmlElement(name = "DiscFaxNum")
    protected String discFaxNum;
    @XmlElement(name = "DiscNightPhone")
    protected String discNightPhone;
    @XmlElement(name = "DiscDate")
    protected String discDate;
    @XmlElement(name = "DiscReaCd")
    protected String discReaCd;
    @XmlElement(name = "NewDatCode")
    protected String newDatCode;
    @XmlElement(name = "ServiceID")
    protected String serviceID;
    @XmlElement(name = "LegacyOrderNum")
    protected String legacyOrderNum;
    @XmlElement(name = "TpDat")
    protected String tpDat;
    @XmlElement(name = "LegacyOrderType")
    protected String legacyOrderType;
    @XmlElement(name = "LegacyOrderStatus")
    protected String legacyOrderStatus;
    @XmlElement(name = "PrimaryLegacyOrderActivityStatus")
    protected String primaryLegacyOrderActivityStatus;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderProducts")
    protected ArrayOfOrdOfferProductDTO orderProducts;
    @XmlElement(name = "OrderOfferAssignments")
    protected ArrayOfOrdOfferAssignmentDTO orderOfferAssignments;
    @XmlElement(name = "OrderServiceAccounts")
    protected ArrayOfOrdServiceAccountDTO orderServiceAccounts;
    @XmlElement(name = "OrderServices")
    protected ArrayOfOrdServiceDTO orderServices;
    @XmlElement(name = "ApplicationDate")
    protected String applicationDate;
    @XmlElement(name = "AppointmentDate")
    protected String appointmentDate;
    @XmlElement(name = "CeaseRentalDate")
    protected String ceaseRentalDate;
    @XmlElement(name = "DatCode")
    protected String datCode;
    @XmlElement(name = "InstallationAddress")
    protected OrdAddressDTO installationAddress;
    @XmlElement(name = "IssueDate")
    protected String issueDate;
    @XmlElement(name = "OrderDtlID")
    protected String orderDtlID;
    @XmlElement(name = "ServiceRequestDate")
    protected String serviceRequestDate;
    @XmlElement(name = "TypeOfService")
    protected String typeOfService;
    @XmlElement(name = "Lob")
    protected String lob;
    @XmlElement(name = "InventoryReqd")
    protected String inventoryReqd;
    @XmlElement(name = "ToVisitReqd")
    protected String toVisitReqd;
    @XmlElement(name = "FromVisitReqd")
    protected String fromVisitReqd;
    @XmlElement(name = "SalesInformation")
    protected OrdSalesInfoDTO salesInformation;
    @XmlElement(name = "CustomerNum")
    protected String customerNum;
    @XmlElement(name = "Domain")
    protected String domain;
    @XmlElement(name = "Appointment")
    protected UamsAppointmentDTO appointment;
    @XmlElement(name = "BomStatus")
    protected String bomStatus;
    @XmlElement(name = "OrderDetailGroup")
    protected OrdDetailGroupDTO orderDetailGroup;
    @XmlElement(name = "ProfileImage")
    protected OrdDetailDTO profileImage;
    @XmlElement(name = "MaxOrderServiceID")
    protected int maxOrderServiceID;
    @XmlElement(name = "CustomerLevel")
    protected int customerLevel;
    @XmlElement(name = "CustomerSystemID")
    protected String customerSystemID;
    @XmlElement(name = "OrderDetailRemarks")
    protected ArrayOfOrdDetailRemarkDTO orderDetailRemarks;
    @XmlElement(name = "CcSeviceID")
    protected String ccSeviceID;
    @XmlElement(name = "SiteContact")
    protected String siteContact;
    @XmlElement(name = "TempLineInd")
    protected String tempLineInd;
    @XmlElement(name = "TargetDisconnectDate")
    protected String targetDisconnectDate;
    @XmlElement(name = "PrimaryOrderService")
    protected OrdServiceDTO primaryOrderService;
    @XmlElement(name = "DuplexGroupID")
    protected String duplexGroupID;
    @XmlElement(name = "OrderDepartments")
    protected ArrayOfOrdDeptDTO orderDepartments;
    @XmlElement(name = "SecondaryOrderServices")
    protected ArrayOfOrdServiceDTO secondaryOrderServices;
    @XmlElement(name = "SslInd")
    protected String sslInd;
    @XmlElement(name = "AssignedStaffId")
    protected String assignedStaffId;
    @XmlElement(name = "Boc")
    protected String boc;
    @XmlElement(name = "InstallExpansionInd")
    protected String installExpansionInd;
    @XmlElement(name = "InitialDepositInd")
    protected String initialDepositInd;
    @XmlElement(name = "ServiceRequestTime")
    protected String serviceRequestTime;
    @XmlElement(name = "ManualBillInd")
    protected String manualBillInd;
    @XmlElement(name = "ApplicationTime")
    protected String applicationTime;
    @XmlElement(name = "PseudoOrdInd")
    protected String pseudoOrdInd;
    @XmlElement(name = "PenaltyHandleInd")
    protected String penaltyHandleInd;
    @XmlElement(name = "AgreementNum")
    protected String agreementNum;
    @XmlElement(name = "ErChargeMethod")
    protected String erChargeMethod;
    @XmlElement(name = "UserSbIdInput")
    protected String userSbIdInput;

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
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the discDayPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscDayPhone() {
        return discDayPhone;
    }

    /**
     * Sets the value of the discDayPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscDayPhone(String value) {
        this.discDayPhone = value;
    }

    /**
     * Gets the value of the discFaxNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscFaxNum() {
        return discFaxNum;
    }

    /**
     * Sets the value of the discFaxNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscFaxNum(String value) {
        this.discFaxNum = value;
    }

    /**
     * Gets the value of the discNightPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscNightPhone() {
        return discNightPhone;
    }

    /**
     * Sets the value of the discNightPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscNightPhone(String value) {
        this.discNightPhone = value;
    }

    /**
     * Gets the value of the discDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscDate() {
        return discDate;
    }

    /**
     * Sets the value of the discDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscDate(String value) {
        this.discDate = value;
    }

    /**
     * Gets the value of the discReaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscReaCd() {
        return discReaCd;
    }

    /**
     * Sets the value of the discReaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscReaCd(String value) {
        this.discReaCd = value;
    }

    /**
     * Gets the value of the newDatCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDatCode() {
        return newDatCode;
    }

    /**
     * Sets the value of the newDatCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDatCode(String value) {
        this.newDatCode = value;
    }

    /**
     * Gets the value of the serviceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceID() {
        return serviceID;
    }

    /**
     * Sets the value of the serviceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceID(String value) {
        this.serviceID = value;
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
     * Gets the value of the tpDat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTpDat() {
        return tpDat;
    }

    /**
     * Sets the value of the tpDat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTpDat(String value) {
        this.tpDat = value;
    }

    /**
     * Gets the value of the legacyOrderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyOrderType() {
        return legacyOrderType;
    }

    /**
     * Sets the value of the legacyOrderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyOrderType(String value) {
        this.legacyOrderType = value;
    }

    /**
     * Gets the value of the legacyOrderStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyOrderStatus() {
        return legacyOrderStatus;
    }

    /**
     * Sets the value of the legacyOrderStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyOrderStatus(String value) {
        this.legacyOrderStatus = value;
    }

    /**
     * Gets the value of the primaryLegacyOrderActivityStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryLegacyOrderActivityStatus() {
        return primaryLegacyOrderActivityStatus;
    }

    /**
     * Sets the value of the primaryLegacyOrderActivityStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryLegacyOrderActivityStatus(String value) {
        this.primaryLegacyOrderActivityStatus = value;
    }

    /**
     * Gets the value of the action property.
     * 
     */
    public int getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     */
    public void setAction(int value) {
        this.action = value;
    }

    /**
     * Gets the value of the orderProducts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdOfferProductDTO }
     *     
     */
    public ArrayOfOrdOfferProductDTO getOrderProducts() {
        return orderProducts;
    }

    /**
     * Sets the value of the orderProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdOfferProductDTO }
     *     
     */
    public void setOrderProducts(ArrayOfOrdOfferProductDTO value) {
        this.orderProducts = value;
    }

    /**
     * Gets the value of the orderOfferAssignments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdOfferAssignmentDTO }
     *     
     */
    public ArrayOfOrdOfferAssignmentDTO getOrderOfferAssignments() {
        return orderOfferAssignments;
    }

    /**
     * Sets the value of the orderOfferAssignments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdOfferAssignmentDTO }
     *     
     */
    public void setOrderOfferAssignments(ArrayOfOrdOfferAssignmentDTO value) {
        this.orderOfferAssignments = value;
    }

    /**
     * Gets the value of the orderServiceAccounts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdServiceAccountDTO }
     *     
     */
    public ArrayOfOrdServiceAccountDTO getOrderServiceAccounts() {
        return orderServiceAccounts;
    }

    /**
     * Sets the value of the orderServiceAccounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdServiceAccountDTO }
     *     
     */
    public void setOrderServiceAccounts(ArrayOfOrdServiceAccountDTO value) {
        this.orderServiceAccounts = value;
    }

    /**
     * Gets the value of the orderServices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdServiceDTO }
     *     
     */
    public ArrayOfOrdServiceDTO getOrderServices() {
        return orderServices;
    }

    /**
     * Sets the value of the orderServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdServiceDTO }
     *     
     */
    public void setOrderServices(ArrayOfOrdServiceDTO value) {
        this.orderServices = value;
    }

    /**
     * Gets the value of the applicationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets the value of the applicationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationDate(String value) {
        this.applicationDate = value;
    }

    /**
     * Gets the value of the appointmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the value of the appointmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppointmentDate(String value) {
        this.appointmentDate = value;
    }

    /**
     * Gets the value of the ceaseRentalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCeaseRentalDate() {
        return ceaseRentalDate;
    }

    /**
     * Sets the value of the ceaseRentalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCeaseRentalDate(String value) {
        this.ceaseRentalDate = value;
    }

    /**
     * Gets the value of the datCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatCode() {
        return datCode;
    }

    /**
     * Sets the value of the datCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatCode(String value) {
        this.datCode = value;
    }

    /**
     * Gets the value of the installationAddress property.
     * 
     * @return
     *     possible object is
     *     {@link OrdAddressDTO }
     *     
     */
    public OrdAddressDTO getInstallationAddress() {
        return installationAddress;
    }

    /**
     * Sets the value of the installationAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdAddressDTO }
     *     
     */
    public void setInstallationAddress(OrdAddressDTO value) {
        this.installationAddress = value;
    }

    /**
     * Gets the value of the issueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssueDate() {
        return issueDate;
    }

    /**
     * Sets the value of the issueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueDate(String value) {
        this.issueDate = value;
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
     * Gets the value of the serviceRequestDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceRequestDate() {
        return serviceRequestDate;
    }

    /**
     * Sets the value of the serviceRequestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceRequestDate(String value) {
        this.serviceRequestDate = value;
    }

    /**
     * Gets the value of the typeOfService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfService() {
        return typeOfService;
    }

    /**
     * Sets the value of the typeOfService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfService(String value) {
        this.typeOfService = value;
    }

    /**
     * Gets the value of the lob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLob() {
        return lob;
    }

    /**
     * Sets the value of the lob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLob(String value) {
        this.lob = value;
    }

    /**
     * Gets the value of the inventoryReqd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInventoryReqd() {
        return inventoryReqd;
    }

    /**
     * Sets the value of the inventoryReqd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInventoryReqd(String value) {
        this.inventoryReqd = value;
    }

    /**
     * Gets the value of the toVisitReqd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToVisitReqd() {
        return toVisitReqd;
    }

    /**
     * Sets the value of the toVisitReqd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToVisitReqd(String value) {
        this.toVisitReqd = value;
    }

    /**
     * Gets the value of the fromVisitReqd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromVisitReqd() {
        return fromVisitReqd;
    }

    /**
     * Sets the value of the fromVisitReqd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromVisitReqd(String value) {
        this.fromVisitReqd = value;
    }

    /**
     * Gets the value of the salesInformation property.
     * 
     * @return
     *     possible object is
     *     {@link OrdSalesInfoDTO }
     *     
     */
    public OrdSalesInfoDTO getSalesInformation() {
        return salesInformation;
    }

    /**
     * Sets the value of the salesInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdSalesInfoDTO }
     *     
     */
    public void setSalesInformation(OrdSalesInfoDTO value) {
        this.salesInformation = value;
    }

    /**
     * Gets the value of the customerNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerNum() {
        return customerNum;
    }

    /**
     * Sets the value of the customerNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerNum(String value) {
        this.customerNum = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

    /**
     * Gets the value of the appointment property.
     * 
     * @return
     *     possible object is
     *     {@link UamsAppointmentDTO }
     *     
     */
    public UamsAppointmentDTO getAppointment() {
        return appointment;
    }

    /**
     * Sets the value of the appointment property.
     * 
     * @param value
     *     allowed object is
     *     {@link UamsAppointmentDTO }
     *     
     */
    public void setAppointment(UamsAppointmentDTO value) {
        this.appointment = value;
    }

    /**
     * Gets the value of the bomStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBomStatus() {
        return bomStatus;
    }

    /**
     * Sets the value of the bomStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBomStatus(String value) {
        this.bomStatus = value;
    }

    /**
     * Gets the value of the orderDetailGroup property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public OrdDetailGroupDTO getOrderDetailGroup() {
        return orderDetailGroup;
    }

    /**
     * Sets the value of the orderDetailGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public void setOrderDetailGroup(OrdDetailGroupDTO value) {
        this.orderDetailGroup = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailDTO }
     *     
     */
    public OrdDetailDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailDTO }
     *     
     */
    public void setProfileImage(OrdDetailDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the maxOrderServiceID property.
     * 
     */
    public int getMaxOrderServiceID() {
        return maxOrderServiceID;
    }

    /**
     * Sets the value of the maxOrderServiceID property.
     * 
     */
    public void setMaxOrderServiceID(int value) {
        this.maxOrderServiceID = value;
    }

    /**
     * Gets the value of the customerLevel property.
     * 
     */
    public int getCustomerLevel() {
        return customerLevel;
    }

    /**
     * Sets the value of the customerLevel property.
     * 
     */
    public void setCustomerLevel(int value) {
        this.customerLevel = value;
    }

    /**
     * Gets the value of the customerSystemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerSystemID() {
        return customerSystemID;
    }

    /**
     * Sets the value of the customerSystemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerSystemID(String value) {
        this.customerSystemID = value;
    }

    /**
     * Gets the value of the orderDetailRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdDetailRemarkDTO }
     *     
     */
    public ArrayOfOrdDetailRemarkDTO getOrderDetailRemarks() {
        return orderDetailRemarks;
    }

    /**
     * Sets the value of the orderDetailRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdDetailRemarkDTO }
     *     
     */
    public void setOrderDetailRemarks(ArrayOfOrdDetailRemarkDTO value) {
        this.orderDetailRemarks = value;
    }

    /**
     * Gets the value of the ccSeviceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcSeviceID() {
        return ccSeviceID;
    }

    /**
     * Sets the value of the ccSeviceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcSeviceID(String value) {
        this.ccSeviceID = value;
    }

    /**
     * Gets the value of the siteContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteContact() {
        return siteContact;
    }

    /**
     * Sets the value of the siteContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteContact(String value) {
        this.siteContact = value;
    }

    /**
     * Gets the value of the tempLineInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTempLineInd() {
        return tempLineInd;
    }

    /**
     * Sets the value of the tempLineInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTempLineInd(String value) {
        this.tempLineInd = value;
    }

    /**
     * Gets the value of the targetDisconnectDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetDisconnectDate() {
        return targetDisconnectDate;
    }

    /**
     * Sets the value of the targetDisconnectDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetDisconnectDate(String value) {
        this.targetDisconnectDate = value;
    }

    /**
     * Gets the value of the primaryOrderService property.
     * 
     * @return
     *     possible object is
     *     {@link OrdServiceDTO }
     *     
     */
    public OrdServiceDTO getPrimaryOrderService() {
        return primaryOrderService;
    }

    /**
     * Sets the value of the primaryOrderService property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdServiceDTO }
     *     
     */
    public void setPrimaryOrderService(OrdServiceDTO value) {
        this.primaryOrderService = value;
    }

    /**
     * Gets the value of the duplexGroupID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplexGroupID() {
        return duplexGroupID;
    }

    /**
     * Sets the value of the duplexGroupID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplexGroupID(String value) {
        this.duplexGroupID = value;
    }

    /**
     * Gets the value of the orderDepartments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdDeptDTO }
     *     
     */
    public ArrayOfOrdDeptDTO getOrderDepartments() {
        return orderDepartments;
    }

    /**
     * Sets the value of the orderDepartments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdDeptDTO }
     *     
     */
    public void setOrderDepartments(ArrayOfOrdDeptDTO value) {
        this.orderDepartments = value;
    }

    /**
     * Gets the value of the secondaryOrderServices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdServiceDTO }
     *     
     */
    public ArrayOfOrdServiceDTO getSecondaryOrderServices() {
        return secondaryOrderServices;
    }

    /**
     * Sets the value of the secondaryOrderServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdServiceDTO }
     *     
     */
    public void setSecondaryOrderServices(ArrayOfOrdServiceDTO value) {
        this.secondaryOrderServices = value;
    }

    /**
     * Gets the value of the sslInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSslInd() {
        return sslInd;
    }

    /**
     * Sets the value of the sslInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSslInd(String value) {
        this.sslInd = value;
    }

    /**
     * Gets the value of the assignedStaffId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedStaffId() {
        return assignedStaffId;
    }

    /**
     * Sets the value of the assignedStaffId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedStaffId(String value) {
        this.assignedStaffId = value;
    }

    /**
     * Gets the value of the boc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoc() {
        return boc;
    }

    /**
     * Sets the value of the boc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoc(String value) {
        this.boc = value;
    }

    /**
     * Gets the value of the installExpansionInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstallExpansionInd() {
        return installExpansionInd;
    }

    /**
     * Sets the value of the installExpansionInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstallExpansionInd(String value) {
        this.installExpansionInd = value;
    }

    /**
     * Gets the value of the initialDepositInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitialDepositInd() {
        return initialDepositInd;
    }

    /**
     * Sets the value of the initialDepositInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitialDepositInd(String value) {
        this.initialDepositInd = value;
    }

    /**
     * Gets the value of the serviceRequestTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceRequestTime() {
        return serviceRequestTime;
    }

    /**
     * Sets the value of the serviceRequestTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceRequestTime(String value) {
        this.serviceRequestTime = value;
    }

    /**
     * Gets the value of the manualBillInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManualBillInd() {
        return manualBillInd;
    }

    /**
     * Sets the value of the manualBillInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManualBillInd(String value) {
        this.manualBillInd = value;
    }

    /**
     * Gets the value of the applicationTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationTime() {
        return applicationTime;
    }

    /**
     * Sets the value of the applicationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationTime(String value) {
        this.applicationTime = value;
    }

    /**
     * Gets the value of the pseudoOrdInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPseudoOrdInd() {
        return pseudoOrdInd;
    }

    /**
     * Sets the value of the pseudoOrdInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPseudoOrdInd(String value) {
        this.pseudoOrdInd = value;
    }

    /**
     * Gets the value of the penaltyHandleInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPenaltyHandleInd() {
        return penaltyHandleInd;
    }

    /**
     * Sets the value of the penaltyHandleInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPenaltyHandleInd(String value) {
        this.penaltyHandleInd = value;
    }

    /**
     * Gets the value of the agreementNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementNum() {
        return agreementNum;
    }

    /**
     * Sets the value of the agreementNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementNum(String value) {
        this.agreementNum = value;
    }

    /**
     * Gets the value of the erChargeMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErChargeMethod() {
        return erChargeMethod;
    }

    /**
     * Sets the value of the erChargeMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErChargeMethod(String value) {
        this.erChargeMethod = value;
    }

    /**
     * Gets the value of the userSbIdInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserSbIdInput() {
        return userSbIdInput;
    }

    /**
     * Sets the value of the userSbIdInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserSbIdInput(String value) {
        this.userSbIdInput = value;
    }

}

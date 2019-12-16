
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LegacyOrderActivityDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LegacyOrderActivityDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChgPPPOESessionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="L1SystemOnly" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AppointmentDateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FieldService" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyActivitySeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyOrder" type="{http://www.openuri.org/}LegacyOrderDTO" minOccurs="0"/>
 *         &lt;element name="LegacyOrderRemarks" type="{http://www.openuri.org/}ArrayOfRemarksDTO" minOccurs="0"/>
 *         &lt;element name="OrderService" type="{http://www.openuri.org/}OrdServiceDTO" minOccurs="0"/>
 *         &lt;element name="OrderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}LegacyOrderActivityDTO" minOccurs="0"/>
 *         &lt;element name="AddressInvestigateInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LegacyOrderActivityDTO", propOrder = {
    "status",
    "chgPPPOESessionInd",
    "l1SystemOnly",
    "appointmentDateTime",
    "fieldService",
    "legacyActivitySeqNum",
    "legacyOrder",
    "legacyOrderRemarks",
    "orderService",
    "orderType",
    "profileImage",
    "addressInvestigateInd"
})
public class LegacyOrderActivityDTO
    extends ValueObject
{

    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "ChgPPPOESessionInd")
    protected String chgPPPOESessionInd;
    @XmlElement(name = "L1SystemOnly")
    protected String l1SystemOnly;
    @XmlElement(name = "AppointmentDateTime")
    protected String appointmentDateTime;
    @XmlElement(name = "FieldService")
    protected String fieldService;
    @XmlElement(name = "LegacyActivitySeqNum")
    protected String legacyActivitySeqNum;
    @XmlElement(name = "LegacyOrder")
    protected LegacyOrderDTO legacyOrder;
    @XmlElement(name = "LegacyOrderRemarks")
    protected ArrayOfRemarksDTO legacyOrderRemarks;
    @XmlElement(name = "OrderService")
    protected OrdServiceDTO orderService;
    @XmlElement(name = "OrderType")
    protected String orderType;
    @XmlElement(name = "ProfileImage")
    protected LegacyOrderActivityDTO profileImage;
    @XmlElement(name = "AddressInvestigateInd")
    protected String addressInvestigateInd;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the chgPPPOESessionInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChgPPPOESessionInd() {
        return chgPPPOESessionInd;
    }

    /**
     * Sets the value of the chgPPPOESessionInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChgPPPOESessionInd(String value) {
        this.chgPPPOESessionInd = value;
    }

    /**
     * Gets the value of the l1SystemOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL1SystemOnly() {
        return l1SystemOnly;
    }

    /**
     * Sets the value of the l1SystemOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL1SystemOnly(String value) {
        this.l1SystemOnly = value;
    }

    /**
     * Gets the value of the appointmentDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    /**
     * Sets the value of the appointmentDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppointmentDateTime(String value) {
        this.appointmentDateTime = value;
    }

    /**
     * Gets the value of the fieldService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldService() {
        return fieldService;
    }

    /**
     * Sets the value of the fieldService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldService(String value) {
        this.fieldService = value;
    }

    /**
     * Gets the value of the legacyActivitySeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyActivitySeqNum() {
        return legacyActivitySeqNum;
    }

    /**
     * Sets the value of the legacyActivitySeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyActivitySeqNum(String value) {
        this.legacyActivitySeqNum = value;
    }

    /**
     * Gets the value of the legacyOrder property.
     * 
     * @return
     *     possible object is
     *     {@link LegacyOrderDTO }
     *     
     */
    public LegacyOrderDTO getLegacyOrder() {
        return legacyOrder;
    }

    /**
     * Sets the value of the legacyOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegacyOrderDTO }
     *     
     */
    public void setLegacyOrder(LegacyOrderDTO value) {
        this.legacyOrder = value;
    }

    /**
     * Gets the value of the legacyOrderRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRemarksDTO }
     *     
     */
    public ArrayOfRemarksDTO getLegacyOrderRemarks() {
        return legacyOrderRemarks;
    }

    /**
     * Sets the value of the legacyOrderRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRemarksDTO }
     *     
     */
    public void setLegacyOrderRemarks(ArrayOfRemarksDTO value) {
        this.legacyOrderRemarks = value;
    }

    /**
     * Gets the value of the orderService property.
     * 
     * @return
     *     possible object is
     *     {@link OrdServiceDTO }
     *     
     */
    public OrdServiceDTO getOrderService() {
        return orderService;
    }

    /**
     * Sets the value of the orderService property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdServiceDTO }
     *     
     */
    public void setOrderService(OrdServiceDTO value) {
        this.orderService = value;
    }

    /**
     * Gets the value of the orderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Sets the value of the orderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link LegacyOrderActivityDTO }
     *     
     */
    public LegacyOrderActivityDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegacyOrderActivityDTO }
     *     
     */
    public void setProfileImage(LegacyOrderActivityDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the addressInvestigateInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressInvestigateInd() {
        return addressInvestigateInd;
    }

    /**
     * Sets the value of the addressInvestigateInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressInvestigateInd(String value) {
        this.addressInvestigateInd = value;
    }

}

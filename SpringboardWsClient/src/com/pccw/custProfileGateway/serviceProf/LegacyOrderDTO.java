
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LegacyOrderDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LegacyOrderDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="LegacyOrderRemarks" type="{http://www.openuri.org/}ArrayOfRemarksDTO" minOccurs="0"/>
 *         &lt;element name="LegacyOrderActivityMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyOrderActivities" type="{http://www.openuri.org/}ArrayOfLegacyOrderActivityDTO" minOccurs="0"/>
 *         &lt;element name="LegacyOrderNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderService" type="{http://www.openuri.org/}OrdServiceDTO" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}LegacyOrderDTO" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UamsAppointment" type="{http://www.openuri.org/}UamsAppointmentDTO" minOccurs="0"/>
 *         &lt;element name="TrueOrderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LegacyOrderDTO", propOrder = {
    "legacyOrderRemarks",
    "legacyOrderActivityMap",
    "issueDate",
    "legacyOrderActivities",
    "legacyOrderNum",
    "orderService",
    "profileImage",
    "status",
    "requestID",
    "uamsAppointment",
    "trueOrderType"
})
public class LegacyOrderDTO
    extends ValueObject
{

    @XmlElement(name = "LegacyOrderRemarks")
    protected ArrayOfRemarksDTO legacyOrderRemarks;
    @XmlElement(name = "LegacyOrderActivityMap")
    protected Map legacyOrderActivityMap;
    @XmlElement(name = "IssueDate")
    protected String issueDate;
    @XmlElement(name = "LegacyOrderActivities")
    protected ArrayOfLegacyOrderActivityDTO legacyOrderActivities;
    @XmlElement(name = "LegacyOrderNum")
    protected String legacyOrderNum;
    @XmlElement(name = "OrderService")
    protected OrdServiceDTO orderService;
    @XmlElement(name = "ProfileImage")
    protected LegacyOrderDTO profileImage;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "RequestID")
    protected String requestID;
    @XmlElement(name = "UamsAppointment")
    protected UamsAppointmentDTO uamsAppointment;
    @XmlElement(name = "TrueOrderType")
    protected String trueOrderType;

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
     * Gets the value of the legacyOrderActivityMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getLegacyOrderActivityMap() {
        return legacyOrderActivityMap;
    }

    /**
     * Sets the value of the legacyOrderActivityMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setLegacyOrderActivityMap(Map value) {
        this.legacyOrderActivityMap = value;
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
     * Gets the value of the legacyOrderActivities property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLegacyOrderActivityDTO }
     *     
     */
    public ArrayOfLegacyOrderActivityDTO getLegacyOrderActivities() {
        return legacyOrderActivities;
    }

    /**
     * Sets the value of the legacyOrderActivities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLegacyOrderActivityDTO }
     *     
     */
    public void setLegacyOrderActivities(ArrayOfLegacyOrderActivityDTO value) {
        this.legacyOrderActivities = value;
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
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link LegacyOrderDTO }
     *     
     */
    public LegacyOrderDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegacyOrderDTO }
     *     
     */
    public void setProfileImage(LegacyOrderDTO value) {
        this.profileImage = value;
    }

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
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestID(String value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the uamsAppointment property.
     * 
     * @return
     *     possible object is
     *     {@link UamsAppointmentDTO }
     *     
     */
    public UamsAppointmentDTO getUamsAppointment() {
        return uamsAppointment;
    }

    /**
     * Sets the value of the uamsAppointment property.
     * 
     * @param value
     *     allowed object is
     *     {@link UamsAppointmentDTO }
     *     
     */
    public void setUamsAppointment(UamsAppointmentDTO value) {
        this.uamsAppointment = value;
    }

    /**
     * Gets the value of the trueOrderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrueOrderType() {
        return trueOrderType;
    }

    /**
     * Sets the value of the trueOrderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrueOrderType(String value) {
        this.trueOrderType = value;
    }

}


package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdServiceDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdServiceDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderDetail" type="{http://www.openuri.org/}OrdDetailDTO" minOccurs="0"/>
 *         &lt;element name="OrderServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceIDType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GenericFixFields" type="{http://www.openuri.org/}ArrayOfGenericFixFieldDTO" minOccurs="0"/>
 *         &lt;element name="LegacyOrderActivity" type="{http://www.openuri.org/}LegacyOrderActivityDTO" minOccurs="0"/>
 *         &lt;element name="OrderComponentMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrderComponents" type="{http://www.openuri.org/}ArrayOfOrdOfferProductComponentDTO" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdServiceDTO" minOccurs="0"/>
 *         &lt;element name="DeliveryAddressID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxServiceComponentSeq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ServiceRemarks" type="{http://www.openuri.org/}ArrayOfRemarksDTO" minOccurs="0"/>
 *         &lt;element name="CcServiceMemberNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistingServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "OrdServiceDTO", propOrder = {
    "action",
    "orderDetail",
    "orderServiceID",
    "serviceID",
    "serviceIDType",
    "genericFixFields",
    "legacyOrderActivity",
    "orderComponentMap",
    "orderComponents",
    "profileImage",
    "deliveryAddressID",
    "maxServiceComponentSeq",
    "serviceRemarks",
    "ccServiceMemberNum",
    "existingServiceID",
    "retainSrvId"
})
public class OrdServiceDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderDetail")
    protected OrdDetailDTO orderDetail;
    @XmlElement(name = "OrderServiceID")
    protected String orderServiceID;
    @XmlElement(name = "ServiceID")
    protected String serviceID;
    @XmlElement(name = "ServiceIDType")
    protected String serviceIDType;
    @XmlElement(name = "GenericFixFields")
    protected ArrayOfGenericFixFieldDTO genericFixFields;
    @XmlElement(name = "LegacyOrderActivity")
    protected LegacyOrderActivityDTO legacyOrderActivity;
    @XmlElement(name = "OrderComponentMap")
    protected Map orderComponentMap;
    @XmlElement(name = "OrderComponents")
    protected ArrayOfOrdOfferProductComponentDTO orderComponents;
    @XmlElement(name = "ProfileImage")
    protected OrdServiceDTO profileImage;
    @XmlElement(name = "DeliveryAddressID")
    protected String deliveryAddressID;
    @XmlElement(name = "MaxServiceComponentSeq")
    protected int maxServiceComponentSeq;
    @XmlElement(name = "ServiceRemarks")
    protected ArrayOfRemarksDTO serviceRemarks;
    @XmlElement(name = "CcServiceMemberNum")
    protected String ccServiceMemberNum;
    @XmlElement(name = "ExistingServiceID")
    protected String existingServiceID;
    @XmlElement(name = "RetainSrvId")
    protected String retainSrvId;

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
     * Gets the value of the orderDetail property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailDTO }
     *     
     */
    public OrdDetailDTO getOrderDetail() {
        return orderDetail;
    }

    /**
     * Sets the value of the orderDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailDTO }
     *     
     */
    public void setOrderDetail(OrdDetailDTO value) {
        this.orderDetail = value;
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
     * Gets the value of the serviceIDType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceIDType() {
        return serviceIDType;
    }

    /**
     * Sets the value of the serviceIDType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceIDType(String value) {
        this.serviceIDType = value;
    }

    /**
     * Gets the value of the genericFixFields property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGenericFixFieldDTO }
     *     
     */
    public ArrayOfGenericFixFieldDTO getGenericFixFields() {
        return genericFixFields;
    }

    /**
     * Sets the value of the genericFixFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGenericFixFieldDTO }
     *     
     */
    public void setGenericFixFields(ArrayOfGenericFixFieldDTO value) {
        this.genericFixFields = value;
    }

    /**
     * Gets the value of the legacyOrderActivity property.
     * 
     * @return
     *     possible object is
     *     {@link LegacyOrderActivityDTO }
     *     
     */
    public LegacyOrderActivityDTO getLegacyOrderActivity() {
        return legacyOrderActivity;
    }

    /**
     * Sets the value of the legacyOrderActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegacyOrderActivityDTO }
     *     
     */
    public void setLegacyOrderActivity(LegacyOrderActivityDTO value) {
        this.legacyOrderActivity = value;
    }

    /**
     * Gets the value of the orderComponentMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderComponentMap() {
        return orderComponentMap;
    }

    /**
     * Sets the value of the orderComponentMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderComponentMap(Map value) {
        this.orderComponentMap = value;
    }

    /**
     * Gets the value of the orderComponents property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdOfferProductComponentDTO }
     *     
     */
    public ArrayOfOrdOfferProductComponentDTO getOrderComponents() {
        return orderComponents;
    }

    /**
     * Sets the value of the orderComponents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdOfferProductComponentDTO }
     *     
     */
    public void setOrderComponents(ArrayOfOrdOfferProductComponentDTO value) {
        this.orderComponents = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdServiceDTO }
     *     
     */
    public OrdServiceDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdServiceDTO }
     *     
     */
    public void setProfileImage(OrdServiceDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the deliveryAddressID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryAddressID() {
        return deliveryAddressID;
    }

    /**
     * Sets the value of the deliveryAddressID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryAddressID(String value) {
        this.deliveryAddressID = value;
    }

    /**
     * Gets the value of the maxServiceComponentSeq property.
     * 
     */
    public int getMaxServiceComponentSeq() {
        return maxServiceComponentSeq;
    }

    /**
     * Sets the value of the maxServiceComponentSeq property.
     * 
     */
    public void setMaxServiceComponentSeq(int value) {
        this.maxServiceComponentSeq = value;
    }

    /**
     * Gets the value of the serviceRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRemarksDTO }
     *     
     */
    public ArrayOfRemarksDTO getServiceRemarks() {
        return serviceRemarks;
    }

    /**
     * Sets the value of the serviceRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRemarksDTO }
     *     
     */
    public void setServiceRemarks(ArrayOfRemarksDTO value) {
        this.serviceRemarks = value;
    }

    /**
     * Gets the value of the ccServiceMemberNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcServiceMemberNum() {
        return ccServiceMemberNum;
    }

    /**
     * Sets the value of the ccServiceMemberNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcServiceMemberNum(String value) {
        this.ccServiceMemberNum = value;
    }

    /**
     * Gets the value of the existingServiceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistingServiceID() {
        return existingServiceID;
    }

    /**
     * Sets the value of the existingServiceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistingServiceID(String value) {
        this.existingServiceID = value;
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

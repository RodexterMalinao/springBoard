
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdDetailGroupMemberDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdDetailGroupMemberDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderDetail" type="{http://www.openuri.org/}OrdDetailDTO" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdDetailGroupMemberDTO" minOccurs="0"/>
 *         &lt;element name="OrderDetailGroup" type="{http://www.openuri.org/}OrdDetailGroupDTO" minOccurs="0"/>
 *         &lt;element name="OrderDetailID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HuntSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdDetailGroupMemberDTO", propOrder = {
    "action",
    "orderDetail",
    "profileImage",
    "orderDetailGroup",
    "orderDetailID",
    "huntSequence"
})
public class OrdDetailGroupMemberDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderDetail")
    protected OrdDetailDTO orderDetail;
    @XmlElement(name = "ProfileImage")
    protected OrdDetailGroupMemberDTO profileImage;
    @XmlElement(name = "OrderDetailGroup")
    protected OrdDetailGroupDTO orderDetailGroup;
    @XmlElement(name = "OrderDetailID")
    protected String orderDetailID;
    @XmlElement(name = "HuntSequence")
    protected String huntSequence;

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
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailGroupMemberDTO }
     *     
     */
    public OrdDetailGroupMemberDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailGroupMemberDTO }
     *     
     */
    public void setProfileImage(OrdDetailGroupMemberDTO value) {
        this.profileImage = value;
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
     * Gets the value of the orderDetailID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDetailID() {
        return orderDetailID;
    }

    /**
     * Sets the value of the orderDetailID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDetailID(String value) {
        this.orderDetailID = value;
    }

    /**
     * Gets the value of the huntSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHuntSequence() {
        return huntSequence;
    }

    /**
     * Sets the value of the huntSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHuntSequence(String value) {
        this.huntSequence = value;
    }

}

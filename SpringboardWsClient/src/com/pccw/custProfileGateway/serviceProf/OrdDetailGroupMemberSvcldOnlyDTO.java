
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdDetailGroupMemberSvcldOnlyDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdDetailGroupMemberSvcldOnlyDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="DatCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HuntSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDetailGroup" type="{http://www.openuri.org/}OrdDetailGroupDTO" minOccurs="0"/>
 *         &lt;element name="ServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdDetailGroupMemberSvcldOnlyDTO", propOrder = {
    "datCd",
    "serviceType",
    "action",
    "huntSequence",
    "orderDetailGroup",
    "serviceID"
})
public class OrdDetailGroupMemberSvcldOnlyDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "DatCd")
    protected String datCd;
    @XmlElement(name = "ServiceType")
    protected String serviceType;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "HuntSequence")
    protected String huntSequence;
    @XmlElement(name = "OrderDetailGroup")
    protected OrdDetailGroupDTO orderDetailGroup;
    @XmlElement(name = "ServiceID")
    protected String serviceID;

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
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceType(String value) {
        this.serviceType = value;
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

}

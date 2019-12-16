
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DnDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DnDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="ExchangeCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AssignPriority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfService" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderServiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OcID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDtID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExchangeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InventStsCD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DragonReasonCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DragonReasonDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartRange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndRange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DnDTO", propOrder = {
    "exchangeCategory",
    "assignPriority",
    "typeOfService",
    "message",
    "requestID",
    "orderServiceID",
    "ocID",
    "orderDtID",
    "dn",
    "exchangeId",
    "inventStsCD",
    "dragonReasonCd",
    "dragonReasonDesc",
    "startRange",
    "endRange"
})
public class DnDTO
    extends ValueObject
{

    @XmlElement(name = "ExchangeCategory")
    protected String exchangeCategory;
    @XmlElement(name = "AssignPriority")
    protected String assignPriority;
    @XmlElement(name = "TypeOfService")
    protected String typeOfService;
    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "RequestID")
    protected String requestID;
    @XmlElement(name = "OrderServiceID")
    protected String orderServiceID;
    @XmlElement(name = "OcID")
    protected String ocID;
    @XmlElement(name = "OrderDtID")
    protected String orderDtID;
    @XmlElement(name = "Dn")
    protected String dn;
    @XmlElement(name = "ExchangeId")
    protected String exchangeId;
    @XmlElement(name = "InventStsCD")
    protected String inventStsCD;
    @XmlElement(name = "DragonReasonCd")
    protected String dragonReasonCd;
    @XmlElement(name = "DragonReasonDesc")
    protected String dragonReasonDesc;
    @XmlElement(name = "StartRange")
    protected String startRange;
    @XmlElement(name = "EndRange")
    protected String endRange;

    /**
     * Gets the value of the exchangeCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeCategory() {
        return exchangeCategory;
    }

    /**
     * Sets the value of the exchangeCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeCategory(String value) {
        this.exchangeCategory = value;
    }

    /**
     * Gets the value of the assignPriority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignPriority() {
        return assignPriority;
    }

    /**
     * Sets the value of the assignPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignPriority(String value) {
        this.assignPriority = value;
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
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
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
     * Gets the value of the dn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDn() {
        return dn;
    }

    /**
     * Sets the value of the dn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDn(String value) {
        this.dn = value;
    }

    /**
     * Gets the value of the exchangeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeId() {
        return exchangeId;
    }

    /**
     * Sets the value of the exchangeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeId(String value) {
        this.exchangeId = value;
    }

    /**
     * Gets the value of the inventStsCD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInventStsCD() {
        return inventStsCD;
    }

    /**
     * Sets the value of the inventStsCD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInventStsCD(String value) {
        this.inventStsCD = value;
    }

    /**
     * Gets the value of the dragonReasonCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDragonReasonCd() {
        return dragonReasonCd;
    }

    /**
     * Sets the value of the dragonReasonCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDragonReasonCd(String value) {
        this.dragonReasonCd = value;
    }

    /**
     * Gets the value of the dragonReasonDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDragonReasonDesc() {
        return dragonReasonDesc;
    }

    /**
     * Sets the value of the dragonReasonDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDragonReasonDesc(String value) {
        this.dragonReasonDesc = value;
    }

    /**
     * Gets the value of the startRange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartRange() {
        return startRange;
    }

    /**
     * Sets the value of the startRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartRange(String value) {
        this.startRange = value;
    }

    /**
     * Gets the value of the endRange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndRange() {
        return endRange;
    }

    /**
     * Sets the value of the endRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndRange(String value) {
        this.endRange = value;
    }

}

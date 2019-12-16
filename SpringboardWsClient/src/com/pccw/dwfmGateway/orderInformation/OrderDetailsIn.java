
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderDetailsIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderDetailsIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdactvSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemActnCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActvActnCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderDetailsIn", propOrder = {
    "requestId",
    "requestItemId",
    "orderId",
    "ordactvSeqNum",
    "itemActnCd",
    "actvActnCd"
})
public class OrderDetailsIn {

    @XmlElement(name = "RequestId")
    protected String requestId;
    @XmlElement(name = "RequestItemId")
    protected String requestItemId;
    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "OrdactvSeqNum")
    protected String ordactvSeqNum;
    @XmlElement(name = "ItemActnCd")
    protected String itemActnCd;
    @XmlElement(name = "ActvActnCd")
    protected String actvActnCd;

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the requestItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestItemId() {
        return requestItemId;
    }

    /**
     * Sets the value of the requestItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestItemId(String value) {
        this.requestItemId = value;
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

    /**
     * Gets the value of the ordactvSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdactvSeqNum() {
        return ordactvSeqNum;
    }

    /**
     * Sets the value of the ordactvSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdactvSeqNum(String value) {
        this.ordactvSeqNum = value;
    }

    /**
     * Gets the value of the itemActnCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemActnCd() {
        return itemActnCd;
    }

    /**
     * Sets the value of the itemActnCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemActnCd(String value) {
        this.itemActnCd = value;
    }

    /**
     * Gets the value of the actvActnCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActvActnCd() {
        return actvActnCd;
    }

    /**
     * Sets the value of the actvActnCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActvActnCd(String value) {
        this.actvActnCd = value;
    }

}

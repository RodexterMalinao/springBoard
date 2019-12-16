
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommonCxSparm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommonCxSparm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Req" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReqItem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonCxSparm", propOrder = {
    "ordId",
    "req",
    "reqItem"
})
public class CommonCxSparm {

    @XmlElement(name = "OrdId")
    protected String ordId;
    @XmlElement(name = "Req")
    protected String req;
    @XmlElement(name = "ReqItem")
    protected String reqItem;

    /**
     * Gets the value of the ordId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdId() {
        return ordId;
    }

    /**
     * Sets the value of the ordId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdId(String value) {
        this.ordId = value;
    }

    /**
     * Gets the value of the req property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReq() {
        return req;
    }

    /**
     * Sets the value of the req property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReq(String value) {
        this.req = value;
    }

    /**
     * Gets the value of the reqItem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqItem() {
        return reqItem;
    }

    /**
     * Sets the value of the reqItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqItem(String value) {
        this.reqItem = value;
    }

}

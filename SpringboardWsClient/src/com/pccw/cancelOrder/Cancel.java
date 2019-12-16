
package com.pccw.cancelOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pOcId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="pSrvId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pTypeOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pBoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pCancelReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pCancelRemark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pOcId",
    "pSrvId",
    "pTypeOfSrv",
    "pUserId",
    "pBoc",
    "pCancelReasonCode",
    "pCancelRemark"
})
@XmlRootElement(name = "cancel")
public class Cancel {

    protected long pOcId;
    protected String pSrvId;
    protected String pTypeOfSrv;
    protected String pUserId;
    protected String pBoc;
    protected String pCancelReasonCode;
    protected String pCancelRemark;

    /**
     * Gets the value of the pOcId property.
     * 
     */
    public long getPOcId() {
        return pOcId;
    }

    /**
     * Sets the value of the pOcId property.
     * 
     */
    public void setPOcId(long value) {
        this.pOcId = value;
    }

    /**
     * Gets the value of the pSrvId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPSrvId() {
        return pSrvId;
    }

    /**
     * Sets the value of the pSrvId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPSrvId(String value) {
        this.pSrvId = value;
    }

    /**
     * Gets the value of the pTypeOfSrv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPTypeOfSrv() {
        return pTypeOfSrv;
    }

    /**
     * Sets the value of the pTypeOfSrv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPTypeOfSrv(String value) {
        this.pTypeOfSrv = value;
    }

    /**
     * Gets the value of the pUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUserId() {
        return pUserId;
    }

    /**
     * Sets the value of the pUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUserId(String value) {
        this.pUserId = value;
    }

    /**
     * Gets the value of the pBoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPBoc() {
        return pBoc;
    }

    /**
     * Sets the value of the pBoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPBoc(String value) {
        this.pBoc = value;
    }

    /**
     * Gets the value of the pCancelReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPCancelReasonCode() {
        return pCancelReasonCode;
    }

    /**
     * Sets the value of the pCancelReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPCancelReasonCode(String value) {
        this.pCancelReasonCode = value;
    }

    /**
     * Gets the value of the pCancelRemark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPCancelRemark() {
        return pCancelRemark;
    }

    /**
     * Sets the value of the pCancelRemark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPCancelRemark(String value) {
        this.pCancelRemark = value;
    }

}

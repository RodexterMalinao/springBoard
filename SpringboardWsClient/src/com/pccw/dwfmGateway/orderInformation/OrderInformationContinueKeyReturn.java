
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationContinueKeyReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationContinueKeyReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContActDbKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContActRmkDbKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContActvRmkSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContActvSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContImsDbKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContImsSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContJunRmkDBKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContOrdRmkDbKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContPsefDbKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContPsefInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContPsefSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContVoiceDbKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContVoiceSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformationContinueKeyReturn", propOrder = {
    "contActDbKey",
    "contActRmkDbKey",
    "contActvRmkSeqNum",
    "contActvSeqNum",
    "contImsDbKey",
    "contImsSeqNum",
    "contJunRmkDBKey",
    "contOrdRmkDbKey",
    "contPsefDbKey",
    "contPsefInd",
    "contPsefSeqNum",
    "contSrvNum",
    "contVoiceDbKey",
    "contVoiceSeqNum",
    "orderId"
})
public class OrderInformationContinueKeyReturn {

    @XmlElement(name = "ContActDbKey")
    protected String contActDbKey;
    @XmlElement(name = "ContActRmkDbKey")
    protected String contActRmkDbKey;
    @XmlElement(name = "ContActvRmkSeqNum")
    protected String contActvRmkSeqNum;
    @XmlElement(name = "ContActvSeqNum")
    protected String contActvSeqNum;
    @XmlElement(name = "ContImsDbKey")
    protected String contImsDbKey;
    @XmlElement(name = "ContImsSeqNum")
    protected String contImsSeqNum;
    @XmlElement(name = "ContJunRmkDBKey")
    protected String contJunRmkDBKey;
    @XmlElement(name = "ContOrdRmkDbKey")
    protected String contOrdRmkDbKey;
    @XmlElement(name = "ContPsefDbKey")
    protected String contPsefDbKey;
    @XmlElement(name = "ContPsefInd")
    protected String contPsefInd;
    @XmlElement(name = "ContPsefSeqNum")
    protected String contPsefSeqNum;
    @XmlElement(name = "ContSrvNum")
    protected String contSrvNum;
    @XmlElement(name = "ContVoiceDbKey")
    protected String contVoiceDbKey;
    @XmlElement(name = "ContVoiceSeqNum")
    protected String contVoiceSeqNum;
    @XmlElement(name = "OrderId")
    protected String orderId;

    /**
     * Gets the value of the contActDbKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContActDbKey() {
        return contActDbKey;
    }

    /**
     * Sets the value of the contActDbKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContActDbKey(String value) {
        this.contActDbKey = value;
    }

    /**
     * Gets the value of the contActRmkDbKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContActRmkDbKey() {
        return contActRmkDbKey;
    }

    /**
     * Sets the value of the contActRmkDbKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContActRmkDbKey(String value) {
        this.contActRmkDbKey = value;
    }

    /**
     * Gets the value of the contActvRmkSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContActvRmkSeqNum() {
        return contActvRmkSeqNum;
    }

    /**
     * Sets the value of the contActvRmkSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContActvRmkSeqNum(String value) {
        this.contActvRmkSeqNum = value;
    }

    /**
     * Gets the value of the contActvSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContActvSeqNum() {
        return contActvSeqNum;
    }

    /**
     * Sets the value of the contActvSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContActvSeqNum(String value) {
        this.contActvSeqNum = value;
    }

    /**
     * Gets the value of the contImsDbKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContImsDbKey() {
        return contImsDbKey;
    }

    /**
     * Sets the value of the contImsDbKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContImsDbKey(String value) {
        this.contImsDbKey = value;
    }

    /**
     * Gets the value of the contImsSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContImsSeqNum() {
        return contImsSeqNum;
    }

    /**
     * Sets the value of the contImsSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContImsSeqNum(String value) {
        this.contImsSeqNum = value;
    }

    /**
     * Gets the value of the contJunRmkDBKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContJunRmkDBKey() {
        return contJunRmkDBKey;
    }

    /**
     * Sets the value of the contJunRmkDBKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContJunRmkDBKey(String value) {
        this.contJunRmkDBKey = value;
    }

    /**
     * Gets the value of the contOrdRmkDbKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContOrdRmkDbKey() {
        return contOrdRmkDbKey;
    }

    /**
     * Sets the value of the contOrdRmkDbKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContOrdRmkDbKey(String value) {
        this.contOrdRmkDbKey = value;
    }

    /**
     * Gets the value of the contPsefDbKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContPsefDbKey() {
        return contPsefDbKey;
    }

    /**
     * Sets the value of the contPsefDbKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContPsefDbKey(String value) {
        this.contPsefDbKey = value;
    }

    /**
     * Gets the value of the contPsefInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContPsefInd() {
        return contPsefInd;
    }

    /**
     * Sets the value of the contPsefInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContPsefInd(String value) {
        this.contPsefInd = value;
    }

    /**
     * Gets the value of the contPsefSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContPsefSeqNum() {
        return contPsefSeqNum;
    }

    /**
     * Sets the value of the contPsefSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContPsefSeqNum(String value) {
        this.contPsefSeqNum = value;
    }

    /**
     * Gets the value of the contSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContSrvNum() {
        return contSrvNum;
    }

    /**
     * Sets the value of the contSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContSrvNum(String value) {
        this.contSrvNum = value;
    }

    /**
     * Gets the value of the contVoiceDbKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContVoiceDbKey() {
        return contVoiceDbKey;
    }

    /**
     * Sets the value of the contVoiceDbKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContVoiceDbKey(String value) {
        this.contVoiceDbKey = value;
    }

    /**
     * Gets the value of the contVoiceSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContVoiceSeqNum() {
        return contVoiceSeqNum;
    }

    /**
     * Sets the value of the contVoiceSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContVoiceSeqNum(String value) {
        this.contVoiceSeqNum = value;
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

}

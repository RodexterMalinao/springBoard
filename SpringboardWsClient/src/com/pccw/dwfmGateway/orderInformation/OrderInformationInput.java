
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationInput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContActvSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ContActrmkSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ContPsefSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ContVoiceSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ContImsSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GetActInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GetPsefInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GetOrdrmkInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GetJunrmkInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GetActrmkInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GetVoiceInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GetImsInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContPsefInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContPsefDbkey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContActDbkey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContOrdrmkDbkey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContJunrmkDbkey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContActrmkDbkey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContVoiceDbkey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContImsDbkey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PsefLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActivityLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrdrmkLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="JunrmkLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActrmkLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VoiceLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ImsLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TwoNTieInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformationInput", propOrder = {
    "orderId",
    "contActvSeqNum",
    "contActrmkSeqNum",
    "contPsefSeqNum",
    "contVoiceSeqNum",
    "contImsSeqNum",
    "getActInd",
    "getPsefInd",
    "getOrdrmkInd",
    "getJunrmkInd",
    "getActrmkInd",
    "getVoiceInd",
    "getImsInd",
    "contPsefInd",
    "contSrvNum",
    "contPsefDbkey",
    "contActDbkey",
    "contOrdrmkDbkey",
    "contJunrmkDbkey",
    "contActrmkDbkey",
    "contVoiceDbkey",
    "contImsDbkey",
    "psefLimit",
    "activityLimit",
    "ordrmkLimit",
    "junrmkLimit",
    "actrmkLimit",
    "voiceLimit",
    "imsLimit",
    "twoNTieInd"
})
public class OrderInformationInput {

    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "ContActvSeqNum")
    protected int contActvSeqNum;
    @XmlElement(name = "ContActrmkSeqNum")
    protected int contActrmkSeqNum;
    @XmlElement(name = "ContPsefSeqNum")
    protected int contPsefSeqNum;
    @XmlElement(name = "ContVoiceSeqNum")
    protected int contVoiceSeqNum;
    @XmlElement(name = "ContImsSeqNum")
    protected int contImsSeqNum;
    @XmlElement(name = "GetActInd")
    protected String getActInd;
    @XmlElement(name = "GetPsefInd")
    protected String getPsefInd;
    @XmlElement(name = "GetOrdrmkInd")
    protected String getOrdrmkInd;
    @XmlElement(name = "GetJunrmkInd")
    protected String getJunrmkInd;
    @XmlElement(name = "GetActrmkInd")
    protected String getActrmkInd;
    @XmlElement(name = "GetVoiceInd")
    protected String getVoiceInd;
    @XmlElement(name = "GetImsInd")
    protected String getImsInd;
    @XmlElement(name = "ContPsefInd")
    protected String contPsefInd;
    @XmlElement(name = "ContSrvNum")
    protected String contSrvNum;
    @XmlElement(name = "ContPsefDbkey")
    protected String contPsefDbkey;
    @XmlElement(name = "ContActDbkey")
    protected String contActDbkey;
    @XmlElement(name = "ContOrdrmkDbkey")
    protected String contOrdrmkDbkey;
    @XmlElement(name = "ContJunrmkDbkey")
    protected String contJunrmkDbkey;
    @XmlElement(name = "ContActrmkDbkey")
    protected String contActrmkDbkey;
    @XmlElement(name = "ContVoiceDbkey")
    protected String contVoiceDbkey;
    @XmlElement(name = "ContImsDbkey")
    protected String contImsDbkey;
    @XmlElement(name = "PsefLimit")
    protected int psefLimit;
    @XmlElement(name = "ActivityLimit")
    protected int activityLimit;
    @XmlElement(name = "OrdrmkLimit")
    protected int ordrmkLimit;
    @XmlElement(name = "JunrmkLimit")
    protected int junrmkLimit;
    @XmlElement(name = "ActrmkLimit")
    protected int actrmkLimit;
    @XmlElement(name = "VoiceLimit")
    protected int voiceLimit;
    @XmlElement(name = "ImsLimit")
    protected int imsLimit;
    @XmlElement(name = "TwoNTieInd")
    protected String twoNTieInd;

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
     * Gets the value of the contActvSeqNum property.
     * 
     */
    public int getContActvSeqNum() {
        return contActvSeqNum;
    }

    /**
     * Sets the value of the contActvSeqNum property.
     * 
     */
    public void setContActvSeqNum(int value) {
        this.contActvSeqNum = value;
    }

    /**
     * Gets the value of the contActrmkSeqNum property.
     * 
     */
    public int getContActrmkSeqNum() {
        return contActrmkSeqNum;
    }

    /**
     * Sets the value of the contActrmkSeqNum property.
     * 
     */
    public void setContActrmkSeqNum(int value) {
        this.contActrmkSeqNum = value;
    }

    /**
     * Gets the value of the contPsefSeqNum property.
     * 
     */
    public int getContPsefSeqNum() {
        return contPsefSeqNum;
    }

    /**
     * Sets the value of the contPsefSeqNum property.
     * 
     */
    public void setContPsefSeqNum(int value) {
        this.contPsefSeqNum = value;
    }

    /**
     * Gets the value of the contVoiceSeqNum property.
     * 
     */
    public int getContVoiceSeqNum() {
        return contVoiceSeqNum;
    }

    /**
     * Sets the value of the contVoiceSeqNum property.
     * 
     */
    public void setContVoiceSeqNum(int value) {
        this.contVoiceSeqNum = value;
    }

    /**
     * Gets the value of the contImsSeqNum property.
     * 
     */
    public int getContImsSeqNum() {
        return contImsSeqNum;
    }

    /**
     * Sets the value of the contImsSeqNum property.
     * 
     */
    public void setContImsSeqNum(int value) {
        this.contImsSeqNum = value;
    }

    /**
     * Gets the value of the getActInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetActInd() {
        return getActInd;
    }

    /**
     * Sets the value of the getActInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetActInd(String value) {
        this.getActInd = value;
    }

    /**
     * Gets the value of the getPsefInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetPsefInd() {
        return getPsefInd;
    }

    /**
     * Sets the value of the getPsefInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetPsefInd(String value) {
        this.getPsefInd = value;
    }

    /**
     * Gets the value of the getOrdrmkInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetOrdrmkInd() {
        return getOrdrmkInd;
    }

    /**
     * Sets the value of the getOrdrmkInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetOrdrmkInd(String value) {
        this.getOrdrmkInd = value;
    }

    /**
     * Gets the value of the getJunrmkInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetJunrmkInd() {
        return getJunrmkInd;
    }

    /**
     * Sets the value of the getJunrmkInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetJunrmkInd(String value) {
        this.getJunrmkInd = value;
    }

    /**
     * Gets the value of the getActrmkInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetActrmkInd() {
        return getActrmkInd;
    }

    /**
     * Sets the value of the getActrmkInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetActrmkInd(String value) {
        this.getActrmkInd = value;
    }

    /**
     * Gets the value of the getVoiceInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetVoiceInd() {
        return getVoiceInd;
    }

    /**
     * Sets the value of the getVoiceInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetVoiceInd(String value) {
        this.getVoiceInd = value;
    }

    /**
     * Gets the value of the getImsInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetImsInd() {
        return getImsInd;
    }

    /**
     * Sets the value of the getImsInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetImsInd(String value) {
        this.getImsInd = value;
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
     * Gets the value of the contPsefDbkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContPsefDbkey() {
        return contPsefDbkey;
    }

    /**
     * Sets the value of the contPsefDbkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContPsefDbkey(String value) {
        this.contPsefDbkey = value;
    }

    /**
     * Gets the value of the contActDbkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContActDbkey() {
        return contActDbkey;
    }

    /**
     * Sets the value of the contActDbkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContActDbkey(String value) {
        this.contActDbkey = value;
    }

    /**
     * Gets the value of the contOrdrmkDbkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContOrdrmkDbkey() {
        return contOrdrmkDbkey;
    }

    /**
     * Sets the value of the contOrdrmkDbkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContOrdrmkDbkey(String value) {
        this.contOrdrmkDbkey = value;
    }

    /**
     * Gets the value of the contJunrmkDbkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContJunrmkDbkey() {
        return contJunrmkDbkey;
    }

    /**
     * Sets the value of the contJunrmkDbkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContJunrmkDbkey(String value) {
        this.contJunrmkDbkey = value;
    }

    /**
     * Gets the value of the contActrmkDbkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContActrmkDbkey() {
        return contActrmkDbkey;
    }

    /**
     * Sets the value of the contActrmkDbkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContActrmkDbkey(String value) {
        this.contActrmkDbkey = value;
    }

    /**
     * Gets the value of the contVoiceDbkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContVoiceDbkey() {
        return contVoiceDbkey;
    }

    /**
     * Sets the value of the contVoiceDbkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContVoiceDbkey(String value) {
        this.contVoiceDbkey = value;
    }

    /**
     * Gets the value of the contImsDbkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContImsDbkey() {
        return contImsDbkey;
    }

    /**
     * Sets the value of the contImsDbkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContImsDbkey(String value) {
        this.contImsDbkey = value;
    }

    /**
     * Gets the value of the psefLimit property.
     * 
     */
    public int getPsefLimit() {
        return psefLimit;
    }

    /**
     * Sets the value of the psefLimit property.
     * 
     */
    public void setPsefLimit(int value) {
        this.psefLimit = value;
    }

    /**
     * Gets the value of the activityLimit property.
     * 
     */
    public int getActivityLimit() {
        return activityLimit;
    }

    /**
     * Sets the value of the activityLimit property.
     * 
     */
    public void setActivityLimit(int value) {
        this.activityLimit = value;
    }

    /**
     * Gets the value of the ordrmkLimit property.
     * 
     */
    public int getOrdrmkLimit() {
        return ordrmkLimit;
    }

    /**
     * Sets the value of the ordrmkLimit property.
     * 
     */
    public void setOrdrmkLimit(int value) {
        this.ordrmkLimit = value;
    }

    /**
     * Gets the value of the junrmkLimit property.
     * 
     */
    public int getJunrmkLimit() {
        return junrmkLimit;
    }

    /**
     * Sets the value of the junrmkLimit property.
     * 
     */
    public void setJunrmkLimit(int value) {
        this.junrmkLimit = value;
    }

    /**
     * Gets the value of the actrmkLimit property.
     * 
     */
    public int getActrmkLimit() {
        return actrmkLimit;
    }

    /**
     * Sets the value of the actrmkLimit property.
     * 
     */
    public void setActrmkLimit(int value) {
        this.actrmkLimit = value;
    }

    /**
     * Gets the value of the voiceLimit property.
     * 
     */
    public int getVoiceLimit() {
        return voiceLimit;
    }

    /**
     * Sets the value of the voiceLimit property.
     * 
     */
    public void setVoiceLimit(int value) {
        this.voiceLimit = value;
    }

    /**
     * Gets the value of the imsLimit property.
     * 
     */
    public int getImsLimit() {
        return imsLimit;
    }

    /**
     * Sets the value of the imsLimit property.
     * 
     */
    public void setImsLimit(int value) {
        this.imsLimit = value;
    }

    /**
     * Gets the value of the twoNTieInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwoNTieInd() {
        return twoNTieInd;
    }

    /**
     * Sets the value of the twoNTieInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTwoNTieInd(String value) {
        this.twoNTieInd = value;
    }

}

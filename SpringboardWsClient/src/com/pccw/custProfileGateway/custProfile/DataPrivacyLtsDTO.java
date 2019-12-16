
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataPrivacyLtsDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataPrivacyLtsDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BomLob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DirectMailing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Outbound" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WebBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillInsert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CdOutdial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SmsEye" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Datacast" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcCreateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsResendReq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataPrivacyNotiInfoLtsDTO" type="{http://www.openuri.org/}DataPrivacyNotiInfoLtsDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataPrivacyLtsDTO", propOrder = {
    "custNum",
    "systemId",
    "bomLob",
    "optInd",
    "directMailing",
    "outbound",
    "sms",
    "email",
    "webBill",
    "billInsert",
    "cdOutdial",
    "bm",
    "smsEye",
    "datacast",
    "ccCreateDate",
    "lastUpdDate",
    "lastUpdBy",
    "lastUpdProcess",
    "isResendReq",
    "errCd",
    "errDesc",
    "dataPrivacyNotiInfoLtsDTO"
})
public class DataPrivacyLtsDTO
    extends ValueObject
{

    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "BomLob")
    protected String bomLob;
    @XmlElement(name = "OptInd")
    protected String optInd;
    @XmlElement(name = "DirectMailing")
    protected String directMailing;
    @XmlElement(name = "Outbound")
    protected String outbound;
    @XmlElement(name = "Sms")
    protected String sms;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "WebBill")
    protected String webBill;
    @XmlElement(name = "BillInsert")
    protected String billInsert;
    @XmlElement(name = "CdOutdial")
    protected String cdOutdial;
    @XmlElement(name = "Bm")
    protected String bm;
    @XmlElement(name = "SmsEye")
    protected String smsEye;
    @XmlElement(name = "Datacast")
    protected String datacast;
    @XmlElement(name = "CcCreateDate")
    protected String ccCreateDate;
    @XmlElement(name = "LastUpdDate")
    protected String lastUpdDate;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "LastUpdProcess")
    protected String lastUpdProcess;
    @XmlElement(name = "IsResendReq")
    protected String isResendReq;
    @XmlElement(name = "ErrCd")
    protected String errCd;
    @XmlElement(name = "ErrDesc")
    protected String errDesc;
    @XmlElement(name = "DataPrivacyNotiInfoLtsDTO")
    protected DataPrivacyNotiInfoLtsDTO dataPrivacyNotiInfoLtsDTO;

    /**
     * Gets the value of the custNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustNum() {
        return custNum;
    }

    /**
     * Sets the value of the custNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustNum(String value) {
        this.custNum = value;
    }

    /**
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * Gets the value of the bomLob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBomLob() {
        return bomLob;
    }

    /**
     * Sets the value of the bomLob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBomLob(String value) {
        this.bomLob = value;
    }

    /**
     * Gets the value of the optInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptInd() {
        return optInd;
    }

    /**
     * Sets the value of the optInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptInd(String value) {
        this.optInd = value;
    }

    /**
     * Gets the value of the directMailing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectMailing() {
        return directMailing;
    }

    /**
     * Sets the value of the directMailing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectMailing(String value) {
        this.directMailing = value;
    }

    /**
     * Gets the value of the outbound property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutbound() {
        return outbound;
    }

    /**
     * Sets the value of the outbound property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutbound(String value) {
        this.outbound = value;
    }

    /**
     * Gets the value of the sms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSms() {
        return sms;
    }

    /**
     * Sets the value of the sms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSms(String value) {
        this.sms = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the webBill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebBill() {
        return webBill;
    }

    /**
     * Sets the value of the webBill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebBill(String value) {
        this.webBill = value;
    }

    /**
     * Gets the value of the billInsert property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillInsert() {
        return billInsert;
    }

    /**
     * Sets the value of the billInsert property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillInsert(String value) {
        this.billInsert = value;
    }

    /**
     * Gets the value of the cdOutdial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCdOutdial() {
        return cdOutdial;
    }

    /**
     * Sets the value of the cdOutdial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCdOutdial(String value) {
        this.cdOutdial = value;
    }

    /**
     * Gets the value of the bm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBm() {
        return bm;
    }

    /**
     * Sets the value of the bm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBm(String value) {
        this.bm = value;
    }

    /**
     * Gets the value of the smsEye property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsEye() {
        return smsEye;
    }

    /**
     * Sets the value of the smsEye property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsEye(String value) {
        this.smsEye = value;
    }

    /**
     * Gets the value of the datacast property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatacast() {
        return datacast;
    }

    /**
     * Sets the value of the datacast property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatacast(String value) {
        this.datacast = value;
    }

    /**
     * Gets the value of the ccCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcCreateDate() {
        return ccCreateDate;
    }

    /**
     * Sets the value of the ccCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcCreateDate(String value) {
        this.ccCreateDate = value;
    }

    /**
     * Gets the value of the lastUpdDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdDate() {
        return lastUpdDate;
    }

    /**
     * Sets the value of the lastUpdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdDate(String value) {
        this.lastUpdDate = value;
    }

    /**
     * Gets the value of the lastUpdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdBy() {
        return lastUpdBy;
    }

    /**
     * Sets the value of the lastUpdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdBy(String value) {
        this.lastUpdBy = value;
    }

    /**
     * Gets the value of the lastUpdProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdProcess() {
        return lastUpdProcess;
    }

    /**
     * Sets the value of the lastUpdProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdProcess(String value) {
        this.lastUpdProcess = value;
    }

    /**
     * Gets the value of the isResendReq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsResendReq() {
        return isResendReq;
    }

    /**
     * Sets the value of the isResendReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsResendReq(String value) {
        this.isResendReq = value;
    }

    /**
     * Gets the value of the errCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrCd() {
        return errCd;
    }

    /**
     * Sets the value of the errCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrCd(String value) {
        this.errCd = value;
    }

    /**
     * Gets the value of the errDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrDesc() {
        return errDesc;
    }

    /**
     * Sets the value of the errDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrDesc(String value) {
        this.errDesc = value;
    }

    /**
     * Gets the value of the dataPrivacyNotiInfoLtsDTO property.
     * 
     * @return
     *     possible object is
     *     {@link DataPrivacyNotiInfoLtsDTO }
     *     
     */
    public DataPrivacyNotiInfoLtsDTO getDataPrivacyNotiInfoLtsDTO() {
        return dataPrivacyNotiInfoLtsDTO;
    }

    /**
     * Sets the value of the dataPrivacyNotiInfoLtsDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataPrivacyNotiInfoLtsDTO }
     *     
     */
    public void setDataPrivacyNotiInfoLtsDTO(DataPrivacyNotiInfoLtsDTO value) {
        this.dataPrivacyNotiInfoLtsDTO = value;
    }

}

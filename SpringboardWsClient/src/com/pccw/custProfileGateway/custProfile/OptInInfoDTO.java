
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OptInInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OptInInfoDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="ActionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DirectMailing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DiscloAssoCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Outbound" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BomRowSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ImsRowSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValidityPeriod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WebBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OptInInfoDTO", propOrder = {
    "actionInd",
    "lastUpdBy",
    "lastUpdDate",
    "custLevel",
    "custNum",
    "directMailing",
    "discloAssoCompany",
    "outbound",
    "bomRowSeqNum",
    "imsRowSeqNum",
    "sms",
    "systemId",
    "validityPeriod",
    "email",
    "webBill"
})
public class OptInInfoDTO
    extends ValueObject
{

    @XmlElement(name = "ActionInd")
    protected String actionInd;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "LastUpdDate")
    protected String lastUpdDate;
    @XmlElement(name = "CustLevel")
    protected int custLevel;
    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "DirectMailing")
    protected String directMailing;
    @XmlElement(name = "DiscloAssoCompany")
    protected String discloAssoCompany;
    @XmlElement(name = "Outbound")
    protected String outbound;
    @XmlElement(name = "BomRowSeqNum")
    protected int bomRowSeqNum;
    @XmlElement(name = "ImsRowSeqNum")
    protected int imsRowSeqNum;
    @XmlElement(name = "Sms")
    protected String sms;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "ValidityPeriod")
    protected String validityPeriod;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "WebBill")
    protected String webBill;

    /**
     * Gets the value of the actionInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionInd() {
        return actionInd;
    }

    /**
     * Sets the value of the actionInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionInd(String value) {
        this.actionInd = value;
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
     * Gets the value of the custLevel property.
     * 
     */
    public int getCustLevel() {
        return custLevel;
    }

    /**
     * Sets the value of the custLevel property.
     * 
     */
    public void setCustLevel(int value) {
        this.custLevel = value;
    }

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
     * Gets the value of the discloAssoCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscloAssoCompany() {
        return discloAssoCompany;
    }

    /**
     * Sets the value of the discloAssoCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscloAssoCompany(String value) {
        this.discloAssoCompany = value;
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
     * Gets the value of the bomRowSeqNum property.
     * 
     */
    public int getBomRowSeqNum() {
        return bomRowSeqNum;
    }

    /**
     * Sets the value of the bomRowSeqNum property.
     * 
     */
    public void setBomRowSeqNum(int value) {
        this.bomRowSeqNum = value;
    }

    /**
     * Gets the value of the imsRowSeqNum property.
     * 
     */
    public int getImsRowSeqNum() {
        return imsRowSeqNum;
    }

    /**
     * Sets the value of the imsRowSeqNum property.
     * 
     */
    public void setImsRowSeqNum(int value) {
        this.imsRowSeqNum = value;
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
     * Gets the value of the validityPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * Sets the value of the validityPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidityPeriod(String value) {
        this.validityPeriod = value;
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

}

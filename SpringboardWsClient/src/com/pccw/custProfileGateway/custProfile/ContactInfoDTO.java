
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactInfoDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="LastUpdateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LangSpeakDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LangWrittenDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelationshipDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Relationship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LangWritten" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LangSpeak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BcciRowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BccmDphRowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BccmERowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BccmFx1Rowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BccmFx2Rowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BccmMRowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BccmNphRowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BccmPRowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CntctKey" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CntctName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DayPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DayPhoneExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fax1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fax1Ext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fax2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fax2Ext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImsRowseqnb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobileExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NightPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NightPhoneExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pager" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PagerExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Position" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrimaryInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShopCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobileSMS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SmsLang" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactInfoDTO", propOrder = {
    "lastUpdateDate",
    "langSpeakDesc",
    "langWrittenDesc",
    "relationshipDesc",
    "relationship",
    "langWritten",
    "langSpeak",
    "actionInd",
    "bcciRowseqnb",
    "bccmDphRowseqnb",
    "bccmERowseqnb",
    "bccmFx1Rowseqnb",
    "bccmFx2Rowseqnb",
    "bccmMRowseqnb",
    "bccmNphRowseqnb",
    "bccmPRowseqnb",
    "cntctKey",
    "cntctName",
    "custLevel",
    "custNum",
    "dayPhone",
    "dayPhoneExt",
    "department",
    "email",
    "emailExt",
    "fax1",
    "fax1Ext",
    "fax2",
    "fax2Ext",
    "imsRowseqnb",
    "lastUpdBy",
    "mobile",
    "mobileExt",
    "nightPhone",
    "nightPhoneExt",
    "pager",
    "pagerExt",
    "position",
    "primaryInd",
    "remarks",
    "systemId",
    "title",
    "shopCode",
    "mobileSMS",
    "smsLang"
})
public class ContactInfoDTO
    extends ValueObject
{

    @XmlElement(name = "LastUpdateDate")
    protected String lastUpdateDate;
    @XmlElement(name = "LangSpeakDesc")
    protected String langSpeakDesc;
    @XmlElement(name = "LangWrittenDesc")
    protected String langWrittenDesc;
    @XmlElement(name = "RelationshipDesc")
    protected String relationshipDesc;
    @XmlElement(name = "Relationship")
    protected String relationship;
    @XmlElement(name = "LangWritten")
    protected String langWritten;
    @XmlElement(name = "LangSpeak")
    protected String langSpeak;
    @XmlElement(name = "ActionInd")
    protected String actionInd;
    @XmlElement(name = "BcciRowseqnb")
    protected int bcciRowseqnb;
    @XmlElement(name = "BccmDphRowseqnb")
    protected int bccmDphRowseqnb;
    @XmlElement(name = "BccmERowseqnb")
    protected int bccmERowseqnb;
    @XmlElement(name = "BccmFx1Rowseqnb")
    protected int bccmFx1Rowseqnb;
    @XmlElement(name = "BccmFx2Rowseqnb")
    protected int bccmFx2Rowseqnb;
    @XmlElement(name = "BccmMRowseqnb")
    protected int bccmMRowseqnb;
    @XmlElement(name = "BccmNphRowseqnb")
    protected int bccmNphRowseqnb;
    @XmlElement(name = "BccmPRowseqnb")
    protected int bccmPRowseqnb;
    @XmlElement(name = "CntctKey")
    protected int cntctKey;
    @XmlElement(name = "CntctName")
    protected String cntctName;
    @XmlElement(name = "CustLevel")
    protected int custLevel;
    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "DayPhone")
    protected String dayPhone;
    @XmlElement(name = "DayPhoneExt")
    protected String dayPhoneExt;
    @XmlElement(name = "Department")
    protected String department;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "EmailExt")
    protected String emailExt;
    @XmlElement(name = "Fax1")
    protected String fax1;
    @XmlElement(name = "Fax1Ext")
    protected String fax1Ext;
    @XmlElement(name = "Fax2")
    protected String fax2;
    @XmlElement(name = "Fax2Ext")
    protected String fax2Ext;
    @XmlElement(name = "ImsRowseqnb")
    protected int imsRowseqnb;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "Mobile")
    protected String mobile;
    @XmlElement(name = "MobileExt")
    protected String mobileExt;
    @XmlElement(name = "NightPhone")
    protected String nightPhone;
    @XmlElement(name = "NightPhoneExt")
    protected String nightPhoneExt;
    @XmlElement(name = "Pager")
    protected String pager;
    @XmlElement(name = "PagerExt")
    protected String pagerExt;
    @XmlElement(name = "Position")
    protected String position;
    @XmlElement(name = "PrimaryInd")
    protected String primaryInd;
    @XmlElement(name = "Remarks")
    protected String remarks;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "ShopCode")
    protected String shopCode;
    @XmlElement(name = "MobileSMS")
    protected String mobileSMS;
    @XmlElement(name = "SmsLang")
    protected String smsLang;

    /**
     * Gets the value of the lastUpdateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Sets the value of the lastUpdateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdateDate(String value) {
        this.lastUpdateDate = value;
    }

    /**
     * Gets the value of the langSpeakDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLangSpeakDesc() {
        return langSpeakDesc;
    }

    /**
     * Sets the value of the langSpeakDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLangSpeakDesc(String value) {
        this.langSpeakDesc = value;
    }

    /**
     * Gets the value of the langWrittenDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLangWrittenDesc() {
        return langWrittenDesc;
    }

    /**
     * Sets the value of the langWrittenDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLangWrittenDesc(String value) {
        this.langWrittenDesc = value;
    }

    /**
     * Gets the value of the relationshipDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationshipDesc() {
        return relationshipDesc;
    }

    /**
     * Sets the value of the relationshipDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationshipDesc(String value) {
        this.relationshipDesc = value;
    }

    /**
     * Gets the value of the relationship property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Sets the value of the relationship property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationship(String value) {
        this.relationship = value;
    }

    /**
     * Gets the value of the langWritten property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLangWritten() {
        return langWritten;
    }

    /**
     * Sets the value of the langWritten property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLangWritten(String value) {
        this.langWritten = value;
    }

    /**
     * Gets the value of the langSpeak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLangSpeak() {
        return langSpeak;
    }

    /**
     * Sets the value of the langSpeak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLangSpeak(String value) {
        this.langSpeak = value;
    }

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
     * Gets the value of the bcciRowseqnb property.
     * 
     */
    public int getBcciRowseqnb() {
        return bcciRowseqnb;
    }

    /**
     * Sets the value of the bcciRowseqnb property.
     * 
     */
    public void setBcciRowseqnb(int value) {
        this.bcciRowseqnb = value;
    }

    /**
     * Gets the value of the bccmDphRowseqnb property.
     * 
     */
    public int getBccmDphRowseqnb() {
        return bccmDphRowseqnb;
    }

    /**
     * Sets the value of the bccmDphRowseqnb property.
     * 
     */
    public void setBccmDphRowseqnb(int value) {
        this.bccmDphRowseqnb = value;
    }

    /**
     * Gets the value of the bccmERowseqnb property.
     * 
     */
    public int getBccmERowseqnb() {
        return bccmERowseqnb;
    }

    /**
     * Sets the value of the bccmERowseqnb property.
     * 
     */
    public void setBccmERowseqnb(int value) {
        this.bccmERowseqnb = value;
    }

    /**
     * Gets the value of the bccmFx1Rowseqnb property.
     * 
     */
    public int getBccmFx1Rowseqnb() {
        return bccmFx1Rowseqnb;
    }

    /**
     * Sets the value of the bccmFx1Rowseqnb property.
     * 
     */
    public void setBccmFx1Rowseqnb(int value) {
        this.bccmFx1Rowseqnb = value;
    }

    /**
     * Gets the value of the bccmFx2Rowseqnb property.
     * 
     */
    public int getBccmFx2Rowseqnb() {
        return bccmFx2Rowseqnb;
    }

    /**
     * Sets the value of the bccmFx2Rowseqnb property.
     * 
     */
    public void setBccmFx2Rowseqnb(int value) {
        this.bccmFx2Rowseqnb = value;
    }

    /**
     * Gets the value of the bccmMRowseqnb property.
     * 
     */
    public int getBccmMRowseqnb() {
        return bccmMRowseqnb;
    }

    /**
     * Sets the value of the bccmMRowseqnb property.
     * 
     */
    public void setBccmMRowseqnb(int value) {
        this.bccmMRowseqnb = value;
    }

    /**
     * Gets the value of the bccmNphRowseqnb property.
     * 
     */
    public int getBccmNphRowseqnb() {
        return bccmNphRowseqnb;
    }

    /**
     * Sets the value of the bccmNphRowseqnb property.
     * 
     */
    public void setBccmNphRowseqnb(int value) {
        this.bccmNphRowseqnb = value;
    }

    /**
     * Gets the value of the bccmPRowseqnb property.
     * 
     */
    public int getBccmPRowseqnb() {
        return bccmPRowseqnb;
    }

    /**
     * Sets the value of the bccmPRowseqnb property.
     * 
     */
    public void setBccmPRowseqnb(int value) {
        this.bccmPRowseqnb = value;
    }

    /**
     * Gets the value of the cntctKey property.
     * 
     */
    public int getCntctKey() {
        return cntctKey;
    }

    /**
     * Sets the value of the cntctKey property.
     * 
     */
    public void setCntctKey(int value) {
        this.cntctKey = value;
    }

    /**
     * Gets the value of the cntctName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCntctName() {
        return cntctName;
    }

    /**
     * Sets the value of the cntctName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCntctName(String value) {
        this.cntctName = value;
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
     * Gets the value of the dayPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDayPhone() {
        return dayPhone;
    }

    /**
     * Sets the value of the dayPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDayPhone(String value) {
        this.dayPhone = value;
    }

    /**
     * Gets the value of the dayPhoneExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDayPhoneExt() {
        return dayPhoneExt;
    }

    /**
     * Sets the value of the dayPhoneExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDayPhoneExt(String value) {
        this.dayPhoneExt = value;
    }

    /**
     * Gets the value of the department property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the value of the department property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartment(String value) {
        this.department = value;
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
     * Gets the value of the emailExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailExt() {
        return emailExt;
    }

    /**
     * Sets the value of the emailExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailExt(String value) {
        this.emailExt = value;
    }

    /**
     * Gets the value of the fax1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax1() {
        return fax1;
    }

    /**
     * Sets the value of the fax1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax1(String value) {
        this.fax1 = value;
    }

    /**
     * Gets the value of the fax1Ext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax1Ext() {
        return fax1Ext;
    }

    /**
     * Sets the value of the fax1Ext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax1Ext(String value) {
        this.fax1Ext = value;
    }

    /**
     * Gets the value of the fax2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax2() {
        return fax2;
    }

    /**
     * Sets the value of the fax2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax2(String value) {
        this.fax2 = value;
    }

    /**
     * Gets the value of the fax2Ext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax2Ext() {
        return fax2Ext;
    }

    /**
     * Sets the value of the fax2Ext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax2Ext(String value) {
        this.fax2Ext = value;
    }

    /**
     * Gets the value of the imsRowseqnb property.
     * 
     */
    public int getImsRowseqnb() {
        return imsRowseqnb;
    }

    /**
     * Sets the value of the imsRowseqnb property.
     * 
     */
    public void setImsRowseqnb(int value) {
        this.imsRowseqnb = value;
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
     * Gets the value of the mobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the value of the mobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * Gets the value of the mobileExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileExt() {
        return mobileExt;
    }

    /**
     * Sets the value of the mobileExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileExt(String value) {
        this.mobileExt = value;
    }

    /**
     * Gets the value of the nightPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNightPhone() {
        return nightPhone;
    }

    /**
     * Sets the value of the nightPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNightPhone(String value) {
        this.nightPhone = value;
    }

    /**
     * Gets the value of the nightPhoneExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNightPhoneExt() {
        return nightPhoneExt;
    }

    /**
     * Sets the value of the nightPhoneExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNightPhoneExt(String value) {
        this.nightPhoneExt = value;
    }

    /**
     * Gets the value of the pager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPager() {
        return pager;
    }

    /**
     * Sets the value of the pager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPager(String value) {
        this.pager = value;
    }

    /**
     * Gets the value of the pagerExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPagerExt() {
        return pagerExt;
    }

    /**
     * Sets the value of the pagerExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPagerExt(String value) {
        this.pagerExt = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the primaryInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryInd() {
        return primaryInd;
    }

    /**
     * Sets the value of the primaryInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryInd(String value) {
        this.primaryInd = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
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
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the shopCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * Sets the value of the shopCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShopCode(String value) {
        this.shopCode = value;
    }

    /**
     * Gets the value of the mobileSMS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileSMS() {
        return mobileSMS;
    }

    /**
     * Sets the value of the mobileSMS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileSMS(String value) {
        this.mobileSMS = value;
    }

    /**
     * Gets the value of the smsLang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsLang() {
        return smsLang;
    }

    /**
     * Sets the value of the smsLang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsLang(String value) {
        this.smsLang = value;
    }

}

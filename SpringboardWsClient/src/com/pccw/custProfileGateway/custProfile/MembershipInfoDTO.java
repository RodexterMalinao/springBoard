
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MembershipInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MembershipInfoDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="CustLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MemClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MemClubId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MemNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MemStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RowSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MembershipInfoDTO", propOrder = {
    "custLevel",
    "custNum",
    "effEndDate",
    "effStartDate",
    "memClass",
    "memClubId",
    "memNum",
    "memStatus",
    "memType",
    "rowSeqNum",
    "systemId"
})
public class MembershipInfoDTO
    extends ValueObject
{

    @XmlElement(name = "CustLevel")
    protected int custLevel;
    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "EffEndDate")
    protected String effEndDate;
    @XmlElement(name = "EffStartDate")
    protected String effStartDate;
    @XmlElement(name = "MemClass")
    protected String memClass;
    @XmlElement(name = "MemClubId")
    protected String memClubId;
    @XmlElement(name = "MemNum")
    protected String memNum;
    @XmlElement(name = "MemStatus")
    protected String memStatus;
    @XmlElement(name = "MemType")
    protected String memType;
    @XmlElement(name = "RowSeqNum")
    protected int rowSeqNum;
    @XmlElement(name = "SystemId")
    protected String systemId;

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
     * Gets the value of the effEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffEndDate() {
        return effEndDate;
    }

    /**
     * Sets the value of the effEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffEndDate(String value) {
        this.effEndDate = value;
    }

    /**
     * Gets the value of the effStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffStartDate() {
        return effStartDate;
    }

    /**
     * Sets the value of the effStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffStartDate(String value) {
        this.effStartDate = value;
    }

    /**
     * Gets the value of the memClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemClass() {
        return memClass;
    }

    /**
     * Sets the value of the memClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemClass(String value) {
        this.memClass = value;
    }

    /**
     * Gets the value of the memClubId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemClubId() {
        return memClubId;
    }

    /**
     * Sets the value of the memClubId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemClubId(String value) {
        this.memClubId = value;
    }

    /**
     * Gets the value of the memNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemNum() {
        return memNum;
    }

    /**
     * Sets the value of the memNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemNum(String value) {
        this.memNum = value;
    }

    /**
     * Gets the value of the memStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemStatus() {
        return memStatus;
    }

    /**
     * Sets the value of the memStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemStatus(String value) {
        this.memStatus = value;
    }

    /**
     * Gets the value of the memType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemType() {
        return memType;
    }

    /**
     * Sets the value of the memType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemType(String value) {
        this.memType = value;
    }

    /**
     * Gets the value of the rowSeqNum property.
     * 
     */
    public int getRowSeqNum() {
        return rowSeqNum;
    }

    /**
     * Sets the value of the rowSeqNum property.
     * 
     */
    public void setRowSeqNum(int value) {
        this.rowSeqNum = value;
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

}

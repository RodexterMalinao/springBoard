
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConsentHistoryDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsentHistoryDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="ActionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BcpRowSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ParentCustConcentInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemarkSeq" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "ConsentHistoryDTO", propOrder = {
    "actionInd",
    "bcpRowSeqNum",
    "custLevel",
    "custNum",
    "lastUpdBy",
    "lastUpdDate",
    "parentCustConcentInd",
    "remarks",
    "remarkSeq",
    "rowSeqNum",
    "systemId"
})
public class ConsentHistoryDTO
    extends ValueObject
{

    @XmlElement(name = "ActionInd")
    protected String actionInd;
    @XmlElement(name = "BcpRowSeqNum")
    protected int bcpRowSeqNum;
    @XmlElement(name = "CustLevel")
    protected int custLevel;
    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "LastUpdDate")
    protected String lastUpdDate;
    @XmlElement(name = "ParentCustConcentInd")
    protected String parentCustConcentInd;
    @XmlElement(name = "Remarks")
    protected String remarks;
    @XmlElement(name = "RemarkSeq")
    protected int remarkSeq;
    @XmlElement(name = "RowSeqNum")
    protected int rowSeqNum;
    @XmlElement(name = "SystemId")
    protected String systemId;

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
     * Gets the value of the bcpRowSeqNum property.
     * 
     */
    public int getBcpRowSeqNum() {
        return bcpRowSeqNum;
    }

    /**
     * Sets the value of the bcpRowSeqNum property.
     * 
     */
    public void setBcpRowSeqNum(int value) {
        this.bcpRowSeqNum = value;
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
     * Gets the value of the parentCustConcentInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentCustConcentInd() {
        return parentCustConcentInd;
    }

    /**
     * Sets the value of the parentCustConcentInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentCustConcentInd(String value) {
        this.parentCustConcentInd = value;
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
     * Gets the value of the remarkSeq property.
     * 
     */
    public int getRemarkSeq() {
        return remarkSeq;
    }

    /**
     * Sets the value of the remarkSeq property.
     * 
     */
    public void setRemarkSeq(int value) {
        this.remarkSeq = value;
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

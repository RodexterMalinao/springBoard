
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WIPRmksDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WIPRmksDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="ActionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApplDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelatedServices" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemarkSeq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RequestSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestSourceDetails" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "WIPRmksDTO", propOrder = {
    "actionInd",
    "applDate",
    "custLevel",
    "custNum",
    "lastUpdBy",
    "lastUpdDate",
    "relatedServices",
    "remarks",
    "remarkSeq",
    "requestSource",
    "requestSourceDetails",
    "rowSeqNum",
    "systemId"
})
public class WIPRmksDTO
    extends ValueObject
{

    @XmlElement(name = "ActionInd")
    protected String actionInd;
    @XmlElement(name = "ApplDate")
    protected String applDate;
    @XmlElement(name = "CustLevel")
    protected int custLevel;
    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "LastUpdDate")
    protected String lastUpdDate;
    @XmlElement(name = "RelatedServices")
    protected String relatedServices;
    @XmlElement(name = "Remarks")
    protected String remarks;
    @XmlElement(name = "RemarkSeq")
    protected int remarkSeq;
    @XmlElement(name = "RequestSource")
    protected String requestSource;
    @XmlElement(name = "RequestSourceDetails")
    protected String requestSourceDetails;
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
     * Gets the value of the applDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplDate() {
        return applDate;
    }

    /**
     * Sets the value of the applDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplDate(String value) {
        this.applDate = value;
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
     * Gets the value of the relatedServices property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelatedServices() {
        return relatedServices;
    }

    /**
     * Sets the value of the relatedServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelatedServices(String value) {
        this.relatedServices = value;
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
     * Gets the value of the requestSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestSource() {
        return requestSource;
    }

    /**
     * Sets the value of the requestSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestSource(String value) {
        this.requestSource = value;
    }

    /**
     * Gets the value of the requestSourceDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestSourceDetails() {
        return requestSourceDetails;
    }

    /**
     * Sets the value of the requestSourceDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestSourceDetails(String value) {
        this.requestSourceDetails = value;
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

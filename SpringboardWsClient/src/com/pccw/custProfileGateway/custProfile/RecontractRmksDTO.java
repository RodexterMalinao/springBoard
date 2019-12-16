
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecontractRmksDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecontractRmksDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="ActionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastUpdDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OcOrderItemId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OcOrderNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RowSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SrcAcctNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrcCustLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SrcCustName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrcCustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrcIdDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrcIdDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrcServiceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrcSystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TgtAcctNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TgtCustLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TgtCustName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TgtCustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TgtIdDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TgtIdDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TgtServiceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TgtSystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OcCreateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecontractRmksDTO", propOrder = {
    "actionInd",
    "effStartDate",
    "lastUpdBy",
    "lastUpdDate",
    "ocOrderItemId",
    "ocOrderNum",
    "remarks",
    "rowSeqNum",
    "srcAcctNum",
    "srcCustLevel",
    "srcCustName",
    "srcCustNum",
    "srcIdDocNum",
    "srcIdDocType",
    "srcServiceId",
    "srcSystemId",
    "tgtAcctNum",
    "tgtCustLevel",
    "tgtCustName",
    "tgtCustNum",
    "tgtIdDocNum",
    "tgtIdDocType",
    "tgtServiceId",
    "tgtSystemId",
    "serviceNum",
    "ocCreateDate",
    "lob"
})
public class RecontractRmksDTO
    extends ValueObject
{

    @XmlElement(name = "ActionInd")
    protected String actionInd;
    @XmlElement(name = "EffStartDate")
    protected String effStartDate;
    @XmlElement(name = "LastUpdBy")
    protected String lastUpdBy;
    @XmlElement(name = "LastUpdDate")
    protected String lastUpdDate;
    @XmlElement(name = "OcOrderItemId")
    protected int ocOrderItemId;
    @XmlElement(name = "OcOrderNum")
    protected int ocOrderNum;
    @XmlElement(name = "Remarks")
    protected String remarks;
    @XmlElement(name = "RowSeqNum")
    protected int rowSeqNum;
    @XmlElement(name = "SrcAcctNum")
    protected String srcAcctNum;
    @XmlElement(name = "SrcCustLevel")
    protected int srcCustLevel;
    @XmlElement(name = "SrcCustName")
    protected String srcCustName;
    @XmlElement(name = "SrcCustNum")
    protected String srcCustNum;
    @XmlElement(name = "SrcIdDocNum")
    protected String srcIdDocNum;
    @XmlElement(name = "SrcIdDocType")
    protected String srcIdDocType;
    @XmlElement(name = "SrcServiceId")
    protected String srcServiceId;
    @XmlElement(name = "SrcSystemId")
    protected String srcSystemId;
    @XmlElement(name = "TgtAcctNum")
    protected String tgtAcctNum;
    @XmlElement(name = "TgtCustLevel")
    protected int tgtCustLevel;
    @XmlElement(name = "TgtCustName")
    protected String tgtCustName;
    @XmlElement(name = "TgtCustNum")
    protected String tgtCustNum;
    @XmlElement(name = "TgtIdDocNum")
    protected String tgtIdDocNum;
    @XmlElement(name = "TgtIdDocType")
    protected String tgtIdDocType;
    @XmlElement(name = "TgtServiceId")
    protected String tgtServiceId;
    @XmlElement(name = "TgtSystemId")
    protected String tgtSystemId;
    @XmlElement(name = "ServiceNum")
    protected String serviceNum;
    @XmlElement(name = "OcCreateDate")
    protected String ocCreateDate;
    @XmlElement(name = "Lob")
    protected String lob;

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
     * Gets the value of the ocOrderItemId property.
     * 
     */
    public int getOcOrderItemId() {
        return ocOrderItemId;
    }

    /**
     * Sets the value of the ocOrderItemId property.
     * 
     */
    public void setOcOrderItemId(int value) {
        this.ocOrderItemId = value;
    }

    /**
     * Gets the value of the ocOrderNum property.
     * 
     */
    public int getOcOrderNum() {
        return ocOrderNum;
    }

    /**
     * Sets the value of the ocOrderNum property.
     * 
     */
    public void setOcOrderNum(int value) {
        this.ocOrderNum = value;
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
     * Gets the value of the srcAcctNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcAcctNum() {
        return srcAcctNum;
    }

    /**
     * Sets the value of the srcAcctNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcAcctNum(String value) {
        this.srcAcctNum = value;
    }

    /**
     * Gets the value of the srcCustLevel property.
     * 
     */
    public int getSrcCustLevel() {
        return srcCustLevel;
    }

    /**
     * Sets the value of the srcCustLevel property.
     * 
     */
    public void setSrcCustLevel(int value) {
        this.srcCustLevel = value;
    }

    /**
     * Gets the value of the srcCustName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcCustName() {
        return srcCustName;
    }

    /**
     * Sets the value of the srcCustName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcCustName(String value) {
        this.srcCustName = value;
    }

    /**
     * Gets the value of the srcCustNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcCustNum() {
        return srcCustNum;
    }

    /**
     * Sets the value of the srcCustNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcCustNum(String value) {
        this.srcCustNum = value;
    }

    /**
     * Gets the value of the srcIdDocNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcIdDocNum() {
        return srcIdDocNum;
    }

    /**
     * Sets the value of the srcIdDocNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcIdDocNum(String value) {
        this.srcIdDocNum = value;
    }

    /**
     * Gets the value of the srcIdDocType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcIdDocType() {
        return srcIdDocType;
    }

    /**
     * Sets the value of the srcIdDocType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcIdDocType(String value) {
        this.srcIdDocType = value;
    }

    /**
     * Gets the value of the srcServiceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcServiceId() {
        return srcServiceId;
    }

    /**
     * Sets the value of the srcServiceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcServiceId(String value) {
        this.srcServiceId = value;
    }

    /**
     * Gets the value of the srcSystemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcSystemId() {
        return srcSystemId;
    }

    /**
     * Sets the value of the srcSystemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcSystemId(String value) {
        this.srcSystemId = value;
    }

    /**
     * Gets the value of the tgtAcctNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgtAcctNum() {
        return tgtAcctNum;
    }

    /**
     * Sets the value of the tgtAcctNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgtAcctNum(String value) {
        this.tgtAcctNum = value;
    }

    /**
     * Gets the value of the tgtCustLevel property.
     * 
     */
    public int getTgtCustLevel() {
        return tgtCustLevel;
    }

    /**
     * Sets the value of the tgtCustLevel property.
     * 
     */
    public void setTgtCustLevel(int value) {
        this.tgtCustLevel = value;
    }

    /**
     * Gets the value of the tgtCustName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgtCustName() {
        return tgtCustName;
    }

    /**
     * Sets the value of the tgtCustName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgtCustName(String value) {
        this.tgtCustName = value;
    }

    /**
     * Gets the value of the tgtCustNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgtCustNum() {
        return tgtCustNum;
    }

    /**
     * Sets the value of the tgtCustNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgtCustNum(String value) {
        this.tgtCustNum = value;
    }

    /**
     * Gets the value of the tgtIdDocNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgtIdDocNum() {
        return tgtIdDocNum;
    }

    /**
     * Sets the value of the tgtIdDocNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgtIdDocNum(String value) {
        this.tgtIdDocNum = value;
    }

    /**
     * Gets the value of the tgtIdDocType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgtIdDocType() {
        return tgtIdDocType;
    }

    /**
     * Sets the value of the tgtIdDocType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgtIdDocType(String value) {
        this.tgtIdDocType = value;
    }

    /**
     * Gets the value of the tgtServiceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgtServiceId() {
        return tgtServiceId;
    }

    /**
     * Sets the value of the tgtServiceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgtServiceId(String value) {
        this.tgtServiceId = value;
    }

    /**
     * Gets the value of the tgtSystemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgtSystemId() {
        return tgtSystemId;
    }

    /**
     * Sets the value of the tgtSystemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgtSystemId(String value) {
        this.tgtSystemId = value;
    }

    /**
     * Gets the value of the serviceNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceNum() {
        return serviceNum;
    }

    /**
     * Sets the value of the serviceNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceNum(String value) {
        this.serviceNum = value;
    }

    /**
     * Gets the value of the ocCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOcCreateDate() {
        return ocCreateDate;
    }

    /**
     * Sets the value of the ocCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOcCreateDate(String value) {
        this.ocCreateDate = value;
    }

    /**
     * Gets the value of the lob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLob() {
        return lob;
    }

    /**
     * Sets the value of the lob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLob(String value) {
        this.lob = value;
    }

}


package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RemarksDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemarksDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="GroupID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DtlID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemarkContents" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemarkSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Oc_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Remarkid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemarkType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttachTo" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="OrdServiceDTO" type="{http://www.openuri.org/}OrdServiceDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemarksDTO", propOrder = {
    "groupID",
    "dtlID",
    "lob",
    "remarkContents",
    "remarkSequence",
    "updateBy",
    "updateDate",
    "ocId",
    "remarkid",
    "legacyNumber",
    "remarkType",
    "attachTo",
    "ordServiceDTO"
})
public class RemarksDTO
    extends ValueObject
{

    @XmlElement(name = "GroupID")
    protected String groupID;
    @XmlElement(name = "DtlID")
    protected String dtlID;
    @XmlElement(name = "Lob")
    protected String lob;
    @XmlElement(name = "RemarkContents")
    protected String remarkContents;
    @XmlElement(name = "RemarkSequence")
    protected String remarkSequence;
    @XmlElement(name = "UpdateBy")
    protected String updateBy;
    @XmlElement(name = "UpdateDate")
    protected String updateDate;
    @XmlElement(name = "Oc_id")
    protected String ocId;
    @XmlElement(name = "Remarkid")
    protected String remarkid;
    @XmlElement(name = "LegacyNumber")
    protected String legacyNumber;
    @XmlElement(name = "RemarkType")
    protected String remarkType;
    @XmlElement(name = "AttachTo")
    protected Object attachTo;
    @XmlElement(name = "OrdServiceDTO")
    protected OrdServiceDTO ordServiceDTO;

    /**
     * Gets the value of the groupID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * Sets the value of the groupID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupID(String value) {
        this.groupID = value;
    }

    /**
     * Gets the value of the dtlID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtlID() {
        return dtlID;
    }

    /**
     * Sets the value of the dtlID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtlID(String value) {
        this.dtlID = value;
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

    /**
     * Gets the value of the remarkContents property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarkContents() {
        return remarkContents;
    }

    /**
     * Sets the value of the remarkContents property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarkContents(String value) {
        this.remarkContents = value;
    }

    /**
     * Gets the value of the remarkSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarkSequence() {
        return remarkSequence;
    }

    /**
     * Sets the value of the remarkSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarkSequence(String value) {
        this.remarkSequence = value;
    }

    /**
     * Gets the value of the updateBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * Sets the value of the updateBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateBy(String value) {
        this.updateBy = value;
    }

    /**
     * Gets the value of the updateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the value of the updateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateDate(String value) {
        this.updateDate = value;
    }

    /**
     * Gets the value of the ocId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOcId() {
        return ocId;
    }

    /**
     * Sets the value of the ocId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOcId(String value) {
        this.ocId = value;
    }

    /**
     * Gets the value of the remarkid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarkid() {
        return remarkid;
    }

    /**
     * Sets the value of the remarkid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarkid(String value) {
        this.remarkid = value;
    }

    /**
     * Gets the value of the legacyNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyNumber() {
        return legacyNumber;
    }

    /**
     * Sets the value of the legacyNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyNumber(String value) {
        this.legacyNumber = value;
    }

    /**
     * Gets the value of the remarkType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarkType() {
        return remarkType;
    }

    /**
     * Sets the value of the remarkType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarkType(String value) {
        this.remarkType = value;
    }

    /**
     * Gets the value of the attachTo property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAttachTo() {
        return attachTo;
    }

    /**
     * Sets the value of the attachTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAttachTo(Object value) {
        this.attachTo = value;
    }

    /**
     * Gets the value of the ordServiceDTO property.
     * 
     * @return
     *     possible object is
     *     {@link OrdServiceDTO }
     *     
     */
    public OrdServiceDTO getOrdServiceDTO() {
        return ordServiceDTO;
    }

    /**
     * Sets the value of the ordServiceDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdServiceDTO }
     *     
     */
    public void setOrdServiceDTO(OrdServiceDTO value) {
        this.ordServiceDTO = value;
    }

}


package com.pccw.appendOrdRmk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceRequestDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceRequestDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="LegacyOrdNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyActvSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StaffNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OcId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DtlId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DtlSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Boc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceRequestDTO", propOrder = {
    "legacyOrdNum",
    "legacyActvSeq",
    "staffNum",
    "ocId",
    "dtlId",
    "dtlSeq",
    "boc"
})
@XmlSeeAlso({
    OrderRemarkDTO.class
})
public class ServiceRequestDTO
    extends ValueObject
{

    @XmlElement(name = "LegacyOrdNum")
    protected String legacyOrdNum;
    @XmlElement(name = "LegacyActvSeq")
    protected String legacyActvSeq;
    @XmlElement(name = "StaffNum")
    protected String staffNum;
    @XmlElement(name = "OcId")
    protected String ocId;
    @XmlElement(name = "DtlId")
    protected String dtlId;
    @XmlElement(name = "DtlSeq")
    protected String dtlSeq;
    @XmlElement(name = "Boc")
    protected String boc;

    /**
     * Gets the value of the legacyOrdNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyOrdNum() {
        return legacyOrdNum;
    }

    /**
     * Sets the value of the legacyOrdNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyOrdNum(String value) {
        this.legacyOrdNum = value;
    }

    /**
     * Gets the value of the legacyActvSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyActvSeq() {
        return legacyActvSeq;
    }

    /**
     * Sets the value of the legacyActvSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyActvSeq(String value) {
        this.legacyActvSeq = value;
    }

    /**
     * Gets the value of the staffNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaffNum() {
        return staffNum;
    }

    /**
     * Sets the value of the staffNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaffNum(String value) {
        this.staffNum = value;
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
     * Gets the value of the dtlId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtlId() {
        return dtlId;
    }

    /**
     * Sets the value of the dtlId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtlId(String value) {
        this.dtlId = value;
    }

    /**
     * Gets the value of the dtlSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtlSeq() {
        return dtlSeq;
    }

    /**
     * Sets the value of the dtlSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtlSeq(String value) {
        this.dtlSeq = value;
    }

    /**
     * Gets the value of the boc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoc() {
        return boc;
    }

    /**
     * Sets the value of the boc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoc(String value) {
        this.boc = value;
    }

}

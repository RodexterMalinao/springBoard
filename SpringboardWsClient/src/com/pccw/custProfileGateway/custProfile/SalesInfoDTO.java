
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesInfoDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="AmCntctName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AmCntctPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AsmCntctName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AsmCntctPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CmrId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LxnMktSegment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LxnMktSubSegment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "SalesInfoDTO", propOrder = {
    "amCntctName",
    "amCntctPhone",
    "asmCntctName",
    "asmCntctPhone",
    "cmrId",
    "custLevel",
    "custNum",
    "lxnMktSegment",
    "lxnMktSubSegment",
    "rowSeqNum",
    "systemId"
})
public class SalesInfoDTO
    extends ValueObject
{

    @XmlElement(name = "AmCntctName")
    protected String amCntctName;
    @XmlElement(name = "AmCntctPhone")
    protected String amCntctPhone;
    @XmlElement(name = "AsmCntctName")
    protected String asmCntctName;
    @XmlElement(name = "AsmCntctPhone")
    protected String asmCntctPhone;
    @XmlElement(name = "CmrId")
    protected String cmrId;
    @XmlElement(name = "CustLevel")
    protected int custLevel;
    @XmlElement(name = "CustNum")
    protected String custNum;
    @XmlElement(name = "LxnMktSegment")
    protected String lxnMktSegment;
    @XmlElement(name = "LxnMktSubSegment")
    protected String lxnMktSubSegment;
    @XmlElement(name = "RowSeqNum")
    protected int rowSeqNum;
    @XmlElement(name = "SystemId")
    protected String systemId;

    /**
     * Gets the value of the amCntctName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmCntctName() {
        return amCntctName;
    }

    /**
     * Sets the value of the amCntctName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmCntctName(String value) {
        this.amCntctName = value;
    }

    /**
     * Gets the value of the amCntctPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmCntctPhone() {
        return amCntctPhone;
    }

    /**
     * Sets the value of the amCntctPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmCntctPhone(String value) {
        this.amCntctPhone = value;
    }

    /**
     * Gets the value of the asmCntctName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsmCntctName() {
        return asmCntctName;
    }

    /**
     * Sets the value of the asmCntctName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsmCntctName(String value) {
        this.asmCntctName = value;
    }

    /**
     * Gets the value of the asmCntctPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsmCntctPhone() {
        return asmCntctPhone;
    }

    /**
     * Sets the value of the asmCntctPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsmCntctPhone(String value) {
        this.asmCntctPhone = value;
    }

    /**
     * Gets the value of the cmrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmrId() {
        return cmrId;
    }

    /**
     * Sets the value of the cmrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmrId(String value) {
        this.cmrId = value;
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
     * Gets the value of the lxnMktSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLxnMktSegment() {
        return lxnMktSegment;
    }

    /**
     * Sets the value of the lxnMktSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLxnMktSegment(String value) {
        this.lxnMktSegment = value;
    }

    /**
     * Gets the value of the lxnMktSubSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLxnMktSubSegment() {
        return lxnMktSubSegment;
    }

    /**
     * Sets the value of the lxnMktSubSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLxnMktSubSegment(String value) {
        this.lxnMktSubSegment = value;
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

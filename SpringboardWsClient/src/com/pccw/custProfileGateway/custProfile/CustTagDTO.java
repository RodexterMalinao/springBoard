
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustTagDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustTagDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="PremierInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BomCustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PremierCustType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PremierCustDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PremierAddrType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PremierAddrDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustTagDTO", propOrder = {
    "premierInd",
    "bomCustNum",
    "premierCustType",
    "premierCustDesc",
    "premierAddrType",
    "premierAddrDesc"
})
public class CustTagDTO
    extends ValueObject
{

    @XmlElement(name = "PremierInd")
    protected String premierInd;
    @XmlElement(name = "BomCustNum")
    protected String bomCustNum;
    @XmlElement(name = "PremierCustType")
    protected String premierCustType;
    @XmlElement(name = "PremierCustDesc")
    protected String premierCustDesc;
    @XmlElement(name = "PremierAddrType")
    protected String premierAddrType;
    @XmlElement(name = "PremierAddrDesc")
    protected String premierAddrDesc;

    /**
     * Gets the value of the premierInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremierInd() {
        return premierInd;
    }

    /**
     * Sets the value of the premierInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremierInd(String value) {
        this.premierInd = value;
    }

    /**
     * Gets the value of the bomCustNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBomCustNum() {
        return bomCustNum;
    }

    /**
     * Sets the value of the bomCustNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBomCustNum(String value) {
        this.bomCustNum = value;
    }

    /**
     * Gets the value of the premierCustType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremierCustType() {
        return premierCustType;
    }

    /**
     * Sets the value of the premierCustType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremierCustType(String value) {
        this.premierCustType = value;
    }

    /**
     * Gets the value of the premierCustDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremierCustDesc() {
        return premierCustDesc;
    }

    /**
     * Sets the value of the premierCustDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremierCustDesc(String value) {
        this.premierCustDesc = value;
    }

    /**
     * Gets the value of the premierAddrType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremierAddrType() {
        return premierAddrType;
    }

    /**
     * Sets the value of the premierAddrType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremierAddrType(String value) {
        this.premierAddrType = value;
    }

    /**
     * Gets the value of the premierAddrDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremierAddrDesc() {
        return premierAddrDesc;
    }

    /**
     * Sets the value of the premierAddrDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremierAddrDesc(String value) {
        this.premierAddrDesc = value;
    }

}

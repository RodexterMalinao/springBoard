
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationPsefReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationPsefReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdactvSeqNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PsefInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PsefCde" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PsefQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PsefActnCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EqmtColorCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExtnNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PsefExistQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformationPsefReturn", propOrder = {
    "orderId",
    "ordactvSeqNum",
    "psefInd",
    "psefCde",
    "psefQty",
    "psefActnCd",
    "eqmtColorCd",
    "extnNum",
    "psefExistQty"
})
public class OrderInformationPsefReturn {

    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "OrdactvSeqNum")
    protected int ordactvSeqNum;
    @XmlElement(name = "PsefInd")
    protected String psefInd;
    @XmlElement(name = "PsefCde")
    protected String psefCde;
    @XmlElement(name = "PsefQty")
    protected int psefQty;
    @XmlElement(name = "PsefActnCd")
    protected String psefActnCd;
    @XmlElement(name = "EqmtColorCd")
    protected String eqmtColorCd;
    @XmlElement(name = "ExtnNum")
    protected String extnNum;
    @XmlElement(name = "PsefExistQty")
    protected int psefExistQty;

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the ordactvSeqNum property.
     * 
     */
    public int getOrdactvSeqNum() {
        return ordactvSeqNum;
    }

    /**
     * Sets the value of the ordactvSeqNum property.
     * 
     */
    public void setOrdactvSeqNum(int value) {
        this.ordactvSeqNum = value;
    }

    /**
     * Gets the value of the psefInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsefInd() {
        return psefInd;
    }

    /**
     * Sets the value of the psefInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsefInd(String value) {
        this.psefInd = value;
    }

    /**
     * Gets the value of the psefCde property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsefCde() {
        return psefCde;
    }

    /**
     * Sets the value of the psefCde property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsefCde(String value) {
        this.psefCde = value;
    }

    /**
     * Gets the value of the psefQty property.
     * 
     */
    public int getPsefQty() {
        return psefQty;
    }

    /**
     * Sets the value of the psefQty property.
     * 
     */
    public void setPsefQty(int value) {
        this.psefQty = value;
    }

    /**
     * Gets the value of the psefActnCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsefActnCd() {
        return psefActnCd;
    }

    /**
     * Sets the value of the psefActnCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsefActnCd(String value) {
        this.psefActnCd = value;
    }

    /**
     * Gets the value of the eqmtColorCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEqmtColorCd() {
        return eqmtColorCd;
    }

    /**
     * Sets the value of the eqmtColorCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEqmtColorCd(String value) {
        this.eqmtColorCd = value;
    }

    /**
     * Gets the value of the extnNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtnNum() {
        return extnNum;
    }

    /**
     * Sets the value of the extnNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtnNum(String value) {
        this.extnNum = value;
    }

    /**
     * Gets the value of the psefExistQty property.
     * 
     */
    public int getPsefExistQty() {
        return psefExistQty;
    }

    /**
     * Sets the value of the psefExistQty property.
     * 
     */
    public void setPsefExistQty(int value) {
        this.psefExistQty = value;
    }

}

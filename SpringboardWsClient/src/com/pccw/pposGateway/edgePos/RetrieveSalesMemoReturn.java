
package com.pccw.pposGateway.edgePos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetrieveSalesMemoReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveSalesMemoReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HXmlData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DXmlData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TXmlData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CXmlData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MXmlData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LXmlData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrieveSalesMemoReturn", propOrder = {
    "result",
    "hXmlData",
    "dXmlData",
    "tXmlData",
    "cXmlData",
    "mXmlData",
    "lXmlData",
    "errMsg"
})
public class RetrieveSalesMemoReturn {

    @XmlElement(name = "Result")
    protected String result;
    @XmlElement(name = "HXmlData")
    protected String hXmlData;
    @XmlElement(name = "DXmlData")
    protected String dXmlData;
    @XmlElement(name = "TXmlData")
    protected String tXmlData;
    @XmlElement(name = "CXmlData")
    protected String cXmlData;
    @XmlElement(name = "MXmlData")
    protected String mXmlData;
    @XmlElement(name = "LXmlData")
    protected String lXmlData;
    @XmlElement(name = "ErrMsg")
    protected String errMsg;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * Gets the value of the hXmlData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHXmlData() {
        return hXmlData;
    }

    /**
     * Sets the value of the hXmlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHXmlData(String value) {
        this.hXmlData = value;
    }

    /**
     * Gets the value of the dXmlData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDXmlData() {
        return dXmlData;
    }

    /**
     * Sets the value of the dXmlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDXmlData(String value) {
        this.dXmlData = value;
    }

    /**
     * Gets the value of the tXmlData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTXmlData() {
        return tXmlData;
    }

    /**
     * Sets the value of the tXmlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTXmlData(String value) {
        this.tXmlData = value;
    }

    /**
     * Gets the value of the cXmlData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCXmlData() {
        return cXmlData;
    }

    /**
     * Sets the value of the cXmlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCXmlData(String value) {
        this.cXmlData = value;
    }

    /**
     * Gets the value of the mXmlData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMXmlData() {
        return mXmlData;
    }

    /**
     * Sets the value of the mXmlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMXmlData(String value) {
        this.mXmlData = value;
    }

    /**
     * Gets the value of the lXmlData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLXmlData() {
        return lXmlData;
    }

    /**
     * Sets the value of the lXmlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLXmlData(String value) {
        this.lXmlData = value;
    }

    /**
     * Gets the value of the errMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * Sets the value of the errMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrMsg(String value) {
        this.errMsg = value;
    }

}

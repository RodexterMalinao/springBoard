
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataPrivacyNotiInfoLtsDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataPrivacyNotiInfoLtsDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="AgreeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MediaType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MediaValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SentInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SentDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataPrivacyNotiInfoLtsDTO", propOrder = {
    "agreeDate",
    "mediaType",
    "mediaValue",
    "salesCd",
    "remarks",
    "sentInd",
    "sentDate"
})
public class DataPrivacyNotiInfoLtsDTO
    extends ValueObject
{

    @XmlElement(name = "AgreeDate")
    protected String agreeDate;
    @XmlElement(name = "MediaType")
    protected String mediaType;
    @XmlElement(name = "MediaValue")
    protected String mediaValue;
    @XmlElement(name = "SalesCd")
    protected String salesCd;
    @XmlElement(name = "Remarks")
    protected String remarks;
    @XmlElement(name = "SentInd")
    protected String sentInd;
    @XmlElement(name = "SentDate")
    protected String sentDate;

    /**
     * Gets the value of the agreeDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreeDate() {
        return agreeDate;
    }

    /**
     * Sets the value of the agreeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreeDate(String value) {
        this.agreeDate = value;
    }

    /**
     * Gets the value of the mediaType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Sets the value of the mediaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaType(String value) {
        this.mediaType = value;
    }

    /**
     * Gets the value of the mediaValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaValue() {
        return mediaValue;
    }

    /**
     * Sets the value of the mediaValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaValue(String value) {
        this.mediaValue = value;
    }

    /**
     * Gets the value of the salesCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesCd() {
        return salesCd;
    }

    /**
     * Sets the value of the salesCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesCd(String value) {
        this.salesCd = value;
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
     * Gets the value of the sentInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSentInd() {
        return sentInd;
    }

    /**
     * Sets the value of the sentInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSentInd(String value) {
        this.sentInd = value;
    }

    /**
     * Gets the value of the sentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSentDate() {
        return sentDate;
    }

    /**
     * Sets the value of the sentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSentDate(String value) {
        this.sentDate = value;
    }

}

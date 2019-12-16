
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArpuInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArpuInfoDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="ArpuType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrossEffPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IddFreeMin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NetEffPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TpEffDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TpEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TpExpiredMonths" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="PromoStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PromoEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PromotionExpiredMonths" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReturnMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReturnCd" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArpuInfoDTO", propOrder = {
    "arpuType",
    "grossEffPrice",
    "iddFreeMin",
    "netEffPrice",
    "tpEffDate",
    "tpEndDate",
    "category",
    "description",
    "tpExpiredMonths",
    "promoStartDate",
    "promoEndDate",
    "promotionExpiredMonths",
    "returnMsg",
    "returnCd"
})
public class ArpuInfoDTO
    extends ValueObject
{

    @XmlElement(name = "ArpuType")
    protected String arpuType;
    @XmlElement(name = "GrossEffPrice")
    protected String grossEffPrice;
    @XmlElement(name = "IddFreeMin")
    protected String iddFreeMin;
    @XmlElement(name = "NetEffPrice")
    protected String netEffPrice;
    @XmlElement(name = "TpEffDate")
    protected String tpEffDate;
    @XmlElement(name = "TpEndDate")
    protected String tpEndDate;
    @XmlElement(name = "Category")
    protected String category;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "TpExpiredMonths")
    protected double tpExpiredMonths;
    @XmlElement(name = "PromoStartDate")
    protected String promoStartDate;
    @XmlElement(name = "PromoEndDate")
    protected String promoEndDate;
    @XmlElement(name = "PromotionExpiredMonths")
    protected String promotionExpiredMonths;
    @XmlElement(name = "ReturnMsg")
    protected String returnMsg;
    @XmlElement(name = "ReturnCd")
    protected int returnCd;

    /**
     * Gets the value of the arpuType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArpuType() {
        return arpuType;
    }

    /**
     * Sets the value of the arpuType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArpuType(String value) {
        this.arpuType = value;
    }

    /**
     * Gets the value of the grossEffPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrossEffPrice() {
        return grossEffPrice;
    }

    /**
     * Sets the value of the grossEffPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrossEffPrice(String value) {
        this.grossEffPrice = value;
    }

    /**
     * Gets the value of the iddFreeMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIddFreeMin() {
        return iddFreeMin;
    }

    /**
     * Sets the value of the iddFreeMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIddFreeMin(String value) {
        this.iddFreeMin = value;
    }

    /**
     * Gets the value of the netEffPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetEffPrice() {
        return netEffPrice;
    }

    /**
     * Sets the value of the netEffPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetEffPrice(String value) {
        this.netEffPrice = value;
    }

    /**
     * Gets the value of the tpEffDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTpEffDate() {
        return tpEffDate;
    }

    /**
     * Sets the value of the tpEffDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTpEffDate(String value) {
        this.tpEffDate = value;
    }

    /**
     * Gets the value of the tpEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTpEndDate() {
        return tpEndDate;
    }

    /**
     * Sets the value of the tpEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTpEndDate(String value) {
        this.tpEndDate = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the tpExpiredMonths property.
     * 
     */
    public double getTpExpiredMonths() {
        return tpExpiredMonths;
    }

    /**
     * Sets the value of the tpExpiredMonths property.
     * 
     */
    public void setTpExpiredMonths(double value) {
        this.tpExpiredMonths = value;
    }

    /**
     * Gets the value of the promoStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoStartDate() {
        return promoStartDate;
    }

    /**
     * Sets the value of the promoStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoStartDate(String value) {
        this.promoStartDate = value;
    }

    /**
     * Gets the value of the promoEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoEndDate() {
        return promoEndDate;
    }

    /**
     * Sets the value of the promoEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoEndDate(String value) {
        this.promoEndDate = value;
    }

    /**
     * Gets the value of the promotionExpiredMonths property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionExpiredMonths() {
        return promotionExpiredMonths;
    }

    /**
     * Sets the value of the promotionExpiredMonths property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionExpiredMonths(String value) {
        this.promotionExpiredMonths = value;
    }

    /**
     * Gets the value of the returnMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnMsg() {
        return returnMsg;
    }

    /**
     * Sets the value of the returnMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnMsg(String value) {
        this.returnMsg = value;
    }

    /**
     * Gets the value of the returnCd property.
     * 
     */
    public int getReturnCd() {
        return returnCd;
    }

    /**
     * Sets the value of the returnCd property.
     * 
     */
    public void setReturnCd(int value) {
        this.returnCd = value;
    }

}

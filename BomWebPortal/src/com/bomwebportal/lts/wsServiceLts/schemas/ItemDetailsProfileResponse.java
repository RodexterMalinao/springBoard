//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.15 at 10:56:32 AM CST 
//


package com.bomwebportal.lts.wsServiceLts.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OfferDetailsResult" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="arpuType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="tpEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="tpEffDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="grossEffPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="iddFreeMin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="netEffPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="promoEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="promotionExpiredMonths" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="promoStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="returnCd" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="returnMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="tpExpiredMonths" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "offerDetailsResult"
})
@XmlRootElement(name = "ItemDetailsProfileResponse")
public class ItemDetailsProfileResponse {

    @XmlElement(name = "OfferDetailsResult", required = true)
    protected List<ItemDetailsProfileResponse.OfferDetailsResult> offerDetailsResult;

    /**
     * Gets the value of the offerDetailsResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the offerDetailsResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfferDetailsResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemDetailsProfileResponse.OfferDetailsResult }
     * 
     * 
     */
    public List<ItemDetailsProfileResponse.OfferDetailsResult> getOfferDetailsResult() {
        if (offerDetailsResult == null) {
            offerDetailsResult = new ArrayList<ItemDetailsProfileResponse.OfferDetailsResult>();
        }
        return this.offerDetailsResult;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="arpuType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="tpEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="tpEffDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="grossEffPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="iddFreeMin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="netEffPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="promoEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="promotionExpiredMonths" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="promoStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="returnCd" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="returnMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="tpExpiredMonths" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "arpuType",
        "tpEndDate",
        "tpEffDate",
        "grossEffPrice",
        "iddFreeMin",
        "netEffPrice",
        "promoEndDate",
        "promotionExpiredMonths",
        "promoStartDate",
        "returnCd",
        "returnMsg",
        "category",
        "description",
        "tpExpiredMonths"
    })
    public static class OfferDetailsResult {

        protected String arpuType;
        protected String tpEndDate;
        protected String tpEffDate;
        protected String grossEffPrice;
        protected String iddFreeMin;
        protected String netEffPrice;
        protected String promoEndDate;
        protected String promotionExpiredMonths;
        protected String promoStartDate;
        protected int returnCd;
        protected String returnMsg;
        protected String category;
        protected String description;
        protected double tpExpiredMonths;

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

    }

}

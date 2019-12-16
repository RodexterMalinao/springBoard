
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdPricingSchemeDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdPricingSchemeDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="LegacyProdId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyTariffCatg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LegacyTariffHdId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChargeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CombinationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderPricingTierMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrderPricingAssignment" type="{http://www.openuri.org/}OrdPricingAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderPricingTiers" type="{http://www.openuri.org/}ArrayOfOrdPricingTierDTO" minOccurs="0"/>
 *         &lt;element name="WaiveOneTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WaiveOneTimeReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PricingSchemeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OrderOfferProduct" type="{http://www.openuri.org/}OrdOfferProductDTO" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdPricingSchemeDTO" minOccurs="0"/>
 *         &lt;element name="ComponentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderPsef" type="{http://www.openuri.org/}OrdPsefDTO" minOccurs="0"/>
 *         &lt;element name="PricingSchemeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PromotionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PromotionRecurringReductMonth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PromotionRecurringStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PromotionRecurringEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdPricingSchemeDTO", propOrder = {
    "legacyProdId",
    "legacyTariffCatg",
    "legacyTariffHdId",
    "chargeType",
    "combinationId",
    "quantity",
    "orderPricingTierMap",
    "orderPricingAssignment",
    "action",
    "orderPricingTiers",
    "waiveOneTime",
    "waiveOneTimeReason",
    "pricingSchemeID",
    "orderOfferAssignment",
    "orderOfferProduct",
    "profileImage",
    "componentId",
    "productID",
    "orderPsef",
    "pricingSchemeType",
    "promotionCode",
    "promotionRecurringReductMonth",
    "promotionRecurringStartDate",
    "promotionRecurringEndDate"
})
public class OrdPricingSchemeDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "LegacyProdId")
    protected String legacyProdId;
    @XmlElement(name = "LegacyTariffCatg")
    protected String legacyTariffCatg;
    @XmlElement(name = "LegacyTariffHdId")
    protected String legacyTariffHdId;
    @XmlElement(name = "ChargeType")
    protected String chargeType;
    @XmlElement(name = "CombinationId")
    protected String combinationId;
    @XmlElement(name = "Quantity")
    protected int quantity;
    @XmlElement(name = "OrderPricingTierMap")
    protected Map orderPricingTierMap;
    @XmlElement(name = "OrderPricingAssignment")
    protected OrdPricingAssignmentDTO orderPricingAssignment;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderPricingTiers")
    protected ArrayOfOrdPricingTierDTO orderPricingTiers;
    @XmlElement(name = "WaiveOneTime")
    protected String waiveOneTime;
    @XmlElement(name = "WaiveOneTimeReason")
    protected String waiveOneTimeReason;
    @XmlElement(name = "PricingSchemeID")
    protected String pricingSchemeID;
    @XmlElement(name = "OrderOfferAssignment")
    protected OrdOfferAssignmentDTO orderOfferAssignment;
    @XmlElement(name = "OrderOfferProduct")
    protected OrdOfferProductDTO orderOfferProduct;
    @XmlElement(name = "ProfileImage")
    protected OrdPricingSchemeDTO profileImage;
    @XmlElement(name = "ComponentId")
    protected String componentId;
    @XmlElement(name = "ProductID")
    protected String productID;
    @XmlElement(name = "OrderPsef")
    protected OrdPsefDTO orderPsef;
    @XmlElement(name = "PricingSchemeType")
    protected String pricingSchemeType;
    @XmlElement(name = "PromotionCode")
    protected String promotionCode;
    @XmlElement(name = "PromotionRecurringReductMonth")
    protected String promotionRecurringReductMonth;
    @XmlElement(name = "PromotionRecurringStartDate")
    protected String promotionRecurringStartDate;
    @XmlElement(name = "PromotionRecurringEndDate")
    protected String promotionRecurringEndDate;

    /**
     * Gets the value of the legacyProdId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyProdId() {
        return legacyProdId;
    }

    /**
     * Sets the value of the legacyProdId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyProdId(String value) {
        this.legacyProdId = value;
    }

    /**
     * Gets the value of the legacyTariffCatg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyTariffCatg() {
        return legacyTariffCatg;
    }

    /**
     * Sets the value of the legacyTariffCatg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyTariffCatg(String value) {
        this.legacyTariffCatg = value;
    }

    /**
     * Gets the value of the legacyTariffHdId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyTariffHdId() {
        return legacyTariffHdId;
    }

    /**
     * Sets the value of the legacyTariffHdId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyTariffHdId(String value) {
        this.legacyTariffHdId = value;
    }

    /**
     * Gets the value of the chargeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeType() {
        return chargeType;
    }

    /**
     * Sets the value of the chargeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeType(String value) {
        this.chargeType = value;
    }

    /**
     * Gets the value of the combinationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombinationId() {
        return combinationId;
    }

    /**
     * Sets the value of the combinationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombinationId(String value) {
        this.combinationId = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(int value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the orderPricingTierMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderPricingTierMap() {
        return orderPricingTierMap;
    }

    /**
     * Sets the value of the orderPricingTierMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderPricingTierMap(Map value) {
        this.orderPricingTierMap = value;
    }

    /**
     * Gets the value of the orderPricingAssignment property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPricingAssignmentDTO }
     *     
     */
    public OrdPricingAssignmentDTO getOrderPricingAssignment() {
        return orderPricingAssignment;
    }

    /**
     * Sets the value of the orderPricingAssignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPricingAssignmentDTO }
     *     
     */
    public void setOrderPricingAssignment(OrdPricingAssignmentDTO value) {
        this.orderPricingAssignment = value;
    }

    /**
     * Gets the value of the action property.
     * 
     */
    public int getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     */
    public void setAction(int value) {
        this.action = value;
    }

    /**
     * Gets the value of the orderPricingTiers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdPricingTierDTO }
     *     
     */
    public ArrayOfOrdPricingTierDTO getOrderPricingTiers() {
        return orderPricingTiers;
    }

    /**
     * Sets the value of the orderPricingTiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdPricingTierDTO }
     *     
     */
    public void setOrderPricingTiers(ArrayOfOrdPricingTierDTO value) {
        this.orderPricingTiers = value;
    }

    /**
     * Gets the value of the waiveOneTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaiveOneTime() {
        return waiveOneTime;
    }

    /**
     * Sets the value of the waiveOneTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaiveOneTime(String value) {
        this.waiveOneTime = value;
    }

    /**
     * Gets the value of the waiveOneTimeReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaiveOneTimeReason() {
        return waiveOneTimeReason;
    }

    /**
     * Sets the value of the waiveOneTimeReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaiveOneTimeReason(String value) {
        this.waiveOneTimeReason = value;
    }

    /**
     * Gets the value of the pricingSchemeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingSchemeID() {
        return pricingSchemeID;
    }

    /**
     * Sets the value of the pricingSchemeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingSchemeID(String value) {
        this.pricingSchemeID = value;
    }

    /**
     * Gets the value of the orderOfferAssignment property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferAssignmentDTO }
     *     
     */
    public OrdOfferAssignmentDTO getOrderOfferAssignment() {
        return orderOfferAssignment;
    }

    /**
     * Sets the value of the orderOfferAssignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferAssignmentDTO }
     *     
     */
    public void setOrderOfferAssignment(OrdOfferAssignmentDTO value) {
        this.orderOfferAssignment = value;
    }

    /**
     * Gets the value of the orderOfferProduct property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferProductDTO }
     *     
     */
    public OrdOfferProductDTO getOrderOfferProduct() {
        return orderOfferProduct;
    }

    /**
     * Sets the value of the orderOfferProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferProductDTO }
     *     
     */
    public void setOrderOfferProduct(OrdOfferProductDTO value) {
        this.orderOfferProduct = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPricingSchemeDTO }
     *     
     */
    public OrdPricingSchemeDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPricingSchemeDTO }
     *     
     */
    public void setProfileImage(OrdPricingSchemeDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the componentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComponentId() {
        return componentId;
    }

    /**
     * Sets the value of the componentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComponentId(String value) {
        this.componentId = value;
    }

    /**
     * Gets the value of the productID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductID() {
        return productID;
    }

    /**
     * Sets the value of the productID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductID(String value) {
        this.productID = value;
    }

    /**
     * Gets the value of the orderPsef property.
     * 
     * @return
     *     possible object is
     *     {@link OrdPsefDTO }
     *     
     */
    public OrdPsefDTO getOrderPsef() {
        return orderPsef;
    }

    /**
     * Sets the value of the orderPsef property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdPsefDTO }
     *     
     */
    public void setOrderPsef(OrdPsefDTO value) {
        this.orderPsef = value;
    }

    /**
     * Gets the value of the pricingSchemeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingSchemeType() {
        return pricingSchemeType;
    }

    /**
     * Sets the value of the pricingSchemeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingSchemeType(String value) {
        this.pricingSchemeType = value;
    }

    /**
     * Gets the value of the promotionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionCode() {
        return promotionCode;
    }

    /**
     * Sets the value of the promotionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionCode(String value) {
        this.promotionCode = value;
    }

    /**
     * Gets the value of the promotionRecurringReductMonth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionRecurringReductMonth() {
        return promotionRecurringReductMonth;
    }

    /**
     * Sets the value of the promotionRecurringReductMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionRecurringReductMonth(String value) {
        this.promotionRecurringReductMonth = value;
    }

    /**
     * Gets the value of the promotionRecurringStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionRecurringStartDate() {
        return promotionRecurringStartDate;
    }

    /**
     * Sets the value of the promotionRecurringStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionRecurringStartDate(String value) {
        this.promotionRecurringStartDate = value;
    }

    /**
     * Gets the value of the promotionRecurringEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionRecurringEndDate() {
        return promotionRecurringEndDate;
    }

    /**
     * Sets the value of the promotionRecurringEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionRecurringEndDate(String value) {
        this.promotionRecurringEndDate = value;
    }

}

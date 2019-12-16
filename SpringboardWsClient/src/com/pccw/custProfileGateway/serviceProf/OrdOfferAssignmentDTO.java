
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdOfferAssignmentDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdOfferAssignmentDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="ChildOrdOfferAssignments" type="{http://www.openuri.org/}ArrayOfOrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="ParentOrdOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="Domain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDetailMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrderTermMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrderPricingSchemeMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrdOffer" type="{http://www.openuri.org/}OrdOfferDTO" minOccurs="0"/>
 *         &lt;element name="OfferSubID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Products" type="{http://www.openuri.org/}ArrayOfOrdOfferProductDTO" minOccurs="0"/>
 *         &lt;element name="OrderTerms" type="{http://www.openuri.org/}ArrayOfOrdTermsDTO" minOccurs="0"/>
 *         &lt;element name="OrderSubOfferItemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderPricingSchemes" type="{http://www.openuri.org/}ArrayOfOrdPricingSchemeDTO" minOccurs="0"/>
 *         &lt;element name="OrderProducts" type="{http://www.openuri.org/}ArrayOfOrdOfferProductDTO" minOccurs="0"/>
 *         &lt;element name="OrderDetails" type="{http://www.openuri.org/}ArrayOfOrdDetailDTO" minOccurs="0"/>
 *         &lt;element name="OrderDiscounts" type="{http://www.openuri.org/}ArrayOfOrdDiscountDTO" minOccurs="0"/>
 *         &lt;element name="OrderIncentives" type="{http://www.openuri.org/}ArrayOfOrdIncentiveDTO" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="ProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdOfferAssignmentDTO", propOrder = {
    "childOrdOfferAssignments",
    "parentOrdOfferAssignment",
    "domain",
    "orderDetailMap",
    "orderTermMap",
    "orderPricingSchemeMap",
    "ordOffer",
    "offerSubID",
    "products",
    "orderTerms",
    "orderSubOfferItemID",
    "orderPricingSchemes",
    "orderProducts",
    "orderDetails",
    "orderDiscounts",
    "orderIncentives",
    "action",
    "profileImage",
    "productId"
})
public class OrdOfferAssignmentDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "ChildOrdOfferAssignments")
    protected ArrayOfOrdOfferAssignmentDTO childOrdOfferAssignments;
    @XmlElement(name = "ParentOrdOfferAssignment")
    protected OrdOfferAssignmentDTO parentOrdOfferAssignment;
    @XmlElement(name = "Domain")
    protected String domain;
    @XmlElement(name = "OrderDetailMap")
    protected Map orderDetailMap;
    @XmlElement(name = "OrderTermMap")
    protected Map orderTermMap;
    @XmlElement(name = "OrderPricingSchemeMap")
    protected Map orderPricingSchemeMap;
    @XmlElement(name = "OrdOffer")
    protected OrdOfferDTO ordOffer;
    @XmlElement(name = "OfferSubID")
    protected String offerSubID;
    @XmlElement(name = "Products")
    protected ArrayOfOrdOfferProductDTO products;
    @XmlElement(name = "OrderTerms")
    protected ArrayOfOrdTermsDTO orderTerms;
    @XmlElement(name = "OrderSubOfferItemID")
    protected String orderSubOfferItemID;
    @XmlElement(name = "OrderPricingSchemes")
    protected ArrayOfOrdPricingSchemeDTO orderPricingSchemes;
    @XmlElement(name = "OrderProducts")
    protected ArrayOfOrdOfferProductDTO orderProducts;
    @XmlElement(name = "OrderDetails")
    protected ArrayOfOrdDetailDTO orderDetails;
    @XmlElement(name = "OrderDiscounts")
    protected ArrayOfOrdDiscountDTO orderDiscounts;
    @XmlElement(name = "OrderIncentives")
    protected ArrayOfOrdIncentiveDTO orderIncentives;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "ProfileImage")
    protected OrdOfferAssignmentDTO profileImage;
    @XmlElement(name = "ProductId")
    protected String productId;

    /**
     * Gets the value of the childOrdOfferAssignments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdOfferAssignmentDTO }
     *     
     */
    public ArrayOfOrdOfferAssignmentDTO getChildOrdOfferAssignments() {
        return childOrdOfferAssignments;
    }

    /**
     * Sets the value of the childOrdOfferAssignments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdOfferAssignmentDTO }
     *     
     */
    public void setChildOrdOfferAssignments(ArrayOfOrdOfferAssignmentDTO value) {
        this.childOrdOfferAssignments = value;
    }

    /**
     * Gets the value of the parentOrdOfferAssignment property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferAssignmentDTO }
     *     
     */
    public OrdOfferAssignmentDTO getParentOrdOfferAssignment() {
        return parentOrdOfferAssignment;
    }

    /**
     * Sets the value of the parentOrdOfferAssignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferAssignmentDTO }
     *     
     */
    public void setParentOrdOfferAssignment(OrdOfferAssignmentDTO value) {
        this.parentOrdOfferAssignment = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

    /**
     * Gets the value of the orderDetailMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderDetailMap() {
        return orderDetailMap;
    }

    /**
     * Sets the value of the orderDetailMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderDetailMap(Map value) {
        this.orderDetailMap = value;
    }

    /**
     * Gets the value of the orderTermMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderTermMap() {
        return orderTermMap;
    }

    /**
     * Sets the value of the orderTermMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderTermMap(Map value) {
        this.orderTermMap = value;
    }

    /**
     * Gets the value of the orderPricingSchemeMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderPricingSchemeMap() {
        return orderPricingSchemeMap;
    }

    /**
     * Sets the value of the orderPricingSchemeMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderPricingSchemeMap(Map value) {
        this.orderPricingSchemeMap = value;
    }

    /**
     * Gets the value of the ordOffer property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferDTO }
     *     
     */
    public OrdOfferDTO getOrdOffer() {
        return ordOffer;
    }

    /**
     * Sets the value of the ordOffer property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferDTO }
     *     
     */
    public void setOrdOffer(OrdOfferDTO value) {
        this.ordOffer = value;
    }

    /**
     * Gets the value of the offerSubID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferSubID() {
        return offerSubID;
    }

    /**
     * Sets the value of the offerSubID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferSubID(String value) {
        this.offerSubID = value;
    }

    /**
     * Gets the value of the products property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdOfferProductDTO }
     *     
     */
    public ArrayOfOrdOfferProductDTO getProducts() {
        return products;
    }

    /**
     * Sets the value of the products property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdOfferProductDTO }
     *     
     */
    public void setProducts(ArrayOfOrdOfferProductDTO value) {
        this.products = value;
    }

    /**
     * Gets the value of the orderTerms property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdTermsDTO }
     *     
     */
    public ArrayOfOrdTermsDTO getOrderTerms() {
        return orderTerms;
    }

    /**
     * Sets the value of the orderTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdTermsDTO }
     *     
     */
    public void setOrderTerms(ArrayOfOrdTermsDTO value) {
        this.orderTerms = value;
    }

    /**
     * Gets the value of the orderSubOfferItemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderSubOfferItemID() {
        return orderSubOfferItemID;
    }

    /**
     * Sets the value of the orderSubOfferItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderSubOfferItemID(String value) {
        this.orderSubOfferItemID = value;
    }

    /**
     * Gets the value of the orderPricingSchemes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdPricingSchemeDTO }
     *     
     */
    public ArrayOfOrdPricingSchemeDTO getOrderPricingSchemes() {
        return orderPricingSchemes;
    }

    /**
     * Sets the value of the orderPricingSchemes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdPricingSchemeDTO }
     *     
     */
    public void setOrderPricingSchemes(ArrayOfOrdPricingSchemeDTO value) {
        this.orderPricingSchemes = value;
    }

    /**
     * Gets the value of the orderProducts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdOfferProductDTO }
     *     
     */
    public ArrayOfOrdOfferProductDTO getOrderProducts() {
        return orderProducts;
    }

    /**
     * Sets the value of the orderProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdOfferProductDTO }
     *     
     */
    public void setOrderProducts(ArrayOfOrdOfferProductDTO value) {
        this.orderProducts = value;
    }

    /**
     * Gets the value of the orderDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdDetailDTO }
     *     
     */
    public ArrayOfOrdDetailDTO getOrderDetails() {
        return orderDetails;
    }

    /**
     * Sets the value of the orderDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdDetailDTO }
     *     
     */
    public void setOrderDetails(ArrayOfOrdDetailDTO value) {
        this.orderDetails = value;
    }

    /**
     * Gets the value of the orderDiscounts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdDiscountDTO }
     *     
     */
    public ArrayOfOrdDiscountDTO getOrderDiscounts() {
        return orderDiscounts;
    }

    /**
     * Sets the value of the orderDiscounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdDiscountDTO }
     *     
     */
    public void setOrderDiscounts(ArrayOfOrdDiscountDTO value) {
        this.orderDiscounts = value;
    }

    /**
     * Gets the value of the orderIncentives property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdIncentiveDTO }
     *     
     */
    public ArrayOfOrdIncentiveDTO getOrderIncentives() {
        return orderIncentives;
    }

    /**
     * Sets the value of the orderIncentives property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdIncentiveDTO }
     *     
     */
    public void setOrderIncentives(ArrayOfOrdIncentiveDTO value) {
        this.orderIncentives = value;
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
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferAssignmentDTO }
     *     
     */
    public OrdOfferAssignmentDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferAssignmentDTO }
     *     
     */
    public void setProfileImage(OrdOfferAssignmentDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the productId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductId(String value) {
        this.productId = value;
    }

}

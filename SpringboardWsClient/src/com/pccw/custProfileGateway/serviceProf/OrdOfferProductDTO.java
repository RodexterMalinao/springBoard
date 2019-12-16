
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdOfferProductDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdOfferProductDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderDetailMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrderUniqueKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderSubItemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderOfferAssignment" type="{http://www.openuri.org/}OrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OrderComponents" type="{http://www.openuri.org/}ArrayOfOrdOfferProductComponentDTO" minOccurs="0"/>
 *         &lt;element name="OrderDetails" type="{http://www.openuri.org/}ArrayOfOrdDetailDTO" minOccurs="0"/>
 *         &lt;element name="Lob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderPricingSchemeMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrderPricingSchemes" type="{http://www.openuri.org/}ArrayOfOrdPricingSchemeDTO" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdOfferProductDTO" minOccurs="0"/>
 *         &lt;element name="MaxOrderComponentSeq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CcProductSubKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderPsef" type="{http://www.openuri.org/}OrdPsefDTO" minOccurs="0"/>
 *         &lt;element name="ChargeCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdOfferProductDTO", propOrder = {
    "orderDetailMap",
    "orderUniqueKey",
    "orderSubItemID",
    "productID",
    "action",
    "orderOfferAssignment",
    "orderComponents",
    "orderDetails",
    "lob",
    "orderPricingSchemeMap",
    "orderPricingSchemes",
    "profileImage",
    "maxOrderComponentSeq",
    "ccProductSubKey",
    "orderPsef",
    "chargeCategory"
})
public class OrdOfferProductDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderDetailMap")
    protected Map orderDetailMap;
    @XmlElement(name = "OrderUniqueKey")
    protected String orderUniqueKey;
    @XmlElement(name = "OrderSubItemID")
    protected String orderSubItemID;
    @XmlElement(name = "ProductID")
    protected String productID;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderOfferAssignment")
    protected OrdOfferAssignmentDTO orderOfferAssignment;
    @XmlElement(name = "OrderComponents")
    protected ArrayOfOrdOfferProductComponentDTO orderComponents;
    @XmlElement(name = "OrderDetails")
    protected ArrayOfOrdDetailDTO orderDetails;
    @XmlElement(name = "Lob")
    protected String lob;
    @XmlElement(name = "OrderPricingSchemeMap")
    protected Map orderPricingSchemeMap;
    @XmlElement(name = "OrderPricingSchemes")
    protected ArrayOfOrdPricingSchemeDTO orderPricingSchemes;
    @XmlElement(name = "ProfileImage")
    protected OrdOfferProductDTO profileImage;
    @XmlElement(name = "MaxOrderComponentSeq")
    protected int maxOrderComponentSeq;
    @XmlElement(name = "CcProductSubKey")
    protected String ccProductSubKey;
    @XmlElement(name = "OrderPsef")
    protected OrdPsefDTO orderPsef;
    @XmlElement(name = "ChargeCategory")
    protected String chargeCategory;

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
     * Gets the value of the orderUniqueKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderUniqueKey() {
        return orderUniqueKey;
    }

    /**
     * Sets the value of the orderUniqueKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderUniqueKey(String value) {
        this.orderUniqueKey = value;
    }

    /**
     * Gets the value of the orderSubItemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderSubItemID() {
        return orderSubItemID;
    }

    /**
     * Sets the value of the orderSubItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderSubItemID(String value) {
        this.orderSubItemID = value;
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
     * Gets the value of the orderComponents property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdOfferProductComponentDTO }
     *     
     */
    public ArrayOfOrdOfferProductComponentDTO getOrderComponents() {
        return orderComponents;
    }

    /**
     * Sets the value of the orderComponents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdOfferProductComponentDTO }
     *     
     */
    public void setOrderComponents(ArrayOfOrdOfferProductComponentDTO value) {
        this.orderComponents = value;
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
     * Gets the value of the lob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLob() {
        return lob;
    }

    /**
     * Sets the value of the lob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLob(String value) {
        this.lob = value;
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
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferProductDTO }
     *     
     */
    public OrdOfferProductDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferProductDTO }
     *     
     */
    public void setProfileImage(OrdOfferProductDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the maxOrderComponentSeq property.
     * 
     */
    public int getMaxOrderComponentSeq() {
        return maxOrderComponentSeq;
    }

    /**
     * Sets the value of the maxOrderComponentSeq property.
     * 
     */
    public void setMaxOrderComponentSeq(int value) {
        this.maxOrderComponentSeq = value;
    }

    /**
     * Gets the value of the ccProductSubKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcProductSubKey() {
        return ccProductSubKey;
    }

    /**
     * Sets the value of the ccProductSubKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcProductSubKey(String value) {
        this.ccProductSubKey = value;
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
     * Gets the value of the chargeCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeCategory() {
        return chargeCategory;
    }

    /**
     * Sets the value of the chargeCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeCategory(String value) {
        this.chargeCategory = value;
    }

}


package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdOfferProductComponentDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdOfferProductComponentDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderUniqueKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderComponentSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderProduct" type="{http://www.openuri.org/}OrdOfferProductDTO" minOccurs="0"/>
 *         &lt;element name="OrderService" type="{http://www.openuri.org/}OrdServiceDTO" minOccurs="0"/>
 *         &lt;element name="ComponentID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderPsef" type="{http://www.openuri.org/}OrdPsefDTO" minOccurs="0"/>
 *         &lt;element name="OrderPsefAttributes" type="{http://www.openuri.org/}ArrayOfOrdPsefAttributeDTO" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxPsefAttributeSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ExistingQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderServiceComponentSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcComponentSubKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExtensionPsefs" type="{http://www.openuri.org/}ArrayOfOrdPsefDTO" minOccurs="0"/>
 *         &lt;element name="ExtensionPsefMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="ColorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MeasureUnitQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdOfferProductComponentDTO", propOrder = {
    "orderUniqueKey",
    "orderComponentSeq",
    "orderProduct",
    "orderService",
    "componentID",
    "orderPsef",
    "orderPsefAttributes",
    "action",
    "maxPsefAttributeSequence",
    "existingQuantity",
    "quantity",
    "orderServiceComponentSeq",
    "ccComponentSubKey",
    "extensionPsefs",
    "extensionPsefMap",
    "colorCode",
    "measureUnitQty"
})
public class OrdOfferProductComponentDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderUniqueKey")
    protected String orderUniqueKey;
    @XmlElement(name = "OrderComponentSeq")
    protected String orderComponentSeq;
    @XmlElement(name = "OrderProduct")
    protected OrdOfferProductDTO orderProduct;
    @XmlElement(name = "OrderService")
    protected OrdServiceDTO orderService;
    @XmlElement(name = "ComponentID")
    protected String componentID;
    @XmlElement(name = "OrderPsef")
    protected OrdPsefDTO orderPsef;
    @XmlElement(name = "OrderPsefAttributes")
    protected ArrayOfOrdPsefAttributeDTO orderPsefAttributes;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "MaxPsefAttributeSequence")
    protected int maxPsefAttributeSequence;
    @XmlElement(name = "ExistingQuantity")
    protected int existingQuantity;
    @XmlElement(name = "Quantity")
    protected int quantity;
    @XmlElement(name = "OrderServiceComponentSeq")
    protected String orderServiceComponentSeq;
    @XmlElement(name = "CcComponentSubKey")
    protected String ccComponentSubKey;
    @XmlElement(name = "ExtensionPsefs")
    protected ArrayOfOrdPsefDTO extensionPsefs;
    @XmlElement(name = "ExtensionPsefMap")
    protected Map extensionPsefMap;
    @XmlElement(name = "ColorCode")
    protected String colorCode;
    @XmlElement(name = "MeasureUnitQty")
    protected String measureUnitQty;

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
     * Gets the value of the orderComponentSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderComponentSeq() {
        return orderComponentSeq;
    }

    /**
     * Sets the value of the orderComponentSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderComponentSeq(String value) {
        this.orderComponentSeq = value;
    }

    /**
     * Gets the value of the orderProduct property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferProductDTO }
     *     
     */
    public OrdOfferProductDTO getOrderProduct() {
        return orderProduct;
    }

    /**
     * Sets the value of the orderProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferProductDTO }
     *     
     */
    public void setOrderProduct(OrdOfferProductDTO value) {
        this.orderProduct = value;
    }

    /**
     * Gets the value of the orderService property.
     * 
     * @return
     *     possible object is
     *     {@link OrdServiceDTO }
     *     
     */
    public OrdServiceDTO getOrderService() {
        return orderService;
    }

    /**
     * Sets the value of the orderService property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdServiceDTO }
     *     
     */
    public void setOrderService(OrdServiceDTO value) {
        this.orderService = value;
    }

    /**
     * Gets the value of the componentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComponentID() {
        return componentID;
    }

    /**
     * Sets the value of the componentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComponentID(String value) {
        this.componentID = value;
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
     * Gets the value of the orderPsefAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdPsefAttributeDTO }
     *     
     */
    public ArrayOfOrdPsefAttributeDTO getOrderPsefAttributes() {
        return orderPsefAttributes;
    }

    /**
     * Sets the value of the orderPsefAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdPsefAttributeDTO }
     *     
     */
    public void setOrderPsefAttributes(ArrayOfOrdPsefAttributeDTO value) {
        this.orderPsefAttributes = value;
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
     * Gets the value of the maxPsefAttributeSequence property.
     * 
     */
    public int getMaxPsefAttributeSequence() {
        return maxPsefAttributeSequence;
    }

    /**
     * Sets the value of the maxPsefAttributeSequence property.
     * 
     */
    public void setMaxPsefAttributeSequence(int value) {
        this.maxPsefAttributeSequence = value;
    }

    /**
     * Gets the value of the existingQuantity property.
     * 
     */
    public int getExistingQuantity() {
        return existingQuantity;
    }

    /**
     * Sets the value of the existingQuantity property.
     * 
     */
    public void setExistingQuantity(int value) {
        this.existingQuantity = value;
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
     * Gets the value of the orderServiceComponentSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderServiceComponentSeq() {
        return orderServiceComponentSeq;
    }

    /**
     * Sets the value of the orderServiceComponentSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderServiceComponentSeq(String value) {
        this.orderServiceComponentSeq = value;
    }

    /**
     * Gets the value of the ccComponentSubKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcComponentSubKey() {
        return ccComponentSubKey;
    }

    /**
     * Sets the value of the ccComponentSubKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcComponentSubKey(String value) {
        this.ccComponentSubKey = value;
    }

    /**
     * Gets the value of the extensionPsefs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdPsefDTO }
     *     
     */
    public ArrayOfOrdPsefDTO getExtensionPsefs() {
        return extensionPsefs;
    }

    /**
     * Sets the value of the extensionPsefs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdPsefDTO }
     *     
     */
    public void setExtensionPsefs(ArrayOfOrdPsefDTO value) {
        this.extensionPsefs = value;
    }

    /**
     * Gets the value of the extensionPsefMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getExtensionPsefMap() {
        return extensionPsefMap;
    }

    /**
     * Sets the value of the extensionPsefMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setExtensionPsefMap(Map value) {
        this.extensionPsefMap = value;
    }

    /**
     * Gets the value of the colorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets the value of the colorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColorCode(String value) {
        this.colorCode = value;
    }

    /**
     * Gets the value of the measureUnitQty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasureUnitQty() {
        return measureUnitQty;
    }

    /**
     * Sets the value of the measureUnitQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasureUnitQty(String value) {
        this.measureUnitQty = value;
    }

}

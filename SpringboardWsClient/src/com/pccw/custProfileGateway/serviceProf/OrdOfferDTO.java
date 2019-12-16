
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdOfferDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdOfferDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="OrderProducts" type="{http://www.openuri.org/}ArrayOfOrdOfferProductDTO" minOccurs="0"/>
 *         &lt;element name="OrderOfferAssignmentMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderOfferAssignments" type="{http://www.openuri.org/}ArrayOfOrdOfferAssignmentDTO" minOccurs="0"/>
 *         &lt;element name="OfferClassification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfferID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfferReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfferSubReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderOfferItemID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApplicationDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderDetailGroup" type="{http://www.openuri.org/}OrdDetailGroupDTO" minOccurs="0"/>
 *         &lt;element name="ProfileImage" type="{http://www.openuri.org/}OrdOfferDTO" minOccurs="0"/>
 *         &lt;element name="MaxOrderSubItemID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CcOfferSubKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistingQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NewQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdOfferDTO", propOrder = {
    "orderProducts",
    "orderOfferAssignmentMap",
    "action",
    "orderOfferAssignments",
    "offerClassification",
    "offerID",
    "offerReason",
    "offerSubReason",
    "orderOfferItemID",
    "lob",
    "applicationDate",
    "orderDetailGroup",
    "profileImage",
    "maxOrderSubItemID",
    "ccOfferSubKey",
    "existingQuantity",
    "newQuantity"
})
public class OrdOfferDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "OrderProducts")
    protected ArrayOfOrdOfferProductDTO orderProducts;
    @XmlElement(name = "OrderOfferAssignmentMap")
    protected Map orderOfferAssignmentMap;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "OrderOfferAssignments")
    protected ArrayOfOrdOfferAssignmentDTO orderOfferAssignments;
    @XmlElement(name = "OfferClassification")
    protected String offerClassification;
    @XmlElement(name = "OfferID")
    protected String offerID;
    @XmlElement(name = "OfferReason")
    protected String offerReason;
    @XmlElement(name = "OfferSubReason")
    protected String offerSubReason;
    @XmlElement(name = "OrderOfferItemID")
    protected String orderOfferItemID;
    @XmlElement(name = "Lob")
    protected String lob;
    @XmlElement(name = "ApplicationDate")
    protected String applicationDate;
    @XmlElement(name = "OrderDetailGroup")
    protected OrdDetailGroupDTO orderDetailGroup;
    @XmlElement(name = "ProfileImage")
    protected OrdOfferDTO profileImage;
    @XmlElement(name = "MaxOrderSubItemID")
    protected int maxOrderSubItemID;
    @XmlElement(name = "CcOfferSubKey")
    protected String ccOfferSubKey;
    @XmlElement(name = "ExistingQuantity")
    protected int existingQuantity;
    @XmlElement(name = "NewQuantity")
    protected int newQuantity;

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
     * Gets the value of the orderOfferAssignmentMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderOfferAssignmentMap() {
        return orderOfferAssignmentMap;
    }

    /**
     * Sets the value of the orderOfferAssignmentMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderOfferAssignmentMap(Map value) {
        this.orderOfferAssignmentMap = value;
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
     * Gets the value of the orderOfferAssignments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdOfferAssignmentDTO }
     *     
     */
    public ArrayOfOrdOfferAssignmentDTO getOrderOfferAssignments() {
        return orderOfferAssignments;
    }

    /**
     * Sets the value of the orderOfferAssignments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdOfferAssignmentDTO }
     *     
     */
    public void setOrderOfferAssignments(ArrayOfOrdOfferAssignmentDTO value) {
        this.orderOfferAssignments = value;
    }

    /**
     * Gets the value of the offerClassification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferClassification() {
        return offerClassification;
    }

    /**
     * Sets the value of the offerClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferClassification(String value) {
        this.offerClassification = value;
    }

    /**
     * Gets the value of the offerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferID() {
        return offerID;
    }

    /**
     * Sets the value of the offerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferID(String value) {
        this.offerID = value;
    }

    /**
     * Gets the value of the offerReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferReason() {
        return offerReason;
    }

    /**
     * Sets the value of the offerReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferReason(String value) {
        this.offerReason = value;
    }

    /**
     * Gets the value of the offerSubReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferSubReason() {
        return offerSubReason;
    }

    /**
     * Sets the value of the offerSubReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferSubReason(String value) {
        this.offerSubReason = value;
    }

    /**
     * Gets the value of the orderOfferItemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderOfferItemID() {
        return orderOfferItemID;
    }

    /**
     * Sets the value of the orderOfferItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderOfferItemID(String value) {
        this.orderOfferItemID = value;
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
     * Gets the value of the applicationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets the value of the applicationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationDate(String value) {
        this.applicationDate = value;
    }

    /**
     * Gets the value of the orderDetailGroup property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public OrdDetailGroupDTO getOrderDetailGroup() {
        return orderDetailGroup;
    }

    /**
     * Sets the value of the orderDetailGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public void setOrderDetailGroup(OrdDetailGroupDTO value) {
        this.orderDetailGroup = value;
    }

    /**
     * Gets the value of the profileImage property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferDTO }
     *     
     */
    public OrdOfferDTO getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the value of the profileImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferDTO }
     *     
     */
    public void setProfileImage(OrdOfferDTO value) {
        this.profileImage = value;
    }

    /**
     * Gets the value of the maxOrderSubItemID property.
     * 
     */
    public int getMaxOrderSubItemID() {
        return maxOrderSubItemID;
    }

    /**
     * Sets the value of the maxOrderSubItemID property.
     * 
     */
    public void setMaxOrderSubItemID(int value) {
        this.maxOrderSubItemID = value;
    }

    /**
     * Gets the value of the ccOfferSubKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcOfferSubKey() {
        return ccOfferSubKey;
    }

    /**
     * Sets the value of the ccOfferSubKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcOfferSubKey(String value) {
        this.ccOfferSubKey = value;
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
     * Gets the value of the newQuantity property.
     * 
     */
    public int getNewQuantity() {
        return newQuantity;
    }

    /**
     * Sets the value of the newQuantity property.
     * 
     */
    public void setNewQuantity(int value) {
        this.newQuantity = value;
    }

}

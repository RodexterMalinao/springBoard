
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrdDetailGroupDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrdDetailGroupDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObjectWithAction">
 *       &lt;sequence>
 *         &lt;element name="SplitGrpInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GroupID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GroupType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderOffer" type="{http://www.openuri.org/}OrdOfferDTO" minOccurs="0"/>
 *         &lt;element name="OrderDetailGroupMemberMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrderDetailGroupMembers" type="{http://www.openuri.org/}ArrayOfOrdDetailGroupMemberDTO" minOccurs="0"/>
 *         &lt;element name="ChildOrdDetailGroups" type="{http://www.openuri.org/}ArrayOfOrdDetailGroupDTO" minOccurs="0"/>
 *         &lt;element name="ParentOrdDetailGroup" type="{http://www.openuri.org/}OrdDetailGroupDTO" minOccurs="0"/>
 *         &lt;element name="OrderGroupSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxGroupFeatureSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrderGroupFeatures" type="{http://www.openuri.org/}ArrayOfOrdGroupFeatureDTO" minOccurs="0"/>
 *         &lt;element name="OrderGroupFeatureMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrderDetailGroupMemberSvcIDMap" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *         &lt;element name="OrderDetailGroupMemberSvcIDs" type="{http://www.openuri.org/}ArrayOfOrdDetailGroupMemberSvcldOnlyDTO" minOccurs="0"/>
 *         &lt;element name="GroupDetail" type="{http://www.openuri.org/}OrdDetailGroupDetailDTO" minOccurs="0"/>
 *         &lt;element name="NewNetworkInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistNetworkInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistDiversityInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewDiversityInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProfileParentOrdDetailGroup" type="{http://www.openuri.org/}OrdDetailGroupDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrdDetailGroupDTO", propOrder = {
    "splitGrpInd",
    "description",
    "groupID",
    "groupType",
    "orderOffer",
    "orderDetailGroupMemberMap",
    "orderDetailGroupMembers",
    "childOrdDetailGroups",
    "parentOrdDetailGroup",
    "orderGroupSeq",
    "action",
    "maxGroupFeatureSequence",
    "orderGroupFeatures",
    "orderGroupFeatureMap",
    "orderDetailGroupMemberSvcIDMap",
    "orderDetailGroupMemberSvcIDs",
    "groupDetail",
    "newNetworkInd",
    "existNetworkInd",
    "existDiversityInd",
    "newDiversityInd",
    "profileParentOrdDetailGroup"
})
public class OrdDetailGroupDTO
    extends ValueObjectWithAction
{

    @XmlElement(name = "SplitGrpInd")
    protected String splitGrpInd;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "GroupID")
    protected String groupID;
    @XmlElement(name = "GroupType")
    protected String groupType;
    @XmlElement(name = "OrderOffer")
    protected OrdOfferDTO orderOffer;
    @XmlElement(name = "OrderDetailGroupMemberMap")
    protected Map orderDetailGroupMemberMap;
    @XmlElement(name = "OrderDetailGroupMembers")
    protected ArrayOfOrdDetailGroupMemberDTO orderDetailGroupMembers;
    @XmlElement(name = "ChildOrdDetailGroups")
    protected ArrayOfOrdDetailGroupDTO childOrdDetailGroups;
    @XmlElement(name = "ParentOrdDetailGroup")
    protected OrdDetailGroupDTO parentOrdDetailGroup;
    @XmlElement(name = "OrderGroupSeq")
    protected String orderGroupSeq;
    @XmlElement(name = "Action")
    protected int action;
    @XmlElement(name = "MaxGroupFeatureSequence")
    protected int maxGroupFeatureSequence;
    @XmlElement(name = "OrderGroupFeatures")
    protected ArrayOfOrdGroupFeatureDTO orderGroupFeatures;
    @XmlElement(name = "OrderGroupFeatureMap")
    protected Map orderGroupFeatureMap;
    @XmlElement(name = "OrderDetailGroupMemberSvcIDMap")
    protected Map orderDetailGroupMemberSvcIDMap;
    @XmlElement(name = "OrderDetailGroupMemberSvcIDs")
    protected ArrayOfOrdDetailGroupMemberSvcldOnlyDTO orderDetailGroupMemberSvcIDs;
    @XmlElement(name = "GroupDetail")
    protected OrdDetailGroupDetailDTO groupDetail;
    @XmlElement(name = "NewNetworkInd")
    protected String newNetworkInd;
    @XmlElement(name = "ExistNetworkInd")
    protected String existNetworkInd;
    @XmlElement(name = "ExistDiversityInd")
    protected String existDiversityInd;
    @XmlElement(name = "NewDiversityInd")
    protected String newDiversityInd;
    @XmlElement(name = "ProfileParentOrdDetailGroup")
    protected OrdDetailGroupDTO profileParentOrdDetailGroup;

    /**
     * Gets the value of the splitGrpInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSplitGrpInd() {
        return splitGrpInd;
    }

    /**
     * Sets the value of the splitGrpInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSplitGrpInd(String value) {
        this.splitGrpInd = value;
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
     * Gets the value of the groupID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * Sets the value of the groupID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupID(String value) {
        this.groupID = value;
    }

    /**
     * Gets the value of the groupType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupType() {
        return groupType;
    }

    /**
     * Sets the value of the groupType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupType(String value) {
        this.groupType = value;
    }

    /**
     * Gets the value of the orderOffer property.
     * 
     * @return
     *     possible object is
     *     {@link OrdOfferDTO }
     *     
     */
    public OrdOfferDTO getOrderOffer() {
        return orderOffer;
    }

    /**
     * Sets the value of the orderOffer property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdOfferDTO }
     *     
     */
    public void setOrderOffer(OrdOfferDTO value) {
        this.orderOffer = value;
    }

    /**
     * Gets the value of the orderDetailGroupMemberMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderDetailGroupMemberMap() {
        return orderDetailGroupMemberMap;
    }

    /**
     * Sets the value of the orderDetailGroupMemberMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderDetailGroupMemberMap(Map value) {
        this.orderDetailGroupMemberMap = value;
    }

    /**
     * Gets the value of the orderDetailGroupMembers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdDetailGroupMemberDTO }
     *     
     */
    public ArrayOfOrdDetailGroupMemberDTO getOrderDetailGroupMembers() {
        return orderDetailGroupMembers;
    }

    /**
     * Sets the value of the orderDetailGroupMembers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdDetailGroupMemberDTO }
     *     
     */
    public void setOrderDetailGroupMembers(ArrayOfOrdDetailGroupMemberDTO value) {
        this.orderDetailGroupMembers = value;
    }

    /**
     * Gets the value of the childOrdDetailGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdDetailGroupDTO }
     *     
     */
    public ArrayOfOrdDetailGroupDTO getChildOrdDetailGroups() {
        return childOrdDetailGroups;
    }

    /**
     * Sets the value of the childOrdDetailGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdDetailGroupDTO }
     *     
     */
    public void setChildOrdDetailGroups(ArrayOfOrdDetailGroupDTO value) {
        this.childOrdDetailGroups = value;
    }

    /**
     * Gets the value of the parentOrdDetailGroup property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public OrdDetailGroupDTO getParentOrdDetailGroup() {
        return parentOrdDetailGroup;
    }

    /**
     * Sets the value of the parentOrdDetailGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public void setParentOrdDetailGroup(OrdDetailGroupDTO value) {
        this.parentOrdDetailGroup = value;
    }

    /**
     * Gets the value of the orderGroupSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderGroupSeq() {
        return orderGroupSeq;
    }

    /**
     * Sets the value of the orderGroupSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderGroupSeq(String value) {
        this.orderGroupSeq = value;
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
     * Gets the value of the maxGroupFeatureSequence property.
     * 
     */
    public int getMaxGroupFeatureSequence() {
        return maxGroupFeatureSequence;
    }

    /**
     * Sets the value of the maxGroupFeatureSequence property.
     * 
     */
    public void setMaxGroupFeatureSequence(int value) {
        this.maxGroupFeatureSequence = value;
    }

    /**
     * Gets the value of the orderGroupFeatures property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdGroupFeatureDTO }
     *     
     */
    public ArrayOfOrdGroupFeatureDTO getOrderGroupFeatures() {
        return orderGroupFeatures;
    }

    /**
     * Sets the value of the orderGroupFeatures property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdGroupFeatureDTO }
     *     
     */
    public void setOrderGroupFeatures(ArrayOfOrdGroupFeatureDTO value) {
        this.orderGroupFeatures = value;
    }

    /**
     * Gets the value of the orderGroupFeatureMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderGroupFeatureMap() {
        return orderGroupFeatureMap;
    }

    /**
     * Sets the value of the orderGroupFeatureMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderGroupFeatureMap(Map value) {
        this.orderGroupFeatureMap = value;
    }

    /**
     * Gets the value of the orderDetailGroupMemberSvcIDMap property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOrderDetailGroupMemberSvcIDMap() {
        return orderDetailGroupMemberSvcIDMap;
    }

    /**
     * Sets the value of the orderDetailGroupMemberSvcIDMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOrderDetailGroupMemberSvcIDMap(Map value) {
        this.orderDetailGroupMemberSvcIDMap = value;
    }

    /**
     * Gets the value of the orderDetailGroupMemberSvcIDs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrdDetailGroupMemberSvcldOnlyDTO }
     *     
     */
    public ArrayOfOrdDetailGroupMemberSvcldOnlyDTO getOrderDetailGroupMemberSvcIDs() {
        return orderDetailGroupMemberSvcIDs;
    }

    /**
     * Sets the value of the orderDetailGroupMemberSvcIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrdDetailGroupMemberSvcldOnlyDTO }
     *     
     */
    public void setOrderDetailGroupMemberSvcIDs(ArrayOfOrdDetailGroupMemberSvcldOnlyDTO value) {
        this.orderDetailGroupMemberSvcIDs = value;
    }

    /**
     * Gets the value of the groupDetail property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailGroupDetailDTO }
     *     
     */
    public OrdDetailGroupDetailDTO getGroupDetail() {
        return groupDetail;
    }

    /**
     * Sets the value of the groupDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailGroupDetailDTO }
     *     
     */
    public void setGroupDetail(OrdDetailGroupDetailDTO value) {
        this.groupDetail = value;
    }

    /**
     * Gets the value of the newNetworkInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewNetworkInd() {
        return newNetworkInd;
    }

    /**
     * Sets the value of the newNetworkInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewNetworkInd(String value) {
        this.newNetworkInd = value;
    }

    /**
     * Gets the value of the existNetworkInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistNetworkInd() {
        return existNetworkInd;
    }

    /**
     * Sets the value of the existNetworkInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistNetworkInd(String value) {
        this.existNetworkInd = value;
    }

    /**
     * Gets the value of the existDiversityInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistDiversityInd() {
        return existDiversityInd;
    }

    /**
     * Sets the value of the existDiversityInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistDiversityInd(String value) {
        this.existDiversityInd = value;
    }

    /**
     * Gets the value of the newDiversityInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDiversityInd() {
        return newDiversityInd;
    }

    /**
     * Sets the value of the newDiversityInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDiversityInd(String value) {
        this.newDiversityInd = value;
    }

    /**
     * Gets the value of the profileParentOrdDetailGroup property.
     * 
     * @return
     *     possible object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public OrdDetailGroupDTO getProfileParentOrdDetailGroup() {
        return profileParentOrdDetailGroup;
    }

    /**
     * Sets the value of the profileParentOrdDetailGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdDetailGroupDTO }
     *     
     */
    public void setProfileParentOrdDetailGroup(OrdDetailGroupDTO value) {
        this.profileParentOrdDetailGroup = value;
    }

}

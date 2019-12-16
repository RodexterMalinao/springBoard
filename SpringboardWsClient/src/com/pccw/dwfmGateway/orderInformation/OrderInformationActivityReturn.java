
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationActivityReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationActivityReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClsOfSrvCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CniPcfNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CniPcfType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistBbgExchng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistBbgNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistMbgNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistPayphonNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistPayphonPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExistStbType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromLalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrVendorCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LineClsCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModelNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewBbgNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewBbgExchng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewMbgNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewPayphonNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewPayphonPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewStbType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewTariff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OldUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdActvSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdactvStsCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PcmIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelActvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelatedSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelatedTypeOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelOrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelOrderTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelToFmId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubOwnedEqmtCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToCniPcfNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToLalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToRelActvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToRelatedSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToRelatedTypeOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToRelOrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToRelOrderTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToRelToFmId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToVendorCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrGroupType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FrGroupId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToGroupType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToGroupId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformationActivityReturn", propOrder = {
    "clsOfSrvCd",
    "cniPcfNum",
    "cniPcfType",
    "existBbgExchng",
    "existBbgNum",
    "existMbgNum",
    "existPayphonNum",
    "existPayphonPrefix",
    "existSrvNum",
    "existStbType",
    "fromLalType",
    "frVendorCd",
    "lineClsCd",
    "modelNum",
    "newBbgNum",
    "newBbgExchng",
    "newMbgNum",
    "newPayphonNum",
    "newPayphonPrefix",
    "newStbType",
    "newTariff",
    "newUserName",
    "oldUserName",
    "ordActvSeqNum",
    "ordactvStsCd",
    "orderId",
    "pcmIndicator",
    "relActvNum",
    "relatedSrvNum",
    "relatedTypeOfSrv",
    "relOrderId",
    "relOrderTypeCd",
    "relToFmId",
    "subOwnedEqmtCd",
    "toCniPcfNum",
    "toLalType",
    "toRelActvNum",
    "toRelatedSrvNum",
    "toRelatedTypeOfSrv",
    "toRelOrderId",
    "toRelOrderTypeCd",
    "toRelToFmId",
    "toVendorCd",
    "frGroupType",
    "frGroupId",
    "toGroupType",
    "toGroupId"
})
public class OrderInformationActivityReturn {

    @XmlElement(name = "ClsOfSrvCd")
    protected String clsOfSrvCd;
    @XmlElement(name = "CniPcfNum")
    protected String cniPcfNum;
    @XmlElement(name = "CniPcfType")
    protected String cniPcfType;
    @XmlElement(name = "ExistBbgExchng")
    protected String existBbgExchng;
    @XmlElement(name = "ExistBbgNum")
    protected String existBbgNum;
    @XmlElement(name = "ExistMbgNum")
    protected String existMbgNum;
    @XmlElement(name = "ExistPayphonNum")
    protected String existPayphonNum;
    @XmlElement(name = "ExistPayphonPrefix")
    protected String existPayphonPrefix;
    @XmlElement(name = "ExistSrvNum")
    protected String existSrvNum;
    @XmlElement(name = "ExistStbType")
    protected String existStbType;
    @XmlElement(name = "FromLalType")
    protected String fromLalType;
    @XmlElement(name = "FrVendorCd")
    protected String frVendorCd;
    @XmlElement(name = "LineClsCd")
    protected String lineClsCd;
    @XmlElement(name = "ModelNum")
    protected String modelNum;
    @XmlElement(name = "NewBbgNum")
    protected String newBbgNum;
    @XmlElement(name = "NewBbgExchng")
    protected String newBbgExchng;
    @XmlElement(name = "NewMbgNum")
    protected String newMbgNum;
    @XmlElement(name = "NewPayphonNum")
    protected String newPayphonNum;
    @XmlElement(name = "NewPayphonPrefix")
    protected String newPayphonPrefix;
    @XmlElement(name = "NewStbType")
    protected String newStbType;
    @XmlElement(name = "NewTariff")
    protected String newTariff;
    @XmlElement(name = "NewUserName")
    protected String newUserName;
    @XmlElement(name = "OldUserName")
    protected String oldUserName;
    @XmlElement(name = "OrdActvSeqNum")
    protected String ordActvSeqNum;
    @XmlElement(name = "OrdactvStsCd")
    protected String ordactvStsCd;
    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "PcmIndicator")
    protected String pcmIndicator;
    @XmlElement(name = "RelActvNum")
    protected String relActvNum;
    @XmlElement(name = "RelatedSrvNum")
    protected String relatedSrvNum;
    @XmlElement(name = "RelatedTypeOfSrv")
    protected String relatedTypeOfSrv;
    @XmlElement(name = "RelOrderId")
    protected String relOrderId;
    @XmlElement(name = "RelOrderTypeCd")
    protected String relOrderTypeCd;
    @XmlElement(name = "RelToFmId")
    protected String relToFmId;
    @XmlElement(name = "SubOwnedEqmtCd")
    protected String subOwnedEqmtCd;
    @XmlElement(name = "ToCniPcfNum")
    protected String toCniPcfNum;
    @XmlElement(name = "ToLalType")
    protected String toLalType;
    @XmlElement(name = "ToRelActvNum")
    protected String toRelActvNum;
    @XmlElement(name = "ToRelatedSrvNum")
    protected String toRelatedSrvNum;
    @XmlElement(name = "ToRelatedTypeOfSrv")
    protected String toRelatedTypeOfSrv;
    @XmlElement(name = "ToRelOrderId")
    protected String toRelOrderId;
    @XmlElement(name = "ToRelOrderTypeCd")
    protected String toRelOrderTypeCd;
    @XmlElement(name = "ToRelToFmId")
    protected String toRelToFmId;
    @XmlElement(name = "ToVendorCd")
    protected String toVendorCd;
    @XmlElement(name = "FrGroupType")
    protected String frGroupType;
    @XmlElement(name = "FrGroupId")
    protected String frGroupId;
    @XmlElement(name = "ToGroupType")
    protected String toGroupType;
    @XmlElement(name = "ToGroupId")
    protected String toGroupId;

    /**
     * Gets the value of the clsOfSrvCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClsOfSrvCd() {
        return clsOfSrvCd;
    }

    /**
     * Sets the value of the clsOfSrvCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClsOfSrvCd(String value) {
        this.clsOfSrvCd = value;
    }

    /**
     * Gets the value of the cniPcfNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCniPcfNum() {
        return cniPcfNum;
    }

    /**
     * Sets the value of the cniPcfNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCniPcfNum(String value) {
        this.cniPcfNum = value;
    }

    /**
     * Gets the value of the cniPcfType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCniPcfType() {
        return cniPcfType;
    }

    /**
     * Sets the value of the cniPcfType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCniPcfType(String value) {
        this.cniPcfType = value;
    }

    /**
     * Gets the value of the existBbgExchng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistBbgExchng() {
        return existBbgExchng;
    }

    /**
     * Sets the value of the existBbgExchng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistBbgExchng(String value) {
        this.existBbgExchng = value;
    }

    /**
     * Gets the value of the existBbgNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistBbgNum() {
        return existBbgNum;
    }

    /**
     * Sets the value of the existBbgNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistBbgNum(String value) {
        this.existBbgNum = value;
    }

    /**
     * Gets the value of the existMbgNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistMbgNum() {
        return existMbgNum;
    }

    /**
     * Sets the value of the existMbgNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistMbgNum(String value) {
        this.existMbgNum = value;
    }

    /**
     * Gets the value of the existPayphonNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistPayphonNum() {
        return existPayphonNum;
    }

    /**
     * Sets the value of the existPayphonNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistPayphonNum(String value) {
        this.existPayphonNum = value;
    }

    /**
     * Gets the value of the existPayphonPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistPayphonPrefix() {
        return existPayphonPrefix;
    }

    /**
     * Sets the value of the existPayphonPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistPayphonPrefix(String value) {
        this.existPayphonPrefix = value;
    }

    /**
     * Gets the value of the existSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistSrvNum() {
        return existSrvNum;
    }

    /**
     * Sets the value of the existSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistSrvNum(String value) {
        this.existSrvNum = value;
    }

    /**
     * Gets the value of the existStbType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExistStbType() {
        return existStbType;
    }

    /**
     * Sets the value of the existStbType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExistStbType(String value) {
        this.existStbType = value;
    }

    /**
     * Gets the value of the fromLalType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromLalType() {
        return fromLalType;
    }

    /**
     * Sets the value of the fromLalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromLalType(String value) {
        this.fromLalType = value;
    }

    /**
     * Gets the value of the frVendorCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrVendorCd() {
        return frVendorCd;
    }

    /**
     * Sets the value of the frVendorCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrVendorCd(String value) {
        this.frVendorCd = value;
    }

    /**
     * Gets the value of the lineClsCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineClsCd() {
        return lineClsCd;
    }

    /**
     * Sets the value of the lineClsCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineClsCd(String value) {
        this.lineClsCd = value;
    }

    /**
     * Gets the value of the modelNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelNum() {
        return modelNum;
    }

    /**
     * Sets the value of the modelNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelNum(String value) {
        this.modelNum = value;
    }

    /**
     * Gets the value of the newBbgNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewBbgNum() {
        return newBbgNum;
    }

    /**
     * Sets the value of the newBbgNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewBbgNum(String value) {
        this.newBbgNum = value;
    }

    /**
     * Gets the value of the newBbgExchng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewBbgExchng() {
        return newBbgExchng;
    }

    /**
     * Sets the value of the newBbgExchng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewBbgExchng(String value) {
        this.newBbgExchng = value;
    }

    /**
     * Gets the value of the newMbgNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewMbgNum() {
        return newMbgNum;
    }

    /**
     * Sets the value of the newMbgNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewMbgNum(String value) {
        this.newMbgNum = value;
    }

    /**
     * Gets the value of the newPayphonNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPayphonNum() {
        return newPayphonNum;
    }

    /**
     * Sets the value of the newPayphonNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPayphonNum(String value) {
        this.newPayphonNum = value;
    }

    /**
     * Gets the value of the newPayphonPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPayphonPrefix() {
        return newPayphonPrefix;
    }

    /**
     * Sets the value of the newPayphonPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPayphonPrefix(String value) {
        this.newPayphonPrefix = value;
    }

    /**
     * Gets the value of the newStbType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewStbType() {
        return newStbType;
    }

    /**
     * Sets the value of the newStbType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewStbType(String value) {
        this.newStbType = value;
    }

    /**
     * Gets the value of the newTariff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewTariff() {
        return newTariff;
    }

    /**
     * Sets the value of the newTariff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewTariff(String value) {
        this.newTariff = value;
    }

    /**
     * Gets the value of the newUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewUserName() {
        return newUserName;
    }

    /**
     * Sets the value of the newUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewUserName(String value) {
        this.newUserName = value;
    }

    /**
     * Gets the value of the oldUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldUserName() {
        return oldUserName;
    }

    /**
     * Sets the value of the oldUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldUserName(String value) {
        this.oldUserName = value;
    }

    /**
     * Gets the value of the ordActvSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdActvSeqNum() {
        return ordActvSeqNum;
    }

    /**
     * Sets the value of the ordActvSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdActvSeqNum(String value) {
        this.ordActvSeqNum = value;
    }

    /**
     * Gets the value of the ordactvStsCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdactvStsCd() {
        return ordactvStsCd;
    }

    /**
     * Sets the value of the ordactvStsCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdactvStsCd(String value) {
        this.ordactvStsCd = value;
    }

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the pcmIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPcmIndicator() {
        return pcmIndicator;
    }

    /**
     * Sets the value of the pcmIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPcmIndicator(String value) {
        this.pcmIndicator = value;
    }

    /**
     * Gets the value of the relActvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelActvNum() {
        return relActvNum;
    }

    /**
     * Sets the value of the relActvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelActvNum(String value) {
        this.relActvNum = value;
    }

    /**
     * Gets the value of the relatedSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelatedSrvNum() {
        return relatedSrvNum;
    }

    /**
     * Sets the value of the relatedSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelatedSrvNum(String value) {
        this.relatedSrvNum = value;
    }

    /**
     * Gets the value of the relatedTypeOfSrv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelatedTypeOfSrv() {
        return relatedTypeOfSrv;
    }

    /**
     * Sets the value of the relatedTypeOfSrv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelatedTypeOfSrv(String value) {
        this.relatedTypeOfSrv = value;
    }

    /**
     * Gets the value of the relOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelOrderId() {
        return relOrderId;
    }

    /**
     * Sets the value of the relOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelOrderId(String value) {
        this.relOrderId = value;
    }

    /**
     * Gets the value of the relOrderTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelOrderTypeCd() {
        return relOrderTypeCd;
    }

    /**
     * Sets the value of the relOrderTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelOrderTypeCd(String value) {
        this.relOrderTypeCd = value;
    }

    /**
     * Gets the value of the relToFmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelToFmId() {
        return relToFmId;
    }

    /**
     * Sets the value of the relToFmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelToFmId(String value) {
        this.relToFmId = value;
    }

    /**
     * Gets the value of the subOwnedEqmtCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubOwnedEqmtCd() {
        return subOwnedEqmtCd;
    }

    /**
     * Sets the value of the subOwnedEqmtCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubOwnedEqmtCd(String value) {
        this.subOwnedEqmtCd = value;
    }

    /**
     * Gets the value of the toCniPcfNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToCniPcfNum() {
        return toCniPcfNum;
    }

    /**
     * Sets the value of the toCniPcfNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToCniPcfNum(String value) {
        this.toCniPcfNum = value;
    }

    /**
     * Gets the value of the toLalType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToLalType() {
        return toLalType;
    }

    /**
     * Sets the value of the toLalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToLalType(String value) {
        this.toLalType = value;
    }

    /**
     * Gets the value of the toRelActvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRelActvNum() {
        return toRelActvNum;
    }

    /**
     * Sets the value of the toRelActvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRelActvNum(String value) {
        this.toRelActvNum = value;
    }

    /**
     * Gets the value of the toRelatedSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRelatedSrvNum() {
        return toRelatedSrvNum;
    }

    /**
     * Sets the value of the toRelatedSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRelatedSrvNum(String value) {
        this.toRelatedSrvNum = value;
    }

    /**
     * Gets the value of the toRelatedTypeOfSrv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRelatedTypeOfSrv() {
        return toRelatedTypeOfSrv;
    }

    /**
     * Sets the value of the toRelatedTypeOfSrv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRelatedTypeOfSrv(String value) {
        this.toRelatedTypeOfSrv = value;
    }

    /**
     * Gets the value of the toRelOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRelOrderId() {
        return toRelOrderId;
    }

    /**
     * Sets the value of the toRelOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRelOrderId(String value) {
        this.toRelOrderId = value;
    }

    /**
     * Gets the value of the toRelOrderTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRelOrderTypeCd() {
        return toRelOrderTypeCd;
    }

    /**
     * Sets the value of the toRelOrderTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRelOrderTypeCd(String value) {
        this.toRelOrderTypeCd = value;
    }

    /**
     * Gets the value of the toRelToFmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRelToFmId() {
        return toRelToFmId;
    }

    /**
     * Sets the value of the toRelToFmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRelToFmId(String value) {
        this.toRelToFmId = value;
    }

    /**
     * Gets the value of the toVendorCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToVendorCd() {
        return toVendorCd;
    }

    /**
     * Sets the value of the toVendorCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToVendorCd(String value) {
        this.toVendorCd = value;
    }

    /**
     * Gets the value of the frGroupType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrGroupType() {
        return frGroupType;
    }

    /**
     * Sets the value of the frGroupType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrGroupType(String value) {
        this.frGroupType = value;
    }

    /**
     * Gets the value of the frGroupId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrGroupId() {
        return frGroupId;
    }

    /**
     * Sets the value of the frGroupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrGroupId(String value) {
        this.frGroupId = value;
    }

    /**
     * Gets the value of the toGroupType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToGroupType() {
        return toGroupType;
    }

    /**
     * Sets the value of the toGroupType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToGroupType(String value) {
        this.toGroupType = value;
    }

    /**
     * Gets the value of the toGroupId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToGroupId() {
        return toGroupId;
    }

    /**
     * Sets the value of the toGroupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToGroupId(String value) {
        this.toGroupId = value;
    }

}

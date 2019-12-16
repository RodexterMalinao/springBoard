
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderDetailsOutA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderDetailsOutA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdactvSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdactvStsCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComplEntryDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffBillDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FeaChngInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelatedTypeOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelatedSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CniPcfType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CniPcfInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CniPcfTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CniPcfNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrcTgtInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdDocumentNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MembershipInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstItemNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastItemNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstActvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastActvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesStaffId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesmanRefId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesStaffInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InOutBoundInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisconnectReaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisconnectReaDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcnInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PendSwapRelDnInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SwapDnInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SwapDnRelItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SwapDnRelItemSts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BomInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TlOrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TlOrderTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TlOrdactvSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TlRequestItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderDetailsOutA", propOrder = {
    "requestId",
    "requestItemId",
    "orderId",
    "ordactvSeqNum",
    "ordactvStsCd",
    "complEntryDate",
    "effBillDate",
    "feaChngInd",
    "orderTypeCd",
    "relatedTypeOfSrv",
    "relatedSrvNum",
    "cniPcfType",
    "cniPcfInd",
    "cniPcfTerm",
    "cniPcfNum",
    "srcTgtInd",
    "subNum",
    "idTypeCd",
    "idDocumentNum",
    "membershipInd",
    "firstItemNum",
    "lastItemNum",
    "firstActvNum",
    "lastActvNum",
    "salesStaffId",
    "salesmanRefId",
    "salesStaffInd",
    "inOutBoundInd",
    "disconnectReaCd",
    "disconnectReaDesc",
    "ccnInd",
    "pendSwapRelDnInd",
    "swapDnInd",
    "swapDnRelItemId",
    "swapDnRelItemSts",
    "bomInd",
    "tlOrderId",
    "tlOrderTypeCd",
    "tlOrdactvSeqNum",
    "tlRequestItemId"
})
public class OrderDetailsOutA {

    @XmlElement(name = "RequestId")
    protected String requestId;
    @XmlElement(name = "RequestItemId")
    protected String requestItemId;
    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "OrdactvSeqNum")
    protected String ordactvSeqNum;
    @XmlElement(name = "OrdactvStsCd")
    protected String ordactvStsCd;
    @XmlElement(name = "ComplEntryDate")
    protected String complEntryDate;
    @XmlElement(name = "EffBillDate")
    protected String effBillDate;
    @XmlElement(name = "FeaChngInd")
    protected String feaChngInd;
    @XmlElement(name = "OrderTypeCd")
    protected String orderTypeCd;
    @XmlElement(name = "RelatedTypeOfSrv")
    protected String relatedTypeOfSrv;
    @XmlElement(name = "RelatedSrvNum")
    protected String relatedSrvNum;
    @XmlElement(name = "CniPcfType")
    protected String cniPcfType;
    @XmlElement(name = "CniPcfInd")
    protected String cniPcfInd;
    @XmlElement(name = "CniPcfTerm")
    protected String cniPcfTerm;
    @XmlElement(name = "CniPcfNum")
    protected String cniPcfNum;
    @XmlElement(name = "SrcTgtInd")
    protected String srcTgtInd;
    @XmlElement(name = "SubNum")
    protected String subNum;
    @XmlElement(name = "IdTypeCd")
    protected String idTypeCd;
    @XmlElement(name = "IdDocumentNum")
    protected String idDocumentNum;
    @XmlElement(name = "MembershipInd")
    protected String membershipInd;
    @XmlElement(name = "FirstItemNum")
    protected String firstItemNum;
    @XmlElement(name = "LastItemNum")
    protected String lastItemNum;
    @XmlElement(name = "FirstActvNum")
    protected String firstActvNum;
    @XmlElement(name = "LastActvNum")
    protected String lastActvNum;
    @XmlElement(name = "SalesStaffId")
    protected String salesStaffId;
    @XmlElement(name = "SalesmanRefId")
    protected String salesmanRefId;
    @XmlElement(name = "SalesStaffInd")
    protected String salesStaffInd;
    @XmlElement(name = "InOutBoundInd")
    protected String inOutBoundInd;
    @XmlElement(name = "DisconnectReaCd")
    protected String disconnectReaCd;
    @XmlElement(name = "DisconnectReaDesc")
    protected String disconnectReaDesc;
    @XmlElement(name = "CcnInd")
    protected String ccnInd;
    @XmlElement(name = "PendSwapRelDnInd")
    protected String pendSwapRelDnInd;
    @XmlElement(name = "SwapDnInd")
    protected String swapDnInd;
    @XmlElement(name = "SwapDnRelItemId")
    protected String swapDnRelItemId;
    @XmlElement(name = "SwapDnRelItemSts")
    protected String swapDnRelItemSts;
    @XmlElement(name = "BomInd")
    protected String bomInd;
    @XmlElement(name = "TlOrderId")
    protected String tlOrderId;
    @XmlElement(name = "TlOrderTypeCd")
    protected String tlOrderTypeCd;
    @XmlElement(name = "TlOrdactvSeqNum")
    protected String tlOrdactvSeqNum;
    @XmlElement(name = "TlRequestItemId")
    protected String tlRequestItemId;

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the requestItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestItemId() {
        return requestItemId;
    }

    /**
     * Sets the value of the requestItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestItemId(String value) {
        this.requestItemId = value;
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
     * Gets the value of the ordactvSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdactvSeqNum() {
        return ordactvSeqNum;
    }

    /**
     * Sets the value of the ordactvSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdactvSeqNum(String value) {
        this.ordactvSeqNum = value;
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
     * Gets the value of the complEntryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplEntryDate() {
        return complEntryDate;
    }

    /**
     * Sets the value of the complEntryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplEntryDate(String value) {
        this.complEntryDate = value;
    }

    /**
     * Gets the value of the effBillDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffBillDate() {
        return effBillDate;
    }

    /**
     * Sets the value of the effBillDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffBillDate(String value) {
        this.effBillDate = value;
    }

    /**
     * Gets the value of the feaChngInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeaChngInd() {
        return feaChngInd;
    }

    /**
     * Sets the value of the feaChngInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeaChngInd(String value) {
        this.feaChngInd = value;
    }

    /**
     * Gets the value of the orderTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderTypeCd() {
        return orderTypeCd;
    }

    /**
     * Sets the value of the orderTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderTypeCd(String value) {
        this.orderTypeCd = value;
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
     * Gets the value of the cniPcfInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCniPcfInd() {
        return cniPcfInd;
    }

    /**
     * Sets the value of the cniPcfInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCniPcfInd(String value) {
        this.cniPcfInd = value;
    }

    /**
     * Gets the value of the cniPcfTerm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCniPcfTerm() {
        return cniPcfTerm;
    }

    /**
     * Sets the value of the cniPcfTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCniPcfTerm(String value) {
        this.cniPcfTerm = value;
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
     * Gets the value of the srcTgtInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcTgtInd() {
        return srcTgtInd;
    }

    /**
     * Sets the value of the srcTgtInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcTgtInd(String value) {
        this.srcTgtInd = value;
    }

    /**
     * Gets the value of the subNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubNum() {
        return subNum;
    }

    /**
     * Sets the value of the subNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubNum(String value) {
        this.subNum = value;
    }

    /**
     * Gets the value of the idTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTypeCd() {
        return idTypeCd;
    }

    /**
     * Sets the value of the idTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTypeCd(String value) {
        this.idTypeCd = value;
    }

    /**
     * Gets the value of the idDocumentNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDocumentNum() {
        return idDocumentNum;
    }

    /**
     * Sets the value of the idDocumentNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDocumentNum(String value) {
        this.idDocumentNum = value;
    }

    /**
     * Gets the value of the membershipInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMembershipInd() {
        return membershipInd;
    }

    /**
     * Sets the value of the membershipInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMembershipInd(String value) {
        this.membershipInd = value;
    }

    /**
     * Gets the value of the firstItemNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstItemNum() {
        return firstItemNum;
    }

    /**
     * Sets the value of the firstItemNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstItemNum(String value) {
        this.firstItemNum = value;
    }

    /**
     * Gets the value of the lastItemNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastItemNum() {
        return lastItemNum;
    }

    /**
     * Sets the value of the lastItemNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastItemNum(String value) {
        this.lastItemNum = value;
    }

    /**
     * Gets the value of the firstActvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstActvNum() {
        return firstActvNum;
    }

    /**
     * Sets the value of the firstActvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstActvNum(String value) {
        this.firstActvNum = value;
    }

    /**
     * Gets the value of the lastActvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastActvNum() {
        return lastActvNum;
    }

    /**
     * Sets the value of the lastActvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastActvNum(String value) {
        this.lastActvNum = value;
    }

    /**
     * Gets the value of the salesStaffId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesStaffId() {
        return salesStaffId;
    }

    /**
     * Sets the value of the salesStaffId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesStaffId(String value) {
        this.salesStaffId = value;
    }

    /**
     * Gets the value of the salesmanRefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesmanRefId() {
        return salesmanRefId;
    }

    /**
     * Sets the value of the salesmanRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesmanRefId(String value) {
        this.salesmanRefId = value;
    }

    /**
     * Gets the value of the salesStaffInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesStaffInd() {
        return salesStaffInd;
    }

    /**
     * Sets the value of the salesStaffInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesStaffInd(String value) {
        this.salesStaffInd = value;
    }

    /**
     * Gets the value of the inOutBoundInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInOutBoundInd() {
        return inOutBoundInd;
    }

    /**
     * Sets the value of the inOutBoundInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInOutBoundInd(String value) {
        this.inOutBoundInd = value;
    }

    /**
     * Gets the value of the disconnectReaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisconnectReaCd() {
        return disconnectReaCd;
    }

    /**
     * Sets the value of the disconnectReaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisconnectReaCd(String value) {
        this.disconnectReaCd = value;
    }

    /**
     * Gets the value of the disconnectReaDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisconnectReaDesc() {
        return disconnectReaDesc;
    }

    /**
     * Sets the value of the disconnectReaDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisconnectReaDesc(String value) {
        this.disconnectReaDesc = value;
    }

    /**
     * Gets the value of the ccnInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcnInd() {
        return ccnInd;
    }

    /**
     * Sets the value of the ccnInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcnInd(String value) {
        this.ccnInd = value;
    }

    /**
     * Gets the value of the pendSwapRelDnInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPendSwapRelDnInd() {
        return pendSwapRelDnInd;
    }

    /**
     * Sets the value of the pendSwapRelDnInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPendSwapRelDnInd(String value) {
        this.pendSwapRelDnInd = value;
    }

    /**
     * Gets the value of the swapDnInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwapDnInd() {
        return swapDnInd;
    }

    /**
     * Sets the value of the swapDnInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwapDnInd(String value) {
        this.swapDnInd = value;
    }

    /**
     * Gets the value of the swapDnRelItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwapDnRelItemId() {
        return swapDnRelItemId;
    }

    /**
     * Sets the value of the swapDnRelItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwapDnRelItemId(String value) {
        this.swapDnRelItemId = value;
    }

    /**
     * Gets the value of the swapDnRelItemSts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwapDnRelItemSts() {
        return swapDnRelItemSts;
    }

    /**
     * Sets the value of the swapDnRelItemSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwapDnRelItemSts(String value) {
        this.swapDnRelItemSts = value;
    }

    /**
     * Gets the value of the bomInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBomInd() {
        return bomInd;
    }

    /**
     * Sets the value of the bomInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBomInd(String value) {
        this.bomInd = value;
    }

    /**
     * Gets the value of the tlOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTlOrderId() {
        return tlOrderId;
    }

    /**
     * Sets the value of the tlOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTlOrderId(String value) {
        this.tlOrderId = value;
    }

    /**
     * Gets the value of the tlOrderTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTlOrderTypeCd() {
        return tlOrderTypeCd;
    }

    /**
     * Sets the value of the tlOrderTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTlOrderTypeCd(String value) {
        this.tlOrderTypeCd = value;
    }

    /**
     * Gets the value of the tlOrdactvSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTlOrdactvSeqNum() {
        return tlOrdactvSeqNum;
    }

    /**
     * Sets the value of the tlOrdactvSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTlOrdactvSeqNum(String value) {
        this.tlOrdactvSeqNum = value;
    }

    /**
     * Gets the value of the tlRequestItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTlRequestItemId() {
        return tlRequestItemId;
    }

    /**
     * Sets the value of the tlRequestItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTlRequestItemId(String value) {
        this.tlRequestItemId = value;
    }

}

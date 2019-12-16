
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExtgInvInfoReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExtgInvInfoReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StsCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StsCdDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StsDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdslInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BbgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BuildId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CitnGpNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpsdfCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpSubCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DptypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ext2nTie1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ext2nTie2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExtLalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExtShrSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExtShrSrvType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FznInventInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MbgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OoaInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PayPhonId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PndgOrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PndgOrderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelIMSOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelIMSOrdType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IddTosInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TosColsrv1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TosColsrv2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DpCat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtgInvInfoReturn", propOrder = {
    "stsCd",
    "stsCdDesc",
    "stsDate",
    "adslInd",
    "bbgId",
    "buildId",
    "citnGpNum",
    "dpsdfCd",
    "dpSubCat",
    "dptypeCd",
    "ext2NTie1",
    "ext2NTie2",
    "extLalType",
    "extShrSrvNum",
    "extShrSrvType",
    "fznInventInd",
    "mbgId",
    "ooaInd",
    "payPhonId",
    "pndgOrderId",
    "pndgOrderType",
    "relIMSOrder",
    "relIMSOrdType",
    "iddTosInd",
    "tosColsrv1",
    "tosColsrv2",
    "dpCat"
})
public class ExtgInvInfoReturn {

    @XmlElement(name = "StsCd")
    protected String stsCd;
    @XmlElement(name = "StsCdDesc")
    protected String stsCdDesc;
    @XmlElement(name = "StsDate")
    protected String stsDate;
    @XmlElement(name = "AdslInd")
    protected String adslInd;
    @XmlElement(name = "BbgId")
    protected String bbgId;
    @XmlElement(name = "BuildId")
    protected String buildId;
    @XmlElement(name = "CitnGpNum")
    protected String citnGpNum;
    @XmlElement(name = "DpsdfCd")
    protected String dpsdfCd;
    @XmlElement(name = "DpSubCat")
    protected String dpSubCat;
    @XmlElement(name = "DptypeCd")
    protected String dptypeCd;
    @XmlElement(name = "Ext2nTie1")
    protected String ext2NTie1;
    @XmlElement(name = "Ext2nTie2")
    protected String ext2NTie2;
    @XmlElement(name = "ExtLalType")
    protected String extLalType;
    @XmlElement(name = "ExtShrSrvNum")
    protected String extShrSrvNum;
    @XmlElement(name = "ExtShrSrvType")
    protected String extShrSrvType;
    @XmlElement(name = "FznInventInd")
    protected String fznInventInd;
    @XmlElement(name = "MbgId")
    protected String mbgId;
    @XmlElement(name = "OoaInd")
    protected String ooaInd;
    @XmlElement(name = "PayPhonId")
    protected String payPhonId;
    @XmlElement(name = "PndgOrderId")
    protected String pndgOrderId;
    @XmlElement(name = "PndgOrderType")
    protected String pndgOrderType;
    @XmlElement(name = "RelIMSOrder")
    protected String relIMSOrder;
    @XmlElement(name = "RelIMSOrdType")
    protected String relIMSOrdType;
    @XmlElement(name = "IddTosInd")
    protected String iddTosInd;
    @XmlElement(name = "TosColsrv1")
    protected String tosColsrv1;
    @XmlElement(name = "TosColsrv2")
    protected String tosColsrv2;
    @XmlElement(name = "DpCat")
    protected String dpCat;

    /**
     * Gets the value of the stsCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStsCd() {
        return stsCd;
    }

    /**
     * Sets the value of the stsCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStsCd(String value) {
        this.stsCd = value;
    }

    /**
     * Gets the value of the stsCdDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStsCdDesc() {
        return stsCdDesc;
    }

    /**
     * Sets the value of the stsCdDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStsCdDesc(String value) {
        this.stsCdDesc = value;
    }

    /**
     * Gets the value of the stsDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStsDate() {
        return stsDate;
    }

    /**
     * Sets the value of the stsDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStsDate(String value) {
        this.stsDate = value;
    }

    /**
     * Gets the value of the adslInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdslInd() {
        return adslInd;
    }

    /**
     * Sets the value of the adslInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdslInd(String value) {
        this.adslInd = value;
    }

    /**
     * Gets the value of the bbgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBbgId() {
        return bbgId;
    }

    /**
     * Sets the value of the bbgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBbgId(String value) {
        this.bbgId = value;
    }

    /**
     * Gets the value of the buildId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildId() {
        return buildId;
    }

    /**
     * Sets the value of the buildId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildId(String value) {
        this.buildId = value;
    }

    /**
     * Gets the value of the citnGpNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitnGpNum() {
        return citnGpNum;
    }

    /**
     * Sets the value of the citnGpNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitnGpNum(String value) {
        this.citnGpNum = value;
    }

    /**
     * Gets the value of the dpsdfCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpsdfCd() {
        return dpsdfCd;
    }

    /**
     * Sets the value of the dpsdfCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpsdfCd(String value) {
        this.dpsdfCd = value;
    }

    /**
     * Gets the value of the dpSubCat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpSubCat() {
        return dpSubCat;
    }

    /**
     * Sets the value of the dpSubCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpSubCat(String value) {
        this.dpSubCat = value;
    }

    /**
     * Gets the value of the dptypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDptypeCd() {
        return dptypeCd;
    }

    /**
     * Sets the value of the dptypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDptypeCd(String value) {
        this.dptypeCd = value;
    }

    /**
     * Gets the value of the ext2NTie1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExt2NTie1() {
        return ext2NTie1;
    }

    /**
     * Sets the value of the ext2NTie1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExt2NTie1(String value) {
        this.ext2NTie1 = value;
    }

    /**
     * Gets the value of the ext2NTie2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExt2NTie2() {
        return ext2NTie2;
    }

    /**
     * Sets the value of the ext2NTie2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExt2NTie2(String value) {
        this.ext2NTie2 = value;
    }

    /**
     * Gets the value of the extLalType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtLalType() {
        return extLalType;
    }

    /**
     * Sets the value of the extLalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtLalType(String value) {
        this.extLalType = value;
    }

    /**
     * Gets the value of the extShrSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtShrSrvNum() {
        return extShrSrvNum;
    }

    /**
     * Sets the value of the extShrSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtShrSrvNum(String value) {
        this.extShrSrvNum = value;
    }

    /**
     * Gets the value of the extShrSrvType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtShrSrvType() {
        return extShrSrvType;
    }

    /**
     * Sets the value of the extShrSrvType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtShrSrvType(String value) {
        this.extShrSrvType = value;
    }

    /**
     * Gets the value of the fznInventInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFznInventInd() {
        return fznInventInd;
    }

    /**
     * Sets the value of the fznInventInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFznInventInd(String value) {
        this.fznInventInd = value;
    }

    /**
     * Gets the value of the mbgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMbgId() {
        return mbgId;
    }

    /**
     * Sets the value of the mbgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMbgId(String value) {
        this.mbgId = value;
    }

    /**
     * Gets the value of the ooaInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOoaInd() {
        return ooaInd;
    }

    /**
     * Sets the value of the ooaInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOoaInd(String value) {
        this.ooaInd = value;
    }

    /**
     * Gets the value of the payPhonId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayPhonId() {
        return payPhonId;
    }

    /**
     * Sets the value of the payPhonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayPhonId(String value) {
        this.payPhonId = value;
    }

    /**
     * Gets the value of the pndgOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPndgOrderId() {
        return pndgOrderId;
    }

    /**
     * Sets the value of the pndgOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPndgOrderId(String value) {
        this.pndgOrderId = value;
    }

    /**
     * Gets the value of the pndgOrderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPndgOrderType() {
        return pndgOrderType;
    }

    /**
     * Sets the value of the pndgOrderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPndgOrderType(String value) {
        this.pndgOrderType = value;
    }

    /**
     * Gets the value of the relIMSOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelIMSOrder() {
        return relIMSOrder;
    }

    /**
     * Sets the value of the relIMSOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelIMSOrder(String value) {
        this.relIMSOrder = value;
    }

    /**
     * Gets the value of the relIMSOrdType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelIMSOrdType() {
        return relIMSOrdType;
    }

    /**
     * Sets the value of the relIMSOrdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelIMSOrdType(String value) {
        this.relIMSOrdType = value;
    }

    /**
     * Gets the value of the iddTosInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIddTosInd() {
        return iddTosInd;
    }

    /**
     * Sets the value of the iddTosInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIddTosInd(String value) {
        this.iddTosInd = value;
    }

    /**
     * Gets the value of the tosColsrv1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTosColsrv1() {
        return tosColsrv1;
    }

    /**
     * Sets the value of the tosColsrv1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTosColsrv1(String value) {
        this.tosColsrv1 = value;
    }

    /**
     * Gets the value of the tosColsrv2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTosColsrv2() {
        return tosColsrv2;
    }

    /**
     * Sets the value of the tosColsrv2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTosColsrv2(String value) {
        this.tosColsrv2 = value;
    }

    /**
     * Gets the value of the dpCat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDpCat() {
        return dpCat;
    }

    /**
     * Sets the value of the dpCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDpCat(String value) {
        this.dpCat = value;
    }

}

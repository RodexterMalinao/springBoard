
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationOutList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationOutList">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}DragonReturn">
 *       &lt;sequence>
 *         &lt;element name="ActvReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationActivityReturn" minOccurs="0"/>
 *         &lt;element name="ContinueKeyReturn" type="{http://www.openuri.org/}OrderInformationContinueKeyReturn" minOccurs="0"/>
 *         &lt;element name="JunctionRmkReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationRemarkReturn" minOccurs="0"/>
 *         &lt;element name="OrdInfo" type="{http://www.openuri.org/}OrderInformationReturn" minOccurs="0"/>
 *         &lt;element name="OrdRemarkReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationRemarkReturn" minOccurs="0"/>
 *         &lt;element name="OrigialVoiceReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationOriVoiceReturn" minOccurs="0"/>
 *         &lt;element name="PsefReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationPsefReturn" minOccurs="0"/>
 *         &lt;element name="RmkReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationRemarkReturn" minOccurs="0"/>
 *         &lt;element name="TargetVoiceReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationTgtVoiceReturn" minOccurs="0"/>
 *         &lt;element name="OriginalDataReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationOriDataReturn" minOccurs="0"/>
 *         &lt;element name="TargetDataReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationTgtDataReturn" minOccurs="0"/>
 *         &lt;element name="TiePairReturn" type="{http://www.openuri.org/}ArrayOfOrderInformationTiePairReturn" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformationOutList", propOrder = {
    "actvReturn",
    "continueKeyReturn",
    "junctionRmkReturn",
    "ordInfo",
    "ordRemarkReturn",
    "origialVoiceReturn",
    "psefReturn",
    "rmkReturn",
    "targetVoiceReturn",
    "originalDataReturn",
    "targetDataReturn",
    "tiePairReturn"
})
public class OrderInformationOutList
    extends DragonReturn
{

    @XmlElement(name = "ActvReturn")
    protected ArrayOfOrderInformationActivityReturn actvReturn;
    @XmlElement(name = "ContinueKeyReturn")
    protected OrderInformationContinueKeyReturn continueKeyReturn;
    @XmlElement(name = "JunctionRmkReturn")
    protected ArrayOfOrderInformationRemarkReturn junctionRmkReturn;
    @XmlElement(name = "OrdInfo")
    protected OrderInformationReturn ordInfo;
    @XmlElement(name = "OrdRemarkReturn")
    protected ArrayOfOrderInformationRemarkReturn ordRemarkReturn;
    @XmlElement(name = "OrigialVoiceReturn")
    protected ArrayOfOrderInformationOriVoiceReturn origialVoiceReturn;
    @XmlElement(name = "PsefReturn")
    protected ArrayOfOrderInformationPsefReturn psefReturn;
    @XmlElement(name = "RmkReturn")
    protected ArrayOfOrderInformationRemarkReturn rmkReturn;
    @XmlElement(name = "TargetVoiceReturn")
    protected ArrayOfOrderInformationTgtVoiceReturn targetVoiceReturn;
    @XmlElement(name = "OriginalDataReturn")
    protected ArrayOfOrderInformationOriDataReturn originalDataReturn;
    @XmlElement(name = "TargetDataReturn")
    protected ArrayOfOrderInformationTgtDataReturn targetDataReturn;
    @XmlElement(name = "TiePairReturn")
    protected ArrayOfOrderInformationTiePairReturn tiePairReturn;

    /**
     * Gets the value of the actvReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationActivityReturn }
     *     
     */
    public ArrayOfOrderInformationActivityReturn getActvReturn() {
        return actvReturn;
    }

    /**
     * Sets the value of the actvReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationActivityReturn }
     *     
     */
    public void setActvReturn(ArrayOfOrderInformationActivityReturn value) {
        this.actvReturn = value;
    }

    /**
     * Gets the value of the continueKeyReturn property.
     * 
     * @return
     *     possible object is
     *     {@link OrderInformationContinueKeyReturn }
     *     
     */
    public OrderInformationContinueKeyReturn getContinueKeyReturn() {
        return continueKeyReturn;
    }

    /**
     * Sets the value of the continueKeyReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderInformationContinueKeyReturn }
     *     
     */
    public void setContinueKeyReturn(OrderInformationContinueKeyReturn value) {
        this.continueKeyReturn = value;
    }

    /**
     * Gets the value of the junctionRmkReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationRemarkReturn }
     *     
     */
    public ArrayOfOrderInformationRemarkReturn getJunctionRmkReturn() {
        return junctionRmkReturn;
    }

    /**
     * Sets the value of the junctionRmkReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationRemarkReturn }
     *     
     */
    public void setJunctionRmkReturn(ArrayOfOrderInformationRemarkReturn value) {
        this.junctionRmkReturn = value;
    }

    /**
     * Gets the value of the ordInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrderInformationReturn }
     *     
     */
    public OrderInformationReturn getOrdInfo() {
        return ordInfo;
    }

    /**
     * Sets the value of the ordInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderInformationReturn }
     *     
     */
    public void setOrdInfo(OrderInformationReturn value) {
        this.ordInfo = value;
    }

    /**
     * Gets the value of the ordRemarkReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationRemarkReturn }
     *     
     */
    public ArrayOfOrderInformationRemarkReturn getOrdRemarkReturn() {
        return ordRemarkReturn;
    }

    /**
     * Sets the value of the ordRemarkReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationRemarkReturn }
     *     
     */
    public void setOrdRemarkReturn(ArrayOfOrderInformationRemarkReturn value) {
        this.ordRemarkReturn = value;
    }

    /**
     * Gets the value of the origialVoiceReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationOriVoiceReturn }
     *     
     */
    public ArrayOfOrderInformationOriVoiceReturn getOrigialVoiceReturn() {
        return origialVoiceReturn;
    }

    /**
     * Sets the value of the origialVoiceReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationOriVoiceReturn }
     *     
     */
    public void setOrigialVoiceReturn(ArrayOfOrderInformationOriVoiceReturn value) {
        this.origialVoiceReturn = value;
    }

    /**
     * Gets the value of the psefReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationPsefReturn }
     *     
     */
    public ArrayOfOrderInformationPsefReturn getPsefReturn() {
        return psefReturn;
    }

    /**
     * Sets the value of the psefReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationPsefReturn }
     *     
     */
    public void setPsefReturn(ArrayOfOrderInformationPsefReturn value) {
        this.psefReturn = value;
    }

    /**
     * Gets the value of the rmkReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationRemarkReturn }
     *     
     */
    public ArrayOfOrderInformationRemarkReturn getRmkReturn() {
        return rmkReturn;
    }

    /**
     * Sets the value of the rmkReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationRemarkReturn }
     *     
     */
    public void setRmkReturn(ArrayOfOrderInformationRemarkReturn value) {
        this.rmkReturn = value;
    }

    /**
     * Gets the value of the targetVoiceReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationTgtVoiceReturn }
     *     
     */
    public ArrayOfOrderInformationTgtVoiceReturn getTargetVoiceReturn() {
        return targetVoiceReturn;
    }

    /**
     * Sets the value of the targetVoiceReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationTgtVoiceReturn }
     *     
     */
    public void setTargetVoiceReturn(ArrayOfOrderInformationTgtVoiceReturn value) {
        this.targetVoiceReturn = value;
    }

    /**
     * Gets the value of the originalDataReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationOriDataReturn }
     *     
     */
    public ArrayOfOrderInformationOriDataReturn getOriginalDataReturn() {
        return originalDataReturn;
    }

    /**
     * Sets the value of the originalDataReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationOriDataReturn }
     *     
     */
    public void setOriginalDataReturn(ArrayOfOrderInformationOriDataReturn value) {
        this.originalDataReturn = value;
    }

    /**
     * Gets the value of the targetDataReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationTgtDataReturn }
     *     
     */
    public ArrayOfOrderInformationTgtDataReturn getTargetDataReturn() {
        return targetDataReturn;
    }

    /**
     * Sets the value of the targetDataReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationTgtDataReturn }
     *     
     */
    public void setTargetDataReturn(ArrayOfOrderInformationTgtDataReturn value) {
        this.targetDataReturn = value;
    }

    /**
     * Gets the value of the tiePairReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrderInformationTiePairReturn }
     *     
     */
    public ArrayOfOrderInformationTiePairReturn getTiePairReturn() {
        return tiePairReturn;
    }

    /**
     * Sets the value of the tiePairReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrderInformationTiePairReturn }
     *     
     */
    public void setTiePairReturn(ArrayOfOrderInformationTiePairReturn value) {
        this.tiePairReturn = value;
    }

}

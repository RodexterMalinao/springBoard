
package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInformationTiePairReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInformationTiePairReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FromToInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrdactvSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TwoNTiePairNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TwoNTiePairSeqNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfSrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformationTiePairReturn", propOrder = {
    "fromToInd",
    "lalType",
    "ordactvSeqNum",
    "orderId",
    "srvNum",
    "twoNTiePairNum",
    "twoNTiePairSeqNum",
    "typeOfSrv"
})
public class OrderInformationTiePairReturn {

    @XmlElement(name = "FromToInd")
    protected String fromToInd;
    @XmlElement(name = "LalType")
    protected String lalType;
    @XmlElement(name = "OrdactvSeqNum")
    protected String ordactvSeqNum;
    @XmlElement(name = "OrderId")
    protected String orderId;
    @XmlElement(name = "SrvNum")
    protected String srvNum;
    @XmlElement(name = "TwoNTiePairNum")
    protected String twoNTiePairNum;
    @XmlElement(name = "TwoNTiePairSeqNum")
    protected String twoNTiePairSeqNum;
    @XmlElement(name = "TypeOfSrv")
    protected String typeOfSrv;

    /**
     * Gets the value of the fromToInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromToInd() {
        return fromToInd;
    }

    /**
     * Sets the value of the fromToInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromToInd(String value) {
        this.fromToInd = value;
    }

    /**
     * Gets the value of the lalType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLalType() {
        return lalType;
    }

    /**
     * Sets the value of the lalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLalType(String value) {
        this.lalType = value;
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
     * Gets the value of the srvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvNum() {
        return srvNum;
    }

    /**
     * Sets the value of the srvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvNum(String value) {
        this.srvNum = value;
    }

    /**
     * Gets the value of the twoNTiePairNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwoNTiePairNum() {
        return twoNTiePairNum;
    }

    /**
     * Sets the value of the twoNTiePairNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTwoNTiePairNum(String value) {
        this.twoNTiePairNum = value;
    }

    /**
     * Gets the value of the twoNTiePairSeqNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwoNTiePairSeqNum() {
        return twoNTiePairSeqNum;
    }

    /**
     * Sets the value of the twoNTiePairSeqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTwoNTiePairSeqNum(String value) {
        this.twoNTiePairSeqNum = value;
    }

    /**
     * Gets the value of the typeOfSrv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfSrv() {
        return typeOfSrv;
    }

    /**
     * Sets the value of the typeOfSrv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfSrv(String value) {
        this.typeOfSrv = value;
    }

}


package com.pccw.dwfmGateway.orderInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderDetailsOutD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderDetailsOutD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LalShrNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LalSrvType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TwoNTie1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TwoNTie2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OldNewInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderDetailsOutD", propOrder = {
    "lalType",
    "lalShrNum",
    "lalSrvType",
    "twoNTie1",
    "twoNTie2",
    "oldNewInd"
})
public class OrderDetailsOutD {

    @XmlElement(name = "LalType")
    protected String lalType;
    @XmlElement(name = "LalShrNum")
    protected String lalShrNum;
    @XmlElement(name = "LalSrvType")
    protected String lalSrvType;
    @XmlElement(name = "TwoNTie1")
    protected String twoNTie1;
    @XmlElement(name = "TwoNTie2")
    protected String twoNTie2;
    @XmlElement(name = "OldNewInd")
    protected String oldNewInd;

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
     * Gets the value of the lalShrNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLalShrNum() {
        return lalShrNum;
    }

    /**
     * Sets the value of the lalShrNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLalShrNum(String value) {
        this.lalShrNum = value;
    }

    /**
     * Gets the value of the lalSrvType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLalSrvType() {
        return lalSrvType;
    }

    /**
     * Sets the value of the lalSrvType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLalSrvType(String value) {
        this.lalSrvType = value;
    }

    /**
     * Gets the value of the twoNTie1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwoNTie1() {
        return twoNTie1;
    }

    /**
     * Sets the value of the twoNTie1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTwoNTie1(String value) {
        this.twoNTie1 = value;
    }

    /**
     * Gets the value of the twoNTie2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwoNTie2() {
        return twoNTie2;
    }

    /**
     * Sets the value of the twoNTie2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTwoNTie2(String value) {
        this.twoNTie2 = value;
    }

    /**
     * Gets the value of the oldNewInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldNewInd() {
        return oldNewInd;
    }

    /**
     * Sets the value of the oldNewInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldNewInd(String value) {
        this.oldNewInd = value;
    }

}

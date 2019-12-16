
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="psCustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="psSrvType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="psSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "psCustNum",
    "psSrvType",
    "psSrvNum"
})
@XmlRootElement(name = "getCrCallPlanEnquiry")
public class GetCrCallPlanEnquiry {

    protected String psCustNum;
    protected String psSrvType;
    protected String psSrvNum;

    /**
     * Gets the value of the psCustNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsCustNum() {
        return psCustNum;
    }

    /**
     * Sets the value of the psCustNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsCustNum(String value) {
        this.psCustNum = value;
    }

    /**
     * Gets the value of the psSrvType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsSrvType() {
        return psSrvType;
    }

    /**
     * Sets the value of the psSrvType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsSrvType(String value) {
        this.psSrvType = value;
    }

    /**
     * Gets the value of the psSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsSrvNum() {
        return psSrvNum;
    }

    /**
     * Sets the value of the psSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsSrvNum(String value) {
        this.psSrvNum = value;
    }

}

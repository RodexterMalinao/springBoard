
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
 *         &lt;element name="psServiceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="psServiceNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="psCustNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "psServiceType",
    "psServiceNum",
    "psCustNum"
})
@XmlRootElement(name = "getTermPlanEnquiry")
public class GetTermPlanEnquiry {

    protected String psServiceType;
    protected String psServiceNum;
    protected String psCustNum;

    /**
     * Gets the value of the psServiceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsServiceType() {
        return psServiceType;
    }

    /**
     * Sets the value of the psServiceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsServiceType(String value) {
        this.psServiceType = value;
    }

    /**
     * Gets the value of the psServiceNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsServiceNum() {
        return psServiceNum;
    }

    /**
     * Sets the value of the psServiceNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsServiceNum(String value) {
        this.psServiceNum = value;
    }

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

}

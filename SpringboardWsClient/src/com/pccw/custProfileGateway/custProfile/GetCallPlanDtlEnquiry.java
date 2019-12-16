
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
 *         &lt;element name="psPlanCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="psPlanHolderKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="psSrvSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "psPlanCd",
    "psPlanHolderKey",
    "psSrvSeq"
})
@XmlRootElement(name = "getCallPlanDtlEnquiry")
public class GetCallPlanDtlEnquiry {

    protected String psPlanCd;
    protected String psPlanHolderKey;
    protected String psSrvSeq;

    /**
     * Gets the value of the psPlanCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsPlanCd() {
        return psPlanCd;
    }

    /**
     * Sets the value of the psPlanCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsPlanCd(String value) {
        this.psPlanCd = value;
    }

    /**
     * Gets the value of the psPlanHolderKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsPlanHolderKey() {
        return psPlanHolderKey;
    }

    /**
     * Sets the value of the psPlanHolderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsPlanHolderKey(String value) {
        this.psPlanHolderKey = value;
    }

    /**
     * Gets the value of the psSrvSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsSrvSeq() {
        return psSrvSeq;
    }

    /**
     * Sets the value of the psSrvSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsSrvSeq(String value) {
        this.psSrvSeq = value;
    }

}


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
 *         &lt;element name="psDN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="psDocNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="psSrvType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="psDocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "psDN",
    "psDocNum",
    "psSrvType",
    "psDocType"
})
@XmlRootElement(name = "getCustNumEnquiry")
public class GetCustNumEnquiry {

    protected String psDN;
    protected String psDocNum;
    protected String psSrvType;
    protected String psDocType;

    /**
     * Gets the value of the psDN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsDN() {
        return psDN;
    }

    /**
     * Sets the value of the psDN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsDN(String value) {
        this.psDN = value;
    }

    /**
     * Gets the value of the psDocNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsDocNum() {
        return psDocNum;
    }

    /**
     * Sets the value of the psDocNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsDocNum(String value) {
        this.psDocNum = value;
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
     * Gets the value of the psDocType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsDocType() {
        return psDocType;
    }

    /**
     * Sets the value of the psDocType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsDocType(String value) {
        this.psDocType = value;
    }

}

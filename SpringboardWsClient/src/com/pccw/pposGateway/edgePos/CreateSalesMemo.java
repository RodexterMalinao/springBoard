
package com.pccw.pposGateway.edgePos;

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
 *         &lt;element name="pStoreIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pOrderRegInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "pStoreIP",
    "pOrderRegInfo"
})
@XmlRootElement(name = "createSalesMemo")
public class CreateSalesMemo {

    protected String pStoreIP;
    protected String pOrderRegInfo;

    /**
     * Gets the value of the pStoreIP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPStoreIP() {
        return pStoreIP;
    }

    /**
     * Sets the value of the pStoreIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPStoreIP(String value) {
        this.pStoreIP = value;
    }

    /**
     * Gets the value of the pOrderRegInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOrderRegInfo() {
        return pOrderRegInfo;
    }

    /**
     * Sets the value of the pOrderRegInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOrderRegInfo(String value) {
        this.pOrderRegInfo = value;
    }

}

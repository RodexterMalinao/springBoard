
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
 *         &lt;element name="pSalesMemoNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "pSalesMemoNum"
})
@XmlRootElement(name = "retrieveSalesMemo")
public class RetrieveSalesMemo {

    protected String pStoreIP;
    protected String pSalesMemoNum;

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
     * Gets the value of the pSalesMemoNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPSalesMemoNum() {
        return pSalesMemoNum;
    }

    /**
     * Sets the value of the pSalesMemoNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPSalesMemoNum(String value) {
        this.pSalesMemoNum = value;
    }

}

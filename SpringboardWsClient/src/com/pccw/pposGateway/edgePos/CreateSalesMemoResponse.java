
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
 *         &lt;element name="createSalesMemoResult" type="{http://www.openuri.org/}SalesMemoResponse" minOccurs="0"/>
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
    "createSalesMemoResult"
})
@XmlRootElement(name = "createSalesMemoResponse")
public class CreateSalesMemoResponse {

    protected SalesMemoResponse createSalesMemoResult;

    /**
     * Gets the value of the createSalesMemoResult property.
     * 
     * @return
     *     possible object is
     *     {@link SalesMemoResponse }
     *     
     */
    public SalesMemoResponse getCreateSalesMemoResult() {
        return createSalesMemoResult;
    }

    /**
     * Sets the value of the createSalesMemoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesMemoResponse }
     *     
     */
    public void setCreateSalesMemoResult(SalesMemoResponse value) {
        this.createSalesMemoResult = value;
    }

}

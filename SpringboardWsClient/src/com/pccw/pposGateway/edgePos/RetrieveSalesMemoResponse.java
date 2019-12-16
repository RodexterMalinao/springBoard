
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
 *         &lt;element name="retrieveSalesMemoResult" type="{http://www.openuri.org/}RetrieveSalesMemoReturn" minOccurs="0"/>
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
    "retrieveSalesMemoResult"
})
@XmlRootElement(name = "retrieveSalesMemoResponse")
public class RetrieveSalesMemoResponse {

    protected RetrieveSalesMemoReturn retrieveSalesMemoResult;

    /**
     * Gets the value of the retrieveSalesMemoResult property.
     * 
     * @return
     *     possible object is
     *     {@link RetrieveSalesMemoReturn }
     *     
     */
    public RetrieveSalesMemoReturn getRetrieveSalesMemoResult() {
        return retrieveSalesMemoResult;
    }

    /**
     * Sets the value of the retrieveSalesMemoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetrieveSalesMemoReturn }
     *     
     */
    public void setRetrieveSalesMemoResult(RetrieveSalesMemoReturn value) {
        this.retrieveSalesMemoResult = value;
    }

}


package com.pccw.appendOrdRmk;

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
 *         &lt;element name="appendOrdRmkResult" type="{http://www.openuri.org/}ServiceResponseDTO" minOccurs="0"/>
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
    "appendOrdRmkResult"
})
@XmlRootElement(name = "appendOrdRmkResponse")
public class AppendOrdRmkResponse {

    protected ServiceResponseDTO appendOrdRmkResult;

    /**
     * Gets the value of the appendOrdRmkResult property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceResponseDTO }
     *     
     */
    public ServiceResponseDTO getAppendOrdRmkResult() {
        return appendOrdRmkResult;
    }

    /**
     * Sets the value of the appendOrdRmkResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceResponseDTO }
     *     
     */
    public void setAppendOrdRmkResult(ServiceResponseDTO value) {
        this.appendOrdRmkResult = value;
    }

}

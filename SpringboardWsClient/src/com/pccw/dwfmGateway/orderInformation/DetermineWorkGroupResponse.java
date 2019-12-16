
package com.pccw.dwfmGateway.orderInformation;

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
 *         &lt;element name="determineWorkGroupResult" type="{http://www.openuri.org/}OrderInformationResponse" minOccurs="0"/>
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
    "determineWorkGroupResult"
})
@XmlRootElement(name = "determineWorkGroupResponse")
public class DetermineWorkGroupResponse {

    protected OrderInformationResponse determineWorkGroupResult;

    /**
     * Gets the value of the determineWorkGroupResult property.
     * 
     * @return
     *     possible object is
     *     {@link OrderInformationResponse }
     *     
     */
    public OrderInformationResponse getDetermineWorkGroupResult() {
        return determineWorkGroupResult;
    }

    /**
     * Sets the value of the determineWorkGroupResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderInformationResponse }
     *     
     */
    public void setDetermineWorkGroupResult(OrderInformationResponse value) {
        this.determineWorkGroupResult = value;
    }

}

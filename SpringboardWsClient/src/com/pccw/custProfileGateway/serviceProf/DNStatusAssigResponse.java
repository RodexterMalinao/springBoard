
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="DNStatusAssigResult" type="{http://www.openuri.org/}ServiceResponse" minOccurs="0"/>
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
    "dnStatusAssigResult"
})
@XmlRootElement(name = "DNStatusAssigResponse")
public class DNStatusAssigResponse {

    @XmlElement(name = "DNStatusAssigResult")
    protected ServiceResponse dnStatusAssigResult;

    /**
     * Gets the value of the dnStatusAssigResult property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceResponse }
     *     
     */
    public ServiceResponse getDNStatusAssigResult() {
        return dnStatusAssigResult;
    }

    /**
     * Sets the value of the dnStatusAssigResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceResponse }
     *     
     */
    public void setDNStatusAssigResult(ServiceResponse value) {
        this.dnStatusAssigResult = value;
    }

}

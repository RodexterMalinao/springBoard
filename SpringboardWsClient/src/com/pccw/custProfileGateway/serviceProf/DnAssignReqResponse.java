
package com.pccw.custProfileGateway.serviceProf;

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
 *         &lt;element name="dnAssignReqResult" type="{http://www.openuri.org/}DNAssignOutput" minOccurs="0"/>
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
    "dnAssignReqResult"
})
@XmlRootElement(name = "dnAssignReqResponse")
public class DnAssignReqResponse {

    protected DNAssignOutput dnAssignReqResult;

    /**
     * Gets the value of the dnAssignReqResult property.
     * 
     * @return
     *     possible object is
     *     {@link DNAssignOutput }
     *     
     */
    public DNAssignOutput getDnAssignReqResult() {
        return dnAssignReqResult;
    }

    /**
     * Sets the value of the dnAssignReqResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DNAssignOutput }
     *     
     */
    public void setDnAssignReqResult(DNAssignOutput value) {
        this.dnAssignReqResult = value;
    }

}


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
 *         &lt;element name="getDNAssignmentResultResult" type="{http://www.openuri.org/}ArrayOfDNAssignmentResultDTO" minOccurs="0"/>
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
    "getDNAssignmentResultResult"
})
@XmlRootElement(name = "getDNAssignmentResultResponse")
public class GetDNAssignmentResultResponse {

    protected ArrayOfDNAssignmentResultDTO getDNAssignmentResultResult;

    /**
     * Gets the value of the getDNAssignmentResultResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDNAssignmentResultDTO }
     *     
     */
    public ArrayOfDNAssignmentResultDTO getGetDNAssignmentResultResult() {
        return getDNAssignmentResultResult;
    }

    /**
     * Sets the value of the getDNAssignmentResultResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDNAssignmentResultDTO }
     *     
     */
    public void setGetDNAssignmentResultResult(ArrayOfDNAssignmentResultDTO value) {
        this.getDNAssignmentResultResult = value;
    }

}

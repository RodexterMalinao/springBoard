
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
 *         &lt;element name="pDetermineWkgReq" type="{http://www.openuri.org/}ArrayOfDetermineWkgReq" minOccurs="0"/>
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
    "pDetermineWkgReq"
})
@XmlRootElement(name = "determineWorkGroup")
public class DetermineWorkGroup {

    protected ArrayOfDetermineWkgReq pDetermineWkgReq;

    /**
     * Gets the value of the pDetermineWkgReq property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDetermineWkgReq }
     *     
     */
    public ArrayOfDetermineWkgReq getPDetermineWkgReq() {
        return pDetermineWkgReq;
    }

    /**
     * Sets the value of the pDetermineWkgReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDetermineWkgReq }
     *     
     */
    public void setPDetermineWkgReq(ArrayOfDetermineWkgReq value) {
        this.pDetermineWkgReq = value;
    }

}

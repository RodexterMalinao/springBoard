
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
 *         &lt;element name="getIddffpInfoResult" type="{http://www.openuri.org/}ArrayOfArpuInfoDTO" minOccurs="0"/>
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
    "getIddffpInfoResult"
})
@XmlRootElement(name = "getIddffpInfoResponse")
public class GetIddffpInfoResponse {

    protected ArrayOfArpuInfoDTO getIddffpInfoResult;

    /**
     * Gets the value of the getIddffpInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfArpuInfoDTO }
     *     
     */
    public ArrayOfArpuInfoDTO getGetIddffpInfoResult() {
        return getIddffpInfoResult;
    }

    /**
     * Sets the value of the getIddffpInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfArpuInfoDTO }
     *     
     */
    public void setGetIddffpInfoResult(ArrayOfArpuInfoDTO value) {
        this.getIddffpInfoResult = value;
    }

}

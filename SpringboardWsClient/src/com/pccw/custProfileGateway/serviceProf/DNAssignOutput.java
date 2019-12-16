
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DNAssignOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DNAssignOutput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DnAssignOutArray" type="{http://www.openuri.org/}ArrayOfDNAssignOutDetail" minOccurs="0"/>
 *         &lt;element name="DrgRtnSts" type="{http://www.openuri.org/}DragonReturnStatus" minOccurs="0"/>
 *         &lt;element name="ReaoArray" type="{http://www.openuri.org/}ArrayOfBoMSrvReaOut" minOccurs="0"/>
 *         &lt;element name="ReqoArray" type="{http://www.openuri.org/}ArrayOfBoMSrvReqOut" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DNAssignOutput", propOrder = {
    "dnAssignOutArray",
    "drgRtnSts",
    "reaoArray",
    "reqoArray"
})
public class DNAssignOutput {

    @XmlElement(name = "DnAssignOutArray")
    protected ArrayOfDNAssignOutDetail dnAssignOutArray;
    @XmlElement(name = "DrgRtnSts")
    protected DragonReturnStatus drgRtnSts;
    @XmlElement(name = "ReaoArray")
    protected ArrayOfBoMSrvReaOut reaoArray;
    @XmlElement(name = "ReqoArray")
    protected ArrayOfBoMSrvReqOut reqoArray;

    /**
     * Gets the value of the dnAssignOutArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDNAssignOutDetail }
     *     
     */
    public ArrayOfDNAssignOutDetail getDnAssignOutArray() {
        return dnAssignOutArray;
    }

    /**
     * Sets the value of the dnAssignOutArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDNAssignOutDetail }
     *     
     */
    public void setDnAssignOutArray(ArrayOfDNAssignOutDetail value) {
        this.dnAssignOutArray = value;
    }

    /**
     * Gets the value of the drgRtnSts property.
     * 
     * @return
     *     possible object is
     *     {@link DragonReturnStatus }
     *     
     */
    public DragonReturnStatus getDrgRtnSts() {
        return drgRtnSts;
    }

    /**
     * Sets the value of the drgRtnSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link DragonReturnStatus }
     *     
     */
    public void setDrgRtnSts(DragonReturnStatus value) {
        this.drgRtnSts = value;
    }

    /**
     * Gets the value of the reaoArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBoMSrvReaOut }
     *     
     */
    public ArrayOfBoMSrvReaOut getReaoArray() {
        return reaoArray;
    }

    /**
     * Sets the value of the reaoArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBoMSrvReaOut }
     *     
     */
    public void setReaoArray(ArrayOfBoMSrvReaOut value) {
        this.reaoArray = value;
    }

    /**
     * Gets the value of the reqoArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBoMSrvReqOut }
     *     
     */
    public ArrayOfBoMSrvReqOut getReqoArray() {
        return reqoArray;
    }

    /**
     * Sets the value of the reqoArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBoMSrvReqOut }
     *     
     */
    public void setReqoArray(ArrayOfBoMSrvReqOut value) {
        this.reqoArray = value;
    }

}

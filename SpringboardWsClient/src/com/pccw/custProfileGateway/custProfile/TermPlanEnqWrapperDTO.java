
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TermPlanEnqWrapperDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TermPlanEnqWrapperDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="AoTermPlanEnqDTO" type="{http://www.openuri.org/}ArrayOfTermPlanEnqDTO" minOccurs="0"/>
 *         &lt;element name="sErrCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sErrMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TermPlanEnqWrapperDTO", propOrder = {
    "aoTermPlanEnqDTO",
    "sErrCode",
    "sErrMsg"
})
public class TermPlanEnqWrapperDTO
    extends ValueObject
{

    @XmlElement(name = "AoTermPlanEnqDTO")
    protected ArrayOfTermPlanEnqDTO aoTermPlanEnqDTO;
    protected String sErrCode;
    protected String sErrMsg;

    /**
     * Gets the value of the aoTermPlanEnqDTO property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTermPlanEnqDTO }
     *     
     */
    public ArrayOfTermPlanEnqDTO getAoTermPlanEnqDTO() {
        return aoTermPlanEnqDTO;
    }

    /**
     * Sets the value of the aoTermPlanEnqDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTermPlanEnqDTO }
     *     
     */
    public void setAoTermPlanEnqDTO(ArrayOfTermPlanEnqDTO value) {
        this.aoTermPlanEnqDTO = value;
    }

    /**
     * Gets the value of the sErrCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSErrCode() {
        return sErrCode;
    }

    /**
     * Sets the value of the sErrCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSErrCode(String value) {
        this.sErrCode = value;
    }

    /**
     * Gets the value of the sErrMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSErrMsg() {
        return sErrMsg;
    }

    /**
     * Sets the value of the sErrMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSErrMsg(String value) {
        this.sErrMsg = value;
    }

}

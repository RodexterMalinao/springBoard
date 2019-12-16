
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CallPlanEnqWrapperDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CallPlanEnqWrapperDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="sSrvType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sSrvNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sSubInd0060" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AoCallPlanEnqDTO" type="{http://www.openuri.org/}ArrayOfCallPlanEnqDTO" minOccurs="0"/>
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
@XmlType(name = "CallPlanEnqWrapperDTO", propOrder = {
    "sSrvType",
    "sSrvNum",
    "sSubInd0060",
    "aoCallPlanEnqDTO",
    "sErrCode",
    "sErrMsg"
})
public class CallPlanEnqWrapperDTO
    extends ValueObject
{

    protected String sSrvType;
    protected String sSrvNum;
    protected String sSubInd0060;
    @XmlElement(name = "AoCallPlanEnqDTO")
    protected ArrayOfCallPlanEnqDTO aoCallPlanEnqDTO;
    protected String sErrCode;
    protected String sErrMsg;

    /**
     * Gets the value of the sSrvType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSSrvType() {
        return sSrvType;
    }

    /**
     * Sets the value of the sSrvType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSSrvType(String value) {
        this.sSrvType = value;
    }

    /**
     * Gets the value of the sSrvNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSSrvNum() {
        return sSrvNum;
    }

    /**
     * Sets the value of the sSrvNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSSrvNum(String value) {
        this.sSrvNum = value;
    }

    /**
     * Gets the value of the sSubInd0060 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSSubInd0060() {
        return sSubInd0060;
    }

    /**
     * Sets the value of the sSubInd0060 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSSubInd0060(String value) {
        this.sSubInd0060 = value;
    }

    /**
     * Gets the value of the aoCallPlanEnqDTO property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCallPlanEnqDTO }
     *     
     */
    public ArrayOfCallPlanEnqDTO getAoCallPlanEnqDTO() {
        return aoCallPlanEnqDTO;
    }

    /**
     * Sets the value of the aoCallPlanEnqDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCallPlanEnqDTO }
     *     
     */
    public void setAoCallPlanEnqDTO(ArrayOfCallPlanEnqDTO value) {
        this.aoCallPlanEnqDTO = value;
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


package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CallPlanDtlEnqDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CallPlanDtlEnqDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="PlanCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanSrvSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConDuration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WaiveInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartFromDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartToDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FreeMin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CallPlanDtlEnqDTO", propOrder = {
    "planCd",
    "planSrvSeq",
    "conStartDate",
    "conEndDate",
    "conDuration",
    "waiveInd",
    "startFromDate",
    "startToDate",
    "freeMin"
})
public class CallPlanDtlEnqDTO
    extends ValueObject
{

    @XmlElement(name = "PlanCd")
    protected String planCd;
    @XmlElement(name = "PlanSrvSeq")
    protected String planSrvSeq;
    @XmlElement(name = "ConStartDate")
    protected String conStartDate;
    @XmlElement(name = "ConEndDate")
    protected String conEndDate;
    @XmlElement(name = "ConDuration")
    protected String conDuration;
    @XmlElement(name = "WaiveInd")
    protected String waiveInd;
    @XmlElement(name = "StartFromDate")
    protected String startFromDate;
    @XmlElement(name = "StartToDate")
    protected String startToDate;
    @XmlElement(name = "FreeMin")
    protected String freeMin;

    /**
     * Gets the value of the planCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanCd() {
        return planCd;
    }

    /**
     * Sets the value of the planCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanCd(String value) {
        this.planCd = value;
    }

    /**
     * Gets the value of the planSrvSeq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanSrvSeq() {
        return planSrvSeq;
    }

    /**
     * Sets the value of the planSrvSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanSrvSeq(String value) {
        this.planSrvSeq = value;
    }

    /**
     * Gets the value of the conStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConStartDate() {
        return conStartDate;
    }

    /**
     * Sets the value of the conStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConStartDate(String value) {
        this.conStartDate = value;
    }

    /**
     * Gets the value of the conEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConEndDate() {
        return conEndDate;
    }

    /**
     * Sets the value of the conEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConEndDate(String value) {
        this.conEndDate = value;
    }

    /**
     * Gets the value of the conDuration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConDuration() {
        return conDuration;
    }

    /**
     * Sets the value of the conDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConDuration(String value) {
        this.conDuration = value;
    }

    /**
     * Gets the value of the waiveInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaiveInd() {
        return waiveInd;
    }

    /**
     * Sets the value of the waiveInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaiveInd(String value) {
        this.waiveInd = value;
    }

    /**
     * Gets the value of the startFromDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartFromDate() {
        return startFromDate;
    }

    /**
     * Sets the value of the startFromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartFromDate(String value) {
        this.startFromDate = value;
    }

    /**
     * Gets the value of the startToDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartToDate() {
        return startToDate;
    }

    /**
     * Sets the value of the startToDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartToDate(String value) {
        this.startToDate = value;
    }

    /**
     * Gets the value of the freeMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeMin() {
        return freeMin;
    }

    /**
     * Sets the value of the freeMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeMin(String value) {
        this.freeMin = value;
    }

}

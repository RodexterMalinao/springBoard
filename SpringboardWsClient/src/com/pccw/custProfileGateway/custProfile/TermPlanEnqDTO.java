
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TermPlanEnqDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TermPlanEnqDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="TermPlanCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermPlanDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RebateMonthAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TermPlanPeriod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TermPlanEnqDTO", propOrder = {
    "termPlanCode",
    "effStartDate",
    "effEndDate",
    "termPlanDesc",
    "rebateMonthAmount",
    "termPlanPeriod"
})
public class TermPlanEnqDTO
    extends ValueObject
{

    @XmlElement(name = "TermPlanCode")
    protected String termPlanCode;
    @XmlElement(name = "EffStartDate")
    protected String effStartDate;
    @XmlElement(name = "EffEndDate")
    protected String effEndDate;
    @XmlElement(name = "TermPlanDesc")
    protected String termPlanDesc;
    @XmlElement(name = "RebateMonthAmount")
    protected String rebateMonthAmount;
    @XmlElement(name = "TermPlanPeriod")
    protected String termPlanPeriod;

    /**
     * Gets the value of the termPlanCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermPlanCode() {
        return termPlanCode;
    }

    /**
     * Sets the value of the termPlanCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermPlanCode(String value) {
        this.termPlanCode = value;
    }

    /**
     * Gets the value of the effStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffStartDate() {
        return effStartDate;
    }

    /**
     * Sets the value of the effStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffStartDate(String value) {
        this.effStartDate = value;
    }

    /**
     * Gets the value of the effEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffEndDate() {
        return effEndDate;
    }

    /**
     * Sets the value of the effEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffEndDate(String value) {
        this.effEndDate = value;
    }

    /**
     * Gets the value of the termPlanDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermPlanDesc() {
        return termPlanDesc;
    }

    /**
     * Sets the value of the termPlanDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermPlanDesc(String value) {
        this.termPlanDesc = value;
    }

    /**
     * Gets the value of the rebateMonthAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRebateMonthAmount() {
        return rebateMonthAmount;
    }

    /**
     * Sets the value of the rebateMonthAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRebateMonthAmount(String value) {
        this.rebateMonthAmount = value;
    }

    /**
     * Gets the value of the termPlanPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermPlanPeriod() {
        return termPlanPeriod;
    }

    /**
     * Sets the value of the termPlanPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermPlanPeriod(String value) {
        this.termPlanPeriod = value;
    }

}

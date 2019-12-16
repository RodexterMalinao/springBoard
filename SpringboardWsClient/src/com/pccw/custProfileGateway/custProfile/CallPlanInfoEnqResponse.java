
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CallPlanInfoEnqResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CallPlanInfoEnqResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="CallPlanInfoDTO" type="{http://www.openuri.org/}ArrayOfCallPlanInfoDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CallPlanInfoEnqResponse", propOrder = {
    "callPlanInfoDTO"
})
public class CallPlanInfoEnqResponse
    extends ServiceResponse
{

    @XmlElement(name = "CallPlanInfoDTO")
    protected ArrayOfCallPlanInfoDTO callPlanInfoDTO;

    /**
     * Gets the value of the callPlanInfoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCallPlanInfoDTO }
     *     
     */
    public ArrayOfCallPlanInfoDTO getCallPlanInfoDTO() {
        return callPlanInfoDTO;
    }

    /**
     * Sets the value of the callPlanInfoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCallPlanInfoDTO }
     *     
     */
    public void setCallPlanInfoDTO(ArrayOfCallPlanInfoDTO value) {
        this.callPlanInfoDTO = value;
    }

}

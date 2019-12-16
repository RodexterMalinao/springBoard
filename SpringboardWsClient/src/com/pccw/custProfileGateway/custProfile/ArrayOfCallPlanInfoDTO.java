
package com.pccw.custProfileGateway.custProfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCallPlanInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCallPlanInfoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CallPlanInfoDTO" type="{http://www.openuri.org/}CallPlanInfoDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCallPlanInfoDTO", propOrder = {
    "callPlanInfoDTO"
})
public class ArrayOfCallPlanInfoDTO {

    @XmlElement(name = "CallPlanInfoDTO", nillable = true)
    protected List<CallPlanInfoDTO> callPlanInfoDTO;

    /**
     * Gets the value of the callPlanInfoDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the callPlanInfoDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCallPlanInfoDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CallPlanInfoDTO }
     * 
     * 
     */
    public List<CallPlanInfoDTO> getCallPlanInfoDTO() {
        if (callPlanInfoDTO == null) {
            callPlanInfoDTO = new ArrayList<CallPlanInfoDTO>();
        }
        return this.callPlanInfoDTO;
    }

}

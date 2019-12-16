
package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrdPenaltyTierDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrdPenaltyTierDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdPenaltyTierDTO" type="{http://www.openuri.org/}OrdPenaltyTierDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrdPenaltyTierDTO", propOrder = {
    "ordPenaltyTierDTO"
})
public class ArrayOfOrdPenaltyTierDTO {

    @XmlElement(name = "OrdPenaltyTierDTO", nillable = true)
    protected List<OrdPenaltyTierDTO> ordPenaltyTierDTO;

    /**
     * Gets the value of the ordPenaltyTierDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordPenaltyTierDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdPenaltyTierDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdPenaltyTierDTO }
     * 
     * 
     */
    public List<OrdPenaltyTierDTO> getOrdPenaltyTierDTO() {
        if (ordPenaltyTierDTO == null) {
            ordPenaltyTierDTO = new ArrayList<OrdPenaltyTierDTO>();
        }
        return this.ordPenaltyTierDTO;
    }

}

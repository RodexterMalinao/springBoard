
package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrdPenaltyDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrdPenaltyDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdPenaltyDTO" type="{http://www.openuri.org/}OrdPenaltyDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrdPenaltyDTO", propOrder = {
    "ordPenaltyDTO"
})
public class ArrayOfOrdPenaltyDTO {

    @XmlElement(name = "OrdPenaltyDTO", nillable = true)
    protected List<OrdPenaltyDTO> ordPenaltyDTO;

    /**
     * Gets the value of the ordPenaltyDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordPenaltyDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdPenaltyDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdPenaltyDTO }
     * 
     * 
     */
    public List<OrdPenaltyDTO> getOrdPenaltyDTO() {
        if (ordPenaltyDTO == null) {
            ordPenaltyDTO = new ArrayList<OrdPenaltyDTO>();
        }
        return this.ordPenaltyDTO;
    }

}

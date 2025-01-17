
package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrdIncentiveDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrdIncentiveDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdIncentiveDTO" type="{http://www.openuri.org/}OrdIncentiveDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrdIncentiveDTO", propOrder = {
    "ordIncentiveDTO"
})
public class ArrayOfOrdIncentiveDTO {

    @XmlElement(name = "OrdIncentiveDTO", nillable = true)
    protected List<OrdIncentiveDTO> ordIncentiveDTO;

    /**
     * Gets the value of the ordIncentiveDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordIncentiveDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdIncentiveDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdIncentiveDTO }
     * 
     * 
     */
    public List<OrdIncentiveDTO> getOrdIncentiveDTO() {
        if (ordIncentiveDTO == null) {
            ordIncentiveDTO = new ArrayList<OrdIncentiveDTO>();
        }
        return this.ordIncentiveDTO;
    }

}

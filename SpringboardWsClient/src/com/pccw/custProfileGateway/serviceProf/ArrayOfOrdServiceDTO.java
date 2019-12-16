
package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrdServiceDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrdServiceDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdServiceDTO" type="{http://www.openuri.org/}OrdServiceDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrdServiceDTO", propOrder = {
    "ordServiceDTO"
})
public class ArrayOfOrdServiceDTO {

    @XmlElement(name = "OrdServiceDTO", nillable = true)
    protected List<OrdServiceDTO> ordServiceDTO;

    /**
     * Gets the value of the ordServiceDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordServiceDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdServiceDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdServiceDTO }
     * 
     * 
     */
    public List<OrdServiceDTO> getOrdServiceDTO() {
        if (ordServiceDTO == null) {
            ordServiceDTO = new ArrayList<OrdServiceDTO>();
        }
        return this.ordServiceDTO;
    }

}

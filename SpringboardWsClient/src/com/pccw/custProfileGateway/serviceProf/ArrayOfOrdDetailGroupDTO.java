
package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrdDetailGroupDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrdDetailGroupDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdDetailGroupDTO" type="{http://www.openuri.org/}OrdDetailGroupDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrdDetailGroupDTO", propOrder = {
    "ordDetailGroupDTO"
})
public class ArrayOfOrdDetailGroupDTO {

    @XmlElement(name = "OrdDetailGroupDTO", nillable = true)
    protected List<OrdDetailGroupDTO> ordDetailGroupDTO;

    /**
     * Gets the value of the ordDetailGroupDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordDetailGroupDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdDetailGroupDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdDetailGroupDTO }
     * 
     * 
     */
    public List<OrdDetailGroupDTO> getOrdDetailGroupDTO() {
        if (ordDetailGroupDTO == null) {
            ordDetailGroupDTO = new ArrayList<OrdDetailGroupDTO>();
        }
        return this.ordDetailGroupDTO;
    }

}
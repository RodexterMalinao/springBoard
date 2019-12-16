
package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrdPsefAttributeDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrdPsefAttributeDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdPsefAttributeDTO" type="{http://www.openuri.org/}OrdPsefAttributeDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrdPsefAttributeDTO", propOrder = {
    "ordPsefAttributeDTO"
})
public class ArrayOfOrdPsefAttributeDTO {

    @XmlElement(name = "OrdPsefAttributeDTO", nillable = true)
    protected List<OrdPsefAttributeDTO> ordPsefAttributeDTO;

    /**
     * Gets the value of the ordPsefAttributeDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordPsefAttributeDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdPsefAttributeDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdPsefAttributeDTO }
     * 
     * 
     */
    public List<OrdPsefAttributeDTO> getOrdPsefAttributeDTO() {
        if (ordPsefAttributeDTO == null) {
            ordPsefAttributeDTO = new ArrayList<OrdPsefAttributeDTO>();
        }
        return this.ordPsefAttributeDTO;
    }

}

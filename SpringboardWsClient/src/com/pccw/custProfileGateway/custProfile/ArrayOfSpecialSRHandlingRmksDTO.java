
package com.pccw.custProfileGateway.custProfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSpecialSRHandlingRmksDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSpecialSRHandlingRmksDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SpecialSRHandlingRmksDTO" type="{http://www.openuri.org/}SpecialSRHandlingRmksDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSpecialSRHandlingRmksDTO", propOrder = {
    "specialSRHandlingRmksDTO"
})
public class ArrayOfSpecialSRHandlingRmksDTO {

    @XmlElement(name = "SpecialSRHandlingRmksDTO", nillable = true)
    protected List<SpecialSRHandlingRmksDTO> specialSRHandlingRmksDTO;

    /**
     * Gets the value of the specialSRHandlingRmksDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specialSRHandlingRmksDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecialSRHandlingRmksDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SpecialSRHandlingRmksDTO }
     * 
     * 
     */
    public List<SpecialSRHandlingRmksDTO> getSpecialSRHandlingRmksDTO() {
        if (specialSRHandlingRmksDTO == null) {
            specialSRHandlingRmksDTO = new ArrayList<SpecialSRHandlingRmksDTO>();
        }
        return this.specialSRHandlingRmksDTO;
    }

}


package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGenericFixFieldDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGenericFixFieldDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GenericFixFieldDTO" type="{http://www.openuri.org/}GenericFixFieldDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGenericFixFieldDTO", propOrder = {
    "genericFixFieldDTO"
})
public class ArrayOfGenericFixFieldDTO {

    @XmlElement(name = "GenericFixFieldDTO", nillable = true)
    protected List<GenericFixFieldDTO> genericFixFieldDTO;

    /**
     * Gets the value of the genericFixFieldDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genericFixFieldDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenericFixFieldDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenericFixFieldDTO }
     * 
     * 
     */
    public List<GenericFixFieldDTO> getGenericFixFieldDTO() {
        if (genericFixFieldDTO == null) {
            genericFixFieldDTO = new ArrayList<GenericFixFieldDTO>();
        }
        return this.genericFixFieldDTO;
    }

}

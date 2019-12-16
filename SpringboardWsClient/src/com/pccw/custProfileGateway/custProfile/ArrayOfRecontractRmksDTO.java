
package com.pccw.custProfileGateway.custProfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRecontractRmksDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRecontractRmksDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecontractRmksDTO" type="{http://www.openuri.org/}RecontractRmksDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRecontractRmksDTO", propOrder = {
    "recontractRmksDTO"
})
public class ArrayOfRecontractRmksDTO {

    @XmlElement(name = "RecontractRmksDTO", nillable = true)
    protected List<RecontractRmksDTO> recontractRmksDTO;

    /**
     * Gets the value of the recontractRmksDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recontractRmksDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecontractRmksDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecontractRmksDTO }
     * 
     * 
     */
    public List<RecontractRmksDTO> getRecontractRmksDTO() {
        if (recontractRmksDTO == null) {
            recontractRmksDTO = new ArrayList<RecontractRmksDTO>();
        }
        return this.recontractRmksDTO;
    }

}

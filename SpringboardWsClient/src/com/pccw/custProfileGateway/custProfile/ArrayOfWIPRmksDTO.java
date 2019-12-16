
package com.pccw.custProfileGateway.custProfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfWIPRmksDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfWIPRmksDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WIPRmksDTO" type="{http://www.openuri.org/}WIPRmksDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfWIPRmksDTO", propOrder = {
    "wipRmksDTO"
})
public class ArrayOfWIPRmksDTO {

    @XmlElement(name = "WIPRmksDTO", nillable = true)
    protected List<WIPRmksDTO> wipRmksDTO;

    /**
     * Gets the value of the wipRmksDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wipRmksDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWIPRmksDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WIPRmksDTO }
     * 
     * 
     */
    public List<WIPRmksDTO> getWIPRmksDTO() {
        if (wipRmksDTO == null) {
            wipRmksDTO = new ArrayList<WIPRmksDTO>();
        }
        return this.wipRmksDTO;
    }

}

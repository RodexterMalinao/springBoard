
package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOrdDetailGroupMemberDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrdDetailGroupMemberDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrdDetailGroupMemberDTO" type="{http://www.openuri.org/}OrdDetailGroupMemberDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrdDetailGroupMemberDTO", propOrder = {
    "ordDetailGroupMemberDTO"
})
public class ArrayOfOrdDetailGroupMemberDTO {

    @XmlElement(name = "OrdDetailGroupMemberDTO", nillable = true)
    protected List<OrdDetailGroupMemberDTO> ordDetailGroupMemberDTO;

    /**
     * Gets the value of the ordDetailGroupMemberDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordDetailGroupMemberDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdDetailGroupMemberDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdDetailGroupMemberDTO }
     * 
     * 
     */
    public List<OrdDetailGroupMemberDTO> getOrdDetailGroupMemberDTO() {
        if (ordDetailGroupMemberDTO == null) {
            ordDetailGroupMemberDTO = new ArrayList<OrdDetailGroupMemberDTO>();
        }
        return this.ordDetailGroupMemberDTO;
    }

}

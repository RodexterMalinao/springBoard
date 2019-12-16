
package com.pccw.custProfileGateway.custProfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfContactInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfContactInfoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContactInfoDTO" type="{http://www.openuri.org/}ContactInfoDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfContactInfoDTO", propOrder = {
    "contactInfoDTO"
})
public class ArrayOfContactInfoDTO {

    @XmlElement(name = "ContactInfoDTO", nillable = true)
    protected List<ContactInfoDTO> contactInfoDTO;

    /**
     * Gets the value of the contactInfoDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactInfoDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactInfoDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactInfoDTO }
     * 
     * 
     */
    public List<ContactInfoDTO> getContactInfoDTO() {
        if (contactInfoDTO == null) {
            contactInfoDTO = new ArrayList<ContactInfoDTO>();
        }
        return this.contactInfoDTO;
    }

}

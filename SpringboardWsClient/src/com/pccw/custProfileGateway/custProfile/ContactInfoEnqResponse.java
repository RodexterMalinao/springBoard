
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactInfoEnqResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactInfoEnqResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="ContactInfoDTO" type="{http://www.openuri.org/}ContactInfoDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactInfoEnqResponse", propOrder = {
    "contactInfoDTO"
})
public class ContactInfoEnqResponse
    extends ServiceResponse
{

    @XmlElement(name = "ContactInfoDTO")
    protected ContactInfoDTO contactInfoDTO;

    /**
     * Gets the value of the contactInfoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoDTO }
     *     
     */
    public ContactInfoDTO getContactInfoDTO() {
        return contactInfoDTO;
    }

    /**
     * Sets the value of the contactInfoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoDTO }
     *     
     */
    public void setContactInfoDTO(ContactInfoDTO value) {
        this.contactInfoDTO = value;
    }

}

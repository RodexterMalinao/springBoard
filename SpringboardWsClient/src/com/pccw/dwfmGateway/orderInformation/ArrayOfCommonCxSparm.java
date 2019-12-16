
package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCommonCxSparm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCommonCxSparm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CommonCxSparm" type="{http://www.openuri.org/}CommonCxSparm" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCommonCxSparm", propOrder = {
    "commonCxSparm"
})
public class ArrayOfCommonCxSparm {

    @XmlElement(name = "CommonCxSparm", nillable = true)
    protected List<CommonCxSparm> commonCxSparm;

    /**
     * Gets the value of the commonCxSparm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commonCxSparm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommonCxSparm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommonCxSparm }
     * 
     * 
     */
    public List<CommonCxSparm> getCommonCxSparm() {
        if (commonCxSparm == null) {
            commonCxSparm = new ArrayList<CommonCxSparm>();
        }
        return this.commonCxSparm;
    }

}


package com.pccw.dwfmGateway.orderInformation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDetermineWkgOa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDetermineWkgOa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DetermineWkgOa" type="{http://www.openuri.org/}DetermineWkgOa" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDetermineWkgOa", propOrder = {
    "determineWkgOa"
})
public class ArrayOfDetermineWkgOa {

    @XmlElement(name = "DetermineWkgOa", nillable = true)
    protected List<DetermineWkgOa> determineWkgOa;

    /**
     * Gets the value of the determineWkgOa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the determineWkgOa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetermineWkgOa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetermineWkgOa }
     * 
     * 
     */
    public List<DetermineWkgOa> getDetermineWkgOa() {
        if (determineWkgOa == null) {
            determineWkgOa = new ArrayList<DetermineWkgOa>();
        }
        return this.determineWkgOa;
    }

}

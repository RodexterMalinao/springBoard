
package com.pccw.custProfileGateway.serviceProf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfBoMSrvReqOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfBoMSrvReqOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BoMSrvReqOut" type="{http://www.openuri.org/}BoMSrvReqOut" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfBoMSrvReqOut", propOrder = {
    "boMSrvReqOut"
})
public class ArrayOfBoMSrvReqOut {

    @XmlElement(name = "BoMSrvReqOut", nillable = true)
    protected List<BoMSrvReqOut> boMSrvReqOut;

    /**
     * Gets the value of the boMSrvReqOut property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the boMSrvReqOut property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBoMSrvReqOut().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BoMSrvReqOut }
     * 
     * 
     */
    public List<BoMSrvReqOut> getBoMSrvReqOut() {
        if (boMSrvReqOut == null) {
            boMSrvReqOut = new ArrayList<BoMSrvReqOut>();
        }
        return this.boMSrvReqOut;
    }

}

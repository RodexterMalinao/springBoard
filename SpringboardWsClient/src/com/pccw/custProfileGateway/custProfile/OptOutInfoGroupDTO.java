
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OptOutInfoGroupDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OptOutInfoGroupDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *         &lt;element name="OptOutInfo" type="{http://www.openuri.org/}Map" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OptOutInfoGroupDTO", propOrder = {
    "optOutInfo"
})
public class OptOutInfoGroupDTO
    extends ValueObject
{

    @XmlElement(name = "OptOutInfo")
    protected Map optOutInfo;

    /**
     * Gets the value of the optOutInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getOptOutInfo() {
        return optOutInfo;
    }

    /**
     * Sets the value of the optOutInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setOptOutInfo(Map value) {
        this.optOutInfo = value;
    }

}

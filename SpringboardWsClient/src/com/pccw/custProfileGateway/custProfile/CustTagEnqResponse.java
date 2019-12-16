
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustTagEnqResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustTagEnqResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="CustTagDTO" type="{http://www.openuri.org/}CustTagDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustTagEnqResponse", propOrder = {
    "custTagDTO"
})
public class CustTagEnqResponse
    extends ServiceResponse
{

    @XmlElement(name = "CustTagDTO")
    protected CustTagDTO custTagDTO;

    /**
     * Gets the value of the custTagDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CustTagDTO }
     *     
     */
    public CustTagDTO getCustTagDTO() {
        return custTagDTO;
    }

    /**
     * Sets the value of the custTagDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustTagDTO }
     *     
     */
    public void setCustTagDTO(CustTagDTO value) {
        this.custTagDTO = value;
    }

}

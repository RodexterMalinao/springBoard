
package com.pccw.custProfileGateway.custProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="updateInvPreassgnJunctionMsgResult" type="{http://www.openuri.org/}InvPreassgnJunctionMsgUpdResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "updateInvPreassgnJunctionMsgResult"
})
@XmlRootElement(name = "updateInvPreassgnJunctionMsgResponse")
public class UpdateInvPreassgnJunctionMsgResponse {

    protected InvPreassgnJunctionMsgUpdResponse updateInvPreassgnJunctionMsgResult;

    /**
     * Gets the value of the updateInvPreassgnJunctionMsgResult property.
     * 
     * @return
     *     possible object is
     *     {@link InvPreassgnJunctionMsgUpdResponse }
     *     
     */
    public InvPreassgnJunctionMsgUpdResponse getUpdateInvPreassgnJunctionMsgResult() {
        return updateInvPreassgnJunctionMsgResult;
    }

    /**
     * Sets the value of the updateInvPreassgnJunctionMsgResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvPreassgnJunctionMsgUpdResponse }
     *     
     */
    public void setUpdateInvPreassgnJunctionMsgResult(InvPreassgnJunctionMsgUpdResponse value) {
        this.updateInvPreassgnJunctionMsgResult = value;
    }

}

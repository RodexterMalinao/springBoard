
package com.pccw.custProfileGateway.serviceProf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValueObjectWithAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValueObjectWithAction">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openuri.org/}ValueObject">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueObjectWithAction")
@XmlSeeAlso({
    OrdGroupFeatureAttributeDTO.class,
    OrdGroupFeatureDTO.class,
    OrdOfferDTO.class,
    OrdDetailGroupMemberSvcldOnlyDTO.class,
    OrdDetailGroupMemberDTO.class,
    OrdPricingAssignmentDTO.class,
    OrdPricingSchemeDTO.class,
    OrdOfferProductDTO.class,
    OrdDeptDTO.class,
    OrdServiceAccountDTO.class,
    OrdPsefDTO.class,
    OrdIncentiveAssignmentDTO.class,
    OrdOfferAssignmentDTO.class,
    OrdServiceDTO.class,
    OrdPenaltyAssignmentDTO.class,
    OrdPenaltyTierDTO.class,
    OrdDetailGroupDTO.class,
    OrdDetailGroupDetailDTO.class,
    OrdDiscountAssignmentDTO.class,
    OrdAddressDTO.class,
    OrdIncentiveDTO.class,
    OrdPsefAttributeDTO.class,
    OrdTermsGroupDTO.class,
    OrdOfferProductComponentDTO.class,
    OrdTermsDTO.class,
    OrdPenaltyDTO.class,
    OrdPricingTierDTO.class,
    OrdDiscountDTO.class
})
public class ValueObjectWithAction
    extends ValueObject
{


}

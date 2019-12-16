package com.bomwebportal.mob.ccs.service;

import java.util.ArrayList;
import java.util.List;

import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.pccw.bom.mob.schemas.ProductDTO;

public interface OrderHistoryService {

	public OrderDTO getOrder (String orderId , String seqNo);
	public CustomerProfileDTO getCustomerProfile (String orderId , String seqNo);
	public MnpDTO getMnp (String orderId , String seqNo);
	public PaymentDTO getPayment (String orderId, String seqNo);
	public MobileSimInfoDTO getSim (String orderId, String seqNo);
	public SubscriberDTO getBomWebSub(String orderId, String seqNo);
	public AccountDTO getAccount (String orderId , String seqNo);
	public List<ComponentDTO> getComponentList(String orderId, String seqNo);
	public ArrayList<MobCcsMrtBaseDTO> getMobCcsMrtDTO(String orderId, String seqNo);
	public List<ProductDTO> getBomProductList (String orderId, String seqNo);
	public void updateBomWebSim(MobileSimInfoDTO dto, String seqNo);
	public String getMultiIMEI(String orderId, String seqNo);
	public List<ComponentDTO> getPassToBomComponentList(String orderId, String seqNo);
}

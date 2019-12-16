package com.bomwebportal.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.ItemEditDAO;
import com.bomwebportal.dto.ItemEditDTO;
import com.bomwebportal.dto.ItemOfferDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

import com.bomwebportal.dao.ItemDisplayDAO;
import com.bomwebportal.dto.ItemDisplayDTO;

@Transactional(readOnly=true)
public class ItemEditServiceImpl implements ItemEditService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ItemEditDAO itemEditDao;
	private ItemDisplayDAO itemDisplayDao; //for hs price change 20110404
	
	public void setItemEditDao(ItemEditDAO itemEditDao) {
		this.itemEditDao = itemEditDao;
	}

	public ItemEditDAO getItemEditDao() {
		return itemEditDao;
	}
	
	public void setItemDisplayDao(ItemDisplayDAO itemDisplayDao) {
		this.itemDisplayDao = itemDisplayDao;
	}

	public ItemDisplayDAO getItemDisplayDao() {
		return itemDisplayDao;
	}
	
	
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public ItemEditDTO getItem(int itemId ){
		
		try{
			logger.info("getItem() is called in ItemEditServiceImpl");
			return itemEditDao.getItem(itemId); 
		}catch (DAOException de) {
			logger.error("Exception caught in getItemDisplay()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertItemAll(ItemEditDTO dto) {
		logger.info("insertItemAll() is called in ItemEditServiceImpl");
		
		int itemId=-1;
		if(!dto.getEnv().equals("custom")){
			
			String itemIdString=itemEditDao.getItemId(dto.getType(), dto.getEnv());
			itemId= Integer.parseInt(itemIdString);
			dto.setId(itemId);
			
		}
		
		
		try {
			logger.info("insertItem() is called in ItemEditServiceImpl");
			itemId=dto.getId();
			itemEditDao.insertItem(dto);
			
			if ("HS".equals(dto.getType())){
				try {
					logger.info("insertItemDtlHs() is called in ItemEditServiceImpl");
					itemEditDao.insertItemDtlHs(dto);
		
				} catch (DAOException de) {
					logger.error("Exception caught in insertItemDtlHs()", de);
					throw new AppRuntimeException(de);
				}
			}else{
			
				try {
					logger.info("insertItemDtlRpRbVas() is called in ItemEditServiceImpl");
					itemEditDao.insertItemDtlRpRbVas(dto);
		
				} catch (DAOException de) {
					logger.error("Exception caught in insertItemDtlRpRbVas()", de);
					throw new AppRuntimeException(de);
				}
			}

		} catch (DAOException de) {
			itemId=-2;
			logger.error("Exception caught in insertItem()", de);
			throw new AppRuntimeException(de);
		}
		
		
		try {
			logger.info("insertItemPricing() is called in ItemEditServiceImpl");
			itemEditDao.insertItemPricing(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in insertItemPricing()", de);
			throw new AppRuntimeException(de);
		}
		
		return itemId;
		
	}
	
	
	
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateItemAll(ItemEditDTO dto){

		logger.info("updateItemAll() is called in ItemEditServiceImpl");
		try {
			logger.info("updateItem() is called in ItemEditServiceImpl");
			itemEditDao.updateItem(dto);
			
			if ("HS".equals(dto.getType())){
				try {
					logger.info("updateItemDtlHs() is called in ItemEditServiceImpl");
					itemEditDao.updateItemDtlHs(dto);
		
				} catch (DAOException de) {
					logger.error("Exception caught in updateItemDtlHs()", de);
					throw new AppRuntimeException(de);
				}
			}else{
			
				try {
					logger.info("updateItemDtlRpRbVas() is called in ItemEditServiceImpl");
					itemEditDao.updateItemDtlRpRbVas(dto);
		
				} catch (DAOException de) {
					logger.error("Exception caught in updateItemDtlRpRbVas()", de);
					throw new AppRuntimeException(de);
				}
			}
			
			
			//Pricing table
			//Comment & modify by herbert 20110822, standardize the log
			/*
			 * System.out.println("Pricing dto.getEffStartDate():"+dto.getEffStartDate());
			 * System.out.println("Pricing dto.getEffStartDateOriginal():"+dto.getEffStartDateOriginal());
			 */
			logger.debug("Pricing dto.getEffStartDate():"+dto.getEffStartDate());
			logger.debug("Pricing dto.getEffStartDateOriginal():"+dto.getEffStartDateOriginal());
			if(dto.getEffStartDate().equals(dto.getEffStartDateOriginal())){
				//update
				//if(!dto.getEffEndDate().equals(dto.getEffEndDateOriginal())){
					
					itemEditDao.updateItemPricing(dto);
				//}
				
			}else{
				itemEditDao.updateItemPricingEffEndDate(dto);
				
				
				try {
					
					logger.info("insertItemPricing() is called in ItemEditServiceImpl");
					itemEditDao.insertItemPricing(dto);

				} catch (DAOException de) {
					logger.error("Exception caught in insertItemPricing()", de);
					//throw new AppRuntimeException(de);
				}
				
			}
			
			//to here ----20110323
			/*try {
				
				logger.info("updateItemPricing() is called in ItemEditServiceImpl");
				itemEditDao.updateItemPricing(dto);

			} catch (DAOException de) {
				logger.error("Exception caught in updateItemPricing()", de);
				throw new AppRuntimeException(de);
			}*/

		} catch (DAOException de) {
			logger.error("Exception caught in updateItem()", de);
			throw new AppRuntimeException(de);
		}
		
		
	/*	try {
			logger.info("updateItemPricing() is called in ItemEditServiceImpl");
			itemEditDao.updateItemPricing(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in updateItemPricing()", de);
			throw new AppRuntimeException(de);
		}*/
		
		
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void deleteItemAll(ItemEditDTO dto){

		logger.info("deleteItemAll() is called in ItemEditServiceImpl");
		
		try {
			logger.info("deleteItemOfferProductAssign() is called in ItemEditServiceImpl");
			itemEditDao.deleteItemOfferProductAssign(dto.getId());

		} catch (DAOException de) {
			logger.error("Exception caught in deleteItemOfferProductAssign()", de);
			throw new AppRuntimeException(de);
		}
		
		try {
			logger.info("deleteItemOfferAssign() is called in ItemEditServiceImpl");
			itemEditDao.deleteItemOfferAssign(dto.getId());

		} catch (DAOException de) {
			logger.error("Exception caught in deleteItemOfferAssign()", de);
			throw new AppRuntimeException(de);
		}
		
		if ("HS".equals(dto.getType())){
			try {
				logger.info("deleteItemDtlHs() is called in ItemEditServiceImpl");
				itemEditDao.deleteItemDtlHs(dto.getId());
	
			} catch (DAOException de) {
				logger.error("Exception caught in deleteItemDtlHs()", de);
				throw new AppRuntimeException(de);
			}
		}else{
		
			try {
				logger.info("deleteItemDtlRpRbVas() is called in ItemEditServiceImpl");
				itemEditDao.deleteItemDtlRpRbVas(dto.getId());
	
			} catch (DAOException de) {
				logger.error("Exception caught in deleteItemDtlRpRbVas()", de);
				throw new AppRuntimeException(de);
			}
		}
		
		try {
			logger.info("deleteItemPricing() is called in ItemEditServiceImpl");
			itemEditDao.deleteItemPricing(dto.getId());

		} catch (DAOException de) {
			logger.error("Exception caught in deleteItemPricing()", de);
			throw new AppRuntimeException(de);
		}
		
		try {
			logger.info("deleteItem() is called in ItemEditServiceImpl");
			itemEditDao.deleteItem(dto.getId());

		} catch (DAOException de) {
			logger.error("Exception caught in deleteItem()", de);
			throw new AppRuntimeException(de);
		}
		
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertItemOffer(ItemOfferDTO dto) {
		logger.info("insertItemOffer() is called in ItemEditServiceImpl");
	
		try {
			logger.info("insertItemOfferAssign() is called in ItemEditServiceImpl");
			itemEditDao.insertItemOfferAssign( dto);

		} catch (DAOException de) {
			logger.error("Exception caught in insertItemOfferAssign()", de);
			throw new AppRuntimeException(de);
		}
			
		
		try {
			logger.info("insertItemOfferProductAssign() is called in ItemEditServiceImpl");
			itemEditDao.insertItemOfferProductAssign(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in insertItemOfferProductAssign()", de);
			throw new AppRuntimeException(de);
		}
		
		logger.info("insertItemOffer() is finished in ItemEditServiceImpl");
		
		
		
	}
	
	
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ItemOfferDTO> getItemOffer(int itemId){
		try{
			logger.info("getItemOffer() is called in ItemEditServiceImpl");
			return itemEditDao.getItemOffer(itemId); 
		}catch (DAOException de) {
			logger.error("Exception caught in getItemOffer()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void deleteItemOffer(ItemEditDTO dto){

		logger.info("deleteItemOffer() is called in ItemEditServiceImpl");
		
		try {
			logger.info("deleteItemOfferProductAssign() is called in ItemEditServiceImpl");
			itemEditDao.deleteItemOfferProductAssign(dto.getId());

		} catch (DAOException de) {
			logger.error("Exception caught in deleteItemOfferProductAssign()", de);
			throw new AppRuntimeException(de);
		}
		
		try {
			logger.info("deleteItemOfferAssign() is called in ItemEditServiceImpl");
			itemEditDao.deleteItemOfferAssign(dto.getId());

		} catch (DAOException de) {
			logger.error("Exception caught in deleteItemOfferAssign()", de);
			throw new AppRuntimeException(de);
		}
	
	}
	
	
	public List<ItemOfferDTO> stringToListItemOfferDTO(int itemId,
			String inputString) {
		// split textarea to array
		String[] tempList = inputString.split("\r\n");
		// sort array
		java.util.Arrays.sort(tempList, String.CASE_INSENSITIVE_ORDER);
		// line to DTO
		List<ItemOfferDTO> itemOfferList = new ArrayList<ItemOfferDTO>();
		int itemOfferSeq = 0;
		int itemProductSeq = 0;
		int prevOfferId = 0;
		int prevProductId = 0;

		for (String line : tempList) {
			ItemOfferDTO tempDto = new ItemOfferDTO();
			tempDto.setItemId(itemId);
			tempDto.setSourceString(line);
			String cell[] = line.split(",");
			if (cell.length != 5) {
				tempDto.setParsingMessage("not 5 column");
			} else {

				tempDto.setOfferId(Integer.parseInt(cell[0]));
				tempDto.setOfferSubId(Integer.parseInt(cell[1]));
				tempDto.setOfferType(cell[2]);
				tempDto.setProductId(Integer.parseInt(cell[3]));
				tempDto.setProductType(cell[4]);
				if (prevOfferId == tempDto.getOfferId()) {

					itemProductSeq = itemProductSeq + 1;

				} else {
					itemOfferSeq = itemOfferSeq + 1;
					itemProductSeq = 1;// reset
				}
				tempDto.setSelectQty(1);
				tempDto.setItemOfferSeq(itemOfferSeq);
				tempDto.setItemProductSeq(itemProductSeq);

			}

			prevOfferId = tempDto.getOfferId();
			prevProductId = tempDto.getProductId();

			itemOfferList.add(tempDto);

		}

		return itemOfferList;

	}
	
	///////UPDATE PRICING, 20110404
	//for HS pricing update 20110404, need to make sure the value from DTO
	public void updateHsItemPricing(ItemEditDTO dto){
		logger.info("updateHsItemPricing() is called in ItemEditServiceImpl");
		try {
			
			logger.info("updateHsItemPricing() is called in ItemEditServiceImpl");
			//dto.getEffStartDate(),dto.getLastUpdBy(),dto.getId(),dto.getId(), dto.getEffStartDate()YYYY/MM/DD= sysdate
			itemEditDao.updateItemPricingEffEndDate(dto);
			//dto.getId(),
			//dto.getEffStartDate(),
			//dto.getEffEndDate(),
			//dto.getOnetimeType(),
			//dto.getOnetimeAmt(),
			//dto.getRecurrentAmt(),
			//dto.getLastUpdBy());
			itemEditDao.insertItemPricing(dto);
			
			//for update 
			createHSRBItemDisplayLkup(dto.getId(),"en" ,dto.getDescription(),  3);

		} catch (DAOException de) {
			logger.error("Exception caught in updateHsItemPricing()", de);
			
		}
	
	
	} 
	//for HS pricing update 20110404
	public void createHSRBItemDisplayLkup(int itemId,String locale ,String description, int flag){
		ItemDisplayDTO dto = new ItemDisplayDTO();
		dto.setItemId(itemId);
		dto.setLocale(locale);
		dto.setDescription(description);
		
		if(flag==1){
			//create item_select, hsrb_promote
			
			//1.create item_select
			
		
			dto.setDisplayType("ITEM_SELECT");
			dto.setHtml(getHsrbItemSelectHtml("", "","","","","", "")); //need to make sure the value
			try {
			itemDisplayDao.insertItemDisplay(dto);
			} catch (DAOException de) {
				logger.error("Exception caught in updateHsItemPricing()", de);
				
			}
			
			//2. hsrb_promote
			dto.setDisplayType("HSRB_PROMOT");
			dto.setHtml(getHsrbPromotHtml("", "","","","","", "")); //need to make sure the value
			try {
			itemDisplayDao.insertItemDisplay(dto);
			} catch (DAOException de) {
				logger.error("Exception caught in updateHsItemPricing()", de);
				
			}
			
			
		}else if(flag==2){
			//0 HSRB
			
			//2. hsrb_promote
			dto.setDisplayType("HSRB_PROMOT");
			dto.setHtml(getHsrbPromotHtml("", "","","","","", "")); //need to make sure the value
			try {
			itemDisplayDao.insertItemDisplay(dto);
			} catch (DAOException de) {
				logger.error("Exception caught in updateHsItemPricing()", de);
				
			}
			
			
			
		}else if(flag==3){
			//edit HSRB
			dto.setDisplayType("HSRB_PROMOT");
			dto.setHtml(getHsrbPromotHtml("", "","","","","", "")); //need to make sure the value
			try {
			itemDisplayDao.updateItemDisplay(dto);
			} catch (DAOException de) {
				logger.error("Exception caught in updateHsItemPricing()", de);
				
			}
			
			
			
		}
		
		
	
	}
	
	
	public String getHsrbPromotHtml(String nowPrice0, String  upfront1, String i2, String i3, String i4, String i5, String i6){
		
		String pattern=	"<span class=\"purple_text_16\">NOW: <strong>${0}</strong></span><p class=\"contenttextgary\">Upfront: ${1}<br />"
		+"{2}<br />"
		+"<span class=\"contenttext_blk\"><strong>{3}</strong></span> /month for {4} months<br />"
		+"<span class=\"contenttext_blk\">{5} minutes</span><br />"
		+"<span class=\"contenttext_blk\">{6}MB Data</span><br />";
		
		
		return MessageFormat.format(pattern, nowPrice0,upfront1,  i2,  i3,  i4,  i5,  i6);
	}
	
public String getHsrbItemSelectHtml(String nowPrice0, String  upfront1, String i2, String i3, String i4, String i5, String i6){
		
		String pattern=	"<span class=\"purple_text_16\">NOW: <strong>${0}</strong></span><p class=\"contenttextgary\">Upfront: ${1}<br />"
		+"{2}<br />"
		+"<span class=\"contenttext_blk\"><strong>{3}</strong></span> /month for {4} months<br />"
		+"<span class=\"contenttext_blk\">{5} minutes</span><br />"
		+"<span class=\"contenttext_blk\">{6}MB Data</span><br />";
		
		
		return MessageFormat.format(pattern, nowPrice0,upfront1,  i2,  i3,  i4,  i5,  i6);
	}

	
	
	
	
}
	
	
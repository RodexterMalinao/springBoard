package com.activity.service.dataAccess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.activity.dto.ActivityAttachDocDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionBase;

public class SaveDocumentServiceImpl implements SaveDocumentService {

	private RetrieveActivityService retrieveActivityService = null;
	private ServiceActionBase activityAttachDocService = null;

	public void saveAttachDocList(ActivityAttachDocDTO[] pAttachDocs, String pActvId, String pTaskSeq, String pUser){

		ActivityAttachDocDTO[] oldDocs = this.retrieveActivityService.getAttachedDocs(pActvId, pTaskSeq);
		Map<String,ActivityAttachDocDTO> newDocMap = new TreeMap<String,ActivityAttachDocDTO>();
		Map<String,ActivityAttachDocDTO> oldDocMap = new TreeMap<String,ActivityAttachDocDTO>();
		for (int i=0; pAttachDocs!=null && i<pAttachDocs.length; ++i) {
			newDocMap.put(pAttachDocs[i].getDocActvId() + pAttachDocs[i].getDocType() + pAttachDocs[i].getDocSeqNum(), pAttachDocs[i]);
		}
		for (int i=0; oldDocs!=null && i<oldDocs.length; ++i) {
			if (StringUtils.isBlank(oldDocs[i].getDocActvId()) || StringUtils.isBlank(oldDocs[i].getDocSeqNum())) {
				continue;
			}
			oldDocMap.put(oldDocs[i].getDocActvId() + oldDocs[i].getDocType() + oldDocs[i].getDocSeqNum(), oldDocs[i]);
		}
		
		
		Set<String> intersectSet = new HashSet<String>();	// intersection of new and old equip
		Set<ActivityAttachDocDTO> newOldDiffSet = new HashSet<ActivityAttachDocDTO>(); 	// new minus old equip
		Set<ActivityAttachDocDTO> oldNewDiffSet = new HashSet<ActivityAttachDocDTO>();	// old minus new equip
		this.filterDocumentsByDocId(newDocMap, oldDocMap, intersectSet, newOldDiffSet, oldNewDiffSet);
		
		Iterator<ActivityAttachDocDTO> itDoc = newOldDiffSet.iterator();
		ActivityAttachDocDTO doc = null;
		
		//  insert new equip
		while (itDoc.hasNext()) {
			doc = itDoc.next();
			doc.setObjectAction(ObjectActionBaseDTO.ACTION_ADD);
			this.saveAttacheDoc(doc, pActvId, pTaskSeq, pUser);
		}
		itDoc = oldNewDiffSet.iterator();
		
		// delete old equip
		while (itDoc.hasNext()) {
			doc = itDoc.next();
			doc.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			this.saveAttacheDoc(doc, pActvId, pTaskSeq, pUser);
		}
//		Iterator<String> itItemSeq = intersectSet.iterator();
//		String equipId = null;
//		ActivityAttachDocDTO oldEquip = null;
//		ActivityAttachDocDTO newEquip = null;
		
		// update available qty  // No use for attach doc
//		while (itItemSeq.hasNext()) {
//			equipId = itItemSeq.next();
//			oldEquip = oldEquipMap.get(equipId);
//			newEquip = newEquipMap.get(equipId);
//			
//			if (StringUtils.isNotBlank(newEquip.set)) {
//				oldEquip.setQty(newEquip.getQty());
//			} 
//			oldEquip.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
//			this.saveAttacheDoc(oldEquip, pActvId, pTaskSeq, pUser);
//		}
	}

	private void filterDocumentsByDocId(Map<String,ActivityAttachDocDTO> pNewDocMap, Map<String,ActivityAttachDocDTO> pOldDocMap, 
			Collection<String> pIntersectSet, Collection<ActivityAttachDocDTO> pNewOldDiffSet, Collection<ActivityAttachDocDTO> pOldNewDiffSet) {
		
		String[] newEquipId = pNewDocMap.keySet().toArray(new String[pNewDocMap.size()]);
		
		for (int i=0; i<newEquipId.length; ++i) {
			if (pOldDocMap.containsKey(newEquipId[i])) {
				pIntersectSet.add(newEquipId[i]);
			} else {
				pNewOldDiffSet.add(pNewDocMap.get(newEquipId[i]));
			}
		}
		String[] oldEquipId = pOldDocMap.keySet().toArray(new String[pOldDocMap.size()]);
		
		for (int i=0; i<oldEquipId.length; ++i) {
			if (pNewDocMap.containsKey(oldEquipId[i])) {
				pIntersectSet.add(oldEquipId[i]);
			} else {
				pOldNewDiffSet.add(pOldDocMap.get(oldEquipId[i]));
			}
		}
	}
	
	private void saveAttacheDoc(ActivityAttachDocDTO pDoc, String pActvId, String pTaskSeq, String pUser) {
		
		if (pDoc == null) {
			return;
		}
		this.activityAttachDocService.doSave(pDoc, pUser, pActvId, pTaskSeq);
	}

	public RetrieveActivityService getRetrieveActivityService() {
		return retrieveActivityService;
	}

	public void setRetrieveActivityService(
			RetrieveActivityService retrieveActivityService) {
		this.retrieveActivityService = retrieveActivityService;
	}

	public ServiceActionBase getActivityAttachDocService() {
		return activityAttachDocService;
	}

	public void setActivityAttachDocService(
			ServiceActionBase activityAttachDocService) {
		this.activityAttachDocService = activityAttachDocService;
	}	
	
	
}

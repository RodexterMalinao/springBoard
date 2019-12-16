package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.mob.ccs.dto.MobCcsDtoBase;

public abstract class BaseController<T extends MobCcsDtoBase> extends SimpleFormController{
	
	/**
	 * Hash Map that stores collection of object
	 */
	private Map<String, Object> sessionObjMap;
	/**
	 * Hast Set that stores DTOs that extends MobCcsDtoBase object
	 */
	private Set<MobCcsDtoBase> dtoSet;
	
	/**
	 * @return the dtoSet
	 */
	public Set<MobCcsDtoBase> getDtoSet() {
		return dtoSet;
	}

	/**
	 * @return 
	 * @return the dtoSet
	 */
	public T getDtoSet(final Class<? extends MobCcsDtoBase> dto) {
		
		if (dtoSet != null && !dtoSet.isEmpty()) {
			for (Iterator i = dtoSet.iterator(); i.hasNext(); ) {
				if(dto.getClass().equals(i.next().getClass())) {
					return (T) i.next();
				}
			}
		}
		
		return null;
	}
	
	public void add (MobCcsDtoBase dto) {
		if (dtoSet == null) {
			dtoSet = (Set<MobCcsDtoBase>) new HashMap();
		}
		
		dtoSet.add(dto);
	} 
	

	/**
	 * Retrieve value based on key
	 * @param key Key value
	 * @return the Object which specific is mapped
	 */
	public T getObjKeyValue(String key) {
		
		if (sessionObjMap == null) {
			return null;
		}
		
		return (T) sessionObjMap.get(key);
	}
	
	/**
	 * Set object into hash map
	 * @param key with which the specified value is to be associated
	 * @param inputObj value to be associated with the specified key
	 */
	public void setObjMap(String key, Object inputObj) {
		
		if (sessionObjMap == null) {
			sessionObjMap = new HashMap<String, Object>();
		}
		
		sessionObjMap.put(key, inputObj);
	}
	/**
	 * Remove all values inside this map
	 */
	public void clearMap() {
		if (sessionObjMap != null || !sessionObjMap.isEmpty()) {
			sessionObjMap.clear();
		}
	}
}

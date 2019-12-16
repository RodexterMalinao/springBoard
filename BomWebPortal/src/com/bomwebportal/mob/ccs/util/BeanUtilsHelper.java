package com.bomwebportal.mob.ccs.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


public class BeanUtilsHelper extends BeanUtils{
	
	/**
	 * /**
     * <p>Copy property values from the origin bean to the destination bean
     * with specific properties name.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
	 * @param dest Destination bean whose properties are modified
	 * @param orig Origin bean whose properties are retrieved
	 * @param includeProperties Properties name that wanted to copy
	 * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
	 * @throws InvocationTargetException if the property accessor method
     *  throws an exception
	 */
    public static void copySpecificProperties(Object orig, Object dest, String[] includeProperties)
        throws IllegalAccessException, InvocationTargetException {
        
    	BeanWrapper src = new BeanWrapperImpl(orig);
    	BeanWrapper trg = new BeanWrapperImpl(dest);
    	
    	
    	for (String propertyName : includeProperties) {
    		trg.setPropertyValue(propertyName, src.getPropertyValue(propertyName));
    	}
    }
	/**
	 * <p>Compare two objects values. Attributes can be defined from the String array</p>
	 * @param arg1 First object to compare with
	 * @param arg2 Second object to compare with
	 * @param compareProperties Properties name that wanted to compare
	 * @return true : if the value of both objects is the same <br> 
	 * false : if the value of both objects is not the same
	 * @throws IllegalAccessException IllegalAccessException if the caller does not have
     *  access to the property accessor method
	 * @throws InvocationTargetException if the property accessor method
     *  throws an exception
	 */
    public static boolean compareSpecificProperties(Object arg1, Object arg2, String[] compareProperties) 
    		throws IllegalAccessException, InvocationTargetException {
    	
    	BeanWrapper src = new BeanWrapperImpl(arg1);
    	BeanWrapper trg = new BeanWrapperImpl(arg2);
    	
    	boolean result = true;
    	
    	for (String propertyName : compareProperties) {
    		 
    		if (result == false) {
    			return result;
    		}
    		
    		Object srcValue = src.getPropertyValue(propertyName);
    		Object trgValue = trg.getPropertyValue(propertyName);
    		
    		if ((srcValue == null && trgValue != null && trgValue.equals("")) 
    				|| (srcValue != null && trgValue == null && srcValue.equals(""))) {
    			continue;
    		} else if (srcValue instanceof String || trgValue instanceof String) {
    			if (StringUtils.isBlank((String) srcValue) && StringUtils.isBlank((String) trgValue)) {
    				continue;
    			} else if ((StringUtils.isNotBlank((String) srcValue) && StringUtils.isBlank((String) trgValue)) || 
    					(StringUtils.isBlank((String) srcValue) && StringUtils.isNotBlank((String) trgValue))) {
    				result = false;
    			} else if (!srcValue.toString().equals(trgValue.toString())) {
    				result = false;
    			}
    		} else if (srcValue instanceof Date || trgValue instanceof Date) {
    			if (((Date) srcValue).compareTo((Date) trgValue) != 0) {
    				result = false;
    			}
    		} else if (srcValue instanceof Integer || trgValue instanceof Integer) {
    			if (((Integer) srcValue).compareTo((Integer) trgValue) != 0) {
    				result = false;
    			}
    		} else if (srcValue instanceof Double || trgValue instanceof Double) {
    			if (((Double) srcValue).compareTo((Double) trgValue) != 0) {
    				result = false;
    			}
    		} else if (srcValue instanceof BigDecimal || trgValue instanceof BigDecimal) {
    			if (((BigDecimal) srcValue).compareTo((BigDecimal) trgValue) != 0) {
    				result = false;
    			}
    		} else if (srcValue instanceof Boolean || trgValue instanceof Boolean) {
    			if (((Boolean) srcValue).compareTo((Boolean) trgValue) != 0) {
    				result = false;
    			}
    		}
     	}
    	
    	return result;
    }
    
}

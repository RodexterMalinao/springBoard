package com.bomwebportal.service;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dao.AccessRoleDAO;
import com.bomwebportal.dto.AccessPropertyDTO;
import com.bomwebportal.dto.AccessRoleDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.InsufficientPrivilegeException;
import com.pccw.dto.AbstractSecuritySupportFormDTO;

public class SecurityFormDTOFactory {

	private CodeLkupCacheService accessRoleCache = null;
	
	
	@SuppressWarnings("unchecked")
	public <T>T createFormDto(BomSalesUserDTO pUser, String pFuncId, Class<T> pClass) throws InsufficientPrivilegeException {		
		
		AccessPropertyDTO[] properties = null;
		String accessRight = null;
		Object formDto = null;
		
		try {
			Constructor<?> constructor = pClass.getConstructor(new Class[] {String.class, String.class});
			AccessRoleDTO role = (AccessRoleDTO)this.accessRoleCache.get(AccessRoleDAO.buildKey(String.valueOf(pUser.getChannelId()), pUser.getChannelCd(), pUser.getCategory(), pFuncId));
			
			if (role == null) {
				accessRight = AbstractSecuritySupportFormDTO.SECURITY_ENABLE;
			} else {
				accessRight = role.getAccessRight();
				properties = role.getPropertyAccessRole();
			}
			formDto = constructor.newInstance(pFuncId, accessRight);
		} catch (Exception e) {
			throw new AppRuntimeException(e.getMessage(), e);
		}
		if (!(formDto instanceof AbstractSecuritySupportFormDTO)) {
			throw new AppRuntimeException("Object not instance of AbstractSecuritySupportFormDTO");
		}
		if (StringUtils.equals(AbstractSecuritySupportFormDTO.SECURITY_DISABLE, accessRight)) {
			throw new InsufficientPrivilegeException();
		}
		if (properties!=null) {
			for (AccessPropertyDTO accessProperty : properties) {
				((AbstractSecuritySupportFormDTO)formDto).addPropertySecurity(accessProperty.getPropertyName(), accessProperty.getAccessRight());
			}
		}
		return (T)formDto;
	}

	public CodeLkupCacheService getAccessRoleCache() {
		return accessRoleCache;
	}

	public void setAccessRoleCache(CodeLkupCacheService accessRoleCache) {
		this.accessRoleCache = accessRoleCache;
	}
}

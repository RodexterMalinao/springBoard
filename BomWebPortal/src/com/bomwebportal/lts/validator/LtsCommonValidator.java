package com.bomwebportal.lts.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.SignatureDTO;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.util.Utility;

public class LtsCommonValidator {

	public static boolean isValidEmail(String email) {
		return Utility.validateEmail(email);
	}

	public static boolean isAllSignatureSigned(List<SignatureDTO> signatureList) {
		
		if (signatureList == null || signatureList.isEmpty()) {
			return false;
		}
		
		for (SignatureDTO signature : signatureList) {
			if (StringUtils.isBlank(signature.getSignatureString())
					|| ArrayUtils.isEmpty(signature.getSignatureBytes())) {
				return false;
			}
		}
		return true;
	}
	
	public static String removeBaUnwantedBlanks(String str){
		return removeBlanklines(removeTabs(str));
	}
	
	public static String removeBlanklines(String str){
		return StringUtils.join(LtsSbOrderHelper.addresslinesFixer(str.split("\n")), "\n");
	}

	public static String removeTabs(String str){
		return str.replaceAll("\t", "");
	}
	
	public static boolean isValidBaFormat(String ba){
		if(StringUtils.isEmpty(ba)){
			return false;
		}
		String[] splitedBa = ba.split("\n");
		if(splitedBa.length > 6){
			return false;
		}
		for(int i = 0; i < splitedBa.length; i++){
			String baLine = StringUtils.strip(splitedBa[i]);
			if(i == 0){
				if(baLine.length() > 40){
					return false;
				}
			}else{
				if(baLine.length() > 32){
					return false;
				}
			}
		}
		return true;
	}
	public static boolean containsSpecialChar(String str){
		String regEx="-*+[^a-zA-Z0-9`~!@#$%^&*()+_=|{}\\]\\[':;'\",.<>/?~！#￥%……&*（）——【】‘；：”“’。，、？  \t\n\f\r]"; 
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if(m.find()){
			return true;
		}else{
			return false;
		}	
	}
	public static String replaceControlCharacter(String str){
		String regEx = "[\\x00-\\x20&&[^\\x09\\x0c\\x0d]]|\7F";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if(m.find()){
			return m.replaceAll(" ").trim();
		}else{
			return str;
		}
	}
	
	public static boolean isLongerthan100(String str){
		return isLongerThanX(str, 100);
	}
	
	public static boolean isLongerThanX(String str, int x) {
		return str.replaceAll("[\t\n\f\r]", "").length() > x;
	}
	
}

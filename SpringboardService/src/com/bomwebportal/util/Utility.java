package com.bomwebportal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import java.util.UUID;

import com.bomwebportal.bean.LookupTableBean;
import org.apache.commons.lang.StringUtils;

public class Utility {
	private static Locale DEFAULT_LOCALE = Locale.US;

	public static String getDefaultPassword(String docType, String docNum) {
		if (!"HKID".equals(docType)) {// add 20110621
			return "000000";
		}

		StringBuffer defaultPassword = new StringBuffer();
		if ("HKID".equals(docType))
			try {
				for (int i = 0; i < docNum.length(); i++) {
					if (Character.isDigit(docNum.charAt(i))
							&& defaultPassword.length() < 6) {
						defaultPassword.append(docNum.charAt(i));
					}
				}

			} catch (Exception e) {
				return "";
			}
		return defaultPassword.toString();
	}

	public static boolean validateEmail(String val) {

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(val);
		if (matcher.matches()) {
			return true;
		} else {
			// this.setMsg("E-mail validate fail");
			return false;
		}

	}

	public static boolean validatePhoneNum(String val) {
		if (val.length() != 8) {
			// setMsg("Phone length must 8");
			return false;

		} else {
			String expression = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
			Pattern pattern = Pattern.compile(expression,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(val);
			if (matcher.matches()) {
				return true;
			} else {
				// setMsg("Phone validate fail(8 digital)");
				return false;
			}
		}
	}
	
	public static boolean validateNumber(String val) {
			if (val.length() ==0){
				return false;
			}
			String expression = "[1-9][0-9]*";
			Pattern pattern = Pattern.compile(expression,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(val);
			if (matcher.matches()) {
				return true;
			} else {
				return false;
			}
	}

	public static boolean validateMoney(String val) {
		if (val.length() ==0){
			return false;
		}
		String expression = "^[0-9]*(?:\\.[0-9]{1,2})?$";
		Pattern pattern = Pattern.compile(expression,
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(val);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
}
	
	public static boolean validateHKID(String val) {
		if (val.length() != 10 && val.length() != 11) {//edit by wilson 20110128, add length can be 11
			return false;

		} else {
		
			//String expression = "[a-zA-Z][0-9][0-9][0-9][0-9][0-9][0-9][\\(][0-9aA][\\)]";
			String expression = "^[a-zA-Z]{1,2}+\\d{6}\\([0-9a-zAZ-Z]\\)$";//edit by wilson 20110128, add length can be 11
			Pattern pattern = Pattern.compile(expression,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(val);

			if (matcher.matches()) {
				return true;
			} else {
				// setMsg(val + " HKID validate fail, eg.P123456(7)");
				return false;
			}
		}
	}
	
	//add by Eliot 20110608
	public static boolean validateHKBR(String val) {
		if (val.length() != 12) {
			// setMsg("HKBI must length 10");
			//System.out.println(" =======validateHKID(String val)= HKBI must length 10=======");
			return false;

		} else {
		
			String expression = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]-[0-9a-zAZ-Z][0-9a-zAZ-Z][0-9a-zAZ-Z]";			
			Pattern pattern = Pattern.compile(expression,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(val);
			//System.out.println(" =======validateHKBR(String val)========");

			if (matcher.matches()) {
				return true;
			} else {
				// setMsg(val + " HKBR validate fail, eg.12345678-1A2");
				return false;
			}
		}
	}
	
	public static boolean validateHKIDcheckDigit(String input) {
	
		boolean result=false;

		if (input != null && !input.equals("")) {
			try {
				boolean matchPattern = false;
				String firstAlphabet = null;
				String secondAlphabet = null;
				String numberPart = null;
				String checkDigit = null;

			
				if(input.length()==11){
					matchPattern = true;

					firstAlphabet = input.substring(0, 1);
					secondAlphabet = input.substring(1, 2);
					numberPart = input.substring(2, 8);
					checkDigit = input.substring(9, 10);
				}
				else if (input.length()==10){
				
					matchPattern = true;

					firstAlphabet = " ";
					secondAlphabet = input.substring(0, 1);
					numberPart = input.substring(1, 7);
					checkDigit = input.substring(8, 9);
				}

				if (matchPattern) {
					if (checkDigit.equals("A")){
						checkDigit = "10";
					}

					int sum = 0;

					sum += (getLetterValue(firstAlphabet) + 10) * 9;
					sum += (getLetterValue(secondAlphabet) + 10) * 8;

					for (int i = 0; i < numberPart.length(); i++) {			    	
						sum += Integer.parseInt(numberPart.substring(i, i + 1)) * (7 - i);
					}

					int checkNumber = (11 - (sum % 11)) % 11;

					if (checkNumber == Integer.parseInt(checkDigit)) {
						result =true;
					
					} else {
						result =false;
					
					}
				}
			}
			catch (Exception e) {
				result =false;
			
			}
		}
		else {
			
			result =true;
		}


		return result;
	}
	
	//add by eliot 20110609
	public static boolean validateHKBRcheckDigit(String input) {
		int nSum = 0;
		int nChkDigit = 0;
		boolean result = false;
		
		if (input.substring(0).equalsIgnoreCase("#") && "".equals(input) && input.length() != 12)
			result = false;
		else {			
			nSum = 2 * Integer.parseInt(input.substring(0,1));
			nSum = nSum + 9 * Integer.parseInt(input.substring(1,2));
			nSum = nSum + 8 * Integer.parseInt(input.substring(2,3));
			nSum = nSum + 7 * Integer.parseInt(input.substring(3,4));
			nSum = nSum + 4 * Integer.parseInt(input.substring(4,5));
			nSum = nSum + 3 * Integer.parseInt(input.substring(5,6));
			nSum = nSum + 2 * Integer.parseInt(input.substring(6,7));
			
			nChkDigit = 10 - ( nSum % 10);
			
			if (Integer.parseInt(input.substring(7,8)) == (nChkDigit % 10))
				result = true;
		}
		
		return result;
	}

	private static int getLetterValue(String letter) { 
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ ".indexOf(letter);
	}


	public static String stringTo_date(String day, String month, String year) {
		return day + "/" + month + "/" + year;
	}
	
	public static boolean isValidDate(String day, String month, String year) {
		return isValidDate(day, month, year, DEFAULT_LOCALE);
	}
	
	public static boolean isValidDate(String day, String month, String year, Locale locale) {
		try
        {
			Date date;
			DateFormat formatter ; 
			
	          formatter = new SimpleDateFormat("dd/MM/yyyy", locale);
	          formatter.setLenient(false);//must set false
	          date= formatter.parse(day + "/" + month + "/" + year);  
	           
	             
        }catch(ParseException e)
        {
            return false;
        }
        return true;

	}
	
	public static boolean isValidDate(String dateStr) {
		return isValidDate(dateStr, DEFAULT_LOCALE);
	}
	
	public static boolean isValidDate(String dateStr, Locale locale) {
		
		if (dateStr.length()==0){
			return false;
		}
		try
        {
			Date date;
			DateFormat formatter ; 
			
	          formatter = new SimpleDateFormat("dd/MM/yyyy", locale);
	          formatter.setLenient(false);//must set false
	          date = formatter.parse(dateStr);
	           
	             
        }catch(ParseException e)
        {
            return false;
        }
        return true;

	}
	
	
	

	public static String date2String(Date date, String format) {
		return formatDate(date, format, DEFAULT_LOCALE);
	}
	
	

	public static String formatDate(Date date, String format,
			Locale currentLocale) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(format, currentLocale);
		String dateString = df.format(date);
		return dateString;
	}

	public static Date string2Date(String year, String month, String day) {
		return string2Date(year, month, day, DEFAULT_LOCALE);
	}

	public static Date string2Date(String year, String month, String day, Locale locale) {
		try{

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
			Date convertedDate = dateFormat.parse(year+"/"+month+"/"+day);

			return convertedDate;
		}catch(ParseException ex){
			return null;
		}
	}
	
	public static Date string2Date(String date) {
		return string2Date(date, DEFAULT_LOCALE);
	}
	
	public static Date string2Date(String date, Locale locale) {
		try{

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", locale);
			Date convertedDate = dateFormat.parse(date);

			return convertedDate;
		}catch(ParseException ex){
			return null;
		}
	}
	

	public static boolean isContainString(String[] pStrings, String pString) {
		for (int i = 0; i < pStrings.length; i++) {
			if (StringUtils.equals(pStrings[i], pString)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getContactName(String custTitle, String custFirstName, String custLastName){
		String contactName = custLastName + " " + custFirstName;
		int allowLength = 39 - custTitle.length(); 
		if(contactName.length()> allowLength){
			contactName = contactName.substring(0,allowLength);
		}
		return contactName.trim();
	}

	
	public static int getBillPeriod(int input )
    {
		List<Integer> periodArray=LookupTableBean.getInstance().getBillPeriodList();
        // { 10, 14, 17, 24 };
        int index = 0;
        for (int i = 0; i < periodArray.size(); i++)
        {
            int temp = periodArray.get(i);
            if (input < temp)
            {
                index = i-1;
                break;
            }
            else {
                index = -1;
            }
        }
       
        if (index + 2 < periodArray.size())
        {
            return periodArray.get(index + 2);
        }
        else {
            return periodArray.get(0);
           
        }
    }
		
	//add by wilson 20110202, for payment validate
	public static boolean validateCreditCardExpiryDate( String month, String year) {
		return validateCreditCardExpiryDate(month, year, DEFAULT_LOCALE);
	}
	public static boolean validateCreditCardExpiryDate( String month, String year, Locale locale) {
		Date today = new Date();
		String expiryDateStr = "01/" + month+"/"+year;
		SimpleDateFormat outDateFormat = new SimpleDateFormat("dd/MM/yyyy", locale);
		String todayDateStr = outDateFormat.format(today);

		
			int expiryDateDay = Integer.parseInt(expiryDateStr.substring(0, 2));
			int expiryDateMonth = Integer.parseInt(expiryDateStr.substring(3, 5));
			int expiryDateYear = Integer.parseInt(expiryDateStr.substring(6, 10));
			int nowDay = Integer.parseInt(todayDateStr.substring(0, 2));
			int nowMonth = Integer.parseInt(todayDateStr.substring(3, 5));
			int nowYear = Integer.parseInt(todayDateStr.substring(6, 10));
			
			if (expiryDateYear > nowYear){
				if (expiryDateYear - nowYear > 1)
					return true;
				else if (expiryDateYear - nowYear == 1)
					if (expiryDateMonth + 12 - nowMonth >= 3)
						return true;
			}
			else if (expiryDateYear == nowYear)
				if (expiryDateMonth - nowMonth >= 3)
					return true;
		

		return false;

	}
	//add by wilson 20110202, for payment validate
	public static boolean validateCreditCardNumber(String cardNum,
			String cardType) {
		
		
		final int BASE = 52;

		// BOMListBox listboxCardType;

		final String VISA_CARD = "01";
		final String MASTER_CARD = "02";
		final String OTP_CARD = "03";
		final String AMEX_CARD = "04";
		final String DINERS_CARD = "05";

		final int ERROR_INVALID_CARD_TYPE = 1;
		final int ERROR_INVALID_CARD_NUMBER = 2;
		boolean validateResult=true;

		int errorType = ERROR_INVALID_CARD_NUMBER;
		
	

		if (cardNum != null && !cardNum.equals("")) {
			try {
				
				//check Token format
				int					rSUM;
				char				rChr;

				int					r0, r1, r2, r3;
				String				rA, rB, rC, rD;

				r1 = cardNum.length();
				if (r1 < 13 || r1 > 20) {
					//System.out.println("Token is Too Short or Too Long!");
					validateResult=false;
				}

				r2 = r1 - 4;

				for (r0 = 0; r0 < r1; r0++) {
					rChr = cardNum.charAt(r0);
					if (r0 < 6 || r0 >= r2) {
						/* Except Numeric */
						if (!(rChr >= '0' && rChr <= '9')) {
							//System.out.println("Invalid Token Picture!");
							validateResult=false;
						}
					} else {
						/* Except Alphabetic */
						if (!((rChr >= 'A' && rChr <= 'Z') || (rChr >= 'a' && rChr <= 'z'))) {
							//System.out.println("Invalid Token Picture!");
							validateResult=false;
						}
					}
				}
				
				rSUM = 0;

				for (r0=0; r0<cardNum.length(); r0++) {
					rChr = cardNum.charAt(r0);

					r1 = toI10Val(rChr);
					rSUM += r1;

					if (rSUM >= BASE) rSUM -= BASE;
				}

				if (rSUM != 0){
					validateResult=false;
				}
				
				//check Token form & type
				// --if (selectedValue != null){
					//String selectedValue = listboxCardType.getSelectedValue();
					
					if (cardType != null) {
						// Visa Validation
						if (cardType.equals(VISA_CARD)) {
							if (!cardNum.startsWith("4")) {
								validateResult=false;
							} //check length
							if ((cardNum.length() != 13) && (cardNum.length() != 16)) {
								validateResult=false;
							}
							// Master Validation
						} else if (cardType.equals(MASTER_CARD)) {
							if (Integer.parseInt(cardNum.substring(0, 2)) < 51
									|| Integer.parseInt(cardNum.substring(0, 2)) > 55) {
								validateResult=false;
							} //check length
							if (cardNum.length() != 16) {
								validateResult=false;
							} 
							// AMEX Validation
						} else if (cardType.equals(AMEX_CARD)) {
							if ((!cardNum.startsWith("34") && !cardNum
											.startsWith("37"))) {
								validateResult=false;
							} //check length
							if (cardNum.length() != 15) {
								validateResult=false;
							}
						}
							// Diners Club Validation
						else if (cardType.equals(DINERS_CARD)) {
							if ((!cardNum.startsWith("36")
										&& !cardNum.startsWith("38")
										&& !(Integer.parseInt(cardNum.substring(0, 3)) < 300 
												|| Integer.parseInt(cardNum.substring(0, 3)) > 305))) {
								validateResult=false;
							} //check length
							if (cardNum.length() != 14) {
								validateResult=false;
							}
						} else {
							errorType = ERROR_INVALID_CARD_TYPE;
							validateResult=false;
							//System.out.println("ERROR_INVALID_CARD_TYPE error1");
						}
					} else {
						errorType = ERROR_INVALID_CARD_TYPE;
						validateResult=false;
						//System.out.println("ERROR_INVALID_CARD_TYPE error2");
					}
				
			} catch (Exception e) {
				validateResult=false;
			}

		} 

		return validateResult;


	}
		
	public static int toI10Val(char rChr){
		if (rChr >= '0' && rChr <= '9') return ((int) (rChr - '0'));
			else if (rChr >= 'A' && rChr <= 'Z') return ((int) (rChr - 'A'));
			else if (rChr >= 'a' && rChr <= 'z') return ((int) ((rChr - 'a') + 26));
	
		throw new RuntimeException("Invalid rChr:"+rChr);
	}	
	
	public static boolean isDigit(String digit){
		for(int i=0; i<digit.length(); i++){
			if(!Character.isDigit(digit.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkDistrictArea(String districtCode, String areaCode) {
		boolean checkAreaDist = false;

		if (LookupTableBean.getInstance().getAddressDistrictMap()
				.containsKey(districtCode)) {
			String listArea = LookupTableBean.getInstance()
					.getAddressDistrictMap().get(districtCode);
			if (listArea.equals(areaCode))
				checkAreaDist = true;
		}

		return checkAreaDist;

	}
	//Fault ID: 0002498==> Customer info - contact number, 
	//contact number only allow contact no. prefix with 2,3,5,6,8,9
	public static boolean validateHKPhonePreFix(String val) {
		
		String[] pStrings = new String[]{"2","3","5","6","7","8","9"};
		return  isContainString(pStrings, val.substring(0, 1));
	}

	public static boolean validateHKMobilePrefix(String msisdn) {
		// http://en.wikipedia.org/wiki/Telephone_numbers_in_Hong_Kong
		for (String prefix : new String[] { "5", "6", "9" }) {
			if (msisdn.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean validateHKSMSPrefixIMS(String msisdn) {
		for (String prefix : new String[] { "5", "6", "9" }) {
			if (msisdn.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean validateChinaMobilePrefix(String msisdn) {
		// http://en.wikipedia.org/wiki/Telephone_numbers_in_China#Mobile_phones
		for (String prefix : new String[] { 
				"13"
				                            , "145"       , "147"
				, "150", "151", "152", "153", "155", "156", "157", "158", "159"
				, "180"       , "182"       , "185", "186", "187", "188", "189" }) {
			if (msisdn.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}
	
	public static String showLocalIP(){
		String ip = "";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			ip = inet.getHostAddress();
		} catch (Exception e){
			return "N/A";
		}
		
		return ip;
	}
	/**
	 * check if String whether is an Integer type
	 * @param inValue
	 * @return
	 */
	public static boolean isInteger(String inValue) {
		
		try {
			Integer.parseInt(inValue);
		} catch (NumberFormatException n) {
			return false;
		}
		
		return true;
	}

	
	public static Date string2DateWithTime(String date) {
		return string2DateWithTime(date, DEFAULT_LOCALE);
	}
	
	public static Date string2DateWithTime(String date, Locale locale) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm", locale);
			Date convertedDate = dateFormat.parse(date);
			return convertedDate;
		} catch (ParseException ex) {
			return null;
		}
	}
	public static String getUid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
		
	}
	
	// add by Joyce 20121212
	/**
	 * Usage: Validate input passport number<br>
	 * Criteria of a valid passport number:<br>
	 * 1. Not null, not a space, length = 0, ...<br>
	 * 2. Alphanumeric<br>
	 * 3. Minimum 6-digit<br>
	 * @param passportNum
	 * @return true = valid, false = invalid
	 */
	public static boolean validatePassport(String passportNum) {
		
		if (StringUtils.isBlank(passportNum)) {
			return false;
		} else if (!StringUtils.isAlphanumeric(passportNum)) {
			return false;
		} else if (passportNum.length() < 6) {
			return false;
		}
		
		return true;
	}
	
	// validate bank account number
	/*
	 * requires bank account number in digit, with max length: 9
	 */
	public static boolean validateBankAcctNum(String bankAcctNum) {
		if (!validateNumber(bankAcctNum)) {
			return false;
		}
		return bankAcctNum.length() <= 9;
	}
	
	public static boolean validateEmailMobSpecific(String val) {
		// ref:FW: IM1352977 - {System / Network fault} - MRT does not exist in PCRF
		/*
		 * addr-spec:local-part "@" domain local-part:word *("." word) domain:sub-domain *("." sub-domain)
		 * word:!#$*-/0123456789?ABCDEFGHIJKLMNOPQRSTUVWXYZ^_`abcdefghijklmnopqrstuvwxyz{|}~ excluding ()<>@,;:\".[]="%&'+
		 * sub-domain:!#$*-/0123456789?ABCDEFGHIJKLMNOPQRSTUVWXYZ^_`abcdefghijklmnopqrstuvwxyz{|}~ excluding ()<>@,;:\".[]="%&'+ address cannot be longer than
		 * 64 characters.
		 */

		String ATOM = "[a-z0-9!#$*/?^_`{|}~-]";
		String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
		String expression = "^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN + ")$";

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(val);
		
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}

	}
	
	//new validateCreditCardExpiryDateByAppDate function, copy validateCreditCardExpiryDate
	public static boolean validateCreditCardExpiryDateByAppDate(String month,
			String year, Date appDate) {
		return validateCreditCardExpiryDateByAppDate(month, year,
				DEFAULT_LOCALE, appDate);
	}

	//new validateCreditCardExpiryDateByAppDate function, copy validateCreditCardExpiryDate
	public static boolean validateCreditCardExpiryDateByAppDate(String month,
			String year, Locale locale, Date appDate) {
		Date today = appDate;
		String expiryDateStr = "01/" + month + "/" + year;
		SimpleDateFormat outDateFormat = new SimpleDateFormat("dd/MM/yyyy",
				locale);
		String todayDateStr = outDateFormat.format(today);

		int expiryDateMonth = Integer.parseInt(expiryDateStr.substring(3, 5));
		int expiryDateYear = Integer.parseInt(expiryDateStr.substring(6, 10));
		int nowMonth = Integer.parseInt(todayDateStr.substring(3, 5));
		int nowYear = Integer.parseInt(todayDateStr.substring(6, 10));

		if (expiryDateYear > nowYear) {
			if (expiryDateYear - nowYear > 1)
				return true;
			else if (expiryDateYear - nowYear == 1)
				if (expiryDateMonth + 12 - nowMonth >= 3)
					return true;
		} else if (expiryDateYear == nowYear)
			if (expiryDateMonth - nowMonth >= 3)
				return true;

		return false;

	}

}

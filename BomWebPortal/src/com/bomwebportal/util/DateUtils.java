package com.bomwebportal.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.validation.Errors;

public class DateUtils {
	
	/**
	 * Add days into the date input
	 * 
	 * @param date
	 *            Date to be added
	 * @param days
	 *            number of days to add
	 * @return Date after adding days
	 * @author James Wong
	 */
	public static Date dateAfterdays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	/**
	 * Substract days from the date input
	 * 
	 * @param date
	 *            Date to be substract
	 * @param days
	 *            number of days to substract
	 * @return Date after substracting from days
	 * @author James Wong
	 */
	public static Date dateBeforedays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if (days > 0) {
			cal.add(Calendar.DATE, 0 - days);
		} else {
			cal.add(Calendar.DATE, days);
		}

		return cal.getTime();
	}

	/**
	 * Calculate the days different between start date and end date. If
	 * startDate is earlier than the endDate, it will return negative. If
	 * startDate is latter than the endDate it will return positive.
	 * 
	 * @param startDate
	 *            Start Date
	 * @param endDate
	 *            End Date
	 * @return Number different of days between start and end date
	 * @author James Wong
	 */
	public static long daysDiffBetween(final Date startDate, final Date endDate) {

		Calendar sDate = Calendar.getInstance();
		sDate.setTime(startDate);
		Calendar eDate = Calendar.getInstance();
		eDate.setTime(endDate);

		long millSecondDiff = sDate.getTimeInMillis() - eDate.getTimeInMillis();

		return millSecondDiff / (24 * 60 * 60 * 1000);
	}

}

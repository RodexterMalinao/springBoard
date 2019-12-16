package com.bomwebportal.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemTime {
	
	private final static Log logger = LogFactory.getLog(SystemTime.class);

	private static final TimeSource defaultSrc = new TimeSource() {
		public long millis() {
			return System.currentTimeMillis();
		}
	};

	private static TimeSource source = null;

	public static long asMillis() {
		return getTimeSource().millis();
	}

	public static Date asDate() {
		return new Date(asMillis());
	}
	
	public static Calendar asCalendar() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(asMillis());
		return c;
	}

	public static void reset() {
		setTimeSource(null);
	}

	public static void setTimeSource(TimeSource source) {
		logger.info("Using time source : " + source);
		SystemTime.source = source;
	}

	private static TimeSource getTimeSource() {
		return (source != null ? source : defaultSrc);
	}

	public interface TimeSource {
		long millis();
	}
}

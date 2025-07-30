package com.okstatelibrary.spacesui.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateTimeUtil {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public static String getTodayDate() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();

		return dtf.format(now);
	}
	
	public static long getCurretTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	public static String convertTo12HourTime(String dateTime) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatter = new SimpleDateFormat("hh:mm a");

		Date time = null;

		try {
			time = dateFormat.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatter.format(time);
	}

	public static String convertToTime(String dateTime) {
		DateFormat formatter = new SimpleDateFormat("hh:mm a");

		Date time = null;

		try {
			time = dateFormat.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatter.format(time);
	}

	public static String convertToDateTime(String dateTime) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

		Date time = null;

		try {
			time = dateFormat.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatter.format(time);
	}

	public static String convertToDate(String dateTime) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date time = null;

		try {
			time = dateFormat.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatter.format(time);
	}

	public static String convertToISODateTime(String date, String time) {

		String str = date + " " + time;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd hh:mm a");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

		// When booking the 11.30 PM - 11.59 PM time slot the ending time should be
		// newt day 00.00 AM
		if (time.equals("11:59 PM")) {
			dateTime = dateTime.plus(Duration.of(1, ChronoUnit.MINUTES));
		}

		return dateTime.toString() + ":00";

	}
}

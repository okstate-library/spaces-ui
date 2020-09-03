package com.okstatelibrary.spacesui.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {

	public static String convertToTime(String dateTime) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-05:00");
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-05:00");
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-05:00");
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

	public static String convertToISODateTime(String datetime) {
		
		String str = datetime;
		System.out.println("str " + str);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/dd hh:mm a");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		
		return dateTime.toString() + ":00-0500";

	}
}

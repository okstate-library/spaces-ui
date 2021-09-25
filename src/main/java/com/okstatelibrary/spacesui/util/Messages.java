package com.okstatelibrary.spacesui.util;

public class Messages {

	public static String ERROR_BOOKING_NOT_FOUND = "No booking record found!";

	public static String ERROR_FREINDS_EMAIL_FORMAT = "Please enter valid okstate email address(es) separated with comma.";

	public static String ERROR_EXTERNAL_API_NOT_WORKING = "Oops, something went wrong.";

	public static String ERROR_BOOKING_EXCEED_DAYIL_LIMIT = "You have exceeded the daily limit.";

	public static String ERROR_BOOKING_EXCEED_DAYIL_ROOM_LIMIT = "You have reached the two room reservation limit.";

	public static String ERROR_BOOKING_RESERVATION_WITHIN_TWO_HOURS = "You already have a reservation within two hours before or after the time you are trying to book.";

	public static String ERROR_BOOKING_TRY_ALREADY_BOOKED_TIMESLOT = "The time slot you’ve selected is already booked.";

	public static String ERROR_BOOKING_SOMETING_WENT_WRONG = "Something went wrong with booking process. Please try again later. "
			+ "If you continue to receive this error please contact OSU Library Circulation Desk.";

	public static String SUCESS_BOOKING_CANCEL = "This booking has been canceled.";

	public static String ERROR_BOOKING_SUMMARY_MISSING_BOOKING_ID = "The booking ID could not be found because the booking time has elapsed or has already been cancelled.";

	public static String ERROR_BOOKING_CANCEL_SOMETING_WENT_WRONG = "Something went wrong with booking cancellation process. "
			+ "Please try again later If you continue to receive this error please contact OSU Library Circulation Desk.";

	public static String WARNING_NO_CONTIGUOUS_TIME_BLOCKS = "Please select continuous time blocks.";

	public static String WARNING_RESERVE_ONE_ROOM_AT_ONCE = "You may only reserve one room at a time.";

	public static String WARNING_UNSELECT_TIME_SLOTS = "Please unselect first or last time slots only.";

	public static String WARNING_SELECT_ONLY_EIGHT_TIME_SLOTS = "You may select up to 8 time slots at a time.";
}

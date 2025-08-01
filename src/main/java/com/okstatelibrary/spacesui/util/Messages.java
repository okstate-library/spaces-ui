package com.okstatelibrary.spacesui.util;

/**
 * A centralized utility class for holding static message constants used across
 * the application.
 * <p>
 * These messages are commonly used for error reporting, warnings, and success
 * notifications in booking operations and validation processes.
 *
 * <p>
 * Each message is meant to be user-friendly and may be displayed in the UI or
 * returned as part of API responses.
 *
 * <p>
 * Example usage:
 * 
 * <pre>
 * throw new BookingException(Messages.ERROR_BOOKING_NOT_FOUND);
 * </pre>
 */
public class Messages {

	/** Message indicating that no booking record was found. */
	public static String ERROR_BOOKING_NOT_FOUND = "No booking record found!";

	/**
	 * Message requesting valid email address format for friends (Okstate emails,
	 * comma-separated).
	 */
	public static String ERROR_FREINDS_EMAIL_FORMAT = "Please enter valid okstate email address(es) separated with comma.";

	/** Generic error message when an external API fails. */
	public static String ERROR_EXTERNAL_API_NOT_WORKING = "Oops, something went wrong.";

	/**
	 * Message displayed when the user exceeds the allowed number of daily bookings.
	 */
	public static String ERROR_BOOKING_EXCEED_DAYIL_LIMIT = "You have exceeded the daily limit.";

	/**
	 * Message shown when the user attempts to book more rooms than allowed per day.
	 */
	public static String ERROR_BOOKING_EXCEED_DAYIL_ROOM_LIMIT = "You have reached the two room reservation limit.";

	/**
	 * Message shown when a booking overlaps with another within a two-hour buffer.
	 */
	public static String ERROR_BOOKING_RESERVATION_WITHIN_TWO_HOURS = "You already have a reservation within two hours before or after the time you are trying to book.";

	/** Message shown when a selected time slot is already booked. */
	public static String ERROR_BOOKING_TRY_ALREADY_BOOKED_TIMESLOT = "The time slot you’ve selected is already booked.";

	/** Critical booking failure message with suggestion to contact support. */
	public static final String ERROR_BOOKING_SOMETING_WENT_WRONG = "Something went wrong with booking process. Please try again later. "
			+ "If you continue to receive this error please contact ";

	/** Message indicating that a user is unauthorized or inactive. */
	public static String ERROR_USER_UNAUTHORIZE = "Inactive User";

	/** Message shown after a successful booking cancellation. */
	public static String SUCESS_BOOKING_CANCEL = "This booking has been canceled.";

	/**
	 * Message for when booking ID is not found, likely due to expired or cancelled
	 * booking.
	 */
	public static String ERROR_BOOKING_SUMMARY_MISSING_BOOKING_ID = "The booking ID could not be found because the booking time has elapsed or has already been cancelled.";

	/** Error message for failed booking cancellation process. */
	public static final String ERROR_BOOKING_CANCEL_SOMETING_WENT_WRONG = "Something went wrong with booking cancellation process. "
			+ "Please try again later If you continue to receive this error please contact ";

	/** Warning shown when selected time blocks are not continuous. */
	public static String WARNING_NO_CONTIGUOUS_TIME_BLOCKS = "Please select continuous time blocks.";

	/**
	 * Warning shown when the user attempts to reserve more than one room at a time.
	 */
	public static String WARNING_RESERVE_ONE_ROOM_AT_ONCE = "You may only reserve one room at a time.";

	/** Warning to guide the user to unselect only the first or last time slots. */
	public static String WARNING_UNSELECT_TIME_SLOTS = "Please unselect first or last time slots only.";

	/** Warning when the user tries to select more than 8 time slots. */
	public static String WARNING_SELECT_ONLY_EIGHT_TIME_SLOTS = "You may select up to 8 time slots at a time.";
}

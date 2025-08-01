package com.okstatelibrary.spacesui.util;

/**
 * A utility class that provides constants and methods for building URLs used to
 * interact with the LibCal Room Booking API at Oklahoma State University.
 * <p>
 * This class is not meant to be instantiated.
 */
public class URLs {

	/**
	 * Private constructor to prevent instantiation of this utility class.
	 */
	private URLs() {
	}

	/** Base URL for the LibCal v1.1 API. */
	public static final String BASE_URL = "https://okstate.libcal.com/1.1/";

	/** Base URL for accessing Springshare API endpoints. */
	public static final String SPRINGSHARE_API_BASE_URL = "https://okstate.libcal.com/api/1.1/";

	/** Base URL specifically for accessing room/space-related endpoints. */
	public static final String SPACES_BASE_URL = BASE_URL + "space/";

	/** URL to fetch the list of available rooms. */
	public static final String GET_ROOMS_URL = BASE_URL + "/rooms";

	/** URL to obtain an OAuth authentication token. */
	public static final String GET_AUTH_TOKEN_URL = BASE_URL + "oauth/token";

	/** URL to book/reserve a room. */
	public static final String BOOK_A_ROOM_URL = SPACES_BASE_URL + "reserve";

	/** URL to retrieve booking details for a specific reservation. */
	public static final String GET_BOOKING_DETAILS_URL = SPACES_BASE_URL + "booking/";

	/** URL to fetch details of a specific room. */
	public static final String GET_ROOM_DETAILS_URL = SPACES_BASE_URL + "item/";

	/** URL to cancel a room booking. */
	public static final String POST_ROOM_CANCEL_URL = SPACES_BASE_URL + "cancel/";

	/** URL to get the operational hours of a specific location. */
	public static final String GET_LOCATION_HOURS_URL = SPRINGSHARE_API_BASE_URL + "hours/";

	/**
	 * Constructs a URL to fetch room availability for a specific list of room IDs
	 * on a given date.
	 *
	 * @param roomidList comma-separated list of room IDs
	 * @param date       the date for which availability is requested (e.g.,
	 *                   "2025-08-01")
	 * @return the constructed availability URL
	 */
	public static String getSpacesURL(String roomidList, String date) {
		return BASE_URL + "/space/item/" + roomidList + "?availability=" + date;
	}

	/**
	 * Constructs a URL to fetch rooms associated with a specific category ID.
	 *
	 * @param categoryId the ID of the category (e.g., library location)
	 * @return the constructed category-specific rooms URL
	 */
	public static String getRoomsByCategoryURL(String categoryId) {
		return SPACES_BASE_URL + "category/" + categoryId;
	}
}

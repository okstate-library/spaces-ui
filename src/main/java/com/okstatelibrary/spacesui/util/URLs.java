package com.okstatelibrary.spacesui.util;

public class URLs {

	private URLs() {
	}

	public static final String BASE_URL = "https://okstate.libcal.com/1.1/";

	public static final String SPACES_BASE_URL = BASE_URL + "space/";

	public static final String GET_ROOMS_URL = BASE_URL + "/rooms";

	public static final String GET_AUTH_TOKEN_URL = BASE_URL + "oauth/token";

	public static final String BOOK_A_ROOM_URL = SPACES_BASE_URL + "reserve";

	public static final String GET_BOOKING_DETAILS_URL = SPACES_BASE_URL + "booking/";

	public static final String GET_ROOM_DETAILS_URL = SPACES_BASE_URL + "item/";

	public static final String POST_ROOM_CANCEL_URL = SPACES_BASE_URL + "cancel/";

	//public static final String GET_ROOMS_BY_CATEGORY = SPACES_BASE_URL + "category/7030";

	public static String getSpacesURL(String roomidList, String date) {
		return BASE_URL + "/space/item/" + roomidList + "?availability=" + date;
	}
	
	public static String getRoomsByCategoryURL(String categoryId) {
		return SPACES_BASE_URL + "category/" + categoryId;
	}
	
}

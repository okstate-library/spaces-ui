package com.okstatelibrary.spacesui.services;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okstatelibrary.spacesui.globals.GlobalConfigs;
import com.okstatelibrary.spacesui.models.*;
import com.okstatelibrary.spacesui.tenant.MasterData;
import com.okstatelibrary.spacesui.util.URLs;

@Service
public class SpacesService {

	/**
	 * Custom defines system properties.
	 */
	@Autowired
	com.okstatelibrary.spacesui.util.SystemProperties systemProperties;

	private RestTemplate restTemplate = new RestTemplate();

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * Access Token service
	 */
	@Autowired
	AccessTokenService accessTokenService;

	///
	/// Get all the available categories.
	///
	public Category[] getRoomsByCategory(String accessToken, String url)
			throws JsonParseException, JsonMappingException, RestClientException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		try {

			ResponseEntity<Category[]> response = restTemplate.exchange(url, HttpMethod.GET, request, Category[].class);

			Category[] spaceItem = response.getBody();
			return spaceItem;
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	///
	/// Get all available rooms.
	///
	public SpaceItem[] getItems(String accessToken, String url)
			throws JsonParseException, JsonMappingException, RestClientException, IOException {

		System.out.println("accessToken : " + accessToken);
		System.out.println("url : " + url);

		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Authorization", "Bearer " + accessToken);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		try {
			ResponseEntity<SpaceItem[]> response = restTemplate.exchange(url, HttpMethod.GET, request,
					SpaceItem[].class);
			SpaceItem[] spaceItem = response.getBody();
			return spaceItem;
		} catch (Exception e) {

			System.out.println("Error Accessing SpacesItem");
			// TODO: handle exception
			e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	///
	/// Returns whether booking confirms or any error related to the booking.
	///
	public BookingConfirmation bookARoom(String accessToken, RoomBookingPayload payload, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Host", "okstate.libcal.com");
		HttpEntity<?> request = new HttpEntity<Object>(payload, headers);
		try {

			ResponseEntity<BookingConfirmation> roomBookingResponse = restTemplate.exchange(url, HttpMethod.POST,
					request, BookingConfirmation.class);

			return roomBookingResponse.getBody();

		} catch (Exception e) {

			return new BookingConfirmation(e.getMessage());
		}
	}

	///
	/// Returns detail related to the booked session.
	/// otherwise an error.
	///
	public BookedItem[] getBookedItems(String accessToken, String url)
			throws JsonParseException, JsonMappingException, RestClientException, IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		try {

			ResponseEntity<BookedItem[]> response = restTemplate.exchange(url, HttpMethod.GET, request,
					BookedItem[].class);

			return (BookedItem[]) response.getBody();

		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	///
	/// Get the relevant room details otherwise an error.
	///
	public Room[] getRoom(String accessToken, String url)
			throws JsonParseException, JsonMappingException, RestClientException, IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		try {

			ResponseEntity<Room[]> response = restTemplate.exchange(url, HttpMethod.GET, request, Room[].class);

			return (Room[]) response.getBody();

		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	///
	/// Returns the booking cancellation and otherwise an error.
	///
	public CancelConfirmation[] cancel(String accessToken, String url)
			throws JsonParseException, JsonMappingException, RestClientException, IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		try {

			ResponseEntity<CancelConfirmation[]> response = restTemplate.exchange(url, HttpMethod.POST, request,
					CancelConfirmation[].class);

			return (CancelConfirmation[]) response.getBody();

		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

//	///
//	/// Get the library opening hours otherwise an error.
//	///
//	public Location[] getHours(String accessToken, String url)
//			throws JsonParseException, JsonMappingException, RestClientException, IOException {
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", "Bearer " + accessToken);
//		HttpEntity<String> request = new HttpEntity<String>(headers);
//
//		try {
//
//			ResponseEntity<Location[]> response = restTemplate.exchange(url, HttpMethod.GET, request, Location[].class);
//
//			return (Location[]) response.getBody();
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.getMessage();
//			e.printStackTrace();
//			return null;
//		}
//	}

	private String getAccessTokenFromRequest()
			throws JsonParseException, RestClientException, JsonMappingException, IOException, JSONException {

		AccessToken accessToken = accessTokenService.getAccessToken(URLs.GET_AUTH_TOKEN_URL,
				systemProperties.getSpringShareClientId(), systemProperties.getSpringShareSecretkey());

		if (accessToken != null) {
			// System.out.println("accessToken.getAccessToken() : " +
			// accessToken.getAccessToken());
			return accessToken.getAccessToken();
		} else {
			return null;
		}
	}

	public MasterData loadMasterData(GlobalConfigs config)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		MasterData masterData = new MasterData();

		masterData.setCategories(getRoomsByCategory(getAccessTokenFromRequest(),
				URLs.getRoomsByCategoryURL(config.getCategoryNumber())));

		masterData.setCategoryNumber(config.getCategoryNumber());

		masterData.setStudyRooms();

		return masterData;
	}

}

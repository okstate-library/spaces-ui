package com.okstatelibrary.spacesui.services;

import java.io.IOException;

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
import com.okstatelibrary.spacesui.models.BookedItem;
import com.okstatelibrary.spacesui.models.BookingConfirmation;
import com.okstatelibrary.spacesui.models.CancelConfirmation;
import com.okstatelibrary.spacesui.models.Category;
import com.okstatelibrary.spacesui.models.Room;
import com.okstatelibrary.spacesui.models.RoomBookingPayload;
import com.okstatelibrary.spacesui.models.SpaceItem;

@Service
public class SpacesService {

	private RestTemplate restTemplate = new RestTemplate();

	ObjectMapper mapper = new ObjectMapper();

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

	public SpaceItem[] getItems(String accessToken, String url)
			throws JsonParseException, JsonMappingException, RestClientException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		try {
			ResponseEntity<SpaceItem[]> response = restTemplate.exchange(url, HttpMethod.GET, request,
					SpaceItem[].class);
			SpaceItem[] spaceItem = response.getBody();
			return spaceItem;
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	public BookingConfirmation bookARoom(String accessToken, RoomBookingPayload payload, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Host", "okstate.libcal.com");
		HttpEntity<?> request = new HttpEntity<Object>(payload, headers);
		try {

			ObjectMapper Obj = new ObjectMapper();
			String jsonStr = Obj.writeValueAsString(payload);

			// Displaying JSON String
			System.out.println("payload--" + jsonStr);

			System.out.println("payload--" + payload.toString());

			ResponseEntity<BookingConfirmation> roomBookingResponse = restTemplate.exchange(url, HttpMethod.POST,
					request, BookingConfirmation.class);

			return roomBookingResponse.getBody();

		} catch (Exception e) {

			return new BookingConfirmation(e.getMessage());

//			System.out.println("Buffer Error" + e.getMessage());
//
//			e.getMessage();
//			e.printStackTrace();
//			e.toString();
//
//			return null;
		}
	}

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

}

/*
 * Copyright 2020 Vincenzo De Notaris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.okstatelibrary.spacesui.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.okstatelibrary.spacesui.models.AccessToken;
import com.okstatelibrary.spacesui.models.Availability;
import com.okstatelibrary.spacesui.models.BookedItem;
import com.okstatelibrary.spacesui.models.BookingConfirmation;
import com.okstatelibrary.spacesui.models.Bookings;
import com.okstatelibrary.spacesui.models.CancelConfirmation;
import com.okstatelibrary.spacesui.models.Category;
import com.okstatelibrary.spacesui.models.Room;
import com.okstatelibrary.spacesui.models.RoomBookingPayload;
import com.okstatelibrary.spacesui.models.SAMLUser;
import com.okstatelibrary.spacesui.models.SAMLUserList;
import com.okstatelibrary.spacesui.models.SpaceItem;
import com.okstatelibrary.spacesui.services.AccessTokenService;
import com.okstatelibrary.spacesui.services.SpacesService;
import com.okstatelibrary.spacesui.stereotypes.CurrentUser;
import com.okstatelibrary.spacesui.util.DateTimeUtil;
import com.okstatelibrary.spacesui.util.Messages;
import com.okstatelibrary.spacesui.util.URLs;

@Controller
public class HomeController {

	@Autowired
	com.okstatelibrary.spacesui.util.SystemProperties myProperties;
	
	@Autowired
	AccessTokenService accessTokenService;

	@Autowired
	SpacesService spaceService;

	// Logger
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/rest/authenticate", method = RequestMethod.GET)
	public ResponseEntity<AccessToken> authenticate()
			throws RestClientException, JsonParseException, JsonMappingException, IOException, JSONException {

		AccessToken accessToken = accessTokenService.getAccessToken("https://okstate.libcal.com/1.1/oauth/token",
				myProperties.getSpringShareClientId(),
				myProperties.getSpringShareSecretkey());

		System.out.println("AccessToken " + accessToken.getAccessToken());

		return new ResponseEntity<AccessToken>(accessToken, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, @ModelAttribute("date") String date, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException,
			ParseException {

		System.out.println("Index called.");

		if (date.isEmpty() || date == null) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();

			date = dtf.format(now);
		}

		SpaceItem[] spaceItems = madeAvaliableTimeSlots(date);

		model.addAttribute("spaceList", spaceItems);

		model.addAttribute("dateString", date);

		model.addAttribute("spaceListAvalability", "none");

		HttpSession session = request.getSession();

		System.out.println("Session ID: " + session.getId());
		System.out.println("Creation Time: " + new Date(session.getCreationTime()));
		System.out.println("Last Accessed Time: " + new Date(session.getLastAccessedTime()));

		return "pages/index";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel(@ModelAttribute("bookingId") String bookingId, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		System.out.println(bookingId);

		if (bookingId.isEmpty() || bookingId == null) {

			model.addAttribute("summaryModel", null);
			model.addAttribute("errorMessage", Messages.ERROR_BOOKING_SOMETING_WENT_WRONG);

		} else {

			CancelConfirmation[] cancelConfirmation = spaceService.cancel(getAccessTokenFromRequest(),
					URLs.POST_ROOM_CANCEL_URL + bookingId);

			if (cancelConfirmation == null || cancelConfirmation.length == 0) {

				model.addAttribute("summaryModel", null);
				model.addAttribute("errorMessage", Messages.ERROR_BOOKING_CANCEL_SOMETING_WENT_WRONG);
			} else {

				model.addAttribute("summaryModel", null);
				model.addAttribute("errorMessage", Messages.SUCESS_BOOKING_CANCEL);
			}
		}

		return "pages/summary";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/booking", method = RequestMethod.POST)
	public String booking(HttpServletRequest request, @ModelAttribute("roomNumber") String roomNumber,
			@ModelAttribute("roomName") String roomName, @ModelAttribute("dateString") String dateString,
			@ModelAttribute("hid_startTime") String startTime, @ModelAttribute("hid_endTime") String endTime,
			Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		HttpSession session = request.getSession();

		session.setAttribute("roomNumber", roomNumber);
		session.setAttribute("roomName", roomName);
		session.setAttribute("dateString", dateString);
		session.setAttribute("endTime", endTime);
		session.setAttribute("startTime", startTime);

		return "redirect:/saml/login?disco=true";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	public String reserve(HttpServletRequest request, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		SAMLUser samlUser = SAMLUserList.getInstance().getSAMLUser();

		HttpSession session = request.getSession();

		String roomNumber = session.getAttribute("roomNumber").toString();
		String dateString = session.getAttribute("dateString").toString();
		String endTime = session.getAttribute("endTime").toString();
		String startTime = session.getAttribute("startTime").toString();

		Bookings bookings = new Bookings(roomNumber, DateTimeUtil.convertToISODateTime(dateString + " " + endTime));

		RoomBookingPayload roomBookingPayLoad = new RoomBookingPayload(
				DateTimeUtil.convertToISODateTime(dateString + " " + startTime), samlUser.getFirstName(),
				samlUser.getLastName(), samlUser.getEmail(), "11799771", new Bookings[] { bookings });

		BookingConfirmation bookingConfirmation = spaceService.bookARoom(getAccessTokenFromRequest(),
				roomBookingPayLoad, URLs.BOOK_A_ROOM_URL);

		return "redirect:/summary/" + bookingConfirmation.getBooking_id() + "/true";
	}

	@RequestMapping(value = { "/summary/{id}", "/summary/{id}/{isBooked}" })
	public String summary(@PathVariable("id") String id, @PathVariable(required = false) boolean isBooked, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		if (id.isEmpty() || id == null) {
			model.addAttribute("summaryModel", null);
			model.addAttribute("errorMessage", Messages.ERROR_BOOKING_SOMETING_WENT_WRONG);

		} else {

			BookedItem[] bookingItems = spaceService.getBookedItems(getAccessTokenFromRequest(),
					URLs.GET_BOOKING_DETAILS_URL + id);

			if (bookingItems == null || bookingItems.length == 0) {

				model.addAttribute("summaryModel", null);
				model.addAttribute("errorMessage", Messages.ERROR_BOOKING_SUMMARY_MISSING_BOOKING_ID);

			} else {

				BookedItem bookedItem = bookingItems[0];

				bookedItem.setRoom(getRoomDetails(bookedItem.getEid()));

				model.addAttribute("summaryModel", bookedItem);
				model.addAttribute("isBooked", isBooked);

			}
		}

		return "pages/summary";
	}

	@RequestMapping("/landing")
	public String landing(HttpServletRequest request, @CurrentUser User user, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			LOG.debug("Current authentication instance from security context is null");
		else
			LOG.debug("Current authentication instance from security context: " + this.getClass().getSimpleName());

		model.addAttribute("userName", user.getUsername());

		SAMLUser samlUser = SAMLUserList.getInstance().getSAMLUser();

		model.addAttribute("firstName", samlUser.getFirstName());
		model.addAttribute("lastName", samlUser.getLastName());
		model.addAttribute("email", samlUser.getEmail());
		model.addAttribute("cwid", samlUser.getCwid());
		
		HttpSession session = request.getSession();

		String dateString = session.getAttribute("dateString").toString();
		String endTime = session.getAttribute("endTime").toString();
		String startTime = session.getAttribute("startTime").toString();
		String roomName = session.getAttribute("roomName").toString();

		model.addAttribute("dateString", dateString);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("roomName", roomName);

		return "pages/landing";
	}

	private SpaceItem[] madeAvaliableTimeSlots(String date) throws JsonParseException, JsonMappingException,
			RestClientException, IOException, JSONException, ParseException {

		Category[] categoryItems = spaceService.getRoomsByCategory(getAccessTokenFromRequest(),
				URLs.GET_ROOMS_BY_CATEGORY);

		List<SpaceItem> list = new ArrayList<>();

		if (categoryItems.length > 0) {

			SpaceItem[] spaceItems = spaceService.getItems(getAccessTokenFromRequest(),
					URLs.getSpacesURL(categoryItems[0].getItems(), date));

			for (SpaceItem spaceItem : spaceItems) {

				if (spaceItem.getAvailability().length > 0) {

					for (Availability availability : spaceItem.getAvailability()) {

					}

					list.add(spaceItem);

				}

			}
		}

		SpaceItem[] spaceItems = null;

		if (list.size() > 0) {
			spaceItems = list.toArray(new SpaceItem[0]);
		}

		return spaceItems;
	}

	private String getRoomDetails(String roomId)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {
		Room[] rooms = spaceService.getRoom(getAccessTokenFromRequest(), URLs.GET_ROOM_DETAILS_URL + roomId);

		return rooms[0].getName();
	}

	private String getAccessTokenFromRequest()
			throws JsonParseException, RestClientException, JsonMappingException, IOException, JSONException {

		AccessToken accessToken = getAuthenticate();

		return accessToken.getAccessToken();

	}

	private AccessToken getAuthenticate()
			throws RestClientException, JsonParseException, JsonMappingException, IOException, JSONException {

		AccessToken accessToken = accessTokenService.getAccessToken(URLs.GET_AUTH_TOKEN_URL, 
				myProperties.getSpringShareClientId(),
				myProperties.getSpringShareSecretkey());

		return accessToken;
	}

	@Autowired
	private MetadataManager metadata;

	@RequestMapping(value = "/discovery", method = RequestMethod.GET)
	public String idpSelection(HttpServletRequest request, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth == null)
			LOG.debug("Current authentication instance from security context is null");
		else
			LOG.debug("Current authentication instance from security context: " + this.getClass().getSimpleName());

		if (auth == null || (auth instanceof AnonymousAuthenticationToken)) {

			Set<String> idps = metadata.getIDPEntityNames();

			for (String idp : idps)
				LOG.info("Configured Identity Provider for SSO: " + idp);

			model.addAttribute("idps", idps);

			return "redirect:/saml/login?disco=true";
		} else {
			LOG.warn("The current user is already logged.");
			return "redirect:/landing";
		}
	}
}

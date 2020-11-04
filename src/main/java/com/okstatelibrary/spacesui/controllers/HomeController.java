
package com.okstatelibrary.spacesui.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

	// Access Token service 
	@Autowired
	AccessTokenService accessTokenService;

	// Spaces Service 
	@Autowired
	SpacesService spaceService;

	// Logger
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	// All the time slots
	private static final List<Availability> fixedTimeSlots = new ArrayList<Availability>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(new Availability("00:00:00-05:00", "00:30:00-05:00"));
			add(new Availability("00:30:00-05:00", "01:00:00-05:00"));
			add(new Availability("01:00:00-05:00", "01:30:00-05:00"));
			add(new Availability("01:30:00-05:00", "02:00:00-05:00"));
			add(new Availability("02:00:00-05:00", "02:30:00-05:00"));
			add(new Availability("02:30:00-05:00", "03:00:00-05:00"));
			add(new Availability("03:00:00-05:00", "03:30:00-05:00"));
			add(new Availability("03:30:00-05:00", "04:00:00-05:00"));
			add(new Availability("04:00:00-05:00", "04:30:00-05:00"));
			add(new Availability("04:30:00-05:00", "05:00:00-05:00"));
			add(new Availability("05:00:00-05:00", "05:30:00-05:00"));
			add(new Availability("05:30:00-05:00", "06:00:00-05:00"));
			add(new Availability("06:00:00-05:00", "06:30:00-05:00"));
			add(new Availability("06:30:00-05:00", "07:00:00-05:00"));
			add(new Availability("07:00:00-05:00", "07:30:00-05:00"));
			add(new Availability("07:30:00-05:00", "08:00:00-05:00"));
			add(new Availability("08:00:00-05:00", "08:30:00-05:00"));
			add(new Availability("08:30:00-05:00", "09:00:00-05:00"));
			add(new Availability("09:00:00-05:00", "09:30:00-05:00"));
			add(new Availability("09:30:00-05:00", "10:00:00-05:00"));
			add(new Availability("10:00:00-05:00", "10:30:00-05:00"));
			add(new Availability("10:30:00-05:00", "11:00:00-05:00"));
			add(new Availability("11:00:00-05:00", "11:30:00-05:00"));
			add(new Availability("11:30:00-05:00", "12:00:00-05:00"));
			add(new Availability("12:00:00-05:00", "12:30:00-05:00"));
			add(new Availability("12:30:00-05:00", "13:00:00-05:00"));
			add(new Availability("13:00:00-05:00", "13:30:00-05:00"));
			add(new Availability("13:30:00-05:00", "14:00:00-05:00"));
			add(new Availability("14:00:00-05:00", "14:30:00-05:00"));
			add(new Availability("14:30:00-05:00", "15:00:00-05:00"));
			add(new Availability("15:00:00-05:00", "15:30:00-05:00"));
			add(new Availability("15:30:00-05:00", "16:00:00-05:00"));
			add(new Availability("16:00:00-05:00", "16:30:00-05:00"));
			add(new Availability("16:30:00-05:00", "17:00:00-05:00"));
			add(new Availability("17:00:00-05:00", "17:30:00-05:00"));
			add(new Availability("17:30:00-05:00", "18:00:00-05:00"));
			add(new Availability("18:00:00-05:00", "18:30:00-05:00"));
			add(new Availability("18:30:00-05:00", "19:00:00-05:00"));
			add(new Availability("19:00:00-05:00", "19:30:00-05:00"));
			add(new Availability("19:30:00-05:00", "20:00:00-05:00"));
			add(new Availability("20:00:00-05:00", "20:30:00-05:00"));
			add(new Availability("20:30:00-05:00", "21:00:00-05:00"));
			add(new Availability("21:00:00-05:00", "21:30:00-05:00"));
			add(new Availability("21:30:00-05:00", "22:00:00-05:00"));
			add(new Availability("22:00:00-05:00", "22:30:00-05:00"));
			add(new Availability("22:30:00-05:00", "23:00:00-05:00"));
			add(new Availability("23:00:00-05:00", "23:30:00-05:00"));
			add(new Availability("23:30:00-05:00", "23:59:59-05:00"));
		}
	};

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/rest/authenticate", method = RequestMethod.GET)
	public ResponseEntity<AccessToken> authenticate()
			throws RestClientException, JsonParseException, JsonMappingException, IOException, JSONException {

		AccessToken accessToken = accessTokenService.getAccessToken("https://okstate.libcal.com/1.1/oauth/token",
				myProperties.getSpringShareClientId(), myProperties.getSpringShareSecretkey());

		return new ResponseEntity<AccessToken>(accessToken, HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, @ModelAttribute("date") String date, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException,
			ParseException {

		if (date.isEmpty() || date == null) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();

			date = dtf.format(now);
		}

		SpaceItem[] spaceItems = madeAvaliableTimeSlots(date);

		model.addAttribute("spaceList", spaceItems);

		model.addAttribute("dateString", date);

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(300);

		return "pages/index";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel(@ModelAttribute("bookingId") String bookingId, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

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
	public String booking()
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		return "redirect:/saml/login?disco=true";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	public String reserve(HttpServletRequest request, @ModelAttribute("bookRoomNumber") String roomNumber,
			@ModelAttribute("bookDate") String bookDate, @ModelAttribute("bookStartTime") String startTime,
			@ModelAttribute("bookEndTime") String endTime, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {
		
		HttpSession session = request.getSession();

		SAMLUser samlUser = SAMLUserList.getInstance().getSAMLUser(session.getId());

		if (!roomNumber.isEmpty() && !bookDate.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {

			Bookings bookings = new Bookings(roomNumber, DateTimeUtil.convertToISODateTime(bookDate, endTime));

			RoomBookingPayload roomBookingPayLoad = new RoomBookingPayload(
					DateTimeUtil.convertToISODateTime(bookDate, startTime), samlUser.getFirstName(),
					samlUser.getLastName(), samlUser.getEmail(), samlUser.getCwid(), new Bookings[] { bookings });

			BookingConfirmation bookingConfirmation = spaceService.bookARoom(getAccessTokenFromRequest(),
					roomBookingPayLoad, URLs.BOOK_A_ROOM_URL);

			if (bookingConfirmation != null && bookingConfirmation.getBooking_id() != null
					&& !bookingConfirmation.getBooking_id().isEmpty()) {
				return "redirect:/summary/" + bookingConfirmation.getBooking_id() + "/true";

			} else {

				if (bookingConfirmation.getErrorDetails() != null
						&& !bookingConfirmation.getErrorDetails().getErrorId().isEmpty()) {
					return "redirect:/errorpage/" + bookingConfirmation.getErrorDetails().getErrorId();

				} else {
					return "redirect:/errorpage";
				}

			}
		}

		return "redirect:/errorpage/";
	}

	@RequestMapping(value = { "/errorpage", "/errorpage/{id}" })
	public String error(@PathVariable(required = false) String id, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		String errorMessage = Messages.ERROR_BOOKING_SOMETING_WENT_WRONG;

		if (id != null && !id.isEmpty()) {

			if (id.equals("303")) {
				errorMessage = Messages.ERROR_BOOKING_EXCEED_DAYIL_LIMIT;
			} else if (id.equals("302")) {
				errorMessage = Messages.ERROR_BOOKING_TRY_ALREADY_BOOKED_TIMESLOT;
			}
		}

		model.addAttribute("errorMessage", errorMessage);

		return "pages/error";
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

		HttpSession session = request.getSession();

		LOG.info("Session ID: " + session.getId());

		SAMLUser samlUser = SAMLUserList.getInstance().getSAMLUser(session.getId());

		if (samlUser.getFirstName().isEmpty()) {
			LOG.debug("SAML USER  NNull");
		}

		model.addAttribute("firstName", samlUser.getFirstName());
		model.addAttribute("lastName", samlUser.getLastName());
		model.addAttribute("email", samlUser.getEmail());
		model.addAttribute("cwid", samlUser.getCwid());

		return "pages/booking";
	}

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
			return "redirect:/booking";
		}
	}

	private SpaceItem[] madeAvaliableTimeSlots(String date) throws JsonParseException, JsonMappingException,
			RestClientException, IOException, JSONException, ParseException {

		Category[] categoryItems = spaceService.getRoomsByCategory(getAccessTokenFromRequest(),
				URLs.GET_ROOMS_BY_CATEGORY);

		List<SpaceItem> list = new ArrayList<>();

		if (categoryItems.length > 0) {

//			SpaceItem[] spaceItems = spaceService.getItems(getAccessTokenFromRequest(),
//					URLs.getSpacesURL("26354,26355", date));
			
			SpaceItem[] spaceItems = spaceService.getItems(getAccessTokenFromRequest(),
					URLs.getSpacesURL(categoryItems[0].getItems(), date));

			for (SpaceItem spaceItem : spaceItems) {
				
				if (spaceItem.getAvailability().length > 0) {

					List<Availability> availabilityList = new ArrayList<>();

					for (Availability availability : spaceItem.getAvailability()) {

						// From the API sometimes sends time slots related to previous date.
						// Cleaning those type of dates.

						if (DateTimeUtil.convertToDate(availability.getFrom()).equals(date)) {
							availabilityList.add(availability);
						}

					}

					int fromIndex = getFixedTimeSlotIndex(availabilityList.get(0).getFromTime());

					// To get the very last time slot, need to add + 1
					int toIndex = getFixedTimeSlotIndex(
							availabilityList.get(availabilityList.size() - 1).getFromTime()) + 1; 

					List<Availability> newAvailabilityList = new ArrayList<>();

					for (Availability fixedTimeSlot : fixedTimeSlots.subList(fromIndex, toIndex)) {

						Availability avalibility = availabilityList.stream()
								.filter(timeSlot -> timeSlot.getFromTime().equals(fixedTimeSlot.getFrom12HourTime()))
								.findFirst().orElse(null);

						if (avalibility == null) {

							Availability ava = new Availability(date + "T" + fixedTimeSlot.getFrom(),
									date + "T" + fixedTimeSlot.getTo());
							ava.setBooked(true);

							newAvailabilityList.add(ava);

						} else {
							newAvailabilityList.add(avalibility);
						}

					}

					spaceItem.emptyAvailability();

					spaceItem.setAvailability(newAvailabilityList.toArray(new Availability[0]));

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

	private int getFixedTimeSlotIndex(String fromTime) {

		for (int i = 0; i < fixedTimeSlots.size(); i++) {
			if (fixedTimeSlots.get(i).getFrom12HourTime().equals(fromTime)) {
				return i;
			}
		}

		return -1;
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

				myProperties.getSpringShareClientId(), myProperties.getSpringShareSecretkey());

		return accessToken;
	}

	@Autowired
	private MetadataManager metadata;

}

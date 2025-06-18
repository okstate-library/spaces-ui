
package com.okstatelibrary.spacesui.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.okstatelibrary.spacesui.models.*;
import com.okstatelibrary.spacesui.services.AccessTokenService;
import com.okstatelibrary.spacesui.services.FolioService;
import com.okstatelibrary.spacesui.services.SpacesService;
import com.okstatelibrary.spacesui.stereotypes.CurrentUser;
import com.okstatelibrary.spacesui.util.DateTimeUtil;
import com.okstatelibrary.spacesui.util.Messages;
import com.okstatelibrary.spacesui.util.RibbonMessage;
import com.okstatelibrary.spacesui.util.URLs;

/**
 * 
 * Home Controller class
 * 
 * @author Damith
 *
 */
@Controller
public class HomeController {

	/**
	 * Custom defines system properties.
	 */
	@Autowired
	com.okstatelibrary.spacesui.util.SystemProperties systemProperties;

	/**
	 * Access Token service
	 */
	@Autowired
	AccessTokenService accessTokenService;

	/**
	 * Space services
	 */
	@Autowired
	SpacesService spaceService;

	/**
	 * Folio services
	 */
	@Autowired
	FolioService folioService;

	/**
	 * Metadata Manager class
	 */
	@Autowired
	private MetadataManager metadata;

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Defines the seat count drop down list
	 */
	private static final Map<String, String> seatList = new HashMap<String, String>() {
		/**
		 * Put defines data.
		 */
		private static final long serialVersionUID = 1L;

		{
			put("1", "1+");
			put("4", "3+");
		}
	};

	/**
	 * Defines the floor drop down list
	 */
	private static final Map<String, String> floorList = new HashMap<String, String>() {
		/**
		 * put predefine floor list
		 */
		private static final long serialVersionUID = 1L;

		{
			put("0", "Any");
			put("1", "First");
			put("2", "Second");
		}
	};

	/**
	 * All the time slots
	 */
	private static final List<Availability> fixedTimeSlots = new ArrayList<Availability>() {
		/**
		 * Add predefine time slots
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

	/**
	 * Initial method of page loading.
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@RequestMapping(value = { "/", "/{id}" }, method = RequestMethod.GET)
	public String index(@PathVariable(name = "id", required = false) String roomName, HttpServletRequest request,
			Model model) throws JsonParseException, JsonMappingException, RestClientException, IOException,
			JSONException, ParseException {

		if (!StringUtils.isBlank(RibbonMessage.message)) {
			model.addAttribute("ribbonmessagevisibility", "show");
			model.addAttribute("ribbonmessage", RibbonMessage.message);
			model.addAttribute("messageclass", "ribbon-" + RibbonMessage.messageType);
		}

		String accessToken = getAccessTokenFromRequest();

		if (accessToken == null || accessToken.isEmpty()) {
			System.out.println("API not working");

			// return "errorp/101";
			return "redirect:/errorp/101";

		} else {
			String selectedSeats = "0";
			String selectedFloor = "0";

			SpaceItem[] spaceItems = madeAvaliableTimeSlots(DateTimeUtil.getTodayDate(), selectedSeats, selectedFloor);

			model.addAttribute("spaceList", spaceItems);

			model.addAttribute("dateString", DateTimeUtil.getTodayDate());

			int roomCount = 0;
			String roomId = null;

			if (spaceItems != null) {

				if (roomName != null && !roomName.isEmpty()) {

					SpaceItem spaceItem = Arrays.stream(spaceItems)
							.filter(customer -> roomName.equals(customer.getName())).findAny().orElse(null);

					if (spaceItem != null) {
						roomCount = 1;
						roomId = spaceItem.getId();
					} else {
						roomCount = spaceItems.length;
					}

				} else {
					roomCount = spaceItems.length;
				}

			}

			model.addAttribute("totalRooms", roomCount + " Rooms found...");
			model.addAttribute("selectedRoomId", roomId);

			model.addAttribute("seats", seatList);
			model.addAttribute("selectedSeat", selectedSeats);

			model.addAttribute("floors", floorList);
			// model.addAttribute("selectedFloor", selectedFloor);

			// model.addAttribute("location_status", getLocationHours(accessToken,
			// DateTimeUtil.getTodayDate()));

			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(300);

			return "pages/index";
		}

	}

	/**
	 * Index method. List all the available room with time slots in the index page.
	 * 
	 * @param request
	 * @param date
	 * @param seats
	 * @param floor
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String index(HttpServletRequest request, @ModelAttribute("date") String date,
			@ModelAttribute("seats") String seats, @ModelAttribute("floor") String floor, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException,
			ParseException {

		try {
			if (date.isEmpty() || date == null) {

				date = DateTimeUtil.getTodayDate();
			}

			if (seats.isEmpty() || seats == null) {
				seats = "0";
			}

			if (floor.isEmpty() || floor == null) {
				floor = "0";
			}

			SpaceItem[] spaceItems = madeAvaliableTimeSlots(date, seats, floor);

			model.addAttribute("spaceList", spaceItems);

			model.addAttribute("dateString", date);

			model.addAttribute("totalRooms", (spaceItems != null ? spaceItems.length : 0) + " Rooms found...");

			model.addAttribute("seats", seatList);
			model.addAttribute("selectedSeat", seats);

			model.addAttribute("floors", floorList);
			// model.addAttribute("selectedFloor", floor);

			// model.addAttribute("location_status",
			// getLocationHours(getAccessTokenFromRequest(), date));

			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(300);

			return "pages/index";

		} catch (Exception e) {
			System.out.print(e.getStackTrace());
		}

		return "redirect:/errorp";
	}

	/**
	 * 
	 * IDP Selection proceed. If user not logged to relevant IDP it will redirectsto
	 * the SSO.
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 */
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

	/**
	 * 
	 * After IDP redirects, this will redirects to the relevant page to populate
	 * user and booking details
	 * 
	 * @param request
	 * @param user
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws RestClientException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping("/landing")
	public String landing(HttpServletRequest request, @CurrentUser User user, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null)
			LOG.debug("Current authentication instance from security context is null");
		else
			LOG.debug("Current authentication instance from security context: " + this.getClass().getSimpleName());

		model.addAttribute("userName", user.getUsername());

		HttpSession session = request.getSession();

		LOG.info("Session ID: " + session.getId());

		SAMLUser samlUser = SAMLUserList.getInstance().getSAMLUser(session.getId());

		if (samlUser == null || samlUser.getFirstName() == null || samlUser.getFirstName().isEmpty()) {
			return "redirect:/"; // Redirects to home page.
		}

		if (folioService.isUserExists(samlUser.getCwid())) {

			model.addAttribute("firstName", samlUser.getFirstName());
			model.addAttribute("lastName", samlUser.getLastName());
			model.addAttribute("email", samlUser.getEmail());
			model.addAttribute("cwid", samlUser.getCwid());
			// model.addAttribute(modelCategoryAttributeName,
			// session.getAttribute(sessionCategoryAttributeName));

			return "pages/booking";
		} else {
			return "redirect:/errorp/306";
		}
	}

	/**
	 * 
	 * Cancellation of the booking.
	 * 
	 * @param bookingId
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 */
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

	/**
	 * Booking method. If someone not logged this will redirect to their login page.
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/booking", method = RequestMethod.POST)
	public String booking()
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		return "redirect:/saml/login?disco=true";
	}

	/**
	 * Reserve the room.
	 * 
	 * @param request
	 * @param roomNumber
	 * @param bookDate
	 * @param startTime
	 * @param endTime
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	public String reserve(HttpServletRequest request, @ModelAttribute("bookRoomNumber") String roomNumber,
			@ModelAttribute("bookDate") String bookDate, @ModelAttribute("bookStartTime") String startTime,
			@ModelAttribute("bookEndTime") String endTime, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		HttpSession session = request.getSession();

		SAMLUser samlUser = SAMLUserList.getInstance().getSAMLUser(session.getId());

		System.out.println("roomNumber - " + roomNumber);
		System.out.println("bookDate - " + bookDate);
		System.out.println("startTime - " + startTime);
		System.out.println("endTime ID - " + endTime);

		if (!roomNumber.isEmpty() && !bookDate.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {

			Bookings bookings = new Bookings(roomNumber, DateTimeUtil.convertToISODateTime(bookDate, endTime));

			RoomBookingPayload roomBookingPayLoad = new RoomBookingPayload(
					DateTimeUtil.convertToISODateTime(bookDate, startTime), samlUser.getFirstName(),
					samlUser.getLastName(), samlUser.getEmail(), samlUser.getCwid(), new Bookings[] { bookings });

			BookingConfirmation bookingConfirmation = spaceService.bookARoom(getAccessTokenFromRequest(),
					roomBookingPayLoad, URLs.BOOK_A_ROOM_URL);

			SAMLUserList.getInstance().removeFromArray(samlUser);

			System.out.println("bookingConfirmation - " + bookingConfirmation.getErrorDetails());

			if (bookingConfirmation != null && bookingConfirmation.getBooking_id() != null
					&& !bookingConfirmation.getBooking_id().isEmpty()) {

				return "redirect:/summary/" + bookingConfirmation.getBooking_id() + "/true";

			} else {

				if (bookingConfirmation.getErrorDetails() != null
						&& !bookingConfirmation.getErrorDetails().getErrorId().isEmpty()) {
					return "redirect:/errorp/" + bookingConfirmation.getErrorDetails().getErrorId();

				} else {
					return "redirect:/errorp";
				}

			}
		}

		return "redirect:/error";
	}

	/**
	 * Redirects to the relevant error page with message.
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 */
	@RequestMapping(value = { "/errorp", "/errorp/{id}" })
	public String error(@PathVariable(required = false) String id, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		String errorMessage = Messages.ERROR_BOOKING_SOMETING_WENT_WRONG;
		System.out.println("Error ID - " + id);

		if (id != null && !id.isEmpty()) {

			System.out.println("Error ID - " + id);

			if (id.equals("303")) {
				errorMessage = Messages.ERROR_BOOKING_EXCEED_DAYIL_LIMIT;
			} else if (id.equals("302")) {
				errorMessage = Messages.ERROR_BOOKING_TRY_ALREADY_BOOKED_TIMESLOT;
			} else if (id.equals("101")) {
				errorMessage = Messages.ERROR_EXTERNAL_API_NOT_WORKING;
			} else if (id.equals("304")) {
				errorMessage = Messages.ERROR_BOOKING_EXCEED_DAYIL_ROOM_LIMIT;
			} else if (id.equals("305")) {
				errorMessage = Messages.ERROR_BOOKING_RESERVATION_WITHIN_TWO_HOURS;
			} else if (id.equals("306")) {
				errorMessage = Messages.ERROR_USER_UNAUTHORIZE;
			}

			model.addAttribute("errorMessageId", id);
		}

		model.addAttribute("errorMessage", errorMessage);

		return "error";
	}

	/**
	 * Redirects to the relevant error page with message.
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 */
	@RequestMapping(value = { "/spaces" })
	public String spaces(Model model) {

		System.out.println("spaces");

		return "pages/spaces";
	}

	/**
	 * Displays the summary of the booking.
	 * 
	 * @param id
	 * @param isBooked
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 */
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

	/**
	 * 
	 * Returns the available rooms with available time slots. While populating the
	 * time it converts 24 hr time to 12 hr time. Also based on the time slot it
	 * returns already booked time slots. aSo user can get better idea of what time
	 * slots can book.
	 * 
	 * @param date
	 * @param seats
	 * @param floor
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	private SpaceItem[] madeAvaliableTimeSlots(String date, String seats, String floor) throws JsonParseException,
			JsonMappingException, RestClientException, IOException, JSONException, ParseException {

		int seatsCount = Integer.parseInt(seats);

		Category[] categoryItems = spaceService.getRoomsByCategory(getAccessTokenFromRequest(),
				URLs.getRoomsByCategoryURL(systemProperties.getCategoryId()));

		List<SpaceItem> list = new ArrayList<>();

		if (categoryItems.length > 0) {

			SpaceItem[] spaceItems = spaceService.getItems(getAccessTokenFromRequest(),
					URLs.getSpacesURL(categoryItems[0].getItems(), date));

			for (SpaceItem spaceItem : spaceItems) {

				// Print room id with name.
				// System.out.println(spaceItem.getId() + " " + spaceItem.getName());

				if (spaceItem.getAvailability().length > 0 && Integer.parseInt(spaceItem.getCapacity()) >= seatsCount
						&& (floor.equals("0") ? true : floor.equals(spaceItem.getFloor()))) {

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
					int toIndex = getFixedTimeSlotIndex(availabilityList.get(availabilityList.size() - 1).getFromTime())
							+ 1;

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

			// Sorting rooms by name
			Arrays.sort(spaceItems, new Comparator<SpaceItem>() {
				@Override
				public int compare(SpaceItem o1, SpaceItem o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
		}

		return spaceItems;

	}

	/**
	 * Returns the relevant time slot index.
	 * 
	 * @param fromTime
	 * @return
	 */
	private int getFixedTimeSlotIndex(String fromTime) {

		for (int i = 0; i < fixedTimeSlots.size(); i++) {
			if (fixedTimeSlots.get(i).getFrom12HourTime().equals(fromTime)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the room name.
	 * 
	 * @param roomId
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JSONException
	 */
	private String getRoomDetails(String roomId)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {
		Room[] rooms = spaceService.getRoom(getAccessTokenFromRequest(), URLs.GET_ROOM_DETAILS_URL + roomId);

		return rooms[0].getName();
	}

//	private String getLocationHours(String accessToken, String date)
//			throws JSONException, JsonParseException, JsonMappingException, RestClientException, IOException {
//
//		String locationHours = "Closed";
//
//		try {
//
//			String url = URLs.GET_LOCATION_HOURS_URL + systemProperties.getLocationId() + "?&from=" + date + "&to="
//					+ date;
//
//			Location[] hoursJson = spaceService.getHours(accessToken, url);
//
//			if (hoursJson.length > 0) {
//				org.json.JSONObject object = new org.json.JSONObject(hoursJson[0].getDates().toString());
//
//				Iterator<?> keys = object.keys();
//
//				while (keys.hasNext()) {
//					// loop to get the dynamic key
//					String currentDynamicKey = (String) keys.next();
//
//					// get the value of the dynamic key
//					org.json.JSONObject currentDynamicValue = object.getJSONObject(currentDynamicKey);
//
//					String status = currentDynamicValue.getString("status");
//
//					if (status.equalsIgnoreCase("open")) {
//						org.json.JSONArray hours = currentDynamicValue.getJSONArray("hours");
//
//						String hour_string = hours.getJSONObject(0).getString("from") + " - "
//								+ hours.getJSONObject(0).getString("to");
//
//						locationHours = hour_string;
//					} else if (status.equalsIgnoreCase("24hours")) {
//
//						locationHours = "24 hours";
//					} else if (status.equalsIgnoreCase("text")) {
//						locationHours = currentDynamicValue.getString("text");
//
//					}
//				}
//			}
//		} catch (Exception e) {
//			// do something clever with the exception
//			System.out.println(e.getMessage());
//			System.out.println("No Hour details avaliable");
//
//			locationHours = null;
//		}
//
//		return locationHours;
//	}

	/**
	 * Returns the Access token.
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws RestClientException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws JSONException
	 */
	private String getAccessTokenFromRequest()
			throws JsonParseException, RestClientException, JsonMappingException, IOException, JSONException {

		AccessToken accessToken = accessTokenService.getAccessToken(URLs.GET_AUTH_TOKEN_URL,
				systemProperties.getSpringShareClientId(), systemProperties.getSpringShareSecretkey());

		if (accessToken != null) {
			return accessToken.getAccessToken();
		} else {
			return null;
		}
	}

	@RequestMapping(value = { "/session-count" })
	public String getSessions(HttpServletRequest request, Model model) {
		ArrayList<SAMLUser> sessions = SAMLUserList.getInstance().getArray();

		model.addAttribute("sysSessions", sessions);

		return "pages/sessions";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/clean", method = RequestMethod.POST)
	public String clean(HttpServletRequest request, Model model) {
		
		SAMLUserList.getInstance().clean();

		return "redirect:/session-count";
	}

}


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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.okstatelibrary.spacesui.config.SessionListener;
import com.okstatelibrary.spacesui.globals.GlobalConfigs;
import com.okstatelibrary.spacesui.models.*;
import com.okstatelibrary.spacesui.services.AccessTokenService;
import com.okstatelibrary.spacesui.services.FolioService;
import com.okstatelibrary.spacesui.services.SpacesService;
import com.okstatelibrary.spacesui.stereotypes.CurrentUser;
import com.okstatelibrary.spacesui.util.DateTimeUtil;
import com.okstatelibrary.spacesui.util.Globals;
import com.okstatelibrary.spacesui.util.Messages;
import com.okstatelibrary.spacesui.util.RibbonMessage;
import com.okstatelibrary.spacesui.util.URLs;

/**
 * 
 * Home Controller class
 * 
 * @author Damith Perera
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

	private final GlobalConfigs globalConfigs;

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

	public HomeController(GlobalConfigs globalConfigs) {
		this.globalConfigs = globalConfigs;
	}

	private static final String sessionCategoryAttributeName = "categoryUrlId";

	private static final String modelCategoryAttributeName = "categoryAttribute";

	static Globals globalsInstance = null;

	private static final String roomsFoundLabelString = " Room(s) found...";

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

	private void globalSetup()
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		if (globalsInstance == null) {

			globalsInstance = new Globals();

			if (!globalsInstance.getIsProccessed()) {

				Map<String, String> studyRooms = new HashMap<>();

				List<Room> roomList = new ArrayList<>();

//				for (Map.Entry<String, String> entry : this.globalConfigs.getCategoryNumber() .getCategoryList().entrySet()) {
//
//					System.out.println("entry.getKey() " + entry.getKey() + " ---  ");
//
//					Category[] categoryItems = spaceService.getRoomsByCategory(getAccessTokenFromRequest(),
//							URLs.getRoomsByCategoryURL(entry.getKey()));
//
//					System.out.println("entry.getKey() " + entry.getKey() + "--- " + categoryItems[0].getItems());
//
//					studyRooms.put(entry.getKey(), categoryItems[0].getItems());
//
//					Room[] rooms = spaceService.getRoom(getAccessTokenFromRequest(),
//							URLs.GET_ROOM_DETAILS_URL + categoryItems[0].getItems());
//
//					for (Room room : rooms) {
//						roomList.add(room);
//					}
//
//				}

				// for (Map.Entry<String, String> entry : this.globalConfigs.getCategoryNumber()
				// .getCategoryList().entrySet()) {

				// System.out.println("entry.getKey() " + entry.getKey() + " --- ");

				Category[] categoryItems = spaceService.getRoomsByCategory(getAccessTokenFromRequest(),
						URLs.getRoomsByCategoryURL(this.globalConfigs.getCategoryNumber()));

				// System.out.println("entry.getKey() " + entry.getKey() + "--- " +
				// categoryItems[0].getItems());

				studyRooms.put(this.globalConfigs.getCategoryNumber(), categoryItems[0].getItems());

				Room[] rooms = spaceService.getRoom(getAccessTokenFromRequest(),
						URLs.GET_ROOM_DETAILS_URL + categoryItems[0].getItems());

				for (Room room : rooms) {
					roomList.add(room);
				}

				// }

				globalsInstance.setRoomDetails(roomList);

				globalsInstance.setStudyRooms(studyRooms);

				globalsInstance.setIsProccessed(true);

			}
		}
	}

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

		System.out.println("***************************************");
		System.out.println("Normal Loadning");

		if (!StringUtils.isBlank(RibbonMessage.message)) {
			model.addAttribute("ribbonmessagevisibility", "show");
			model.addAttribute("ribbonmessage", RibbonMessage.message);
			model.addAttribute("messageclass", "ribbon-" + RibbonMessage.messageType);
		}

		// "Please be aware that building construction noise may be disruptive.
		// Construction activity takes place Mon-Fri between 8 a.m. and 5 p.m.");

		globalSetup();

		String accessToken = getAccessTokenFromRequest();

		if (accessToken == null || accessToken.isEmpty()) {
			System.out.println("API not working");

			// return "errorp/101";
			return "redirect:/errorp/101";

		} else {

			String selectedSeats = "0";
			String selectedFloor = "0";

			Map<String, String> seatList = this.globalConfigs.getSeatList();

			model.addAttribute("hidefloorselection", this.globalConfigs.hideFloorSelection());

			SpaceItem[] spaceItems = madeAvaliableTimeSlots(DateTimeUtil.getTodayDate(), selectedSeats, selectedFloor,
					this.globalConfigs.getCategoryNumber());

			model.addAttribute("spaceList", spaceItems);

			model.addAttribute("dateString", DateTimeUtil.getTodayDate());

			int roomCount = 0;
			String roomId = null;

			System.out.println("Room Name" + roomName);

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

			model.addAttribute("totalRooms", roomCount + roomsFoundLabelString);
			model.addAttribute("selectedRoomId", roomId);

			model.addAttribute("seats", seatList);
			model.addAttribute("selectedSeat", selectedSeats);

			model.addAttribute("floors", this.globalConfigs.getFloorList());
			model.addAttribute("selectedFloor", selectedFloor);

			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(900);

			session.setAttribute(sessionCategoryAttributeName, this.globalConfigs.getCategoryNumber());

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
			@ModelAttribute("seats") String seats, @ModelAttribute("floor") String floor, Model model) throws JsonParseException, JsonMappingException,
			RestClientException, IOException, JSONException, ParseException {

		try {

			System.out.println("***************************************");
			System.out.println("Drop down filteration request Loadning");

			if (date.isEmpty() || date == null) {

				date = DateTimeUtil.getTodayDate();
			}

			if (seats.isEmpty() || seats == null) {
				seats = "0";
			}

			if (floor.isEmpty() || floor == null) {
				floor = "0";
			}
			
			String	category = this.globalConfigs.getCategoryNumber();
			
			Map<String, String> seatList = this.globalConfigs.getSeatList();

			model.addAttribute("hidefloorselection", this.globalConfigs.hideFloorSelection());

			model.addAttribute("dateString", date);

			model.addAttribute("seats", seatList);
			model.addAttribute("selectedSeat", seats);

			model.addAttribute("floors", this.globalConfigs.getFloorList());
			model.addAttribute("selectedFloor", floor);

			SpaceItem[] spaceItems = madeAvaliableTimeSlots(date, seats, floor, category);

			model.addAttribute("spaceList", spaceItems);

			model.addAttribute("totalRooms", (spaceItems != null ? spaceItems.length : 0) + roomsFoundLabelString);

			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(300);

			session.setAttribute(sessionCategoryAttributeName, category);

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

		// setupPolicyView(model);

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

		System.out.println("session.getAttribute(sessionCategoryAttributeName) - "
				+ session.getAttribute(sessionCategoryAttributeName));

		if (folioService.isUserExists(samlUser.getCwid())) {

			model.addAttribute("firstName", samlUser.getFirstName());
			model.addAttribute("lastName", samlUser.getLastName());
			model.addAttribute("email", samlUser.getEmail());
			model.addAttribute("cwid", samlUser.getCwid());
			model.addAttribute(modelCategoryAttributeName, session.getAttribute(sessionCategoryAttributeName));

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

		String category = "";

		if (bookingId.isEmpty() || bookingId == null) {

			model.addAttribute("summaryModel", null);
			model.addAttribute("errorMessage",
					Messages.ERROR_BOOKING_SOMETING_WENT_WRONG + this.globalConfigs.getHelpDeskName());

		} else {

			// Get the booking information for redirection after cancel process.
			BookedItem[] bookingItems = spaceService.getBookedItems(getAccessTokenFromRequest(),
					URLs.GET_BOOKING_DETAILS_URL + bookingId);

			// Get the category id

			category = bookingItems[0].getCid();

			CancelConfirmation[] cancelConfirmation = spaceService.cancel(getAccessTokenFromRequest(),
					URLs.POST_ROOM_CANCEL_URL + bookingId);

			if (cancelConfirmation == null || cancelConfirmation.length == 0) {

				model.addAttribute("summaryModel", null);
				model.addAttribute("errorMessage",
						Messages.ERROR_BOOKING_CANCEL_SOMETING_WENT_WRONG + this.globalConfigs.getHelpDeskName());

			} else {

				model.addAttribute("summaryModel", null);
				model.addAttribute("errorMessage", Messages.SUCESS_BOOKING_CANCEL);
			}

		}

		model.addAttribute(modelCategoryAttributeName, category);

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

		try {
			System.out.println("Booking call");

			return "redirect:/saml/login?disco=true";
		} catch (Exception e) {
			System.out.print(e.getStackTrace());

		}
		return "redirect:/errorp";
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

		System.out.println("reserve call");

		HttpSession session = request.getSession();

		SAMLUser samlUser = SAMLUserList.getInstance().getSAMLUser(session.getId());

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
	public String error(HttpServletRequest request, @PathVariable(required = false) String id, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		String errorMessage = Messages.ERROR_BOOKING_SOMETING_WENT_WRONG + this.globalConfigs.getHelpDeskName();

		model.addAttribute("showExtra", "true");

		System.out.println("Error ID - " + (id != null ? id : "no  id found"));

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

		HttpSession session = request.getSession();

		model.addAttribute(modelCategoryAttributeName, session.getAttribute(sessionCategoryAttributeName));
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
	public String summary(HttpServletRequest request, @PathVariable("id") String id,
			@PathVariable(required = false) boolean isBooked, Model model)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

		globalSetup();

		String category = "";

		if (id.isEmpty() || id == null) {
			model.addAttribute("summaryModel", null);
			model.addAttribute("errorMessage",
					Messages.ERROR_BOOKING_SOMETING_WENT_WRONG + this.globalConfigs.getHelpDeskName());

		} else {

			System.out.println("categorycategorycategorycategorycategorycategory - " + category);

			BookedItem[] bookingItems = spaceService.getBookedItems(getAccessTokenFromRequest(),
					URLs.GET_BOOKING_DETAILS_URL + id);

			if (bookingItems == null || bookingItems.length == 0) {

				model.addAttribute("summaryModel", null);
				model.addAttribute("errorMessage", Messages.ERROR_BOOKING_SUMMARY_MISSING_BOOKING_ID);

			} else {

				BookedItem bookedItem = bookingItems[0];

				bookedItem.setRoom(globalsInstance.getRoomName(bookedItem.getEid()));

				model.addAttribute("summaryModel", bookedItem);
				model.addAttribute("isBooked", isBooked);

				System.out.println("bookedItem.getCid() -- " + bookedItem.getCid());

				category = bookedItem.getCid();
			}
		}

		System.out.println("category in summary - " + category);

		model.addAttribute(modelCategoryAttributeName, category);

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
	private SpaceItem[] madeAvaliableTimeSlots(String date, String seats, String floor, String category)
			throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException,
			ParseException {

		System.out.println("category - " + category);
		System.out.println("Date - " + date);
		System.out.println("getFloor - " + floor);
		System.out.println("getSeat - " + seats);

		int seatsCount = Integer.parseInt(seats);

		List<SpaceItem> list = new ArrayList<>();

		SpaceItem[] spaceItems = spaceService.getItems(getAccessTokenFromRequest(),
				URLs.getSpacesURL(globalsInstance.getStudyRoomByCategoryId(category), date));

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

		spaceItems = null;

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
			System.out.println("accessToken.getAccessToken() : " + accessToken.getAccessToken());
			return accessToken.getAccessToken();
		} else {
			return null;
		}
	}

	@RequestMapping(value = { "/session-count" })
	public String getSessions(HttpServletRequest request, Model model) {
		ArrayList<SAMLUser> sessions = SAMLUserList.getInstance().getUserArray();

		model.addAttribute("sysSessions", sessions);

		return "pages/sessions";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/clean", method = RequestMethod.POST)
	public String clean(HttpServletRequest request, Model model) {

		SAMLUserList samlUserList = SAMLUserList.getInstance();

		if (samlUserList != null) {
			ArrayList<SAMLUser> sessions = samlUserList.getUserArray();

			if (sessions != null && sessions.size() > 0) {
				for (SAMLUser session : sessions) {
					samlUserList.removeFromArray(session);
				}
			}

		}

		return "redirect:/session-count";
	}

	@GetMapping("/session-remaining")
	public String sessionRemaining(HttpSession session, Model model) {

		System.out.println("session accessing");

		long now = System.currentTimeMillis();
		long lastAccessed = session.getLastAccessedTime();
		int timeout = session.getMaxInactiveInterval(); // in seconds

		long elapsedSeconds = (now - lastAccessed) / 1000;
		long remainingSeconds = timeout - elapsedSeconds;

		model.addAttribute("remainingSeconds", remainingSeconds > 0 ? remainingSeconds : 0);
		return "session-remaining";
	}

}

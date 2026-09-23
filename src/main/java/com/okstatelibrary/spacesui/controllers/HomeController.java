
package com.okstatelibrary.spacesui.controllers;

import java.io.IOException;
import java.io.Serial;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.okstatelibrary.spacesui.util.SystemProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import com.okstatelibrary.spacesui.globals.GlobalConfigs;
import com.okstatelibrary.spacesui.models.*;
import com.okstatelibrary.spacesui.services.AccessTokenService;
import com.okstatelibrary.spacesui.services.FolioService;
import com.okstatelibrary.spacesui.services.SpacesService;
import com.okstatelibrary.spacesui.stereotypes.CurrentUser;
import com.okstatelibrary.spacesui.tenant.MasterData;
import com.okstatelibrary.spacesui.tenant.TenantConfigRegistry;
import com.okstatelibrary.spacesui.tenant.TenantContext;
import com.okstatelibrary.spacesui.tenant.TenantMasterDataCache;
import com.okstatelibrary.spacesui.util.DateTimeUtil;
import com.okstatelibrary.spacesui.util.Messages;
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
     * Provides access to application-level system properties and configuration values.
     */
    @Autowired
    private com.okstatelibrary.spacesui.util.SystemProperties systemProperties;

    /**
     * Service responsible for retrieving and managing API access tokens.
     */
    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * Service responsible for communicating with the Springshare Spaces API.
     */
    @Autowired
    private SpacesService spaceService;

    /**
     * Service responsible for communicating with the FOLIO API.
     */
    @Autowired
    private FolioService folioService;

    /**
     * Registry that provides configuration settings for the current tenant.
     */
    private final TenantConfigRegistry registry;

    /**
     * Cache that stores tenant-specific master data, such as room and category information.
     */
    private final TenantMasterDataCache masterDataCache;

    /**
     * Logger used for application logging and diagnostic messages.
     */
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    /**
     * The variable defines the message to be displayed when rooms are found.
     */
    private static final String roomsFoundLabelString = " Room(s) found...";

    /**
     * All the time slots
     */
    private static final List<Availability> fixedTimeSlots = new ArrayList<>() {
        /**
         * Add predefine time slots
         */
        @Serial
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
     * The variable defines the message to be displayed when rooms are found.
     *
     * @param masterDataCache
     */
    public HomeController(TenantConfigRegistry registry, TenantMasterDataCache masterDataCache) {
        this.registry = registry;
        this.masterDataCache = masterDataCache;
    }


    /**
     * Loads the application home page and initializes the required data, including
     * available rooms, seats, floors, and time slots.
     *
     * <p>The method also validates the API access token and redirects to the
     * appropriate error page if the external API is unavailable.</p>
     *
     * @param request the HTTP request used to manage the user session
     * @param model   the Spring MVC model used to pass data to the view
     * @return the name of the view to display or a redirect URL
     * @throws JsonParseException   if the API response cannot be parsed
     * @throws JsonMappingException if the API response cannot be mapped
     * @throws RestClientException  if an error occurs while communicating with the API
     * @throws IOException          if an input/output error occurs
     * @throws JSONException        if a JSON processing error occurs
     * @throws ParseException       if date or time parsing fails
     *
     *
     */
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) throws JsonParseException, JsonMappingException,
            RestClientException, IOException, JSONException, ParseException {

        readRibbonMessage(model);

        System.out.println("Normal Loading");

        // Please be aware that building construction noise may be disruptive.
        // Construction activity takes place Mon-Fri between 8 a.m. and 5 p.m.;

        globalSetup();

        String accessToken = getAccessTokenFromRequest();

        if (accessToken == null || accessToken.isEmpty()) {
            System.out.println("API not working");

            return "redirect:/errorp/101";

        } else {

            String selectedSeats = "0";
            String selectedFloor = "0";

            GlobalConfigs globalConfigs = registry.getCurrentConfig();

            Map<String, String> seatList = globalConfigs.getSeatList();

            model.addAttribute("hidefloorselection", globalConfigs.hideFloorSelection());

            SpaceItem[] spaceItems = madeAvailableTimeSlots(DateTimeUtil.getTodayDate(), selectedSeats, selectedFloor);

            model.addAttribute("spaceList", spaceItems);

            model.addAttribute("dateString", DateTimeUtil.getTodayDate());

            model.addAttribute("totalRooms", (spaceItems != null ? spaceItems.length : 0)
                    + roomsFoundLabelString);

            model.addAttribute("seats", seatList);
            model.addAttribute("selectedSeat", selectedSeats);

            model.addAttribute("floors", globalConfigs.getFloorList());
            model.addAttribute("selectedFloor", selectedFloor);

            HttpSession session = request.getSession(true);

            session.setMaxInactiveInterval(900);
            System.out.println("session.getMaxInactiveInterval()" + session.getMaxInactiveInterval());

            return "pages/index";
        }

    }



    /**
     * Processes the room search/filter request submitted from the home page.
     *
     * <p>The method filters available rooms based on the selected date, seat
     * capacity, and floor, then returns the updated room availability information
     * to the index page.</p>
     *
     * @param request the HTTP request
     * @param date    the selected date
     * @param seats   the selected minimum seat capacity
     * @param floor   the selected floor
     * @param model   the Spring MVC model used to pass data to the view
     * @return the name of the view to display or a redirect URL
     * @throws JsonParseException   if the API response cannot be parsed
     * @throws JsonMappingException if the API response cannot be mapped
     * @throws RestClientException  if an error occurs while communicating with the API
     * @throws IOException          if an input/output error occurs
     * @throws JSONException        if a JSON processing error occurs
     * @throws ParseException       if date or time parsing fails
     *
     *
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String index(HttpServletRequest request, @ModelAttribute("date") String date,
                        @ModelAttribute("seats") String seats, @ModelAttribute("floor") String floor, Model model)
            throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException,
            ParseException {

        try {

            System.out.println("Drop down filtration request loading");

            if (date.isEmpty() || date == null) {
                date = DateTimeUtil.getTodayDate();
            }

            if (seats.isEmpty() || seats == null) {
                seats = "0";
            }

            if (floor.isEmpty() || floor == null) {
                floor = "0";
            }

            GlobalConfigs globalConfigs = registry.getCurrentConfig();

            String category = globalConfigs.getCategoryNumber();

            Map<String, String> seatList = globalConfigs.getSeatList();

            model.addAttribute("hidefloorselection", globalConfigs.hideFloorSelection());

            model.addAttribute("dateString", date);

            model.addAttribute("seats", seatList);
            model.addAttribute("selectedSeat", seats);

            model.addAttribute("floors", globalConfigs.getFloorList());
            model.addAttribute("selectedFloor", floor);

            SpaceItem[] spaceItems = madeAvailableTimeSlots(date, seats, floor);

            model.addAttribute("spaceList", spaceItems);

            model.addAttribute("totalRooms", (spaceItems != null ? spaceItems.length : 0) + roomsFoundLabelString);

            return "pages/index";

        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }

        return "redirect:/errorp";
    }

    /**
     * Handles the page displayed after successful authentication through the
     * Identity Provider (IdP).
     *
     * <p>The method retrieves the authenticated user's SAML information,
     * verifies that the user exists in the library system, and redirects the
     * user to the booking page. Unauthorized or incomplete user information
     * results in an appropriate error redirect.</p>
     *
     * @param request the HTTP request used to access the user's session
     * @param user    the authenticated user
     * @param model   the Spring MVC model used to pass user information to the view
     * @return the booking page or an appropriate redirect URL
     * @throws JsonParseException   if the API response cannot be parsed
     * @throws JsonMappingException if the API response cannot be mapped
     * @throws RestClientException  if an error occurs while communicating with the API
     * @throws IOException          if an input/output error occurs
     *
     *
     */
    @RequestMapping("/landing")
    public String landing(HttpServletRequest request, @CurrentUser User user, Model model)
            throws JsonParseException, JsonMappingException, RestClientException, IOException {

        System.out.println("/landing page");

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

            System.out.println("samlUser null " + user.getUsername());

            return "redirect:/"; // Redirects to home page.
        }

        if (folioService.isUserExists(samlUser.getCwid())) {

            model.addAttribute("firstName", samlUser.getFirstName());
            model.addAttribute("lastName", samlUser.getLastName());
            model.addAttribute("email", samlUser.getEmail());

            return "pages/booking";
        } else {
            return "redirect:/errorp/306";
        }
    }

    /**
     * Cancels an existing room booking and displays the cancellation result.
     *
     * <p>The method retrieves the booking details, submits the cancellation
     * request to the Spaces API, and displays either a success or error message.</p>
     *
     * @param bookingId the identifier of the booking to cancel
     * @param model     the Spring MVC model used to pass the result to the view
     * @return the booking summary view * @throws JsonParseException if the API response cannot be parsed
     * @throws JsonMappingException if the API response cannot be mapped
     * @throws RestClientException  if an error occurs while communicating with the API
     * @throws IOException          if an input/output error occurs
     * @throws JSONException        if a JSON processing error occurs
     *
     *
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel(@ModelAttribute("bookingId") String bookingId, Model model)
            throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

        System.out.println("cancel");

        GlobalConfigs globalConfigs = registry.getCurrentConfig();

        if (bookingId.isEmpty() || bookingId == null) {

            model.addAttribute("summaryModel", null);
            model.addAttribute("errorMessage",
                    Messages.ERROR_BOOKING_SOMETING_WENT_WRONG + globalConfigs.getHelpDeskName());

        } else {

            // Get the booking information for redirection after cancel process.
            BookedItem[] bookingItems = spaceService.getBookedItems(getAccessTokenFromRequest(),
                    URLs.GET_BOOKING_DETAILS_URL + bookingId);

            CancelConfirmation[] cancelConfirmation = spaceService.cancel(getAccessTokenFromRequest(),
                    URLs.POST_ROOM_CANCEL_URL + bookingId);

            if (cancelConfirmation == null || cancelConfirmation.length == 0) {

                model.addAttribute("summaryModel", null);
                model.addAttribute("errorMessage",
                        Messages.ERROR_BOOKING_CANCEL_SOMETING_WENT_WRONG + globalConfigs.getHelpDeskName());

            } else {

                model.addAttribute("summaryModel", null);
                model.addAttribute("errorMessage", Messages.SUCESS_BOOKING_CANCEL);
            }

        }

        return "pages/summary";
    }

    /**
     * Initiates the room booking process by redirecting the user to the
     * configured SAML authentication endpoint.
     *
     * @return the SAML authentication redirect URL or the application error page
     * if an unexpected error occurs
     *
     *
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/booking", method = RequestMethod.POST)
    public String booking()
            throws RestClientException, IOException, JSONException {
        try {
            System.out.println("Booking call");
            return "redirect:/saml2/authenticate?registrationId=okstate";
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
        return "redirect:/errorp";
    }

    /**
     * Reserves the selected room for the authenticated user.
     *
     * <p>The method validates the booking information, creates the booking
     * payload, submits the reservation to the Spaces API, and redirects the
     * user to either the booking summary or the appropriate error page.</p>
     *
     * @param request    the HTTP request used to retrieve the user's session
     * @param roomNumber the room selected for the reservation
     * @param bookDate   the date of the reservation
     * @param startTime  the reservation start time
     * @param endTime    the reservation end time
     * @param model      the Spring MVC model
     * @return a redirect URL for the booking summary or error page
     * @throws JsonParseException   if the API response cannot be parsed
     * @throws JsonMappingException if the API response cannot be mapped
     * @throws RestClientException  if an error occurs while communicating with the API
     * @throws IOException          if an input/output error occurs
     * @throws JSONException        if a JSON processing error occurs
     *
     *
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public String reserve(HttpServletRequest request, @ModelAttribute("bookRoomNumber") String roomNumber,
                          @ModelAttribute("bookDate") String bookDate, @ModelAttribute("bookStartTime") String startTime,
                          @ModelAttribute("bookEndTime") String endTime, Model model)
            throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

        System.out.println("reserve call ");

        HttpSession session = request.getSession();

        SAMLUser samlUser = SAMLUserList.getInstance().getSAMLUser(session.getId());

        if (!roomNumber.isEmpty() && !bookDate.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {

            Bookings bookings = new Bookings(roomNumber, DateTimeUtil.convertToISODateTime(bookDate, endTime));

            RoomBookingPayload roomBookingPayLoad = new RoomBookingPayload(
                    DateTimeUtil.convertToISODateTime(bookDate, startTime), samlUser.getFirstName(),
                    samlUser.getLastName(), samlUser.getEmail(), samlUser.getCwid(), new Bookings[]{bookings});

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
     * Displays an appropriate error page based on the supplied error code.
     *
     * <p>The method maps application-specific error codes to user-friendly
     * error messages and adds the message information to the model.</p>
     *
     * @param request the HTTP request used for diagnostic information
     * @param id      the optional application error code
     * @param model   the Spring MVC model used to pass error information to the view
     * @return the error view
     * @throws JsonParseException   if JSON parsing fails
     * @throws JsonMappingException if JSON mapping fails
     * @throws RestClientException  if an API communication error occurs
     * @throws IOException          if an input/output error occurs
     * @throws JSONException        if JSON processing fails
     *
     *
     */
    @RequestMapping(value = {"/errorp", "/errorp/{id}"})
    public String error(HttpServletRequest request, @PathVariable(required = false) String id, Model model)
            throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

        System.out.println("********** ERROR  Occured **********");

        GlobalConfigs globalConfigs = registry.getCurrentConfig();

        String errorMessage = Messages.ERROR_BOOKING_SOMETING_WENT_WRONG + globalConfigs.getHelpDeskName();

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
            } else if (id.equals("888")) {

                System.out.println("Request URL     : " + request.getRequestURL());
                System.out.println("Request URI     : " + request.getRequestURI());
                System.out.println("Context Path    : " + request.getContextPath());
                System.out.println("Server Name     : " + request.getServerName());
                System.out.println("Server Port     : " + request.getServerPort());
                System.out.println("Scheme          : " + request.getScheme());
                System.out.println("Host Header     : " + request.getHeader("Host"));
                System.out.println("X-Forwarded-Host: " + request.getHeader("X-Forwarded-Host"));
                System.out.println("X-Forwarded-Proto: " + request.getHeader("X-Forwarded-Proto"));
                System.out.println("X-Forwarded-Port: " + request.getHeader("X-Forwarded-Port"));
                System.out.println("X-Forwarded-For : " + request.getHeader("X-Forwarded-For"));
            }

            model.addAttribute("errorMessageId", id);
        }

        model.addAttribute("errorMessage", errorMessage);

        return "error";
    }

    /**
     * Displays the summary information for a room booking.
     *
     * <p>The method retrieves the booking details from the Spaces API and
     * enriches the booking information with the room name stored in the
     * tenant-specific master data cache.</p>
     *
     * @param request  the HTTP request
     * @param id       the booking identifier
     * @param isBooked indicates whether the booking was successfully created
     * @param model    the Spring MVC model used to pass booking information to the view
     * @return the booking summary view
     * @throws JsonParseException   if the API response cannot be parsed
     * @throws JsonMappingException if the API response cannot be mapped
     * @throws RestClientException  if an API communication error occurs
     * @throws IOException          if an input/output error occurs
     * @throws JSONException        if JSON processing fails
     *
     *
     */
    @RequestMapping(value = {"/summary/{id}", "/summary/{id}/{isBooked}"})
    public String summary(HttpServletRequest request, @PathVariable("id") String id,
                          @PathVariable(required = false) boolean isBooked, Model model)
            throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

        globalSetup();

        GlobalConfigs globalConfigs = registry.getCurrentConfig();

        if (id.isEmpty() || id == null) {
            model.addAttribute("summaryModel", null);
            model.addAttribute("errorMessage",
                    Messages.ERROR_BOOKING_SOMETING_WENT_WRONG + globalConfigs.getHelpDeskName());

        } else {

            BookedItem[] bookingItems = spaceService.getBookedItems(getAccessTokenFromRequest(),
                    URLs.GET_BOOKING_DETAILS_URL + id);

            if (bookingItems == null || bookingItems.length == 0) {

                model.addAttribute("summaryModel", null);
                model.addAttribute("errorMessage", Messages.ERROR_BOOKING_SUMMARY_MISSING_BOOKING_ID);

            } else {

                BookedItem bookedItem = bookingItems[0];

                MasterData masterData = masterDataCache.get(TenantContext.getTenantId());

                masterData.print();

                bookedItem.setRoom(masterData.getRoomName(bookedItem.getEid()));

                model.addAttribute("summaryModel", bookedItem);
                model.addAttribute("isBooked", isBooked);
            }
        }

        return "pages/summary";
    }

    /**
     * Displays the list of currently active SAML user sessions.
     *
     * @param request the HTTP request
     * @param model   the Spring MVC model used to pass active sessions to the view
     * @return the sessions view
     *
     */
    @RequestMapping(value = {"/session-count"})
    public String getSessions(HttpServletRequest request, Model model) {
        ArrayList<SAMLUser> sessions = SAMLUserList.getInstance().getUserArray();
        model.addAttribute("sysSessions", sessions);
        return "pages/sessions";
    }

    /**
     * Removes all active SAML user sessions and redirects to the session
     * monitoring page.
     *
     * @param request the HTTP request
     * @param model   the Spring MVC model
     * @return a redirect to the session count page
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    public String clean(HttpServletRequest request, Model model) {

        SAMLUserList samlUserList = SAMLUserList.getInstance();

        if (samlUserList != null) {
            ArrayList<SAMLUser> sessions = samlUserList.getUserArray();

            if (sessions != null && !sessions.isEmpty()) {
                for (SAMLUser session : sessions) {
                    samlUserList.removeFromArray(session);
                }
            }

        }

        return "redirect:/session-count";
    }

    /**
     * Calculates the remaining time before the current HTTP session expires * due to inactivity.
     * <p>The remaining time is added to the model in seconds for display * on the session timeout page.</p>
     *
     * @param session the current HTTP session
     * @param model   the Spring MVC model used to pass the remaining time to the view
     * @return the session remaining-time view
     */
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

    /**
     * Reads the configured ribbon message from the message file and adds the
     * message information to the model for display on the application page.
     *
     * <p>If the message file does not exist, the ribbon message is hidden.</p>
     *
     * @param model the Spring MVC model used to pass the message information
     *              to the view
     *
     */
    private void readRibbonMessage(Model model) {

        GlobalConfigs config = registry.getCurrentConfig();

        Path file = Path.of(SystemProperties.RibbonMessageFolderPath + config.getInstanceName() + "-message.txt");

        if (Files.exists(file)) {
            try {

                String message = Files.readString(file, StandardCharsets.UTF_8);

                String[] typeAndMessage = message.split(",");

                model.addAttribute("message", message);

                model.addAttribute("ribbonmessagevisibility", "show");
                model.addAttribute("messageclass", "ribbon-" + typeAndMessage[0]);
                model.addAttribute("ribbonmessage", typeAndMessage[1]);
            } catch (IOException e) {
            }
        } else {
            model.addAttribute("ribbonmessagevisibility", "hide");
        }
    }

    /**
     * Performs the global application setup by loading the configured study room
     * category and retrieving the associated room details from the Spaces API.
     *
     * <p>The retrieved rooms are stored in the tenant-specific master data cache
     * for use throughout the application.</p>
     *
     * @throws JsonParseException   if the API response cannot be parsed
     * @throws JsonMappingException if the API response cannot be mapped to an object
     * @throws RestClientException  if an error occurs while communicating with the API
     * @throws IOException          if an input/output error occurs
     * @throws JSONException        if a JSON processing error occurs
     *
     *
     */
    private void globalSetup()
            throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException {

        //Map<String, String> studyRooms = new HashMap<>();

        List<Room> roomList = new ArrayList<>();

        GlobalConfigs config = registry.getCurrentConfig();

        MasterData masterData = masterDataCache.get(TenantContext.getTenantId());

        Category[] categoryItems = masterData.getCategories();

        //studyRooms.put(config.getCategoryNumber(), categoryItems[0].getItems());

        Room[] rooms = spaceService.getRoom(getAccessTokenFromRequest(),
                URLs.GET_ROOM_DETAILS_URL + categoryItems[0].getItems());

        for (Room room : rooms) {
            roomList.add(room);
        }

        masterData.setRooms(roomList);
    }

    /**
     * Retrieves available rooms and builds their availability time slots for
     * the specified date and search criteria. *
     *
     * <p>The method filters rooms by seat capacity and floor, removes availability
     * records belonging to previous dates, and fills missing time slots as booked.
     * The resulting rooms are sorted alphabetically by room name.</p>
     *
     * @param date  the date for which room availability is requested
     * @param seats the minimum required seat capacity
     * @param floor the selected floor; "0" indicates all floors
     * @return an array of available rooms with their time-slot information,
     * or {@code null} if no rooms are available
     * @throws JsonParseException   if the API response cannot be parsed
     * @throws JsonMappingException if the API response cannot be mapped
     * @throws RestClientException  if an API communication error occurs
     * @throws IOException          if an input/output error occurs
     * @throws JSONException        if JSON processing fails
     * @throws ParseException       if date or time parsing fails
     *
     *
     */
    private SpaceItem[] madeAvailableTimeSlots(String date, String seats, String floor)
            throws JsonParseException, JsonMappingException, RestClientException, IOException, JSONException,
            ParseException {

        int seatsCount = Integer.parseInt(seats);

        List<SpaceItem> list = new ArrayList<>();

        MasterData masterData = masterDataCache.get(TenantContext.getTenantId());

        SpaceItem[] spaceItems = spaceService.getItems(getAccessTokenFromRequest(),
                URLs.getSpacesURL(masterData.getStudyRooms(), date));

        for (SpaceItem spaceItem : spaceItems) {

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

                    Availability availability = availabilityList.stream()
                            .filter(timeSlot -> timeSlot.getFromTime().equals(fixedTimeSlot.getFrom12HourTime()))
                            .findFirst().orElse(null);

                    if (availability == null) {

                        Availability ava = new Availability(date + "T" + fixedTimeSlot.getFrom(),
                                date + "T" + fixedTimeSlot.getTo());
                        ava.setBooked(true);

                        newAvailabilityList.add(ava);

                    } else {
                        newAvailabilityList.add(availability);
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
     * Finds the index of a fixed time slot that matches the specified start time.
     *
     * @param fromTime the 12-hour formatted start time to search for
     * @return the index of the matching time slot, or {@code -1} if no match is found
     *
     *
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
     * Retrieves an access token from the Spaces API authentication service.
     *
     * <p>The token is generated using the configured client ID and secret key
     * and is used to authenticate subsequent API requests.</p>
     *
     * @return the access token, or {@code null} if an access token could not be obtained
     * @throws JsonParseException   if the authentication response cannot be parsed
     * @throws RestClientException  if an error occurs while communicating with the API
     * @throws JsonMappingException if the authentication response cannot be mapped
     * @throws IOException          if an input/output error occurs
     * @throws JSONException        if JSON processing fails
     *
     */
    private @Nullable String getAccessTokenFromRequest()
            throws JsonParseException, RestClientException, JsonMappingException, IOException, JSONException {

        AccessToken accessToken = accessTokenService.getAccessToken(URLs.GET_AUTH_TOKEN_URL,
                systemProperties.getSpringShareClientId(), systemProperties.getSpringShareSecretkey());

        if (accessToken != null) {
            return accessToken.getAccessToken();
        } else {
            return null;
        }
    }
}

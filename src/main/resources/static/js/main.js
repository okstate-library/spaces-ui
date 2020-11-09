
$(document).foundation();


$(document).ready(function() {

	$('#date').fdatepicker({
		format : 'yyyy-mm-dd'
	}).on('changeDate', function(event) {
		var pickedDate = $("#date").val();
		// alert(pickedDate);
		$("#dp-form").submit();
	});

});


var selectedSlots = [];
var selectedRoomName = null;
var selectedRoomId = null;
var buttons = [];

// The method that checking for validated time slots.
function addItem(button, roomName, roomId, fromTime, toTime, fromDateTime,
		booked) {

	if (booked == 'false') {

		if (selectedRoomName == null) {

			selectedRoomName = roomName;
			selectedRoomId = roomId;

			$(button).css('background', '#cbdbeb');

			buttons.push(button);

			selectedSlots.push({
				from : fromTime,
				to : toTime,
				fromDateTime : fromDateTime
			});

		} else {
			if (selectedRoomName != roomName) {
				alert('You may only reserve one room at a time.');
				return;
			} else {

				if (selectedSlots.length > 0) {

					if (!(selectedSlots.some(el => el.from === fromTime))) {

						var timeSlotLength = 60000;

						var firstFromTime = getTimeValue(selectedSlots[0].fromDateTime);

						var suppliedTime = getTimeValue(fromDateTime)

						var lastFromTime = getTimeValue(selectedSlots[selectedSlots.length - 1].fromDateTime);

						// checking continuous time selection.
						if ((firstFromTime - suppliedTime > 30)
								|| (lastFromTime - suppliedTime < -30)) {
							alert('Please select continuous time slots.');
							return;
						}

						// Checking more than 8 time slots selection.
						if (selectedSlots.length == 8) {
							alert('You may select up to 8 time slots at a time.');
							return;
						}

						$(button).css('background', '#cbdbeb');

						// making the slot object and push to the lost
						selectedSlots.push({
							from : fromTime,
							to : toTime,
							fromDateTime : fromDateTime
						});

						buttons.push(button);

						//console.error(selectedSlots);

					} else {

						if (selectedSlots[0].from == fromTime) {
							selectedSlots.splice(0, 1);
							$(button).css('background', '');

							if (selectedSlots.length == 0) {
								clearSelection();
								return;
							}

						} else if (selectedSlots[selectedSlots.length - 1].from == fromTime) {

							selectedSlots.splice(selectedSlots.length - 1, 1);
							$(button).css('background', '');
						}

						//console.error(selectedSlots);

					}
				}
			}
		}

		selectedSlots.sort(compare);

		var mainDivName = "confirmWindow-" + roomId;

		var confirmWindow = document.getElementById(mainDivName);
		confirmWindow.style.display = "block";

		var startTime = document.querySelector("#" + mainDivName
				+ " #startTime");
		startTime.textContent = selectedSlots[0].from;

		var endTime = document.querySelector("#" + mainDivName + " #endTime");
		endTime.textContent = selectedSlots[selectedSlots.length - 1].to;

	}
}

// 
function getTimeValue(time) {
	var timeSlotLength = 60000;

	return Date.parse(time) / timeSlotLength;
}

function compare(a, b) {
	if (a.fromDateTime < b.fromDateTime) {
		return -1;
	}
	if (a.fromDateTime > b.fromDateTime) {
		return 1;
	}
	return 0;
}

// Clear the selecrtion in the index page.
function clearSelection() {

	var mainDivName = "confirmWindow-" + selectedRoomId;

	var confirmWindow = document.getElementById(mainDivName);
	confirmWindow.style.display = "none";

	selectedSlots = [];

	selectedRoomName = null;
	selectedRoomId = null;

	for (i = 0; i < buttons.length; i++) {

		$(buttons[i]).css('background', '');
	}

	buttons = [];

}

// Session values storage
function storeSelection() {

	sessionStorage.setItem("selectedRoomId", selectedRoomId);

	sessionStorage.setItem("roomName", selectedRoomName);

	sessionStorage.setItem("dateString",
			document.getElementById('dateString').value);

	sessionStorage.setItem("hid_startTime", selectedSlots[0].from);

	sessionStorage.setItem("hid_endTime",
			selectedSlots[selectedSlots.length - 1].to);

}

// Booking page
function bookingPageLoad() {

	var roomId = sessionStorage.getItem("selectedRoomId");

	if (roomId === undefined || roomId === null) {
		document.getElementById("bookingCard").style.display = "none";
	} else {

		document.getElementById("bookingCard").style.visibility = "visible";

		var roomName = sessionStorage.getItem("roomName");
		var dateString = sessionStorage.getItem("dateString");

		var startTime = sessionStorage.getItem("hid_startTime");
		var endTime = sessionStorage.getItem("hid_endTime");

		document.getElementById("bookRoomNumber").value = roomId;
		document.getElementById("bookDate").value = dateString;
		document.getElementById("bookStartTime").value = startTime;
		document.getElementById("bookEndTime").value = endTime;

		document.getElementById("spanRoomName").textContent = roomName;
		document.getElementById("spanbookDate").textContent = dateString;
		document.getElementById("spanStartTime").textContent = startTime;
		document.getElementById("spanEndTime").textContent = endTime;
	}

	// cleanSession();
}

// Clear the session data.
function cleanSession() {
	sessionStorage.clear()
}



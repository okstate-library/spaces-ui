//$(document).foundation();  

$(document).foundation();

$('[data-reveal-id]').on('click', function() {
	var targetModal = $('#' + $(this).data('revealId'));
	var newText = $(this).text() + ' (' + $(this).data('myNumber') + ')';

	targetModal.find('.buttonText').text(newText);
});

$(document).ready(function() {

	$('#date').fdatepicker({
		format : 'yyyy/mm/dd'
	}).on('changeDate', function(event) {
		var pickedDate = $("#date").val();
		// alert(pickedDate);
		$("#dp-form").submit();
	});

});

function aboutThisRoomModal(roomName, roomDesc) {
	alert(roomName + roomDesc.toString());

}

var selectedSlots = [];
var selectedRoomName = null;
var selectedRoomId = null;
var buttons = [];

function addItem(button, roomName, roomId, fromTime, toTime, fromDateTime) {

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

					if ((firstFromTime - suppliedTime > 30)
							|| (lastFromTime - suppliedTime < -30)) {
						alert('Please select only contiguous time blocks! ');
						return;
					}

					if (selectedSlots.length == 8) {
						alert('You can only select 8 time slots at a time');
						return;
					}

					$(button).css('background', '#cbdbeb');

					selectedSlots.push({
						from : fromTime,
						to : toTime,
						fromDateTime : fromDateTime
					});

					buttons.push(button);

				} else {

					if (selectedSlots[0].from == fromTime) {
						selectedSlots.splice(0, 1);
						$(button).css('background', '');

					} else if (selectedSlots[selectedSlots.length - 1].from == fromTime) {

						selectedSlots.splice(selectedSlots.length - 1, 1);
						$(button).css('background', '');
					}

					console.error(selectedSlots);

					// return;

				}
			}
		}
	}

	// alert(selectedSlots[0]);
	//	
	// if (date1.getTime() > date2.getTime()) {
	// alert("The first date is after the second date!");
	// }

	// var ul = document.getElementById("dynamic-list");
	// var li = document.createElement("li");
	// li.setAttribute('id', fromTime + ' - ' + toTime + ' - ' + fromDateTime);
	// li.appendChild(document.createTextNode(fromTime + ' - ' + toTime + ' - '
	// + fromDateTime));
	// ul.appendChild(li);

	selectedSlots.sort(compare);



	// var roomNameSpan = document.getElementById("roomName");
	// roomNameSpan.textContent = roomName;

	// var startTime = document.getElementById("startTime");
	// startTime.textContent = selectedSlots[0].from;
	//
	// var endTime = document.getElementById("endTime");
	// endTime.textContent = selectedSlots[selectedSlots.length - 1].to;

	var mainDivName = "confirmWindow-" + roomId;

	var confirmWindow = document.getElementById(mainDivName);
	confirmWindow.style.display = "block";

	var startTime = document.querySelector("#" + mainDivName + " #startTime");
	startTime.textContent = selectedSlots[0].from;

	var endTime = document.querySelector("#" + mainDivName + " #endTime");
	endTime.textContent = selectedSlots[selectedSlots.length - 1].to;

	document.getElementById('hid_startTime-' + roomId ).value = selectedSlots[0].from;
	document.getElementById('hid_endTime-' +roomId).value = selectedSlots[selectedSlots.length - 1].to;

	// var hid_startTime = document.querySelector("#" + mainDivName + "
	// #hid_startTime");
	// hid_startTime.textContent = selectedSlots[0].from;
	//	
	// var hid_endTime = document.querySelector("#" + mainDivName + "
	// #hid_endTime");
	// hid_endTime.textContent = selectedSlots[selectedSlots.length - 1].to;
}

function getTimeValue(time) {
	var timeSlotLength = 60000;

	return Date.parse(time) / timeSlotLength;
}

function compare(a, b) {
	if (a.from < b.from) {
		return -1;
	}
	if (a.from > b.from) {
		return 1;
	}
	return 0;
}

function clearSelection() {
	// selectedSlots = [];

	
	var  mainDivName= "confirmWindow-" + selectedRoomId;

	
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

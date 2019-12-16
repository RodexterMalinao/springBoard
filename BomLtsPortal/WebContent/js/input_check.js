

function check_format(input) {
	if (input == "") {
		return true;
	} else
		return false;
}

function check_digit(input) {
	var result = input.match(/^[235689]+[^\D]*$/);

	var msg = error_msg7;
	var error = error_msg3;
	var output = new Array(2);

	if (input.length != 0 && (result == null || input.length != 8)) {
		output[0] = error;
		output[1] = "error";
	} else if (input.length == 0) {
		output[0] = msg;
		output[1] = "error";
	} else {
		output[0] = "";
		output[1] = "";
	}
	return output;
}

function check_email(input) {
	var result = input
			.match(/^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,4})$/);

	var msg = error_msg8;
	var error = error_msg4;

	var output = new Array(2);

	if (result == null && input.length != 0) {
		output[0] = error;
		output[1] = "error";
	} else if (input.length == 0) {
		output[0] = msg;
		output[1] = "error";
	} else {
		output[0] = "";
		output[1] = "";
	}

	return output;
}
'use strict';

$(function() {
	$("#confirmationPasswordField, #password").on('keyup', function() {
		let password = $("#password").val();
		let confirmationPasswordField = $("#confirmationPasswordField").val();
		$.ajax({
			url: "/check/confirmation-password",
			type: "post",
			dataType: "json",
			data: {
				password: password,
				confirmationPasswordField: confirmationPasswordField
			},
			async: true,
		}).done(function(data) {
			/*console.log(data);*/
			/*console.dir(JSON.stringify(data));*/
			/*console.log("password=" + password);*/
			/*console.log("confirmationPasswordField=" + confirmationPasswordField);*/
			$("#confirmationPasswordResult").text(data.resultMessage);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			/*alert("エラー");*/
			console.log("XMLHttpRequest:" + XMLHttpRequest);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown);
		});
	});
});



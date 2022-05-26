'use strict';

$(function(){
	$("#administrator_insert_btn").on("click", function(){
    $("#administrator_insert_btn").prop("disabled", true);
    $("#administrator_insert_btn").closest('form').submit();
  });
});

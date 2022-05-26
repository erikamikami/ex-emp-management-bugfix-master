package jp.co.sample.emp_management.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class AdministratorApiController {

	@RequestMapping(value = "/confirmation-password", method = RequestMethod.POST)
	public Map<String, String> checkConfirmationPassword(String password, String confirmationPasswordField) {
		Map<String, String> result = new HashMap<>();
		String resultMessage = null;
		if (password.equals(confirmationPasswordField)) {
			resultMessage = "同じ値です";
		} else if (!(password.equals(confirmationPasswordField))) {
			resultMessage = "同じ値を入力してください";
		}
			

		result.put("resultMessage", resultMessage);
		return result;
	}

}

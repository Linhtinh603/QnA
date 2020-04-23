package vn.edu.iuh.qna.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.edu.iuh.qna.utils.WebUtils;

@Controller
public class HomeController {
	@GetMapping("/login")
	public String login(Model model, Principal principal) {
		return "login";
	}

	@GetMapping("/403")
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();

			String userInfo = WebUtils.toString(loginedUser);

			model.addAttribute("userInfo", userInfo);

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}

//
//	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
//	public String logoutSuccessfulPage(Model model) {
//		model.addAttribute("title", "Logout");
//		return "logoutSuccessfulPage";
//	}
//

}

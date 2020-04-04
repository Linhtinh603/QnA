package vn.edu.iuh.qna.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.edu.iuh.qna.entity.UserModel;
import vn.edu.iuh.qna.repository.UserRepository;
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
			User loginedUser = (User) ((Authentication) principal).getPrincipal();

			String userInfo = WebUtils.toString(loginedUser);

			model.addAttribute("userInfo", userInfo);

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}

//	@Secured("USER")
//	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
//	public String welcomePage(Model model) {
//		model.addAttribute("title", "Welcome");
//		model.addAttribute("message", "This is welcome page!");
//		//userRepo.save(new AppUser(null,"huu","123",true,null));
//		//userRepo.findAll().forEach(System.out::println);
//		return "welcomePage";
//	}
//
//	@Secured("ADMIN")
//	@RequestMapping(value = "/admin", method = RequestMethod.GET)
//	public String adminPage(Model model, Principal principal) {
//
//		User loginedUser = (User) ((Authentication) principal).getPrincipal();
//
//		String userInfo = WebUtils.toString(loginedUser);
//		model.addAttribute("userInfo", userInfo);
//
//		return "adminPage";
//	}
//
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String loginPage(Model model) {
//
//		return "loginPage";
//	}
//
//	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
//	public String logoutSuccessfulPage(Model model) {
//		model.addAttribute("title", "Logout");
//		return "logoutSuccessfulPage";
//	}
//
//	@Secured("ROLE_USER")
//	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
//	public String userInfo(Model model, Principal principal) {
//
//		// Sau khi user login thanh cong se co principal
//		String userName = principal.getName();
//
//		System.out.println("User Name: " + userName);
//
//		User loginedUser = (User) ((Authentication) principal).getPrincipal();
//
//		String userInfo = WebUtils.toString(loginedUser);
//		model.addAttribute("userInfo", userInfo);
//
//		return "userInfoPage";
//	}
//

}

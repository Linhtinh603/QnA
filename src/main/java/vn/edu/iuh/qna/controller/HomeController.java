package vn.edu.iuh.qna.controller;

import static vn.edu.iuh.qna.config.WebSecurityConfig.ROLE_CONFIG;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.edu.iuh.qna.config.WebSecurityConfig.RoleConfig;
import vn.edu.iuh.qna.utils.WebUtils;

@Controller
public class HomeController {
	// @Secured("ROLE_ANONYMOUS")
	@GetMapping("/login")
	public String login(Model model, Principal principal) {
		if(principal!=null){
			Authentication auth =(Authentication) principal;
			Collection<? extends GrantedAuthority> authors = auth.getAuthorities();
			GrantedAuthority author = authors.iterator().next();
			RoleConfig roleConfig = ROLE_CONFIG.get(author.getAuthority());
			if (roleConfig != null) {
				return "redirect:"+roleConfig.getPath();
			}
		}
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

	@GetMapping("/404")
	public String notFound(Model model, Principal principal) {
		return "404Page";
	}

}

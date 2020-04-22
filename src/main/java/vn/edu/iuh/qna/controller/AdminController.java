package vn.edu.iuh.qna.controller;

import static vn.edu.iuh.qna.config.WebSecurityConfig.ROLE_ADMIN;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Secured(ROLE_ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController {
	@GetMapping
	public String home() {
		return "admin/report";
	}
	
	@GetMapping("manage_account")
	public String manageAccount() {
		return "admin/manage_account";
	}
}

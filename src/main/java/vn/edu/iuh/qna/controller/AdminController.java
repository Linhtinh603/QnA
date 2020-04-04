package vn.edu.iuh.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

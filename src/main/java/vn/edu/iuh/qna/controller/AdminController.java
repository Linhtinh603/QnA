package vn.edu.iuh.qna.controller;

import static vn.edu.iuh.qna.config.WebSecurityConfig.ROLE_ADMIN;
import static vn.edu.iuh.qna.config.WebSecurityConfig.ROLE_USER;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.edu.iuh.qna.dto.UserInfoReqDto;
import vn.edu.iuh.qna.entity.UserModel;
import vn.edu.iuh.qna.service.UserService;
import vn.edu.iuh.qna.utils.EncrytedPasswordUtils;

@Secured(ROLE_ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;

	@GetMapping
	public String home() {
		return "admin/report";
	}

	@GetMapping("manage_accounts")
	public String manageAccounts() {
		return "admin/manage_account";
	}

	@GetMapping("add_account")
	public String addAccount(Model model) {
		model.addAttribute("user", new UserInfoReqDto());
		return "admin/add_account";
	}

	@PostMapping("add_account")
	public String doAddAccount(@Valid @ModelAttribute("user") UserInfoReqDto user, BindingResult bindingResult,
			Model model) {
		if (!bindingResult.hasErrors()) {
			Optional<UserModel> userOp = userService.findByUserName(user.getUserName());
			if (!userOp.isPresent()) {
				UserModel userModel = new UserModel();
				userModel.setBirthday(user.getBirthday());
				userModel.setCreateTime(new Date());
				userModel.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(user.getPassword()));
				userModel.setFullName(user.getFullName());
				userModel.setJobPosition(user.getJobPosition());
				userModel.setRole(ROLE_USER);
				userModel.setStatus(true);
				userModel.setUserName(user.getUserName());
				userService.save(userModel);
				return "redirect:/admin/manage_accounts";
			} else {
				bindingResult.rejectValue("userName", "", "Tên đăng nhập đã tồn tại");
			}
		}
		model.addAttribute("user", user);
		return "admin/add_account";

	}

	@GetMapping("manage_questions")
	public String manageQuestions() {
		return "admin/manage_question";
	}
}

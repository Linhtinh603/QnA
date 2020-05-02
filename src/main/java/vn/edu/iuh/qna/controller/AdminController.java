package vn.edu.iuh.qna.controller;

import static vn.edu.iuh.qna.config.WebSecurityConfig.ROLE_ADMIN;
import static vn.edu.iuh.qna.config.WebSecurityConfig.ROLE_USER;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.edu.iuh.qna.dto.UserInfoReqDto;
import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.entity.UserModel;
import vn.edu.iuh.qna.service.QuestionService;
import vn.edu.iuh.qna.service.ReportService;
import vn.edu.iuh.qna.service.ReportService.CountReportDto;
import vn.edu.iuh.qna.service.UserService;
import vn.edu.iuh.qna.utils.EncrytedPasswordUtils;

@Secured(ROLE_ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;

	@Autowired
	private ReportService reportService;

	@GetMapping
	public String home(@RequestParam(required = false) String from, @RequestParam(required = false) String to,
			Model model) {
		SimpleDateFormat SDFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = SDFormat.parse(from);
			toDate = SDFormat.parse(to);
			if (fromDate.getTime() > toDate.getTime()) {
				throw new Exception();
			}
		} catch (Exception ex) {
			Calendar cal = Calendar.getInstance();
			toDate = cal.getTime();
			cal.add(Calendar.MONTH, -1);
			fromDate = cal.getTime();
		}

		Map<String, List<CountReportDto>> report = reportService.queryAdminReport(fromDate, toDate);
		model.addAttribute("report", report);
		model.addAttribute("from", fromDate);
		model.addAttribute("to", toDate);
		return "admin/report";
	}

	@GetMapping("manage_accounts")
	public String manageAccounts(Model model) {
		List<UserModel> users = userService.findByRole(ROLE_USER);
		model.addAttribute("users", users);
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
				userModel.setFollowingQuestions(new LinkedList<>());
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

	@GetMapping("edit_account/{userId}")
	public String editAccount(Model model, @PathVariable String userId) {
		Optional<UserModel> userModelOp = userService.findById(userId);
		if (!userModelOp.isPresent()) {
			return "404Page";
		}
		UserModel userModel = userModelOp.get();
		UserInfoReqDto user = new UserInfoReqDto();
		user.setId(userModel.getId());
		user.setBirthday(userModel.getBirthday());
		user.setConfirmPassword(userModel.getEncrytedPassword());
		user.setFullName(userModel.getFullName());
		user.setJobPosition(userModel.getJobPosition());
		user.setPassword(userModel.getEncrytedPassword());
		user.setUserName(userModel.getUserName());
		model.addAttribute("user", user);
		return "admin/edit_account";
	}

	@PostMapping("edit_account")
	public String doEditAccount(@Valid @ModelAttribute("user") UserInfoReqDto user, BindingResult bindingResult,
			Model model) {
		if (!bindingResult.hasErrors()) {
			Optional<UserModel> userOp = userService.findById(user.getId());
			UserModel userModel = userOp.orElse(null);
			if (userOp.isPresent() && userModel.getId().equals(user.getId())) {
				userModel.setBirthday(user.getBirthday());
				if (!userModel.getEncrytedPassword().equals(user.getPassword())) {
					userModel.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(user.getPassword()));
				}
				userModel.setFullName(user.getFullName());
				userModel.setJobPosition(user.getJobPosition());
				userModel.setUserName(user.getUserName());
				userService.save(userModel);
				return "redirect:/admin/manage_accounts";
			}
		}
		model.addAttribute("user", user);
		return "admin/edit_account";
	}

	@ResponseBody
	@PostMapping("change_account_status")
	public ResponseEntity<String> doChangeAccountStatus(@RequestParam String userId, @RequestParam boolean status) {
		Optional<UserModel> userOp = userService.findById(userId);
		if (userOp.isPresent()) {
			userOp.get().setStatus(status);
			userService.save(userOp.get());
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("manage_questions")
	public String manageQuestions(Model model) {
		List<QuestionModel> questions = questionService.findAll().stream().filter(q -> !q.isDeleted()).collect(Collectors.toList());
		model.addAttribute("questions", questions);
		return "admin/manage_question";
	}

	@ResponseBody
	@PostMapping("delete_question")
	public ResponseEntity<String> deleteUser(@RequestParam String questionId) {
		Optional<QuestionModel> questionOp = questionService.finById(questionId);
		if (!questionOp.isPresent()) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		questionOp.get().setDeleted(true);
		questionService.save(questionOp.get());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}

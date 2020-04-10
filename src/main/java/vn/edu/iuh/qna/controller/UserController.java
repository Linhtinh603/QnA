package vn.edu.iuh.qna.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.edu.iuh.qna.dto.UserDetailRequestDto;
import vn.edu.iuh.qna.entity.CategoryModel;
import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.entity.UserModel;
import vn.edu.iuh.qna.service.CategoryService;
import vn.edu.iuh.qna.service.QuestionService;
import vn.edu.iuh.qna.service.UserService;
import vn.edu.iuh.qna.utils.EncrytedPasswordUtils;
import vn.edu.iuh.qna.utils.StringUtils;

@Secured("ROLE_USER")
@Controller
public class UserController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;

//	UserModel user=new UserModel();
//	user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword("123"));
//	user.setName("Huỳnh Cao Hữu Linh");
//	user.setPosition("DEV 0");
//	user.setRole("ROLE_USER");
//	user.setStatus(true);
//	user.setUserName("LinhHCH");
//	this.userService.save(user);
//	
	@GetMapping("/")
	public String home(Model model) {
		List<CategoryModel> listCategory = categoryService.findAll();
//		listCategory.add(new CategoryModel("Khác"));
		model.addAttribute("categories", listCategory);
		return "user/home";
	}

	@GetMapping("/search")
	public String search(@RequestParam(required = false) String key, @RequestParam(required = false) String category) {
		return "user/home";
	}

	@GetMapping("/questions/new")
	public String createQuestion(Model model) {
		List<CategoryModel> listCategory = categoryService.findAll();
//		listCategory.add(new CategoryModel("Khác"));
		model.addAttribute("categories", listCategory);
		model.addAttribute("question", new QuestionModel());
		return "user/create_question";
	}

	@ResponseBody
	@PostMapping("/questions/new")
	public ResponseEntity<String> doCreateQuestion(@RequestBody QuestionModel question, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetailRequestDto)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		UserDetailRequestDto userDetail = (UserDetailRequestDto) principal;
		question.setAuthor(userDetail.getUser());
		question.setCreateTime(LocalDateTime.now());
		question.setStatus(true);
		question.setTitleNormalized(StringUtils.normalize(question.getTitle()));
		question.setUpdateTime(LocalDateTime.now());
		question.setView(0);
		questionService.save(question);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@GetMapping("/questions/{id}")
	public String viewQuestion() {
		return "user/view_question";
	}

	@GetMapping("/questions/edit/{id}")
	public String editQuestion(Model model, @PathVariable String id) {
		if ("".equals(id)) {
			return "redirect:/";
		}
		Optional<QuestionModel> question = questionService.finById(id);
		if (question.isEmpty() || question.get().isStatus() == false) {
			return "redirect:/";
		}
		List<CategoryModel> listCategory = categoryService.findAll();
//		listCategory.add(new CategoryModel("Khác"));
		model.addAttribute("categories", listCategory);
		model.addAttribute("question", question.get());
		return "user/edit_question";
	}

	@ResponseBody
	@PostMapping("/questions/edit")
	@Transactional
	public ResponseEntity<String> doEditQuestion(@RequestBody QuestionModel question, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (question == null || question.getId() == null || question.getAuthor() == null
				|| question.getAuthor().getId() == null || !(principal instanceof UserDetailRequestDto)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		UserDetailRequestDto userDetail = (UserDetailRequestDto) principal;
		if (!question.getAuthor().getId().equals(userDetail.getUser().getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<QuestionModel> originQuestion = questionService.finById(question.getId());
		if (originQuestion.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		originQuestion.get().setTitleNormalized(StringUtils.normalize(question.getTitle()));
		originQuestion.get().setUpdateTime(LocalDateTime.now());
		originQuestion.get().setCategory(question.getCategory());
		originQuestion.get().setContent(question.getContent());
		questionService.save(originQuestion.get());
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@GetMapping({ "/my_profile", "/my_profile/posted_questions" })
	public String postedQuestion() {
		return "user/posted_questions";
	}

	@GetMapping("/my_profile/posted_questions")
	public String starQuestion() {
		return "user/star_questions";
	}

}

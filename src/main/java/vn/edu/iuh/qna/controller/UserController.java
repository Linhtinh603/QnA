package vn.edu.iuh.qna.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.edu.iuh.qna.dto.UserDetailReqDto;
import vn.edu.iuh.qna.entity.AnswerModel;
import vn.edu.iuh.qna.entity.CategoryModel;
import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.service.CategoryService;
import vn.edu.iuh.qna.service.QuestionService;
import vn.edu.iuh.qna.service.ReportService;
import vn.edu.iuh.qna.service.ReportService.CountReportDto;
import vn.edu.iuh.qna.utils.StringUtils;

@Secured("ROLE_USER")
@Controller
public class UserController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private ReportService reportService;

	@GetMapping("/")
	public String home(Model model, @RequestParam(required = false, name = "category") String categoryId,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int size) {
		List<CategoryModel> listCategory = categoryService.findAll();
		model.addAttribute("categories", listCategory);
		Optional<CategoryModel> categoryOptional = Optional.empty();
		if (categoryId != null) {
			categoryOptional = listCategory.stream().filter(c -> c.getId().equals(categoryId)).findFirst();
		}
		Page<QuestionModel> listQuestion = null;
		if (categoryOptional.isPresent()) {
			model.addAttribute("categoryId", categoryOptional.get().getId());
			listQuestion = questionService.findByCategory(categoryOptional.get(), PageRequest.of(page - 1, size));
		} else {
			listQuestion = questionService.findAll(PageRequest.of(page - 1, size));
		}
		model.addAttribute("questions", listQuestion);
		return "user/home";
	}

	@GetMapping("/search")
	public String search(Model model, @RequestParam(required = false, name = "key") String key,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "2") int size) {
		if (key == null || key.trim().equals("")) {
			return "redirect:/";
		}
		model.addAttribute("key", key);
		List<CategoryModel> listCategory = categoryService.findAll();
		model.addAttribute("categories", listCategory);
		Page<QuestionModel> listQuestion = questionService.findByTitleNormalizedContaining(StringUtils.normalize(key),
				PageRequest.of(page - 1, size));
		model.addAttribute("questions", listQuestion);
		return "user/search";
	}

	@GetMapping("/questions/new")
	public String createQuestion(Model model) {
		List<CategoryModel> listCategory = categoryService.findAll();
		model.addAttribute("categories", listCategory);
		model.addAttribute("question", new QuestionModel());
		return "user/create_question";
	}

	@ResponseBody
	@PostMapping("/questions/new")
	public ResponseEntity<String> doCreateQuestion(@RequestBody QuestionModel question, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetailReqDto)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		UserDetailReqDto userDetail = (UserDetailReqDto) principal;
		question.setAuthor(userDetail.getUser());
		question.setCreateTime(new Date());
		question.setStatus(true);
		question.setTitleNormalized(StringUtils.normalize(question.getTitle()));
		question.setFollower(0);
		question.setAnswers(new ArrayList<>());
		question.setView(0);
		question.setRightAnswerId("");
		questionService.save(question);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@GetMapping("/questions/{id}")
	public String viewQuestion(Model model, @PathVariable String id, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetailReqDto)) {
			return "404Page";
		}
		UserDetailReqDto userDetail = (UserDetailReqDto) principal;
		model.addAttribute("userId", userDetail.getUser().getId());

		List<CategoryModel> listCategory = categoryService.findAll();
		model.addAttribute("categories", listCategory);
		Optional<QuestionModel> question = questionService.finById(id);
		if (question.get().isStatus() == false) {
			return "404Page";
		}
		model.addAttribute("question", question.get());
		if (question.get().getAuthor().getUserName().equals(userDetail.getUsername())) {
			model.addAttribute("answer", new AnswerModel());
			return "user/view_question";
		}
		question.get().setView(question.get().getView() + 1);
		questionService.save(question.get());
		model.addAttribute("answer", new AnswerModel());
		return "user/reply_question";
	}

	@GetMapping("/questions/edit/{id}")
	public String editQuestion(Model model, @PathVariable String id) {
		if ("".equals(id)) {
			return "redirect:/";
		}
		Optional<QuestionModel> question = questionService.finById(id);

		if (!question.isPresent() || question.get().isStatus() == false) {
			return "redirect:/";
		}
		List<CategoryModel> listCategory = categoryService.findAll();
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
				|| question.getAuthor().getId() == null || !(principal instanceof UserDetailReqDto)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		UserDetailReqDto userDetail = (UserDetailReqDto) principal;
		if (!question.getAuthor().getId().equals(userDetail.getUser().getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<QuestionModel> originQuestion = questionService.finById(question.getId());
		if (!originQuestion.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		originQuestion.get().setTitleNormalized(StringUtils.normalize(question.getTitle()));
		originQuestion.get().setUpdateTime(new Date());
		originQuestion.get().setCategory(question.getCategory());
		originQuestion.get().setContent(question.getContent());
		questionService.save(originQuestion.get());
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@GetMapping({ "/my_profile", "/my_profile/posted_questions" })
	public String postedQuestion() {
		return "user/posted_questions";
	}

	@GetMapping("/my_profile/star_questions")
	public String starQuestion() {

		return "user/star_questions";
	}

	@GetMapping("/my_profile/account")
	public String account(Model model, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		UserDetailReqDto userDetail = (UserDetailReqDto) principal;
		model.addAttribute("user", userDetail.getUser());
		return "user/account";
	}

	@GetMapping("/my_profile/statistics")
	public String statistics(Model model, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		UserDetailReqDto userDetail = (UserDetailReqDto) principal;

		Calendar cal = Calendar.getInstance();
		Date toDate = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date fromDate = cal.getTime();

		Map<String, List<CountReportDto>> report = reportService.queryUserReport(userDetail.getUser(), fromDate,
				toDate);
		model.addAttribute("report", report);
		model.addAttribute("from", fromDate);
		model.addAttribute("to", toDate);
		return "user/statistics";
	}

}

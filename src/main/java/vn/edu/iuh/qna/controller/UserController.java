package vn.edu.iuh.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
	@GetMapping("/")
	public String home() {
		return "user/home";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam String key,@RequestParam String category) {
		return "user/search";
	}
	
	@GetMapping("/questions/new")
	public String createQuestion() {
		return "user/create_question";
	}
	
	@PostMapping("/questions/new")
	public String doCreateQuestion() {
		return "redirect:/user/home";
	}
	
	@GetMapping("/questions/{id}")
	public String viewQuestion() {
		return "user/view_question";
	}
	
	@GetMapping("/questions/edit")
	public String editQuestion() {
		return "user/edit_question";
	}
	
	@PostMapping("/questions/edit")
	public String doEditQuestion() {
		return "redirect:/user/home";
	}
	
	@GetMapping({"/my_profile","/my_profile/posted_questions"})
	public String postedQuestion() {
		return "user/posted_questions";
	}
	
	@GetMapping("/my_profile/posted_questions")
	public String starQuestion() {
		return "user/star_questions";
	}
	
}

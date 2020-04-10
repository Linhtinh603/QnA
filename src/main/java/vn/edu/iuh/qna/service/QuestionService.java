package vn.edu.iuh.qna.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.QuestionModel;

@Service
public interface QuestionService {
	void save(QuestionModel question);
	Optional<QuestionModel> finById(String id);
}

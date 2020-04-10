package vn.edu.iuh.qna.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.repository.QuestionRepository;
import vn.edu.iuh.qna.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	@Override
	public void save(QuestionModel question) {
		questionRepository.save(question);
	}
	@Override
	public Optional<QuestionModel> finById(String id) {
		return questionRepository.findById(id);
	}

}

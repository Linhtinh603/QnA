package vn.edu.iuh.qna.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.CategoryModel;
import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.entity.UserModel;
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

	@Override
	public Page<QuestionModel> findAll(Pageable pageable) {
		return questionRepository.findAll(pageable);
	}

	@Override
	public List<QuestionModel> findAll() {
		return questionRepository.findAll();
	}

	@Override
	public Page<QuestionModel> findByCategory(CategoryModel category, Pageable pageable) {
		return questionRepository.findByCategory(category, pageable);
	}

	@Override
	public Page<QuestionModel> findByTitleNormalizedContaining(String titleNormalized, Pageable pageable) {
		return questionRepository.findByTitleNormalizedContaining(titleNormalized, pageable);
	}

	@Override
	public Page<QuestionModel> findByAuthor(UserModel author, Pageable pageable) {
		return questionRepository.findByAuthorAndIsDeletedFalse(author, pageable);
	}
	
	@Override
	public void delete(String id) {
		questionRepository.deleteById(id);
	}
}

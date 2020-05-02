package vn.edu.iuh.qna.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.CategoryModel;
import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.entity.UserModel;

@Service
public interface QuestionService {
	void save(QuestionModel question);
	Optional<QuestionModel> finById(String id);
	Page<QuestionModel> findAll(Pageable pageable);
	List<QuestionModel> findAll();
	Page<QuestionModel> findByCategory(CategoryModel category,Pageable pageable);
	Page<QuestionModel> findByTitleNormalizedContaining(String titleNormalized,Pageable pageable);
	Page<QuestionModel> findByAuthor(UserModel author, Pageable pageable);
	void delete(String id);
}

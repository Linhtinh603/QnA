package vn.edu.iuh.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import vn.edu.iuh.qna.entity.CategoryModel;
import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.entity.UserModel;

@Repository
public interface QuestionRepository extends MongoRepository<QuestionModel, String> {
	Page<QuestionModel> findByCategory(CategoryModel category,Pageable pageable);
	Page<QuestionModel> findByTitleNormalizedContaining(String titleNormalized,Pageable pageable);
	Page<QuestionModel> findByAuthorAndIsDeletedFalse(UserModel author, Pageable pageable);
}

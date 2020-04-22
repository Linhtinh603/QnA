package vn.edu.iuh.qna.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import vn.edu.iuh.qna.entity.QuestionModel;

@Repository
public interface QuestionRepository extends MongoRepository<QuestionModel, String> {

}

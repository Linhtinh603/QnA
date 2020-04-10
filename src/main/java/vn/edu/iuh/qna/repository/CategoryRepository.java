package vn.edu.iuh.qna.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import vn.edu.iuh.qna.entity.CategoryModel;

@Repository
public interface CategoryRepository  extends MongoRepository<CategoryModel, String> {

}

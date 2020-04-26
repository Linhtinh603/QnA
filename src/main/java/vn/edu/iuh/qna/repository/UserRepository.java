package vn.edu.iuh.qna.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import vn.edu.iuh.qna.entity.UserModel;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
	Optional<UserModel> findByUserName(String userName);

	public List<UserModel> findByRole(String role);
}

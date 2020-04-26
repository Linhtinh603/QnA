package vn.edu.iuh.qna.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.UserModel;

@Service
public interface UserService {
	void save(UserModel user);
	
	Optional<UserModel> findById(String id);
	
	Optional<UserModel> findByUserName(String userName);

	List<UserModel> findByRole(String role);
}

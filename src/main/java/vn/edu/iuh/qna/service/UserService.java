package vn.edu.iuh.qna.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.UserModel;

@Service
public interface UserService {
	void save(UserModel user);
	Optional<UserModel> findByUserName(String userName);
}

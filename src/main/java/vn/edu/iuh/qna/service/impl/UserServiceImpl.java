package vn.edu.iuh.qna.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.UserModel;
import vn.edu.iuh.qna.repository.UserRepository;
import vn.edu.iuh.qna.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<UserModel> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<UserModel> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public void save(UserModel user) {
		userRepository.save(user);
	}

	@Override
	public List<UserModel> findByRole(String role) {
		return userRepository.findByRole(role);
	}
}

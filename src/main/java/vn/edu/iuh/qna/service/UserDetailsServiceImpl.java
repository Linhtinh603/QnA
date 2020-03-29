package vn.edu.iuh.qna.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.dto.UserDetailRequest;
import vn.edu.iuh.qna.entity.UserModel;
import vn.edu.iuh.qna.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<UserModel> userOptional = userRepository.findByUserName(userName);
		return userOptional.map(user->new UserDetailRequest(user))
				.orElseThrow(() -> new UsernameNotFoundException("User " + userName + " was not found in the database"));
	}
}

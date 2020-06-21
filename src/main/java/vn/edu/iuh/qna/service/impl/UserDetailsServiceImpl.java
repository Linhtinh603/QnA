package vn.edu.iuh.qna.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.dto.UserDetailReqDto;
import vn.edu.iuh.qna.entity.UserModel;
import vn.edu.iuh.qna.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<UserModel> userOptional = userService.findByUserName(userName);
		return userOptional.map(UserDetailReqDto::new)
				.orElseThrow(() -> new UsernameNotFoundException("User " + userName + " was not found in the database"));
	}
}

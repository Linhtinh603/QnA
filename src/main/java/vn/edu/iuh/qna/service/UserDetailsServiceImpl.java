package vn.edu.iuh.qna.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.AppUser;
import vn.edu.iuh.qna.repository.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AppUserRepository appUserDAO;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<AppUser> appUser = this.appUserDAO.findByUserName(userName);
		AppUser user = appUser.orElseThrow(
				() -> new UsernameNotFoundException("User " + userName + " was not found in the database"));
		
		System.out.println("Found User: " + appUser);
		List<GrantedAuthority> grantList = user.getRoles().stream()
				.map(appRole -> new SimpleGrantedAuthority(appRole.getRoleName())).collect(Collectors.toList());
		UserDetails userDetails = (UserDetails) new User(user.getUserName(), //
				user.getEncrytedPassword(), grantList);

		return userDetails;
	}
}

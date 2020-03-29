package vn.edu.iuh.qna.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.qna.entity.UserModel;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailRequest implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserModel user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantList=Arrays.asList(new SimpleGrantedAuthority(user.getRole().getRoleName()));
		return grantList;
	}

	@Override
	public String getPassword() {
		return user.getEncrytedPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}

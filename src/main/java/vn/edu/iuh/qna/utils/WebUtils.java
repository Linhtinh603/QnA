package vn.edu.iuh.qna.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.experimental.UtilityClass;
import vn.edu.iuh.qna.dto.UserDetailRequestDto;

@UtilityClass
public class WebUtils {

	public String toString(UserDetailRequestDto user) {
		StringBuilder sb = new StringBuilder();

		sb.append("UserName:").append(user.getUsername());

		Collection<GrantedAuthority> authorities = user.getAuthorities();
		if (authorities != null && !authorities.isEmpty()) {
			sb.append(" (");
			boolean first = true;
			for (GrantedAuthority a : authorities) {
				if (first) {
					sb.append(a.getAuthority());
					first = false;
				} else {
					sb.append(", ").append(a.getAuthority());
				}
			}
			sb.append(")");
		}
		return sb.toString();
	}

}
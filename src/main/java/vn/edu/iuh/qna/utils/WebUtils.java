package vn.edu.iuh.qna.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WebUtils {

	public String toString(UserDetails user) {
		StringBuilder sb = new StringBuilder();

		sb.append("UserName:").append(user.getUsername());

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
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
package vn.edu.iuh.qna.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document("AppUser")
public class AppUser {
	@Id
	private String userId;

	private String userName;

	private String encrytedPassword;

	private boolean enabled;
	
	private List<AppRole> roles;
}
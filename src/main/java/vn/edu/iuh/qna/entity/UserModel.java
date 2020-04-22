package vn.edu.iuh.qna.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = { "id" })
@Document("users")
public class UserModel {
	@Id
	private String id;
	private String fullName;
	private LocalDate birthday;
	private String jobPosition;
	@DBRef
	private List<AnswerModel> followingQuestion;
	private boolean status;
	private String role;
	private String encrytedPassword;
	private String userName;
	@Field("create_time")
	private LocalDate createTime;

}
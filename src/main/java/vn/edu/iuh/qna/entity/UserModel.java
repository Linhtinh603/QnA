package vn.edu.iuh.qna.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
	private String name;
	private String position;
	@DBRef
	private List<AnswerModel> markedQuestion;
	private boolean status;
	private RoleModel role;
	private String encrytedPassword;
	private String userName;

}
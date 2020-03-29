package vn.edu.iuh.qna.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
@EqualsAndHashCode(of= {"id"})
@Document("questions")
public class QuestionModel {
	@Id
	private String id;
	private String title;
	private String content;
	@Indexed
	@DBRef
	private UserModel author;
	private int view;
	private boolean status;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private List<AnswerModel> answers;
	@DBRef
	private Category category;
}

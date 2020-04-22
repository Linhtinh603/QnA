package vn.edu.iuh.qna.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

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
	@Indexed
	private String titleNormalized;
	private String content;
	@Indexed
	@DBRef
	private UserModel author;
	private int view;
	private boolean status;
	private boolean resolved;
	private long follower;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date createTime;
	private Date updateTime;
	private List<AnswerModel> answers;
	@DBRef
	private CategoryModel category;
}

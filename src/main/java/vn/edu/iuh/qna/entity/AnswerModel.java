package vn.edu.iuh.qna.entity;
import java.sql.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
@EqualsAndHashCode(of= {"id"})
public class AnswerModel {
	private String id;
	private String content;
	@Indexed
	@DBRef
	private UserModel author;
	private boolean approval;
	@Field("create_time")
	private Date createTime;
	
}

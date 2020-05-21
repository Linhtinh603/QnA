package vn.edu.iuh.qna.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
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
@Document("categories")
public class CategoryModel {
	@Id
	private String id;
	@NotBlank(message = "Tên thể loại bị trống")
	@Size(min = 5,max = 30,message = "Tên thể loại nằm trong khoảng {min} đến {max} kí tự")
	private String name;
	public CategoryModel(String name) {
		super();
		this.name = name;
	}
	
}

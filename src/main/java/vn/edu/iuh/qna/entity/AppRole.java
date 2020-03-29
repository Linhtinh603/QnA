package vn.edu.iuh.qna.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document("AppRole")
public class AppRole {
     
    @Id
    private String roleId;
 
    private String roleName;
}

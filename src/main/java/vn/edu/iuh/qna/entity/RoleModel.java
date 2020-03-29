package vn.edu.iuh.qna.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document("roles")
public class RoleModel {
     
    @Id
    private String id;
    private String roleName;
}

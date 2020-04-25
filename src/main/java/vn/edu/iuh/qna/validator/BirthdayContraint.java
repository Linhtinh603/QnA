package vn.edu.iuh.qna.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = BirthdayValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthdayContraint {
	String message() default "Ngày sinh phải đủ 18 tuổi";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

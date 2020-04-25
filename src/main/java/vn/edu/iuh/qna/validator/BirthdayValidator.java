package vn.edu.iuh.qna.validator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BirthdayValidator implements ConstraintValidator<BirthdayContraint, Date> {

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		if(value==null) {
			return true;
		}
		LocalDate date = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate before18year= LocalDate.now().minusYears(18);
		return !date.isAfter(before18year);
	}

}

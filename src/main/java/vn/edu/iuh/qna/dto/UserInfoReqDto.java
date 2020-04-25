package vn.edu.iuh.qna.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import vn.edu.iuh.qna.validator.BirthdayContraint;

@Data
public class UserInfoReqDto {
	@NotBlank(message="Họ và tên bị trống")
	private String fullName;
	@NotNull(message="Ngày sinh bị trống")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message = "Ngày sinh không được quá ngày hiện tại")
	@BirthdayContraint
	private Date birthday;
	@NotBlank(message="Vị trí công việc bị trống")
	private String jobPosition;
	@NotBlank(message="Mật khẩu bị trống")
	private String password;
	@NotNull(message = "Xác nhận mật khẩu không khớp")
	private String confirmPassword;
	@NotBlank(message="Tên tài khoản bị trống")
	private String userName;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		checkPassword();
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
		checkPassword();
	}

	private void checkPassword() {
		if (this.password == null || this.confirmPassword == null) {
			return;
		} else if (!this.password.equals(confirmPassword)) {
			this.confirmPassword = null;
		}
	}
}

package vn.edu.iuh.qna.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EncrytedPasswordUtils {
	// Encryte Password with BCryptPasswordEncoder
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}

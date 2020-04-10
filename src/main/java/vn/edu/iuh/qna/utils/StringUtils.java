package vn.edu.iuh.qna.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
	public String normalize(String str) {
		String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
		// loại bỏ các ký tự unicode
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("đ", "d");
	}
}

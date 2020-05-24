package vn.edu.iuh.qna.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.dto.PieDto;
import vn.edu.iuh.qna.entity.UserModel;

@Service
public interface ReportService {
	List<PieDto> reportByQuestionHaveAnswer(Date from, Date to);
	List<PieDto> reportByCategory(Date from, Date to);
	List<PieDto> reportByStatus(Date from, Date to);
	List<PieDto> reportByUserAndQuestionHaveAnswer(Date from, Date to, UserModel user);
	List<PieDto> reportByUserAndCategory(Date from, Date to, UserModel user);
	List<PieDto> reportByUserQuestionDeleteByAdmin();
}

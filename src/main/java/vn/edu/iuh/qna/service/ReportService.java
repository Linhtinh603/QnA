package vn.edu.iuh.qna.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.Data;

@Service
public interface ReportService {
	List<CountReportDto> queryQuestionReport(Date from, Date to);
	List<CountReportDto> queryAnswerReport(Date from, Date to);
	Map<String, List<CountReportDto>> queryAdminReport(Date from,Date to);

	@Data
	public static class CountReportDto {
		private long number;
		private String date;
	}
}

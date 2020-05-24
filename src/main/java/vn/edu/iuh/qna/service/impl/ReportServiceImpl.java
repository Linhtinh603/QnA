package vn.edu.iuh.qna.service.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.dto.PieDto;
import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.entity.UserModel;
import vn.edu.iuh.qna.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private MongoTemplate mongoTemplate;

	private List<PieDto> postProcessPieDto(List<PieDto> report,final String TRUE_LABEL,final String FALSE_LABEL){
		report.forEach(pie -> {
			if (pie.getLabel().equals("true")) {
				pie.setLabel(TRUE_LABEL);
			} else {
				pie.setLabel(FALSE_LABEL);
			}
		});
		if (report.size() < 2) {
			report = new ArrayList<>(report);
			if (report.size() == 1) {
				if (report.get(0).getLabel().equals(TRUE_LABEL)) {
					report.add(new PieDto(FALSE_LABEL, 0));
				} else {
					report.add(0, new PieDto(TRUE_LABEL, 0));
				}
			} else {
				report.add(new PieDto(TRUE_LABEL, 0));
				report.add(new PieDto(FALSE_LABEL, 0));
			}
		}
		return report;
	}
	@Override
	public List<PieDto> reportByQuestionHaveAnswer(Date from, Date to) {
		Criteria c = new Criteria().andOperator(Criteria.where("createTime").gte(from),
				Criteria.where("createTime").lte(to));
		Aggregation aggregation = newAggregation(match(c),
				project("id").and(ArrayOperators.arrayOf("answers").length()).as("length"),
				project("id").and("length").ne(0).as("empty"), group("empty").count().as("data"),
				project("data").and("_id").as("label"));
		AggregationResults<PieDto> result = mongoTemplate.aggregate(aggregation, QuestionModel.class, PieDto.class);
		List<PieDto> report = result.getMappedResults();
		final String TRUE_LABEL = "Có câu trả lời";
		final String FALSE_LABEL = "Chưa có câu trả lời";
		return postProcessPieDto(report, TRUE_LABEL, FALSE_LABEL);
	}

	@Override
	public List<PieDto> reportByCategory(Date from, Date to) {
		Criteria c = new Criteria().andOperator(Criteria.where("createTime").gte(from),
				Criteria.where("createTime").lte(to));
		Aggregation aggregation = newAggregation(match(c),
				project("id").and(ArrayOperators.arrayOf(ObjectToArray.toArray("$category")).elementAt(1))
						.as("category"),
				group("category.v").count().as("data"), lookup("categories", "_id", "_id", "category"),
				project("data").and("category.name").as("label"));
		AggregationResults<PieDto> result = mongoTemplate.aggregate(aggregation, QuestionModel.class, PieDto.class);
		List<PieDto> report = result.getMappedResults();
		return report;
	}

	@Override
	public List<PieDto> reportByStatus(Date from, Date to) {
		Aggregation aggregation = newAggregation(
				group("status").count().as("data")
				,project("data").and("_id").as("label")
				);
		AggregationResults<PieDto> result = mongoTemplate.aggregate(aggregation, UserModel.class, PieDto.class);
		List<PieDto> report = result.getMappedResults();
		final String TRUE_LABEL = "Tài khoản đang hoạt động";
		final String FALSE_LABEL = "Tài khoản bị vô hiệu hóa";
		return postProcessPieDto(report, TRUE_LABEL, FALSE_LABEL);
	}

	@Override
	public List<PieDto> reportByUserAndQuestionHaveAnswer(Date from, Date to, UserModel user) {
		Criteria c = new Criteria().andOperator(Criteria.where("createTime").gte(from),
				Criteria.where("createTime").lte(to),Criteria.where("author").is(user));
		Aggregation aggregation = newAggregation(match(c),
				project("id").and(ArrayOperators.arrayOf("answers").length()).as("length"),
				project("id").and("length").ne(0).as("empty"), group("empty").count().as("data"),
				project("data").and("_id").as("label"));
		AggregationResults<PieDto> result = mongoTemplate.aggregate(aggregation, QuestionModel.class, PieDto.class);
		List<PieDto> report = result.getMappedResults();
		final String TRUE_LABEL = "Có câu trả lời";
		final String FALSE_LABEL = "Chưa có câu trả lời";
		return postProcessPieDto(report, TRUE_LABEL, FALSE_LABEL);
	}

	@Override
	public List<PieDto> reportByUserAndCategory(Date from, Date to, UserModel user) {
		Criteria c = new Criteria().andOperator(Criteria.where("createTime").gte(from),
				Criteria.where("createTime").lte(to),Criteria.where("author").is(user));
		Aggregation aggregation = newAggregation(match(c),
				project("id").and(ArrayOperators.arrayOf(ObjectToArray.toArray("$category")).elementAt(1))
						.as("category"),
				group("category.v").count().as("data"), lookup("categories", "_id", "_id", "category"),
				project("data").and("category.name").as("label"));
		AggregationResults<PieDto> result = mongoTemplate.aggregate(aggregation, QuestionModel.class, PieDto.class);
		List<PieDto> report = result.getMappedResults();
		return report;
	}

	@Override
	public List<PieDto> reportByUserQuestionDeleteByAdmin() {
		// TODO Auto-generated method stub
		return null;
	}

}

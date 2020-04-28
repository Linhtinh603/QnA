package vn.edu.iuh.qna.service.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<CountReportDto> queryAnswerReport(Date from, Date to) {
		Criteria c = new Criteria().andOperator(Criteria.where("createTime").gte(from),
				Criteria.where("createTime").lte(to));
		Aggregation aggregation = newAggregation(unwind("answers"), match(c),
				project("_id").and(DateOperators.DateToString.dateOf("answers.createTime").toString("%d-%m-%Y")).as("date"),
				group("date").count().as("number"), project("number").and("_id").as("date").andExclude("_id"));
		AggregationResults<CountReportDto> result = mongoTemplate.aggregate(aggregation, "questions",
				CountReportDto.class);
		return result.getMappedResults();
	}

	@Override
	public List<CountReportDto> queryQuestionReport(Date from, Date to) {
		Criteria c = new Criteria().andOperator(Criteria.where("createTime").gte(from),
				Criteria.where("createTime").lte(to));
		Aggregation aggregation = newAggregation(match(c),
				project("id").and(DateOperators.DateToString.dateOf("createTime").toString("%d-%m-%Y")).as("date"),
				group("date").count().as("number"), project("number").and("_id").as("date").andExclude("_id"));
		AggregationResults<CountReportDto> result = mongoTemplate.aggregate(aggregation, QuestionModel.class,
				CountReportDto.class);
		return result.getMappedResults();
	}

	@Override
	public Map<String, List<CountReportDto>> queryAdminReport(Date from, Date to) {
		Map<String, List<CountReportDto>> report = new HashMap<>();
		report.put("question", queryQuestionReport(from, to));
		report.put("answer", queryAnswerReport(from, to));
		return report;
	}

}

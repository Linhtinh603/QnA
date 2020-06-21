package vn.edu.iuh.qna.controller;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import vn.edu.iuh.qna.dto.UserDetailReqDto;
import vn.edu.iuh.qna.entity.AnswerModel;
import vn.edu.iuh.qna.entity.QuestionModel;
import vn.edu.iuh.qna.service.QuestionService;

@Slf4j
@Controller
public class WebSocketController {

	@Autowired
	private QuestionService questionService;
	@MessageMapping("reply/{questionId}")
	@SendTo("/topic/answer/{questionId}")
	public AnswerModel replyQuestion(@Payload AnswerModel answer, Authentication authentication, @DestinationVariable  String questionId) {
		Object principal = authentication.getPrincipal();
		if (answer == null || answer.getContent().length() < 10 || !(principal instanceof UserDetailReqDto)) {
			return null;
		}
		UserDetailReqDto userDetail = (UserDetailReqDto) principal;
		UUID uuid = UUID.randomUUID();
		answer.setId(uuid.toString());
		answer.setAuthor(userDetail.getUser());
		answer.setCreateTime(new Date());
		Optional<QuestionModel> question = questionService.finById(questionId);
		question.get().getAnswers().add(answer);
		try {
		questionService.save(question.get());
		}catch(Exception e) {
			log.debug("============== Loi save");
		}

        return answer;
    }
}

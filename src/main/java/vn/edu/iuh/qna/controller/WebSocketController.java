package vn.edu.iuh.qna.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import vn.edu.iuh.qna.entity.AnswerModel;

@Controller
public class WebSocketController {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
	@MessageMapping("reply")
	@SendTo("/topic/answer")
	public AnswerModel answer(@Payload AnswerModel answer) {
		logger.info("logg webcontroller {}",answer.getContent());
        return answer;
    }
}

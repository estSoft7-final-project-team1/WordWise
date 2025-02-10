package est.wordwise.domain.conversation.config;
import est.wordwise.domain.conversation.service.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private final ConversationService conversationService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("입력 받은 메시지 : {}", payload);
        String response = conversationService.getContentFromApiWithQuery(payload);
        log.info("답변 : {}", response);
        session.sendMessage( new TextMessage(response));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 프롬프트 입력
        String promptResponse = conversationService.getContentFromApiWithPrompt();
        log.info("프롬프트 답변 : {}", promptResponse);
        session.sendMessage(new TextMessage("안녕하세요 회화봇입니다. \n당신의 수준을 상 중 하로 답변하세요. \n해당 수준에 맞춰 회화난이도를 조절합니다."));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}

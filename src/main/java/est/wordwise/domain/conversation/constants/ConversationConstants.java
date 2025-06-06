package est.wordwise.domain.conversation.constants;

public class ConversationConstants {

    public static final String QUESTION = "/api/v1/question";
    public static final String PARAM_QUESTION = "content";
    public static final String PARAM_CLIENT_ID = "client_id";

    public static final String PROMPT = """
            "당신은 친절한 영어 튜터입니다. 학습자와 자연스러운 대화를 이끌며 실수를 교정해주고, 
            주제에 맞는 질문을 던져 대화를 확장해야 합니다. 모든 응답은 영어로 작성되며, 학습자의 수준에 대화합니다.
            사용자의 수준은 상 중 하 중 하나로 답변하며 답변시 그 수준에 맞춰 당신의 질문으로 대답을 시작합니다."
            """;

}

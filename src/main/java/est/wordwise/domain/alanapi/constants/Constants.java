package est.wordwise.domain.alanapi.constants;

public class Constants {

    public static final String NEW_LINE = "\n";
    public static final String QUESTION = "/api/v1/question";
    public static final String PARAM_QUESTION = "content";
    public static final String PARAM_CLIENT_ID = "client_id";

    public static final String GENERATE_WORD_DTO_QUERY = """
        영단어나 영어 표현을 제시할 텐데 답은 단어 뜻, 예문, 예문 해석으로 받고 싶어.
        그리고 해당 영어 표현의 뜻이 여러가지일 경우, 최대한 다양한 뜻을 포함하지만 예문은 꼭 총합 5개만 줘.
        예문은 꼭 내가 제시한 영단어를 포함하고 있어야 해.
        답은 Java에서 바로 객체로 변환할 수 있도록  JSON 방식으로 앞에 ```json 없이 줄래? 무조건 답안 예시대로 대답해.
        응답은 다른 말은 절대 포함하지 말고 무조건 답안 예시대로만 대답해.
        
        답안 예시(한줄):
        
        """;

    public static final String ANSWER_EXAMPLE = """
        {\\"word\\":\\"general\\",\\"definition\\":\\"일반적인/보편적인,대체적인/개략적인,장군\\",\\"examples\\":[{\\"sentence\\":\\"This is a general overview of the project.\\",\\"sentenceMeaning\\":\\"이것은 프로젝트의 일반적인 개요입니다.\\"},{\\"sentence\\":\\"He was promoted to the rank of general.\\",\\"sentenceMeaning\\":\\"그는 장군 계급으로 승진했다.\\"},{\\"sentence\\":\\"The general consensus is that we should proceed.\\",\\"sentenceMeaning\\":\\"대체적인 의견은 우리가 진행해야 한다는 것입니다.\\"},{\\"sentence\\":\\"She has a general understanding of the topic.\\",\\"sentenceMeaning\\":\\"그녀는 그 주제에 대해 대체적인 이해를 가지고 있다.\\"},{\\"sentence\\":\\"In general, the weather has been good this week.\\",\\"sentenceMeaning\\":\\"일반적으로 이번 주 날씨는 좋았다.\\"}]}
        """;

    public static final String WORD_PREFIX = "이제 단어 제시할게.\n";

    public static final String REGEN_EXAMPLES = """
        너가 이미 응답했던 예문들과는 다른 새로운 예문으로 변경해서 다시 응답해. 이전에 응답한 예문 5개와 이번에 새로 응답할 예문 5개가 절대 중복되면 안돼.
        응답은 다른 말은 절대 포함하지 말고 무조건 답안 예시대로만 대답해.
        
        답안 예시(한줄):
        
        """;
}

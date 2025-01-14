package est.wordwise.domain.word.service;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class WordServiceTest {

    @Autowired
    private WordService wordService;

    @Test
    void test() {
        String temp = "```json\n{\n  \"word\": \"general\",\n  \"meaning\": \"일반적인, 보편적인/대체적인, 개략적인/장군\",\n  \"examples\": [\n    {\n      \"sentence\": \"The general opinion is that the project will be successful.\",\n      \"translation\": \"일반적인 의견은 그 프로젝트가 성공할 것이라는 것이다.\"\n    },\n    {\n      \"sentence\": \"She has a general knowledge of many subjects.\",\n      \"translation\": \"그녀는 많은 주제에 대해 일반적인 지식을 가지고 있다.\"\n    },\n    {\n      \"sentence\": \"In general, the weather is good in this region.\",\n      \"translation\": \"일반적으로 이 지역의 날씨는 좋다.\"\n    },\n    {\n      \"sentence\": \"He gave a general description of the plan.\",\n      \"translation\": \"그는 그 계획에 대해 대체적인 설명을 했다.\"\n    },\n    {\n      \"sentence\": \"The general led his troops into battle.\",\n      \"translation\": \"장군은 그의 군대를 전투로 이끌었다.\"\n    }\n  ]\n}\n```";
        System.out.println(temp);
    }

    @Test
    @DisplayName("RestClient 간단한 요청-응답 테스트")
    void alanApiTest() {

        String question = "Yes라고 대답해줘";

        String responseContent = wordService.getContentFromApiWithQuestion(question);

        assertThat(responseContent).contains("Yes");
    }
}
package est.wordwise.domain.alanapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import est.wordwise.domain.alanapi.dto.ResponseContent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AlanApiServiceTest {

    @Autowired
    private AlanApiService alanApiService;

    @Test
    @DisplayName("JSON 객체 변환 테스트")
    void parseJsonToResponseContentTest() throws Exception {
        // given
        String jsonString = "{\"word\":\"consist\",\"definition\":\"구성되다, 이루어지다, 일치하다\",\"examples\":[{\"sentence\":\"The committee consists of ten members.\",\"sentenceMeaning\":\"위원회는 열 명의 구성원으로 이루어져 있다.\"},{\"sentence\":\"Her responsibilities consist of managing the team.\",\"sentenceMeaning\":\"그녀의 책임은 팀을 관리하는 것으로 구성된다.\"},{\"sentence\":\"The book consists of three main parts.\",\"sentenceMeaning\":\"그 책은 세 부분으로 구성되어 있다.\"},{\"sentence\":\"Success consists in hard work and determination.\",\"sentenceMeaning\":\"성공은 열심히 일하고 결단력에 달려 있다.\"},{\"sentence\":\"Their opinions consist with each other.\",\"sentenceMeaning\":\"그들의 의견은 서로 일치한다.\"}]}";

        // when
        ResponseContent responseContent = alanApiService.parseJsonToResponseContent(jsonString);

        // then
        assertThat(responseContent).isNotNull();
        assertThat(responseContent).isInstanceOf(ResponseContent.class);
        assertThat(responseContent.getWord()).isEqualTo("consist");
        log.info("responseContent = {}", responseContent);
    }

    @Test
    @DisplayName("Alan API 요청-응답 테스트")
    void alanApiTest() throws Exception {
        String query = "Yes 라고 대답해봐";

        String answer = alanApiService.getContentFromApiWithQuery(query);

        assertThat(answer).contains("Yes");
        log.info("answer = {}", answer);
    }
}
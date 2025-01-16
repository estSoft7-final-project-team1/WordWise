package est.wordwise.domain.personalexample.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.Word;
import est.wordwise.domain.alanapi.dto.ResponseContent;
import est.wordwise.domain.alanapi.service.AlanApiService;
import est.wordwise.domain.word.dto.WordDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
class PersonalExampleServiceTest {

    @Autowired
    private PersonalExampleService personalExampleService;

    @Autowired
    private AlanApiService alanApiService;

    private Member member;
    private Word word;
    private WordDto wordDto;

    @BeforeEach
    @Rollback
    void setUp() {
        String jsonString = "{\"word\":\"consist\",\"definition\":\"구성되다, 이루어지다, 일치하다\",\"examples\":[{\"sentence\":\"The committee consists of ten members.\",\"sentenceMeaning\":\"위원회는 열 명의 구성원으로 이루어져 있다.\"},{\"sentence\":\"Her responsibilities consist of managing the team.\",\"sentenceMeaning\":\"그녀의 책임은 팀을 관리하는 것으로 구성된다.\"},{\"sentence\":\"The book consists of three main parts.\",\"sentenceMeaning\":\"그 책은 세 부분으로 구성되어 있다.\"},{\"sentence\":\"Success consists in hard work and determination.\",\"sentenceMeaning\":\"성공은 열심히 일하고 결단력에 달려 있다.\"},{\"sentence\":\"Their opinions consist with each other.\",\"sentenceMeaning\":\"그들의 의견은 서로 일치한다.\"}]}";

        ResponseContent responseContent = alanApiService.parseJsonToResponseContent(jsonString);

        wordDto = WordDto.from(responseContent);
        member = Member.builder()
            .id(0L)
            .email("member1@email.com")
            .nickname("member1")
            .build();
    }

//    @Test
//    @DisplayName("personalExample create 테스트")
//    void personalExampleCreateTest() throws Exception {
//        PersonalExample personalExample = personalExampleService.createPersonalExample(member,
//            wordDto);
//    }
}
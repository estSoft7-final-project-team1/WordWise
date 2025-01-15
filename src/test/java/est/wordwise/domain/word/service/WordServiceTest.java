package est.wordwise.domain.word.service;

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
        String temp = "";
        System.out.println(temp);
    }

    @Test
    @DisplayName("RestClient 간단한 요청-응답 테스트")
    void alanApiTest() {

        String question = "Yes라고 대답해줘";
        
    }
}
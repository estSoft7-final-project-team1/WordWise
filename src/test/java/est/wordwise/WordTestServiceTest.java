package est.wordwise;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.WordBook;
import est.wordwise.common.repository.MemberRepository;
import est.wordwise.common.repository.PersonalExampleRepository;
import est.wordwise.common.repository.WordBookRepository;
import est.wordwise.domain.wordtest.dto.WordTestDto;
import est.wordwise.domain.wordtest.service.WordTestService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Slf4j
class WordTestServiceTest {

    @Test
    void contextLoads() {

        assertThat();
    }

}
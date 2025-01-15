package est.wordwise.domain.wordtest.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.WordBook;
import est.wordwise.common.repository.PersonalExampleRepository;
import est.wordwise.common.repository.WordBookRepository;
import est.wordwise.domain.wordtest.dto.AnswerDto;
import est.wordwise.domain.wordtest.dto.WordTestDto;
import est.wordwise.domain.wordtest.exception.SentenceNotFoundException;
import est.wordwise.domain.wordtest.exception.WordBookNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordTestService {
    private final WordBookRepository wordBookRepository;
    private final PersonalExampleRepository personalExampleRepository;
    private final Random random = new Random();

    //사용자가 저장한 단어 랜덤 뽑기
    public WordBook getRandomWordBook(Member member) {
        List<WordBook> wordList = wordBookRepository.findByMember(member);
        if (wordList.isEmpty()) {
            throw new WordBookNotFoundException("해당 회원이 저장한 단어가 없습니다.");
        }
        return wordList.get(random.nextInt(wordList.size()));
    }

    // 사용자가 저장한 예문 랜덤 뽑기
    public PersonalExample getRandomSentence(WordBook wordBook) {
        List<PersonalExample> sentenceList = personalExampleRepository.findByWordBook(wordBook);
        if (sentenceList.isEmpty()) {
            throw new SentenceNotFoundException("예문을 찾을 수 없습니다.");
        }
        return sentenceList.get(random.nextInt(sentenceList.size()));
    }

    //문제 생성, 정답단어-> 사용자의 정보를 기반으로 예문 찾기 -> 예문의 빈칸 파싱 -> 문제생성
    public WordTestDto generateWordTest(Member member) {
        WordBook randomWordBook = getRandomWordBook(member);
        PersonalExample randomSentence = getRandomSentence(randomWordBook);

        String answer = randomSentence.getWordBook().getWord().getWordText();
        String sentence = randomSentence.getExample().getSentence().toLowerCase()
                .replace(answer, "_______");
        String sentenceMeaning = randomSentence.getExample().getSentenceMeaning();
        log.info(sentence);
        return WordTestDto.of(answer, sentence, sentenceMeaning);
    }

    public boolean wordTest(AnswerDto answerDto) {
        //만약 정답과 내가 적은 답이 일치하면 true 아니면 false
        if (answerDto.getAnswer().equals(answerDto.getUserAnswer())) {
            return true;
        } else {
            return false;
        }
    }
}

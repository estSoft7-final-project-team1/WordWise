package est.wordwise.domain.wordtest.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.WordBook;
import est.wordwise.common.exception.WordBookNotFoundException;
import est.wordwise.common.repository.WordBookRepository;
import est.wordwise.domain.wordtest.dto.AnswerDto;
import est.wordwise.domain.wordtest.dto.WordTestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateWordTestService {
    private final WordBookRepository wordBookRepository;

    // 단어장 찾기
    public WordBook findWordBookById(Long exampleId) {
        return wordBookRepository.findById(exampleId).orElseThrow(
                ()->new WordBookNotFoundException("해당 단어를 찾을 수 없습니다")
        );
    }

    // 사용자가 저장한 단어 목록 뽑기
    public List<WordBook> findMyWordList(Member member) {
        List<WordBook> wordList = wordBookRepository.findByMember(member);
        if (wordList.isEmpty()) {
            throw new WordBookNotFoundException("해당 회원이 저장한 단어가 없습니다.");
        }
        return wordList;
    }

    // 사용자가 저장한 예문 목록 불러오기
    public List<PersonalExample> findMyExampleList(List<WordBook> wordList)  {
        List<PersonalExample> personalExampleList = new ArrayList<>();
        for (WordBook wordBook : wordList) {
            for (PersonalExample personalExample : wordBook.getPersonalExamples()) {
                personalExampleList.add(personalExample);
            }
        }
        return personalExampleList;
    }

    // 불러온 예문을 기반으로 문제 목록 생성
    public List<WordTestDto> createWordTestList (List<PersonalExample> personalExampleList) {
        return personalExampleList.stream()
                .map(this::createWordTestDto)
                .collect(Collectors.toList());
    }

    // 예문을 문제 형식으로 만들기
    public WordTestDto createWordTestDto(PersonalExample personal) {
        String answer = personal.getWordBook().getWord().getWordText().toLowerCase();
        String sentence = personal.getExample().getSentence().toLowerCase()
                .replace(answer, "_______");
        String sentenceMeaning = personal.getExample().getSentenceMeaning();
        return WordTestDto.of(answer, sentence, sentenceMeaning, personal.getWordBook().getId(), personal.getId());
    }

    // 컨트롤러에서 처리할 과정
    public List<WordTestDto> createWordTestForMember(Member member) {
        List<WordBook> wordList = findMyWordList(member);
        List<PersonalExample> personalExampleList = findMyExampleList(wordList);
        return createWordTestList(personalExampleList);
    }

}

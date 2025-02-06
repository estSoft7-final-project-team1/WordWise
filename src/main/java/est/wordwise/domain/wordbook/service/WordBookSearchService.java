package est.wordwise.domain.wordbook.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.Word;
import est.wordwise.common.entity.WordBook;
import est.wordwise.common.util.MemberService;
import est.wordwise.domain.word.service.WordService;
import est.wordwise.domain.wordbook.dto.WordBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordBookSearchService {

    private final WordService wordService;
    private final WordBookService wordBookService;
    private final MemberService memberService;

    // 내 단어장에서 단어로 검색
    public WordBookDto getWordBookByWordText(String wordText) {
        Member member = memberService.getCurrentMember();
        Word word = wordService.getWordByWordText(wordText);
        WordBook wordBook = wordBookService.getWordBookByMemberAndWord(member, word);

        if (wordBook == null) {
            return null;
        }

        return WordBookDto.from(wordBook);
    }
}

package est.wordwise.domain.wordtest.service;

import est.wordwise.common.entity.*;

public class TestFixture {
    // Member 생성
    public static Member createMember(Long id) {
        return Member.builder()
                .id(id)
                .email("test@test.com")
                .nickname("tester")
                .password("password")
                .provider(SocialType.GOOGLE)
                .build();
    }

    // Word 생성
    public static Word createWord(String wordText) {
        Word word = new Word();
        word.setWordText(wordText); // 엔티티에 Setter가 있다고 가정 (실제 프로젝트는 DTO 변환 사용)
        return word;
    }

    // WordBook 생성
    public static WordBook createWordBook(Member member, Word word) {
        return WordBook.of(member, word);
    }

    // Example 생성
    public static Example createExample(Word word, String sentence) {
        ExampleDto exampleDto = new ExampleDto(sentence, "의미");
        return Example.from(word, exampleDto);
    }

    // PersonalExample 생성
    public static PersonalExample createPersonalExample(WordBook wordBook, Example example) {
        return PersonalExample.of(wordBook, example);
    }
}

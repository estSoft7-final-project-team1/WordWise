package est.wordwise.wordBook.dto;


import est.wordwise.entity.Member;
import est.wordwise.entity.Word;
import est.wordwise.entity.WordBook;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 기본생성자의 접근제어를 protected로 설정해서 의미없는 객체 생성 체크

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordBookRequest {

    // 요청 dto, wordbook entity 생성하기 위한

    private Long id;

    @Builder
    public WordBookRequest(Long id) {
        this.id = id;
    }

    private Member member;

    private Word word;

    private int testCount;
    private int failCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean deleted;

    public WordBookRequest(Member member, Word word, int testCount, int failCount) {
        this.member = member;
        this.word = word;
        this.testCount = testCount;
        this.failCount = failCount;
    }


}
package est.wordwise.domain.wordbook.dto;


import est.wordwise.common.entity.WordBook;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WordBookResponse {
    // 응답 dto

    // 내 단어장 id
    private Long id;

    // 단어명, 단어뜻
    private String wordText;
    private String definition;

    // 테스트횟수, 틀린횟수, 생성일자
    private int testCount;
    private int failCount;
    private String createdAt;

    // @builder로 각 필드를 초기화 해주는게 좋음
    // 인스턴스 멤버 선언 순서에 영향을 받기에, 변수의 순서를 바꾸게되면 생성자 입력값 순서도 바뀌기에
    @Builder
    public WordBookResponse(Long id, String wordText, String definition, int testCount,
        int failCount, String createdAt) {
        this.id = id;
        this.wordText = wordText;
        this.definition = definition;
        this.testCount = testCount;
        this.failCount = failCount;
        this.createdAt = createdAt;
    }

    public static WordBookResponse fromEntity(WordBook wordBook) {

        // 충돌 막기 위해 날짜 포맷 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // LocalDateTime -> String으로
        String formattedDate = wordBook.getCreatedAt().format(formatter);

        return WordBookResponse.builder()
            .id(wordBook.getId())
            .wordText(wordBook.getWord().getWordText())
            .definition(wordBook.getWord().getDefinition())
            .testCount(wordBook.getTestCount())
            .failCount(wordBook.getFailCount())
            .createdAt(formattedDate)
            .build();
    }
    // 추 후 시간을 string으로 보내주지말고 UI에 맞게 반환하도록 추가 로직 구현,

}

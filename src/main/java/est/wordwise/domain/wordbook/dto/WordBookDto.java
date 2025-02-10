package est.wordwise.domain.wordbook.dto;

import est.wordwise.common.entity.WordBook;
import est.wordwise.domain.example.dto.ExampleDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordBookDto {

    private Long id;
    private Long memberId;
    private String memberEmail;
    private String wordText;
    private String definition;
    private List<ExampleDto> examples = new ArrayList<>();
    private int testCount;
    private int failCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static WordBookDto from(WordBook wordBook) {
        return WordBookDto.builder()
            .id(wordBook.getId())
            .memberId(wordBook.getMember().getId())
            .memberEmail(wordBook.getMember().getEmail())
            .wordText(wordBook.getWord().getWordText())
            .definition(wordBook.getWord().getDefinition())
            .examples(wordBook.getPersonalExamples().stream().map(
                personalExample -> ExampleDto.from(personalExample.getExample())
            ).toList())
            .testCount(wordBook.getTestCount())
            .failCount(wordBook.getFailCount())
            .createdAt(wordBook.getCreatedAt())
            .updatedAt(wordBook.getUpdatedAt())
            .build();
    }
}

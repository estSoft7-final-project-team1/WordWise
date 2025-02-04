package est.wordwise.domain.wordtest.dto;

import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.WordBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordTestDto {
    private String word;
    private String sentence;
    private String sentenceMeaning;
    private Long questionId;
    private Long exampleId;

    public static WordTestDto of(String word, String sentence, String sentenceMeaning, Long questionId, Long exampleId) {
        return WordTestDto.builder()
                .word(word)
                .sentence(sentence)
                .sentenceMeaning(sentenceMeaning)
                .questionId(questionId)
                .exampleId(exampleId)
                .build();
    }
}

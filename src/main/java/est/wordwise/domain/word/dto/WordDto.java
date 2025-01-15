package est.wordwise.domain.word.dto;

import est.wordwise.common.entity.Word;
import est.wordwise.domain.alanapi.dto.ResponseContent;
import est.wordwise.domain.example.dto.ExampleDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordDto {

    private String wordText;
    private String definition;
    List<ExampleDto> exampleDtos;

    public static WordDto from(Word word) {
        return WordDto.builder()
            .wordText(word.getWordText())
            .definition(word.getDefinition())
            .exampleDtos(word.getExamples().stream().map(ExampleDto::from).toList())
            .build();
    }

    public static WordDto from(ResponseContent responseContent) {
        return WordDto.builder()
            .wordText(responseContent.getWord())
            .definition(responseContent.getDefinition())
            .exampleDtos(responseContent.getExamples().stream().map(ExampleDto::from).toList())
            .build();
    }
}

package est.wordwise.domain.word.dto;

import est.wordwise.common.entity.Example;
import est.wordwise.common.entity.Word;
import est.wordwise.domain.alanapi.dto.ResponseContent;
import est.wordwise.domain.example.dto.ExampleDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordDto {

    private String wordText;
    private String definition;
    List<ExampleDto> exampleDtos;
    @Builder.Default
    List<Long> formerExampleIds = new ArrayList<>();

    public static WordDto from(Word word, List<Example> examples) {
        WordDto wordDto = WordDto.builder()
            .wordText(word.getWordText())
            .definition(word.getDefinition())
            .exampleDtos(examples.stream().map(ExampleDto::from).toList())
            .build();

        wordDto.getFormerExampleIds().addAll(examples.stream().map(Example::getId).toList());

        return wordDto;
    }

    public static WordDto from(Word word, List<Example> examples, WordDto formerWordDto) {
        WordDto wordDto = WordDto.builder()
            .wordText(word.getWordText())
            .definition(word.getDefinition())
            .exampleDtos(examples.stream().map(ExampleDto::from).toList())
            .formerExampleIds(formerWordDto.getFormerExampleIds())
            .build();

        wordDto.getFormerExampleIds().addAll(examples.stream().map(Example::getId).toList());

        return wordDto;
    }

    public static WordDto from(ResponseContent responseContent) {
        return WordDto.builder()
            .wordText(responseContent.getWord())
            .definition(responseContent.getDefinition())
            .exampleDtos(responseContent.getExamples().stream().map(ExampleDto::from).toList())
            .build();
    }
}

package est.wordwise.domain.example.dto;

import est.wordwise.common.entity.Example;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExampleDto {

    private String sentence;
    private String sentenceMeaning;

    public static ExampleDto from(Example example) {
        return ExampleDto.builder().sentence(example.getSentence())
            .sentenceMeaning(example.getSentenceMeaning()).build();
    }
}

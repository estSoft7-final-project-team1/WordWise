package est.wordwise.domain.example.dto;

import est.wordwise.common.entity.Example;
import est.wordwise.domain.alanapi.dto.ResponseContentExample;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExampleDto {

    private Long id;
    private String sentence;
    private String sentenceMeaning;

    public static ExampleDto from(Example example) {
        return ExampleDto.builder().sentence(example.getSentence())
            .sentenceMeaning(example.getSentenceMeaning())
            .id(example.getId())
            .build();
    }

    public static ExampleDto from(ResponseContentExample responseContentExample) {
        return ExampleDto.builder().sentence(responseContentExample.getSentence())
            .sentenceMeaning(responseContentExample.getSentenceMeaning()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExampleDto that = (ExampleDto) o;
        return Objects.equals(getSentence(), that.getSentence()) && Objects.equals(
            getSentenceMeaning(), that.getSentenceMeaning());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSentence(), getSentenceMeaning());
    }
}

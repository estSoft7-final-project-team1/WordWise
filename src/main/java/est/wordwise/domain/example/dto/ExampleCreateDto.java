package est.wordwise.domain.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExampleCreateDto {

    private String sentence;
    private String sentenceMeaning;
}

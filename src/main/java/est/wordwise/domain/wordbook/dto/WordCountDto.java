package est.wordwise.domain.wordbook.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordCountDto {

    private String wordText;
    private Long count;
}

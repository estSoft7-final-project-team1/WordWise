package est.wordwise.domain.alanapi.dto;

import java.util.List;
import lombok.Data;

@Data
public class ResponseContent {

    private String word;
    private String definition;
    private List<ResponseContentExample> examples;
}

package est.wordwise.domain.alanapi.dto;

import lombok.Data;

@Data
public class Response {

    private ResponseAction responseAction;
    private String content;

}

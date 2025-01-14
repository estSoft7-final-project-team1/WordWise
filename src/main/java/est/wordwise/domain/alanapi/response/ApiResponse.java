package est.wordwise.domain.alanapi.response;

import lombok.Data;

@Data
public class ApiResponse {

    private Action action;
    private String content;

}

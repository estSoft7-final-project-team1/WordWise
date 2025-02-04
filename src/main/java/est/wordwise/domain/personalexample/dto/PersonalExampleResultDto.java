package est.wordwise.domain.personalexample.dto;

import est.wordwise.common.entity.PersonalExample;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalExampleResultDto {

    private Long wordBookId;
    
    @Builder.Default
    private List<String> duplicatedPersonalExamples = new ArrayList<>();
    @Builder.Default
    private List<String> newPersonalExamples = new ArrayList<>();

    public static PersonalExampleResultDto of(Long wordBookId,
        List<PersonalExample> duplicatedPersonalExamples,
        List<PersonalExample> newPersonalExamples) {

        PersonalExampleResultDto personalExampleResultDto = PersonalExampleResultDto
            .builder()
            .wordBookId(wordBookId)
            .build();

        duplicatedPersonalExamples.forEach(
            personalExample -> personalExampleResultDto.duplicatedPersonalExamples.add(
                personalExample.getExample().getSentence()));

        newPersonalExamples.forEach(
            personalExample -> personalExampleResultDto.newPersonalExamples.add(
                personalExample.getExample().getSentence()));

        return personalExampleResultDto;
    }
}

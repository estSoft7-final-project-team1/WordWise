package est.wordwise.common.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor
public class Example {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "example_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    private String sentence;
    private String sentenceMeaning;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

//    public static Example from(ExampleCreateDto exampleCreateDto) {
//        Example example = new Example();
//        example.word = exampleCreateDto.getWord();
//        example.sentence = exampleCreateDto.getSentence();
//        example.sentenceMeaning = exampleCreateDto.getSentenceMeaning;
//        return example;
//    }

}


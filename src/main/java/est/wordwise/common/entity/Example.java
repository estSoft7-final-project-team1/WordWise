package est.wordwise.common.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private boolean deleted;

//    public static Example from(ExampleCreateDto exampleCreateDto) {
//        Example example = new Example();
//        example.word = exampleCreateDto.getWord();
//        example.sentence = exampleCreateDto.getSentence();
//        example.sentenceMeaning = exampleCreateDto.getSentenceMeaning;
//        return example;
//    }

}


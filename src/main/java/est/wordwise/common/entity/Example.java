package est.wordwise.common.entity;


import est.wordwise.domain.example.dto.ExampleDto;
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
    private String sentenceMeaning; // translation으로 바꾸면 문제되나?

    private boolean deleted;


    public static Example from(Word word, ExampleDto exampleDto) {
        Example example = new Example();
        example.word = word;
        example.sentence = exampleDto.getSentence();
        example.sentenceMeaning = exampleDto.getSentenceMeaning();
        return example;
    }

    public void setWord(Word word) {
        this.word = word;
        word.getExamples().add(this);
    }
}


package est.wordwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long id;

    private String wordText;
    private String definition;

    @OneToMany(mappedBy="word", fetch = FetchType.LAZY)
    List<Example> examples;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    public static Word of(WordCreateDto wordCreateDto) {
        Word word = new Word();
        word.wordText = wordCreateDto.getWordText();
        word.definition = wordCreateDto.getDefinition();
        return word;
    }

}

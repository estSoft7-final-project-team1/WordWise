package est.wordwise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long id;

    private String wordText;
    private String definition;

    @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
    List<Example> examples;

    private boolean deleted;

//    public static Word from(WordCreateDto wordCreateDto) {
//        Word word = new Word();
//        word.wordText = wordCreateDto.getWordText();
//        word.definition = wordCreateDto.getDefinition();
//        return word;
//    }

}

package est.wordwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class PersonalExample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_example_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="word_book_id")
    private WordBook wordBook;

    @ManyToOne
    @JoinColumn(name="example_id")
    private Example example;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean deleted;

    public static PersonalExample of(WordBook wordBook, Example example) {
        PersonalExample personalExample = new PersonalExample();
        personalExample.wordBook = wordBook;
        personalExample.example = example;
        return personalExample;
    }
}

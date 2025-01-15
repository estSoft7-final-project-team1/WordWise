package est.wordwise.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalExample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_example_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "word_book_id")
    private WordBook wordBook;

    @ManyToOne
    @JoinColumn(name = "example_id")
    private Example example;

    private LocalDateTime createdAt;
    private boolean deleted;

    public static PersonalExample of(WordBook wordBook, Example example) {
        PersonalExample personalExample = new PersonalExample();
        personalExample.wordBook = wordBook;
        personalExample.example = example;
        return personalExample;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

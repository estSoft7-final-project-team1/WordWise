package est.wordwise.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class WordBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="word_book_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="word_id")
    private Word word;

    private int testCount;
    private int failCount;

    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    public static WordBook of(Member member, Word word) {
        WordBook wordBook = new WordBook();
        wordBook.member = member;
        wordBook.word = word;
        return wordBook;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}

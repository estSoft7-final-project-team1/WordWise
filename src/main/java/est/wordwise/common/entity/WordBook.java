package est.wordwise.common.entity;



import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_book_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @OneToMany(mappedBy = "wordBook", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalExample> personalExamples = new ArrayList<>();

    private int testCount;
    private int failCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 논리 삭제 deleted = false
    private boolean deleted;

    // builder로 엔티티 객체 생성
    @Builder
    public WordBook(Long id, Member member, Word word, int testCount, int failCount) {
        this.id = id;
        this.member = member;
        this.word = word;
        this.testCount = testCount;
        this.failCount = failCount;
    }

    public static WordBook of(Member member, Word word) {
        WordBook wordBook = new WordBook();
        wordBook.member = member;
        wordBook.word = word;
        wordBook.testCount = 0;
        wordBook.failCount = 0;
        return wordBook;
    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.deleted = false;
    }

    // 수정시 자동으로 updateAt 날짜 갱신
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void success() {
        this.testCount++;
    }
    public void fail() {
        this.failCount++;
        this.testCount++;

    }

}

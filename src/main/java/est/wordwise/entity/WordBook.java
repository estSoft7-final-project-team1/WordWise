package est.wordwise.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // 작성시 자동으로 createAt, updateAt 날짜 설정
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

    // 생성자 바로 사용하지 말고, DTO를 통해 생성할때는 from 메서드를 통해 생성해서 사용

    // dto -> entity
//    public static WordBook from(WordBookTempDto dto) {
//
//        WordBook entity = new WordBook();
//
//        entity.testCount = dto.getTestCount();
//        entity.failCount = dto.getFailCount();
//
//        return entity;
//    }



}

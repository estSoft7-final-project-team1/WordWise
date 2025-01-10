package est.wordwise.entity;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private String phonenumber;

    // 경천님이 보시고 enum 쓰시는대로 수정하시면 됩니다
    private String provider;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted = false;

    @OneToMany(mappedBy = "member", cascadeType.ALL, fetch = FetchType.LAZY)
    private List<WordBook> wordBooks;

    public static Member from(MemberSignupDto memberSignupDto) {
        Member member = new Member();
        member.email = memberSignupDto.getEmail();
        member.password = memberSignupDto.getPassword();
        member.nickname = memberSignupDto.getNickname();
        member.phonenumber = memberSignupDto.getPhonenumber();
        member.provider = memberSignupDto.getProvider();
        member.role = memberSignupDto.getRole();

        return member;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}

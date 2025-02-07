package est.wordwise.common.entity;

import est.wordwise.common.entity.commonEnum.memberEnums.AuthType;
import est.wordwise.common.entity.commonEnum.memberEnums.SocialType;
import est.wordwise.domain.security.dto.MemberSignupDto;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private String phonenumber;

    // 경천님이 보시고 enum 쓰시는대로 수정하시면 됩니다
    @Enumerated(EnumType.STRING)
    private SocialType provider;
    @Enumerated(EnumType.STRING)
    private AuthType role = AuthType.MEMBER;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted = false;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WordBook> wordBooks;

    @Builder
    public Member(SocialType provider, String email, String nickname, String password) {
        this.provider = provider;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

        public static Member from(MemberSignupDto memberSignupDto) {
        Member member = new Member();
        member.nickname = memberSignupDto.getNickname();
        member.email = memberSignupDto.getEmail();
        member.password = memberSignupDto.getPassword();
        member.provider = SocialType.FORMBASED;
        return member;
    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


package est.wordwise.domain.security2.domain;

import est.wordwise.common.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @ManyToOne
    @JoinColumn(name= "member_id")
    private Member member;

    private LocalDateTime createAt = LocalDateTime.now();

    @Builder
    public RefreshToken(String refreshToken, Member member) {
        this.refreshToken = refreshToken;
        this.member = member;
    }
}
package est.wordwise.domain.security.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenBlackList {

    @Id
    @Column(name = "refresh_token_black_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "refresh_token_id")
    private RefreshToken refreshToken;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public RefreshTokenBlackList(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }
}
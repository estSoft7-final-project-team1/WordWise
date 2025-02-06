package est.wordwise.domain.security2.repository;

import est.wordwise.common.entity.Member;
import est.wordwise.domain.security2.domain.RefreshToken;

import java.util.Optional;

public interface TokenRepository {
    RefreshToken save(Member member, String token);
    Optional<RefreshToken> findValidRefreshTokenByToken(String token);
    Optional<RefreshToken> findValidRefreshTokenByMemberId(Long memberId);
    RefreshToken appendBlackList(RefreshToken token);
}

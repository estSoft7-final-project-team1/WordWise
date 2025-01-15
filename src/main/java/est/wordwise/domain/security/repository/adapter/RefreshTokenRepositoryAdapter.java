package est.wordwise.domain.security.repository.adapter;

import est.wordwise.common.entity.Member;
import est.wordwise.domain.security.domain.RefreshToken;
import est.wordwise.domain.security.domain.RefreshTokenBlackList;
import est.wordwise.domain.security.repository.RefreshTokenBlackListRepository;
import est.wordwise.domain.security.repository.RefreshTokenRepository;
import est.wordwise.domain.security.repository.TokenRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements TokenRepository {

    private final RefreshTokenRepository refreshTokenRepo;
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepo;

    private final EntityManager em;


    @Override
    public RefreshToken save(Member member, String token) {
        return refreshTokenRepo.save(
                RefreshToken.builder()
                        .refreshToken(token)
                        .member(member)
                        .build());
    }

    @Override
    public Optional<RefreshToken> findValidRefreshTokenByToken(String token) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepo.findByRefreshToken(token);

        if (refreshTokenOptional.isEmpty()) return refreshTokenOptional;

        RefreshToken findToken = refreshTokenOptional.get();

        boolean isBanned = isBannedRefToken(findToken);

        if (isBanned) {
            return Optional.empty();
        } else{
            return refreshTokenOptional;
        }
    }

    @Override
    public Optional<RefreshToken> findValidRefreshTokenByMemberId(Long memberId) {
        return em.createQuery(
                        "select rf from RefreshToken rf left join RefreshTokenBlackList rtb on rtb.refreshToken = rf where rf.member.id = :memberId and rtb.id is null"
                        , RefreshToken.class)
                .setParameter("memberId", memberId)
                .getResultStream().findFirst();
    }

    @Override
    public RefreshToken appendBlackList(RefreshToken token) {
        refreshTokenBlackListRepo.save(
                RefreshTokenBlackList.builder()
                        .refreshToken(token)
                        .build()
        );
        return token;
    }

    public boolean isBannedRefToken(RefreshToken token) {
        return refreshTokenBlackListRepo.existsByRefreshToken(token);
    }
}

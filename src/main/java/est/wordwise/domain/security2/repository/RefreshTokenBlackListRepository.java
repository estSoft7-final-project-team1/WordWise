package est.wordwise.domain.security2.repository;

import est.wordwise.domain.security2.domain.RefreshToken;
import est.wordwise.domain.security2.domain.RefreshTokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList, Long> {
    boolean existsByRefreshToken(RefreshToken refreshToken);
}

package est.wordwise.domain.security.repository;

import est.wordwise.domain.security.domain.RefreshToken;
import est.wordwise.domain.security.domain.RefreshTokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList, Long> {
    boolean existsByRefreshToken(RefreshToken refreshToken);
}

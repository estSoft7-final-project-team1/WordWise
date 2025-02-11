package est.wordwise.common.repository;


import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    List<Statistics> findByMemberOrderByIdDesc(Member member);
}
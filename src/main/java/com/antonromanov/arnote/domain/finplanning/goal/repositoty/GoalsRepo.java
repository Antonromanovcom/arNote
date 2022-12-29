package com.antonromanov.arnote.domain.finplanning.goal.repositoty;


import com.antonromanov.arnote.domain.finplanning.goal.entity.Goal;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий трат.
 */
@Repository
public interface GoalsRepo  extends JpaRepository<Goal, Long> {
    List<Goal> findAllByUser(ArNoteUser user);
    Optional<Goal> findGoalByIdAndUser(Long id, ArNoteUser user);
    List<Goal> findAllByRepaymentAndUser(Long repayment, ArNoteUser user);
}

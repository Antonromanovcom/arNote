package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.entity.finplan.Freeze;
import com.antonromanov.arnote.model.ArNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Репозиторий фризов.
 */
@Repository
public interface FreezeRepo extends JpaRepository<Freeze, Long>{


    @Query(value="select f from Freeze f where f.user = :user and " +
            "f.startDate <> null and " +
            "EXTRACT(YEAR from f.startDate) = :year and " +
            "EXTRACT(MONTH from f.startDate) = :month")
    Optional<Freeze> findFreezeByUserAndMonthAndYear(@Param("user") ArNoteUser user, @Param("year") int year,
                                                     @Param("month") int month);
}

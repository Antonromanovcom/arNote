package com.antonromanov.arnote.old.repositoty;

import com.antonromanov.arnote.domain.finplanning.loan.entity.Credit;
import com.antonromanov.arnote.old.model.ArNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> getCreditsByUser(@Param("user") ArNoteUser user);
    Optional<Credit> findCreditByUserAndId(ArNoteUser user, Long id);


    @Query(value="select c from Credit c where c.user = :user and " +
            "c.startDate <> null and " +
            "EXTRACT(YEAR from c.startDate) = :year and " +
            "EXTRACT(MONTH from c.startDate) = :month")
    List<Credit> findCreditByUserAndMonthAndYear(@Param("user") ArNoteUser user, @Param("year") int year,
                                                         @Param("month") int month);

}

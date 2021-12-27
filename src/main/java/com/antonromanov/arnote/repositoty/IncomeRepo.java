package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.entity.finplan.Income;
import com.antonromanov.arnote.model.ArNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long>{
    List<Income> findAllByUserOrderByIncomeDateAsc(ArNoteUser user);
    List<Income> findAllByUser(ArNoteUser user);
    Optional<Income> findIncomeByUserAndId(ArNoteUser user, Long id);


    @Query(value="select g from Income g where g.user = :user and " +
            "g.incomeDate <> null and " +
            "EXTRACT(YEAR from g.incomeDate) = :year and " +
            "EXTRACT(MONTH from g.incomeDate) = :month")
    List<Income> findIncomeByUserAndMonthAndYear(@Param("user") ArNoteUser user, @Param("year") int year,
                                                         @Param("month") int month);
}

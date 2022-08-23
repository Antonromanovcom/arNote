package com.antonromanov.arnote.sex.repositoty;


import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository /*extends JpaRepository<Credit, Long>*/{
   /* List<Credit> getCreditsByUser(@Param("user") ArNoteUser user);
    Optional<Credit> findCreditByUserAndId(ArNoteUser user, Long id);


    @Query(value="select c from Credit c where c.user = :user and " +
            "c.startDate <> null and " +
            "EXTRACT(YEAR from c.startDate) = :year and " +
            "EXTRACT(MONTH from c.startDate) = :month")
    List<Credit> findCreditByUserAndMonthAndYear(@Param("user") ArNoteUser user, @Param("year") int year,
                                                         @Param("month") int month);*/

}

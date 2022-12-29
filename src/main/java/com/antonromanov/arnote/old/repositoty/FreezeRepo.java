package com.antonromanov.arnote.old.repositoty;


/**
 * Репозиторий фризов.
 */
//@Repository
public interface FreezeRepo /*extends JpaRepository<Freeze, Long>*/{


    /*@Query(value="select f from Freeze f where f.user = :user and " +
            "f.startDate <> null and " +
            "EXTRACT(YEAR from f.startDate) = :year and " +
            "EXTRACT(MONTH from f.startDate) = :month")
    Optional<Freeze> findFreezeByUserAndMonthAndYear(@Param("user") ArNoteUser user, @Param("year") int year,
                                                     @Param("month") int month);

    List<Freeze> findAllByUser(ArNoteUser user);*/
}

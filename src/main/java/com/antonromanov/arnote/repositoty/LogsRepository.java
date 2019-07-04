package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.Wish;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Репозиторий для работы с логами (есть АС, Lan или нет?
 * А так же вольтаж, ампраж и прочее....
 */
@Repository
public interface LogsRepository extends JpaRepository<Wish, Integer>{

  
    @Query(value="select l from Logs l order by l.lastсontactdate DESC")
    List<Wish> getLastPingedEntry(Pageable pageable);

    @Query(value="select l from Logs l order by l.lastсontactdate DESC, l.lastсontacttime DESC")
    List<Wish> getLastPingedEntry2();

    @Query(value="select l from Logs l order by l.lastсontactdate DESC, l.lastсontacttime DESC")
    List<Wish> getLastPingedEntry3(Pageable pageable);




}

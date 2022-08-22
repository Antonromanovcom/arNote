package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.entity.common.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CalendarRepo extends JpaRepository<CalendarEntity, Long>{

}

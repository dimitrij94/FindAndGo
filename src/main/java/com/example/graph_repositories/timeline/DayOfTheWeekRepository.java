package com.example.graph_repositories.timeline;

import com.example.graph.timeline.DayOfTheWeek;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 28.02.2016.
 */
public interface DayOfTheWeekRepository extends CrudRepository<DayOfTheWeek,String> {
    DayOfTheWeek findByDayName(String dayName);
}

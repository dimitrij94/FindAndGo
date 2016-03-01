package com.example.graph_repositories;

import com.example.graph.schedules.Schedule;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 25.02.2016.
 */
public interface ScheduleRepository extends CrudRepository<Schedule, String> {

}

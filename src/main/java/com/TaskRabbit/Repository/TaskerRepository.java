package com.TaskRabbit.Repository;

import com.TaskRabbit.Entity.Tasker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskerRepository extends JpaRepository<Tasker,Long> {
}

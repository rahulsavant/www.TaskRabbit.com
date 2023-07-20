package com.TaskRabbit.Repository;

import com.TaskRabbit.Entity.Service;
import com.TaskRabbit.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByService(Service service);
}

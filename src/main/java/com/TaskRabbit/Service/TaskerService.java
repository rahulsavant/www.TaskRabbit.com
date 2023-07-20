package com.TaskRabbit.Service;

import com.TaskRabbit.Payload.TaskerDto;

public interface TaskerService {
    TaskerDto createTasker(long taskId, TaskerDto taskerDto);

    TaskerDto updateTasker(long taskId, long taskerId, TaskerDto taskerDto);

    void deleteByTaskerId(long taskId, long taskerId);
}

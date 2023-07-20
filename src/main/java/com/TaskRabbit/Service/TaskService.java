package com.TaskRabbit.Service;

import com.TaskRabbit.Payload.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(long serviceId, TaskDto taskDto);
    TaskDto getTaskByName(long serviceId, String taskDto);
    List<TaskDto> getAllTasksByServiceName(String serviceName);
}

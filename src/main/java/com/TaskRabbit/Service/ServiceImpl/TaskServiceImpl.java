package com.TaskRabbit.Service.ServiceImpl;

import com.TaskRabbit.Entity.Task;
import com.TaskRabbit.Exception.ResourceNotFoundException;
import com.TaskRabbit.Payload.TaskDto;
import com.TaskRabbit.Repository.ServiceRepository;
import com.TaskRabbit.Repository.TaskRepository;
import com.TaskRabbit.Service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private ServiceRepository serviceRepository;
    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    public TaskServiceImpl(ServiceRepository serviceRepository, TaskRepository taskRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskDto createTask(long serviceId,TaskDto taskDto) {
        com.TaskRabbit.Entity.Service service = serviceRepository.findById(serviceId).orElseThrow(
                () -> new ResourceNotFoundException("Service not found with Id:" + serviceId)
        );
        Task task = mapToEntity(taskDto);
        task.setService(service);
        Task newTask = taskRepository.save(task);
        TaskDto dto = mapToDto(newTask);
        return dto;
    }

    @Override
    public TaskDto getTaskByName(long serviceId, String taskName) {
        com.TaskRabbit.Entity.Service service = serviceRepository.findById(serviceId).orElseThrow(
                () -> new ResourceNotFoundException("Service not found with Id: " + serviceId)
        );

        List<Task> tasks = taskRepository.findByService(service);
        Optional<Task> optionalTask = tasks.stream()
                .filter(task -> task.getTaskName().toLowerCase().contains(taskName.toLowerCase()))
                .findFirst();

        Task task = optionalTask.orElseThrow(
                () -> new ResourceNotFoundException("Task not found with name: " + taskName)
        );

        return mapToDto(task);
    }

    @Override
    public List<TaskDto> getAllTasksByServiceName(String serviceName) {
        com.TaskRabbit.Entity.Service service = serviceRepository.findByName(serviceName)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with name: " + serviceName));

        List<Task> tasks = taskRepository.findByService(service);
        List<TaskDto> taskDtos = tasks.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return taskDtos;
    }

    TaskDto mapToDto(Task task){
        TaskDto taskDto=modelMapper.map(task,TaskDto.class);
        return taskDto;
    }

    Task mapToEntity(TaskDto taskDto){
        Task task=modelMapper.map(taskDto,Task.class);
        return task;
    }
}

package com.TaskRabbit.Service.ServiceImpl;

import com.TaskRabbit.Entity.Task;
import com.TaskRabbit.Entity.Tasker;
import com.TaskRabbit.Exception.ResourceNotFoundException;
import com.TaskRabbit.Payload.TaskDto;
import com.TaskRabbit.Payload.TaskerDto;
import com.TaskRabbit.Repository.TaskRepository;
import com.TaskRabbit.Repository.TaskerRepository;
import com.TaskRabbit.Service.TaskerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskerServiceImpl implements TaskerService {

    private TaskRepository taskRepository;
    private TaskerRepository taskerRepository;
    private ModelMapper modelMapper;

    public TaskerServiceImpl(TaskRepository taskRepository, TaskerRepository taskerRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.taskerRepository = taskerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskerDto createTasker(long taskId, TaskerDto taskerDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task Not found with id:" + taskId)
        );
        Tasker tasker = mapToEntity(taskerDto);
        tasker.setTask(task);
        Tasker newTasker = taskerRepository.save(tasker);
        TaskerDto dto = mapToDto(newTasker);
        return dto;

    }

    @Override
    public TaskerDto updateTasker(long taskId, long taskerId, TaskerDto taskerDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task Not found with id:" + taskId)
        );
        Tasker tasker = taskerRepository.findById(taskerId).orElseThrow(
                () -> new ResourceNotFoundException("Tasker Not found with id:" + taskerId)
        );
        tasker.setTaskerName(taskerDto.getTaskerName());
        tasker.setPrice(taskerDto.getPrice());
        Tasker updatedTasker = taskerRepository.save(tasker);
        return mapToDto(updatedTasker);
    }

    @Override
    public void deleteByTaskerId(long taskId, long taskerId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task Not found with id:" + taskId)
        );
        Tasker tasker = taskerRepository.findById(taskerId).orElseThrow(
                () -> new ResourceNotFoundException("Tasker Not found with id:" + taskerId)
        );
        taskerRepository.deleteById(taskerId);
    }

    TaskerDto mapToDto(Tasker tasker){
        TaskerDto taskerDto = modelMapper.map(tasker, TaskerDto.class);
        return taskerDto;
    }

    Tasker mapToEntity(TaskerDto taskerDto){
        Tasker tasker = modelMapper.map(taskerDto, Tasker.class);
        return tasker;
    }
}

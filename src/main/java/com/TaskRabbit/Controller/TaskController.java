package com.TaskRabbit.Controller;

import com.TaskRabbit.Payload.TaskDto;
import com.TaskRabbit.Service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    //http://localhost:8080/api/services/id/task
    @PostMapping("/services/{serviceId}/task")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDto> createTask(@PathVariable("serviceId") long serviceId, @RequestBody TaskDto taskDto) {
        TaskDto dto = taskService.createTask(serviceId, taskDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    //http://localhost:8080/api/services/{serviceId}/task/{taskName}
    @GetMapping("/services/{serviceId}/task/{taskName}")
    public ResponseEntity<TaskDto> getTaskByName(@PathVariable("serviceId") long serviceId,@PathVariable("taskName") String taskName){
        return new ResponseEntity<>(taskService.getTaskByName(serviceId,taskName),HttpStatus.OK);
    }

    // http://localhost:8080/api/services/{serviceName}/tasks
    @GetMapping("/services/{serviceName}/tasks")
    public List<TaskDto> getAllTasksByServiceName(@PathVariable("serviceName") String serviceName) {
        List<TaskDto> tasks = taskService.getAllTasksByServiceName(serviceName);
        return tasks;
    }




}


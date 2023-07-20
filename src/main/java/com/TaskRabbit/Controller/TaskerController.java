package com.TaskRabbit.Controller;

import com.TaskRabbit.Payload.TaskerDto;
import com.TaskRabbit.Service.TaskerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class TaskerController {

    private TaskerService taskerService;

    public TaskerController(TaskerService taskerService) {
        this.taskerService = taskerService;
    }

    //http://localhost:8080/api/tasks/id/taskers
    @PostMapping("/tasks/{taskId}/taskers")
    public ResponseEntity<TaskerDto> createTasker(@PathVariable("taskId") long taskId, @RequestBody TaskerDto taskerDto) {
        TaskerDto dto = taskerService.createTasker(taskId, taskerDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/tasks/1/taskers/1
    @PutMapping("/tasks/{taskId}/taskers/{taskerId}")
    public ResponseEntity<TaskerDto> updateTasker(@PathVariable("taskId") long taskId, @PathVariable("taskerId") long taskerId,@RequestBody TaskerDto taskerDto){
        TaskerDto dto = taskerService.updateTasker(taskId, taskerId, taskerDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/tasks/1/taskers/1
    @DeleteMapping("/tasks/{taskId}/taskers/{taskerId}")
    public ResponseEntity<String> deleteByTaskerId(@PathVariable("taskId") long taskId, @PathVariable("taskerId") long taskerId){
        taskerService.deleteByTaskerId(taskId,taskerId);
        return new ResponseEntity<>("Tasker is deleted!!",HttpStatus.OK);
    }
}

package com.TaskRabbit.Controller;

import com.TaskRabbit.Payload.ServiceDto;
import com.TaskRabbit.Payload.TaskDto;
import com.TaskRabbit.Service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {
    private ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    //http://localhost:8080/services
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto serviceDto) {
        ServiceDto dto = serviceService.createService(serviceDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/services/id
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> getServiceById(@PathVariable Long id) {
        ServiceDto service = serviceService.getServiceById(id);
        return new ResponseEntity<>(service,HttpStatus.OK);
    }

    //http://localhost:8080/services
    @GetMapping
    public List<ServiceDto> getAllServices() {
        List<ServiceDto> services = serviceService.getAllServices();
        return services;
    }

    //http://localhost:8080/services/id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceDto> updateService(@PathVariable Long id,@RequestBody ServiceDto serviceDto) {
        ServiceDto service = serviceService.updateService(id, serviceDto);
        return new ResponseEntity<>(service,HttpStatus.OK);
    }

    //http://localhost:8080/services/1
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteService(@PathVariable("id") long id){
        serviceService.deleteService(id);
        return new ResponseEntity<>("Service is deleted",HttpStatus.OK);
    }

    // http://localhost:8080/services/name/{name}
    @GetMapping("/name/{name}")
    public List<ServiceDto> getServicesByName(@PathVariable String name) {
        List<ServiceDto> services = serviceService.getServicesByName(name);
        return services;
    }

}


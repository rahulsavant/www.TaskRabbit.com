package com.TaskRabbit.Service;

import com.TaskRabbit.Payload.ServiceDto;

import java.util.List;

public interface ServiceService {
    ServiceDto createService(ServiceDto serviceDto);
    ServiceDto getServiceById(Long id);
    List<ServiceDto> getAllServices();
    ServiceDto updateService(Long id, ServiceDto serviceDto);
    void deleteService(long id);
    List<ServiceDto> getServicesByName(String name);
}

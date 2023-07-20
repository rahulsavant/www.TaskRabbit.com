package com.TaskRabbit.Service.ServiceImpl;

import com.TaskRabbit.Entity.Task;
import com.TaskRabbit.Exception.ResourceNotFoundException;
import com.TaskRabbit.Payload.ServiceDto;
import com.TaskRabbit.Payload.TaskDto;
import com.TaskRabbit.Repository.ServiceRepository;
import com.TaskRabbit.Service.ServiceService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.TaskRabbit.Entity.Service;
import org.modelmapper.ModelMapper;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private ServiceRepository serviceRepository;
    private ModelMapper modelMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        Service service=new Service();
        service.setName(serviceDto.getName());
        Service newService = serviceRepository.save(service);

        ServiceDto dto=new ServiceDto();
        dto.setId(newService.getId());
        dto.setName(newService.getName());
        return dto;
    }

    @Override
    public ServiceDto getServiceById(Long id) {
        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Service not found with Id:" + id)
        );
        ServiceDto dto = mapToDto(service);
        return dto;
    }

    @Override
    public List<ServiceDto> getAllServices() {
        List<Service> serviceList = serviceRepository.findAll();
        List<ServiceDto> dtos = serviceList.stream().map(service -> mapToDto(service)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public ServiceDto updateService(Long id, ServiceDto serviceDto) {
        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Service not found with Id:" + id)
        );
        Service service1 = mapToEntity(serviceDto);
        service1.setId(id);
        Service updatedservice = serviceRepository.save(service1);
        ServiceDto dto = mapToDto(updatedservice);
        return dto;
    }

    @Override
    public void deleteService(long id) {
        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Service not found with Id:" + id)
        );
        serviceRepository.deleteById(id);

    }

    @Override
    public List<ServiceDto> getServicesByName(String name) {
        List<Service> serviceList = serviceRepository.findAll();

        List<ServiceDto> dtos = serviceList.stream()
                .filter(service -> service.getName().toLowerCase().contains(name.toLowerCase()))
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return dtos;
    }

    ServiceDto mapToDto(Service service){
        ServiceDto dto=modelMapper.map(service,ServiceDto.class);
        return dto;
    }

    Service mapToEntity(ServiceDto serviceDto){
        Service service=modelMapper.map(serviceDto,Service.class);
        return service;
    }
}




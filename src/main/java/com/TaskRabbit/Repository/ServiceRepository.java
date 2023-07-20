package com.TaskRabbit.Repository;

import com.TaskRabbit.Entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<Service> findByName(String serviceName);
}


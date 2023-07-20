package com.TaskRabbit.Payload;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CustomerRequest {
    private String address;
    private LocalTime time;
    private LocalDate date;
    private String taskDescription;
    private boolean cancelled;
    private Long taskerId;
    private Long mobile;

    private String email;
}


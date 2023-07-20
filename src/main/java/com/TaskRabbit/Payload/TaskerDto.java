package com.TaskRabbit.Payload;

import lombok.Data;

@Data
public class TaskerDto {
    private Long id;
    private String taskerName;
    private String price;
    private String email;

}
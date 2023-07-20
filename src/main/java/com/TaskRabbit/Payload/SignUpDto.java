package com.TaskRabbit.Payload;

import lombok.Data;

@Data
public class SignUpDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Long phoneNumber;
    private String password;
    private Long zipCode;
}

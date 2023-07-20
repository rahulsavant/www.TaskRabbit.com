package com.TaskRabbit.Exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {

    public BlogAPIException (HttpStatus status, String msg){
        super(msg);
    }
}

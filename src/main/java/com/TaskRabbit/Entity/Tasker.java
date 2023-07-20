package com.TaskRabbit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taskers")
public class Tasker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskerName;
    private String price;
    private String email;

    @ManyToOne
    @JoinColumn(name = "tasks_id")
    private Task task;

}

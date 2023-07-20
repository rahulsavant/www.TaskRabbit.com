package com.TaskRabbit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.time.LocalDate;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private LocalTime time;

    private LocalDate date;

    @Column(name = "task_description")
    private String taskDescription;

    private boolean cancelled;

    @ManyToOne
    @JoinColumn(name = "tasker_id")
    private Tasker tasker;

    private Long mobile;

    private String email;
}


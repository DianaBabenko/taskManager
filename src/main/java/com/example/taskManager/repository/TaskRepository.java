package com.example.taskManager.repository;

import com.example.taskManager.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {

}
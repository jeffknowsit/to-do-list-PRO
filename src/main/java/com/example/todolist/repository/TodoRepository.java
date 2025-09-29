package com.example.todolist.repository;

import com.example.todolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserId(Long userId);

    // NEW: Add this method to find todos by user and priority
    List<Todo> findByUserIdAndPriorityOrderByIdDesc(Long userId, int priority);
}
package com.example.todolist.repository;

import com.example.todolist.model.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, Long> {
    // Spring Data JPA will automatically provide methods like save, findById, findAll, delete, etc.
    // You can add custom query methods here later if needed.
}
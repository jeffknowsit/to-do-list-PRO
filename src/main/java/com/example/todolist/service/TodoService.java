package com.example.todolist.service;

import com.example.todolist.dto.DashboardStats;
import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> findByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    public Todo findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    // ======================================================
    // == FIX 1: Add the missing findByUserIdAndPriority method ==
    // ======================================================
    public List<Todo> findByUserIdAndPriority(Long userId, int priority) {
        return todoRepository.findByUserIdAndPriorityOrderByIdDesc(userId, priority);
    }
    
    // ======================================================
    // == FIX 2: Add the missing getDashboardStats method ==
    // ======================================================
    public DashboardStats getDashboardStats(Long userId) {
        LocalDate today = LocalDate.now();
        List<Todo> allTodos = todoRepository.findByUserId(userId);

        // 1. Get all tasks that were actually COMPLETED today.
        List<Todo> completedTodayList = allTodos.stream()
            .filter(todo -> todo.getCompletionDate() != null && todo.getCompletionDate().equals(today))
            .collect(Collectors.toList());
        int tasksCompletedToday = completedTodayList.size();

        // 2. Get all tasks that are DUE today (and not already completed).
        List<Todo> dueTodayList = allTodos.stream()
            .filter(todo -> !todo.isCompleted() && todo.getDueDateTime() != null && todo.getDueDateTime().toLocalDate().equals(today))
            .collect(Collectors.toList());
        
        // 3. The total is the combination of the two lists. A Set prevents duplicates.
        Set<Todo> relevantTasks = new HashSet<>(completedTodayList);
        relevantTasks.addAll(dueTodayList);
        int totalTasksForToday = relevantTasks.size();

        // 4. Calculate the streak.
        Set<LocalDate> completionDates = allTodos.stream()
            .filter(todo -> todo.getCompletionDate() != null)
            .map(Todo::getCompletionDate)
            .collect(Collectors.toSet());

        int streakDays = 0;
        if (completionDates.contains(today) || completionDates.contains(today.minusDays(1))) {
            LocalDate dateToCheck = today;
            if (!completionDates.contains(today)) {
                dateToCheck = today.minusDays(1);
            }
            
            while (completionDates.contains(dateToCheck)) {
                streakDays++;
                dateToCheck = dateToCheck.minusDays(1);
            }
        }

        return new DashboardStats(tasksCompletedToday, totalTasksForToday, streakDays);
    }
}
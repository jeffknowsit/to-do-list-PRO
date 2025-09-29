package com.example.todolist.service;

import com.example.todolist.model.Habit;
import com.example.todolist.model.HabitCompletion;
import com.example.todolist.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;

    public List<Habit> findByUserId(Long userId) {
        // This now calls the powerful JOIN FETCH query from the repository
        return habitRepository.findByUserId(userId);
    }

    public void save(Habit habit) {
        habitRepository.save(habit);
    }

    @Transactional
    public void toggleHabitCompletion(Long habitId, LocalDate date) {
        // Use the standard findById, as the completions will be managed within the transaction
        Habit habit = habitRepository.findById(habitId).orElse(null);
        if (habit == null) {
            return;
        }

        Optional<HabitCompletion> existingCompletion = habit.getCompletions().stream()
            .filter(c -> c.getCompletionDate().equals(date))
            .findFirst();

        if (existingCompletion.isPresent()) {
            // Remove the completion from the habit's list.
            // "orphanRemoval=true" in Habit.java tells Hibernate to delete the database row.
            habit.getCompletions().remove(existingCompletion.get());
        } else {
            // Create a new completion and add it to the habit's list.
            HabitCompletion newCompletion = new HabitCompletion();
            newCompletion.setHabit(habit);
            newCompletion.setCompletionDate(date);
            habit.getCompletions().add(newCompletion);
        }
        
        // Saving the parent (Habit) cascades the changes to its children (HabitCompletion).
        // This is the most reliable way to ensure synchronization.
        habitRepository.save(habit);
    }
}
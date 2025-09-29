package com.example.todolist.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // This field was likely removed or changed in a previous version.
    // It should be restored if you need it. For now, we focus on startDate.
    // private String description; 

    // ======================================================
    // == FIX: Initialize startDate with a default value ==
    // ======================================================
    @Column(nullable = false)
    private LocalDate startDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<HabitCompletion> completions;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<HabitCompletion> getCompletions() { return completions; }
    public void setCompletions(List<HabitCompletion> completions) { this.completions = completions; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return id != null && id.equals(habit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
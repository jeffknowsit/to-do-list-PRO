package com.example.todolist.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dueDateTime;

    private boolean completed = false;
    
    // NEW: Field to track the actual completion date
    private LocalDate completionDate;

    private int priority = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDueDateTime() { return dueDateTime; }
    public void setDueDateTime(LocalDateTime dueDateTime) { this.dueDateTime = dueDateTime; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
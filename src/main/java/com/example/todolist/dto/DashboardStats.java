package com.example.todolist.dto;

public class DashboardStats {
    private int tasksCompletedToday;
    private int totalTasksForToday;
    private int completionPercentage;
    private int streakDays;

    // Constructors, Getters, and Setters
    public DashboardStats(int tasksCompletedToday, int totalTasksForToday, int streakDays) {
        this.tasksCompletedToday = tasksCompletedToday;
        this.totalTasksForToday = totalTasksForToday;
        this.streakDays = streakDays;
        if (totalTasksForToday > 0) {
            this.completionPercentage = (int) (((double) tasksCompletedToday / totalTasksForToday) * 100);
        } else {
            this.completionPercentage = 0;
        }
    }

    public int getTasksCompletedToday() { return tasksCompletedToday; }
    public void setTasksCompletedToday(int tasksCompletedToday) { this.tasksCompletedToday = tasksCompletedToday; }
    public int getTotalTasksForToday() { return totalTasksForToday; }
    public void setTotalTasksForToday(int totalTasksForToday) { this.totalTasksForToday = totalTasksForToday; }
    public int getCompletionPercentage() { return completionPercentage; }
    public void setCompletionPercentage(int completionPercentage) { this.completionPercentage = completionPercentage; }
    public int getStreakDays() { return streakDays; }
    public void setStreakDays(int streakDays) { this.streakDays = streakDays; }
}
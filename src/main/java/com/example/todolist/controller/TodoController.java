package com.example.todolist.controller;

import com.example.todolist.model.Habit;
import com.example.todolist.model.Todo;
import com.example.todolist.model.User;
import com.example.todolist.service.HabitService;
import com.example.todolist.service.TodoService;
import com.example.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService userService;
    @Autowired
    private HabitService habitService;

    @GetMapping("/")
    public String viewHomePage(Model model, @RequestParam(name = "priority", required = false) Integer priority) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        Long userId = currentUser.getId();

        List<Todo> todos;
        if (priority != null && priority > 0) {
            todos = todoService.findByUserIdAndPriority(userId, priority);
        } else {
            todos = todoService.findByUserId(userId);
        }

        model.addAttribute("todos", todos);
        model.addAttribute("newTodo", new Todo());
        model.addAttribute("username", auth.getName());
        model.addAttribute("activePriority", priority);
        return "index";
    }
    
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        model.addAttribute("username", auth.getName());
        model.addAttribute("stats", todoService.getDashboardStats(currentUser.getId()));
        return "dashboard";
    }

    @PostMapping("/add-todo")
    public String addTodo(@ModelAttribute("newTodo") Todo todo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        todo.setUser(currentUser);
        todoService.save(todo);
        return "redirect:/";
    }
    
    @PostMapping("/edit-todo")
    public String editTodo(@ModelAttribute("todo") Todo todo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        todo.setUser(currentUser);
        todoService.save(todo);
        return "redirect:/";
    }
    
    @PostMapping("/update-completion")
    public String updateTodoCompletion(@RequestParam("id") Long id, 
                                       @RequestParam(name = "completed", required = false, defaultValue = "false") boolean completed) {
        Todo todo = todoService.findById(id);
        if (todo != null) {
            todo.setCompleted(completed);
            if (completed) {
                todo.setCompletionDate(LocalDate.now());
            } else {
                todo.setCompletionDate(null);
            }
            todoService.save(todo);
        }
        return "redirect:/";
    }

    @GetMapping("/delete-todo/{id}")
    public String deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/calendar")
    public String showCalendarPage(Model model, 
                                   @RequestParam(required = false) Integer year, 
                                   @RequestParam(required = false) Integer month) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        
        LocalDate displayedMonth = LocalDate.now();
        if (year != null && month != null) {
            displayedMonth = LocalDate.of(year, month, 1);
        }
        
        LocalDate firstDayOfMonth = displayedMonth.withDayOfMonth(1);
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        List<LocalDate> calendarDays = new ArrayList<>();
        
        int dayOfWeekValue = startDayOfWeek == 7 ? 0 : startDayOfWeek;
        for (int i = 0; i < dayOfWeekValue; i++) {
            calendarDays.add(firstDayOfMonth.minusDays(dayOfWeekValue - i));
        }

        for (int i = 1; i <= displayedMonth.lengthOfMonth(); i++) {
            calendarDays.add(LocalDate.of(displayedMonth.getYear(), displayedMonth.getMonth(), i));
        }
        
        int gridSize = 42;
        while (calendarDays.size() < gridSize) {
            calendarDays.add(calendarDays.get(calendarDays.size() - 1).plusDays(1));
        }
        
        model.addAttribute("username", auth.getName());
        model.addAttribute("todos", todoService.findByUserId(currentUser.getId()));
        model.addAttribute("displayedMonth", displayedMonth);
        model.addAttribute("calendarDays", calendarDays);
        return "calendar";
    }

    // Reverted showHabitsPage method
    @GetMapping("/habits")
    public String showHabitsPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        
        model.addAttribute("username", auth.getName());
        model.addAttribute("habits", habitService.findByUserId(currentUser.getId()));
        model.addAttribute("newHabit", new Habit());
        model.addAttribute("currentDate", LocalDate.now());

        return "habits";
    }
    
    @PostMapping("/add-habit")
    public String addHabit(@ModelAttribute("newHabit") Habit habit) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        habit.setUser(currentUser);
        habitService.save(habit);
        return "redirect:/habits";
    }
    
    @PostMapping("/habits/toggle")
    public String toggleHabit(@RequestParam("habitId") Long habitId, @RequestParam("date") String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        habitService.toggleHabitCompletion(habitId, date);
        return "redirect:/habits";
    }
}
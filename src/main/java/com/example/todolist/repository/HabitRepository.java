package com.example.todolist.repository;

import com.example.todolist.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    // This query tells Hibernate: "When you get Habits for a user,
    // you MUST also get all of their associated completions in the same database trip."
    @Query("SELECT h FROM Habit h LEFT JOIN FETCH h.completions WHERE h.user.id = :userId ORDER BY h.id")
    List<Habit> findByUserId(@Param("userId") Long userId);

}
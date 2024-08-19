package com.example.todoEntry.repository;

import com.example.todoEntry.model.TodoEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoEntryRepository extends JpaRepository<TodoEntry, Integer> {
}

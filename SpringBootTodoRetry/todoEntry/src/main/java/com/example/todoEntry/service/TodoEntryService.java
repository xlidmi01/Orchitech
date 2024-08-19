package com.example.todoEntry.service;

import com.example.todoEntry.model.TodoEntry;

import java.util.List;

public interface TodoEntryService {
    public String createTodoEntry(TodoEntry todoEntry);
    public String updateTodoEntry(TodoEntry todoEntry);
    public String deleteTodoEntry(int id);
    public TodoEntry getTodoEntry(int id);
    public List<TodoEntry> getAllTodoEntrys();
    public String updateTodoCompletedStatus(int id, boolean completed);
}

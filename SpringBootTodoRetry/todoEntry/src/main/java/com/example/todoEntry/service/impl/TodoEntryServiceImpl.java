package com.example.todoEntry.service.impl;

import com.example.todoEntry.model.TodoEntry;
import com.example.todoEntry.repository.TodoEntryRepository;
import com.example.todoEntry.service.TodoEntryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoEntryServiceImpl implements TodoEntryService {

    TodoEntryRepository todoEntryRepository;

    public TodoEntryServiceImpl(TodoEntryRepository todoEntryRepository) {
        this.todoEntryRepository = todoEntryRepository;
    }

    @Override
    public String createTodoEntry(TodoEntry todoEntry) {
        todoEntryRepository.save(todoEntry);
        return "Success";
    }

    @Override
    public String updateTodoEntry(TodoEntry todoEntry) {
        todoEntryRepository.save(todoEntry);
        return "Success";
    }

    @Override
    public String deleteTodoEntry(int id) {
        todoEntryRepository.deleteById(id);
        return "Success";
    }

    @Override
    public TodoEntry getTodoEntry(int id) {

        return todoEntryRepository.findById(id).get();
    }

    @Override
    public List<TodoEntry> getAllTodoEntrys() {
        return todoEntryRepository.findAll();
    }

    public String updateTodoCompletedStatus(int id, boolean completed) {
        Optional<TodoEntry> existingTodoEntryOptional = todoEntryRepository.findById(id);

        if (existingTodoEntryOptional.isPresent()) {
            TodoEntry existingTodoEntry = existingTodoEntryOptional.get();
            existingTodoEntry.setCompleted(completed);  // Update the completed status
            todoEntryRepository.save(existingTodoEntry);
        } else {
            throw new RuntimeException("TodoEntry not found with id: " + id);
        }
        return "Success";
    }
}

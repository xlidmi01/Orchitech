package com.example.todoEntry.controller;

import com.example.todoEntry.model.TodoEntry;
import com.example.todoEntry.service.TodoEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("todoEntry")
@CrossOrigin("http://localhost:8081/")
public class TodoEntryController {

    TodoEntryService todoEntryService;

    public TodoEntryController(TodoEntryService todoEntryService) {

        this.todoEntryService = todoEntryService;
    }

    //read specific todoEntry
    @GetMapping("{id}")
    public TodoEntry getTodoEntryDetails(@PathVariable("id") int id){

        return todoEntryService.getTodoEntry(id);
    }

    //read all todoEntry
    @GetMapping()
    public List<TodoEntry> getAllTodoEntryDetails(){

        return todoEntryService.getAllTodoEntrys();
    }

    @PostMapping()
    public String createTodoEntryDetails(@RequestBody TodoEntry id){

        todoEntryService.createTodoEntry(id);
        return "todoEntry Created Successfully";
    }

    @PutMapping()
    public String updateTodoEntryDetails(@RequestBody TodoEntry updatedTodoEntry) {
        todoEntryService.updateTodoEntry(updatedTodoEntry);
        return "todoEntry Updated Successfully";
    }

    @PutMapping("/{id}/completed")
    public ResponseEntity<String> updateTodoCompletedStatus(
            @PathVariable("id") int id,
            @RequestBody Boolean completed) {  // Změněno na Boolean místo Map
        try {
            todoEntryService.updateTodoCompletedStatus(id, completed);
            return ResponseEntity.ok("TodoEntry completed status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update completed status");
        }
    }

    @DeleteMapping("{id}")
    public String deleteTodoEntryDetails(@PathVariable("id") int id){

        todoEntryService.deleteTodoEntry(id);
        return "todoEntry Deleted Successfully";
    }

}

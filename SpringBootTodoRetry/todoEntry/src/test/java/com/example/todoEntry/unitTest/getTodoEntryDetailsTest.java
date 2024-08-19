package com.example.todoEntry.unitTest;

import com.example.todoEntry.controller.TodoEntryController;
import com.example.todoEntry.model.TodoEntry;
import com.example.todoEntry.service.TodoEntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class getTodoEntryDetailsTest {

    @Mock
    private TodoEntryService todoEntryService;

    @InjectMocks
    private TodoEntryController todoEntryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTodoEntryDetails() {
        int id = 1;
        TodoEntry todoEntry = new TodoEntry();
        todoEntry.setId(id);

        // Mock the service call
        when(todoEntryService.getTodoEntry(id)).thenReturn(todoEntry);

        // Call the controller method
        TodoEntry result = todoEntryController.getTodoEntryDetails(id);

        // Verify the result and the interaction with the service
        assertEquals(id, result.getId());
        verify(todoEntryService, times(1)).getTodoEntry(id);
    }
}

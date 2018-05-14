package com.example.fabrikam.TodoDemo.UseCases;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import com.example.fabrikam.TodoDemo.Domain.TodoItemRepository;

public class AddANewItemUseCase {

    private TodoItemRepository repository;

    public AddANewItemUseCase(TodoItemRepository repository) {
        this.repository = repository;
    }

    public void handle(TodoItem todoItemToBeCreated) {
        TodoItem item = new TodoItem(todoItemToBeCreated.getCategory(), todoItemToBeCreated.getName());
        repository.save(item);
    }
}

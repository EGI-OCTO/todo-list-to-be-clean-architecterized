package com.example.fabrikam.TodoDemo.UseCases;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import com.example.fabrikam.TodoDemo.Domain.TodoItemRepository;

import java.util.ArrayList;

public class ListAllItemsUseCase {

    private TodoItemRepository repository;

    public ListAllItemsUseCase(TodoItemRepository repository) {
        this.repository = repository;
    }

    public ArrayList<TodoItem> handle() {
        return repository.findAll();
    }
}

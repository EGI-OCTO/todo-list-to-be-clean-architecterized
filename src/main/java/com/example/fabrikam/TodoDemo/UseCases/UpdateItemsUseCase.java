package com.example.fabrikam.TodoDemo.UseCases;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import com.example.fabrikam.TodoDemo.Domain.TodoItemRepository;

import java.util.ArrayList;

public class UpdateItemsUseCase {

    private TodoItemRepository repository;

    public UpdateItemsUseCase(TodoItemRepository repository) {
        this.repository = repository;
    }

    public void handle(ArrayList<TodoItem> requestItems) {
        for (TodoItem requestItem : requestItems) {
            TodoItem item = new TodoItem(requestItem.getCategory(), requestItem.getName());
            item.setComplete(requestItem.isComplete());
            item.setId(requestItem.getId());
            repository.save(item);
        }
    }
}
package com.example.fabrikam.TodoDemo.Infrastructure;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import com.example.fabrikam.TodoDemo.Domain.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class MySQLTodoItemRepository implements TodoItemRepository {

    @Autowired
    private CRUDTodoItemRepository repository;

    public MySQLTodoItemRepository(CRUDTodoItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public TodoItem save(TodoItem todoItem) {
        return repository.save(todoItem);
    }

    @Override
    public ArrayList<TodoItem> findAll() {
        return (ArrayList<TodoItem>) repository.findAll();
    }
}
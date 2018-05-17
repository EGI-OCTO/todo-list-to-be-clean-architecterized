package com.example.fabrikam.TodoDemo.Domain;

import java.util.ArrayList;

public interface TodoItemRepository {
    public TodoItem save(TodoItem todoItem);

    public ArrayList<TodoItem> findAll();
}
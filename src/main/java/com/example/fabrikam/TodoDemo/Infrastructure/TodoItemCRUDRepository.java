package com.example.fabrikam.TodoDemo.Infrastructure;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface TodoItemCRUDRepository extends CrudRepository<TodoItem, Long> {

}
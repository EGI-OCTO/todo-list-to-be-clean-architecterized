package com.example.fabrikam.TodoDemo;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;

import java.util.ArrayList;

import javax.validation.Valid;

public class TodoListViewModel {

	@Valid
	private ArrayList<TodoItem> todoList = new ArrayList<TodoItem>();
	
	public TodoListViewModel() {}
	
	public TodoListViewModel(ArrayList<TodoItem> todoList) {
		this.todoList = todoList;
	}

	public ArrayList<TodoItem> getTodoList() {
		return todoList;
	}

	public void setTodoList(ArrayList<TodoItem> todoList) {
		this.todoList = todoList;
	}
}
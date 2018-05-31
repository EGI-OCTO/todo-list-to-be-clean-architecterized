package com.example.fabrikam.TodoDemo.Infrastructure;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import com.example.fabrikam.TodoDemo.UseCases.AddANewItemUseCase;
import com.example.fabrikam.TodoDemo.UseCases.ListAllItemsUseCase;
import com.example.fabrikam.TodoDemo.UseCases.UpdateItemsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;

@Controller
public class TodoDemoController {

    @Autowired
    private MySQLTodoItemRepository repository;

    @Autowired
    private AddANewItemUseCase addANewItemUseCase;

    @Autowired
    private UpdateItemsUseCase updateTodoItems;

    @Autowired
    private ListAllItemsUseCase listAllItemsUseCase;

    @RequestMapping("/")
    public String index(Model model) {

        ArrayList<TodoItem> todoList = listAllItemsUseCase.handle();

        model.addAttribute("newitem", new TodoItem());
        model.addAttribute("items", new TodoListViewModel(todoList));
        return "index";
    }

    @RequestMapping("/add")
    public String addTodo(@ModelAttribute TodoItem requestItem) {
        addANewItemUseCase.handle(requestItem);
        return "redirect:/";
    }

    @RequestMapping("/update")
    public String updateTodo(@ModelAttribute TodoListViewModel requestItems) {
        updateTodoItems.handle(requestItems.getTodoList());
        return "redirect:/";
    }
}

package com.example.fabrikam.TodoDemo.UseCases;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import com.example.fabrikam.TodoDemo.Domain.TodoItemRepository;
import com.example.fabrikam.TodoDemo.FastTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UpdateItemsUseCaseTests {

    @Test
    @Category(FastTests.class)
    public void should_update_items()
    {
        // given
        TodoItem todoItemToBeUpdated1 = new TodoItem("category1", "name1");
        todoItemToBeUpdated1.setId(1L);
        todoItemToBeUpdated1.setComplete(true);

        TodoItem todoItemToBeUpdated2 = new TodoItem("category2", "name2");
        todoItemToBeUpdated1.setId(2L);
        todoItemToBeUpdated1.setComplete(false);

        ArrayList<TodoItem> itemsToBeUpdated = new ArrayList<TodoItem>();
        itemsToBeUpdated.add(todoItemToBeUpdated1);
        itemsToBeUpdated.add(todoItemToBeUpdated2);

        TodoItemRepository mock = mock(TodoItemRepository.class);
        ArgumentCaptor<TodoItem> argument = ArgumentCaptor.forClass(TodoItem.class);

        UpdateItemsUseCase useCase = new UpdateItemsUseCase(mock);

        // when
        useCase.handle(itemsToBeUpdated);

        // then
        verify(mock, times(2)).save(argument.capture());
        assertEquals(todoItemToBeUpdated1.getCategory(), argument.getAllValues().get(0).getCategory());
        assertEquals(todoItemToBeUpdated1.getName(), argument.getAllValues().get(0).getName());
        assertEquals(todoItemToBeUpdated1.isComplete(), argument.getAllValues().get(0).isComplete());
        assertEquals(todoItemToBeUpdated1.getId(), argument.getAllValues().get(0).getId());

        assertEquals(todoItemToBeUpdated2.getCategory(), argument.getAllValues().get(1).getCategory());
        assertEquals(todoItemToBeUpdated2.getName(), argument.getAllValues().get(1).getName());
        assertEquals(todoItemToBeUpdated2.isComplete(), argument.getAllValues().get(1).isComplete());
        assertEquals(todoItemToBeUpdated2.getId(), argument.getAllValues().get(1).getId());
    }
}
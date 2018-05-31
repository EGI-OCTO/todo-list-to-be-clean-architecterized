package com.example.fabrikam.TodoDemo.UseCases;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import com.example.fabrikam.TodoDemo.Domain.TodoItemRepository;
import com.example.fabrikam.TodoDemo.FastTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AddANewItemUseCaseTests {

    @Test
    @Category(FastTests.class)
    public void should_add_a_new_item()
    {
        // given

        TodoItem todoItemToBeCreated = new TodoItem("myCategory", "myName");
        TodoItemRepository mock = mock(TodoItemRepository.class);
        ArgumentCaptor<TodoItem> argument = ArgumentCaptor.forClass(TodoItem.class);

        AddANewItemUseCase useCase = new AddANewItemUseCase(mock);

        // when
        useCase.handle(todoItemToBeCreated);

        // then
        verify(mock, times(1)).save(argument.capture());
        assertEquals(todoItemToBeCreated.getCategory(), argument.getValue().getCategory());
        assertEquals(todoItemToBeCreated.getName(), argument.getValue().getName());
    }
}
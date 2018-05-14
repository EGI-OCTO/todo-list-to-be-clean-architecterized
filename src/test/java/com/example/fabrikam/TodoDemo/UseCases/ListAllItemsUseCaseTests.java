package com.example.fabrikam.TodoDemo.UseCases;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import com.example.fabrikam.TodoDemo.Domain.TodoItemRepository;
import com.example.fabrikam.TodoDemo.FastTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ListAllItemsUseCaseTests {

    @Test
    @Category(FastTests.class)
    public void should_list_all_items()
    {
        // given
        TodoItemRepository mock = mock(TodoItemRepository.class);

        ListAllItemsUseCase useCase = new ListAllItemsUseCase(mock);

        // when
        useCase.handle();

        // then
        verify(mock, times(1)).findAll();
    }
}

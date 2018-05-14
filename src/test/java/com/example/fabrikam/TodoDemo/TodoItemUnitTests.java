package com.example.fabrikam.TodoDemo;
import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class TodoItemUnitTests {

    @Test
    @Category(FastTests.class)
    public void should_be_exportable_as_string()
    {
        TodoItem item = new TodoItem("myCategory", "myName");
        assertEquals("myCategory", item.getCategory());
    }
}

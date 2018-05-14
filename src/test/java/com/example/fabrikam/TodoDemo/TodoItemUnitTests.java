package com.example.fabrikam.TodoDemo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class TodoItemUnitTest {

    @Test
    public void should_be_exportable_as_string()
    {
        TodoItem item = new TodoItem("myCategory", "myName");
        assertEquals("toto", item.toString());
    }
}

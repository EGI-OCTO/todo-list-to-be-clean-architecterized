package com.example.fabrikam.TodoDemo.Infrastructure;

import com.example.fabrikam.TodoDemo.Domain.TodoItem;
import com.example.fabrikam.TodoDemo.SlowTests;
import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TodoDemoApplicationTests {

    @Autowired
    private CRUDTodoItemRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void beforeTest() {
        repository.deleteAll();
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_create_new_item() {
        // *********************
        //         GIVEN
        // *********************
        TodoItem expectedTodoItem = new TodoItem("myCategory", "myName");

        // Building the request to be fired
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("category", expectedTodoItem.getCategory());
        parts.add("name", expectedTodoItem.getName());

        // *********************
        //         WHEN
        // *********************
        restTemplate.postForObject("/add", parts, String.class);
        ArrayList<TodoItem> todoList = (ArrayList<TodoItem>) repository.findAll();

        // *********************
        //         ASSERT
        // *********************
        assertEquals(1, todoList.size());
        assertEquals(expectedTodoItem.getCategory(), todoList.get(0).getCategory());

        assertEquals(expectedTodoItem.getName(), todoList.get(0).getName());
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_update_item() {
        // *********************
        //         GIVEN
        // *********************
        TodoItem initialTodoItem = new TodoItem("initialCategory", "initialName");
        TodoItem expectedUpdatedTodoItem = new TodoItem("updatedCategory", "updatedName");
        expectedUpdatedTodoItem.setComplete(true);

        // Populate the DB
        MultiValueMap<String, Object> initialTodoItemParts = new LinkedMultiValueMap<String, Object>();
        initialTodoItemParts.add("category", initialTodoItem.getCategory());
        initialTodoItemParts.add("name", initialTodoItem.getName());

        restTemplate.postForObject("/add", initialTodoItemParts, String.class);
        ArrayList<TodoItem> todoList = (ArrayList<TodoItem>) repository.findAll();
        expectedUpdatedTodoItem.setId(todoList.get(0).getId());

        // Building the request to be fired
        MultiValueMap<String, Object> updatedTodoItemParts = new LinkedMultiValueMap<String, Object>();
        updatedTodoItemParts.add("todoList[0].category", expectedUpdatedTodoItem.getCategory());
        updatedTodoItemParts.add("todoList[0].name", expectedUpdatedTodoItem.getName());
        updatedTodoItemParts.add("todoList[0].complete", expectedUpdatedTodoItem.isComplete());
        updatedTodoItemParts.add("todoList[0].id", expectedUpdatedTodoItem.getId());

        // *********************
        //         WHEN
        // *********************
        restTemplate.postForObject("/update", updatedTodoItemParts, String.class);
        Optional<TodoItem> updatedTodoItemFromDB = repository.findById(expectedUpdatedTodoItem.getId());

        // *********************
        //         ASSERT
        // *********************
        assertEquals(expectedUpdatedTodoItem.getId(), updatedTodoItemFromDB.get().getId());
        assertEquals(expectedUpdatedTodoItem.getCategory(), updatedTodoItemFromDB.get().getCategory());
        assertEquals(expectedUpdatedTodoItem.getName(), updatedTodoItemFromDB.get().getName());
        assertEquals(expectedUpdatedTodoItem.isComplete(), updatedTodoItemFromDB.get().isComplete());
    }

    @Test
    @Category(SlowTests.class)
    @DirtiesContext
    public void should_list_all_items() throws IOException {
        // *********************
        //         GIVEN
        // *********************
        TodoItem item1 = new TodoItem("myCategory1", "myName1");
        TodoItem item2 = new TodoItem("myCategory2", "myName2");

        // populate DB
        MultiValueMap<String, Object> item1Parts = new LinkedMultiValueMap<String, Object>();
        item1Parts.add("category", item1.getCategory());
        item1Parts.add("name", item1.getName());

        MultiValueMap<String, Object> item2Parts = new LinkedMultiValueMap<String, Object>();
        item2Parts.add("category", item2.getCategory());
        item2Parts.add("name", item2.getName());

        restTemplate.postForObject("/add", item1Parts, String.class);
        restTemplate.postForObject("/add", item2Parts, String.class);

        ArrayList<TodoItem> todoList = (ArrayList<TodoItem>) repository.findAll();

        // build the expected <table> to be found in the response
        byte[] encoded = Files.readAllBytes(Paths.get("./src/test/resources/templateHtmlToCheck.txt"));
        String htmlTableToBeChecked = new String(encoded, Charset.defaultCharset());
        htmlTableToBeChecked = String.format(
                htmlTableToBeChecked,
                todoList.get(0).getId(),
                item1.getName(),
                item1.getCategory(),
                todoList.get(1).getId(),
                item2.getName(),
                item2.getCategory()
        );

        // *********************
        //         WHEN
        // *********************
        String body = this.restTemplate.getForObject("/", String.class);


        // *********************
        //         ASSERT
        // *********************
        assertTrue(body.contains(htmlTableToBeChecked));
        // 200 OK is checked implicitly by framework
    }
}

package no.hvl.dat250.rest.todos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Rest-Endpoint for todos.
 */
@RestController
public class TodoController {

  public static final String TODO_WITH_THE_ID_X_NOT_FOUND = "Todo with the id %s not found!";

  private Long idCount = 1L;
  private List<Todo> todos = new ArrayList<>();

  @GetMapping("/todos")
  public List<Todo> getTodo() {
    return this.todos;
  }

  @GetMapping("/todos/{id}")
  public Todo getTodoById(@PathVariable Long id) {
    return this.todos.stream()
        .filter(todo -> todo.getId().equals(id))
        .findFirst()
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id)));
  }

  @PostMapping("/todos")
  public Todo create(@RequestBody Todo todo) {
    if (todo.getId() == null) {
      todo.setId(idCount++);
    }

    this.todos.add(todo);
    return todo;
  }

  @PutMapping("/todos/{id}")
  public Todo update(@PathVariable Long id, @RequestBody Todo todo) {
    Todo todoToUpdate = this.getTodoById(id);

    todoToUpdate.setSummary(todo.getSummary());
    todoToUpdate.setDescription(todo.getDescription());

    return todoToUpdate;
  }

  @DeleteMapping("/todos/{id}")
  public void delete(@PathVariable Long id) {
    this.todos.remove(this.getTodoById(id));
  }
}

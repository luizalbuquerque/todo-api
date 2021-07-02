package io.github.luiz.todo.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.luiz.todo.model.Todo;
import io.github.luiz.todo.repository.TodoRepository;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin("http://localhost:4200/")
public class TodoController {
	
	@Autowired
	private TodoRepository repository;
	
	//Operação Salvar, conhecido como POST
	@PostMapping
	public Todo save(@RequestBody Todo todo) {
		return repository.save(todo);
	}
	
	// Retorna todos os registros da tabela do repository
	@GetMapping
	public List<Todo> getAll(){
		return repository.findAll();
	}
	
	//Busca dados de nossa API por ID
	@GetMapping("{id}")
	public Todo getById(@PathVariable Long id) {
		return repository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	// @DeleteMapping (verbo http apropriado para deleção servidor) e PathVariable indica que é receptor via http
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	// Marca o verbo para o path, atualização parcial num recurso.
	// Ficará basicamente assim
	// http://localhost:8080/api/todos/1/done
	@PatchMapping("{id}/done")
	public Todo markAsDone(@PathVariable Long id) {
		return repository.findById(id).map(todo -> {
			todo.setDone(true);
			todo.setDoneDate(LocalDateTime.now());
			repository.save(todo);
			return todo;
		}).orElse(null);
	}
}

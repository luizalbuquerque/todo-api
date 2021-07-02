package io.github.luiz.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.luiz.todo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	
}

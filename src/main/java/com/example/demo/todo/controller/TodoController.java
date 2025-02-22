package com.example.demo.todo.controller;

import com.example.demo.common.consts.Const;
import com.example.demo.todo.dto.*;
import com.example.demo.todo.entity.Todo;
import com.example.demo.todo.service.TodoSerive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoSerive todoSerive;

    @PostMapping("/todos")
    public ResponseEntity<TodoSaveResponseDto> save(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @RequestBody TodoSaveRequestDto dto) {
        return ResponseEntity.ok(todoSerive.save(memberId, dto));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponseDto>> getAll() {
        return ResponseEntity.ok(todoSerive.findAll());
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponseDto> getone(@PathVariable Long todoId) {
        return ResponseEntity.ok(todoSerive.findById(todoId));
    }

    @PutMapping("/todos/{todoId}")
    public ResponseEntity<TodoUpdateResponseDto> update(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @RequestBody TodoUpdateRequestDto dto,
            @PathVariable Long todoId
    ) {
        return ResponseEntity.ok(todoSerive.update(memberId, dto, todoId));
    }

    @DeleteMapping("/todos/{todoId}")
    public void delete(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @PathVariable Long todoId
    ) {
        todoSerive.deleteById(memberId, todoId);
    }
}

package com.example.demo.todo.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.todo.dto.*;
import com.example.demo.todo.entity.Todo;
import com.example.demo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoSerive {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoSaveResponseDto save(Long memberId, TodoSaveRequestDto dto) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException(" 그런 멤버 없당께")
        );

        Todo todo = new Todo(
                dto.getContent(),
                member
        );
        Todo savedTodo = todoRepository.save(todo);
        return new TodoSaveResponseDto(
                savedTodo.getId(),
                savedTodo.getContent(),
                member.getId(),
                member.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> findAll() {
        List<Todo> todos = todoRepository.findAll();
        List<TodoResponseDto> dtos = new ArrayList<>();
        for (Todo todo : todos) {
            dtos.add(new TodoResponseDto(
                    todo.getId(),
                    todo.getContent()
            ));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public TodoResponseDto findById(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("그런 Todo는 없슈이다")
        );
        return new TodoResponseDto(
                todo.getId(),
                todo.getContent()
        );

    }

    @Transactional
    public TodoUpdateResponseDto update(Long memberId, TodoUpdateRequestDto dto, Long todoId) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("그런 멤버 없다고님아")
        );
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("그런 Todo 없다니깐?????")
        );

        if (todo.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("Todo 작성자가 아니유 너 누구유 빨갱이유?");
        }

        todo.update(dto.getContent());
        return new TodoUpdateResponseDto(
                todo.getId(),
                todo.getContent()
        );
    }

    @Transactional
    public void deleteById(Long memberId, Long todoId) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("그런 멤버 없다고님아")
        );
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("그런 Todo 없다니깐?????")
        );

        if (todo.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("Todo 작성자가 아니유 너 누구유 빨갱이유?");
        }

        todoRepository.deleteById(todoId);
    }
}

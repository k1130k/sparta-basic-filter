package com.example.demo.member.dto;

import lombok.Getter;

@Getter
public class MemberSaveResponseDto {

    private final Long id;
    private final String content;

    public MemberSaveResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}

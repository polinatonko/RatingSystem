package com.example.ratingsystem.domain.dtos.registration;

import com.example.ratingsystem.domain.dtos.comment.CommentCreateDto;
import com.example.ratingsystem.domain.dtos.user.UserCreateDto;
import lombok.Data;

@Data
public class CommentRegistrationRequestDto {
    private CommentCreateDto commentDto;
    private UserCreateDto userDto;
}
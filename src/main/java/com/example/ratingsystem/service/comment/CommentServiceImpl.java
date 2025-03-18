package com.example.ratingsystem.service.comment;

import com.example.ratingsystem.domain.dtos.pagination.PageRequestDto;
import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.repository.CommentRepository;
import com.example.ratingsystem.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        validateAuthorAccess(comment.getAuthor());
        return commentRepository.save(comment);
    }

    @Override
    public void delete(UUID id) {
        commentRepository.findById(id)
                .ifPresent(comment -> {
                    validateAuthorAccess(comment.getAuthor());
                    commentRepository.deleteById(id);
                });
    }

    @Override
    public Optional<Comment> get(UUID id) {
        return id != null ? commentRepository.findById(id) : Optional.empty();
    }

    @Override
    public Page<Comment> getBySellerId(UUID sellerId, PageRequestDto pageRequest) {
        return commentRepository.findBySellerId(sellerId, pageRequest.getPageable());
    }

    @Override
    public Page<Comment> getAll(PageRequestDto pageRequestDto) {
        return commentRepository.findAll(pageRequestDto.getPageable());
    }

    private void validateAuthorAccess(User author) {
        if (author == null || !userService.validateAuthenticatedUser(author.getId())) {
            throw new AccessDeniedException("Only author can manage their comments");
        }
    }
}
package com.example.ratingsystem.service.token;

import com.example.ratingsystem.domain.entities.ConfirmEmailEntity;
import com.example.ratingsystem.repository.ConfirmEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmEmailEntityService implements TokenService<ConfirmEmailEntity, String> {
    private final ConfirmEmailRepository confirmEmailRepository;

    @Override
    public void save(ConfirmEmailEntity entity) {
        confirmEmailRepository.save(entity);
    }

    @Override
    public void delete(String id) {
        confirmEmailRepository.deleteById(id);
    }

    @Override
    public boolean isValid(String id, String token) {
        var entity = confirmEmailRepository.findById(id);
        return entity.isPresent() && entity.get().getToken().equals(token);
    }
}
package com.example.ratingsystem.service.token;

import com.example.ratingsystem.domain.entities.PasswordResetEntity;
import com.example.ratingsystem.repository.PasswordResetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetEntityService implements TokenService<PasswordResetEntity, String> {
    private final PasswordResetRepository passwordResetRepository;

    @Override
    public void save(PasswordResetEntity entity) {
        passwordResetRepository.save(entity);
    }

    @Override
    public void delete(String id) {
        passwordResetRepository.deleteById(id);
    }

    @Override
    public boolean isValid(String id, String token) {
        var entity = passwordResetRepository.findById(id);
        return entity.isPresent() && entity.get().getToken().equals(token);
    }
}
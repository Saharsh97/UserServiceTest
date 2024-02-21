package com.scaler.userservice.repositories;

import com.scaler.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    public Token save(Token token);
    public Optional<Token> findTokenByValueAndDeleted(String value, boolean isDeleted);
    public Optional<Token> findTokenByValueAndDeletedAndExpiryAtGreaterThan(String tokenValue, boolean delete, Date expiryAtGreaterThan);
}

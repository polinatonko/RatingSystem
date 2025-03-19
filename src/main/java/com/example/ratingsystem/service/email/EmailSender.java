package com.example.ratingsystem.service.email;

import com.example.ratingsystem.domain.entities.Email;

/**
 * Interface for email senders.
 */
public interface EmailSender {
    void send(Email email);
}
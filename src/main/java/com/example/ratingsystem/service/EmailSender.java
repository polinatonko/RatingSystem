package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.entities.Email;

public interface EmailSender {
    void send(Email email);
}
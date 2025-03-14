package com.example.ratingsystem.service;

public interface EmailSender {
    void sendSimpleEmail(String from, String to, String subject, String text);
}

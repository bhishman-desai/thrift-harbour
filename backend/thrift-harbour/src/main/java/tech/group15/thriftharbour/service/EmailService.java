package tech.group15.thriftharbour.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}

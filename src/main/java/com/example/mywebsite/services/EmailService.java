package com.example.mywebsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Async("emailExecutor")
    public void sendBookingConfirmation(String toEmail, String userName, 
                                        String filmTitle, String sessionTime, 
                                        int hallNumber, String seatsInfo, String key) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@cinema.com");
        message.setTo(toEmail);
        message.setSubject("Подтверждение бронирования - Кинотеатр CINEMA");
        
        String text = String.format(
            "Здравствуйте, %s!\n\n" +
            "Вы успешно забронировали билеты в кинотеатре CINEMA.\n\n" +
            "🎬 Фильм: %s\n" +
            "⏰ Время: %s\n" +
            "🎫 Зал: %d\n" +
            "🪑 Места: %s\n\n" +
            "Пожалуйста, прибудьте в кинотеатр за 15 минут до начала сеанса.\n" +
            "Для входа покажите это письмо или назовите имя.\n\n" +
            "Ключ для отмены бронирования: %s\n\n" +
            "С уважением,\n" +
            "Кинотеатр CINEMA",
            userName, filmTitle, sessionTime, hallNumber, seatsInfo, key
        );
        
        message.setText(text);
        
        try {
            mailSender.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    @Async("emailExecutor")
    public void sendBookingCancelReservation(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@cinema.com");
        message.setTo(toEmail);
        message.setSubject("Подтверждение отмены бронирования - Кинотеатр CINEMA");
        
        String text = String.format(
            "Здравствуйте!\n\n" +
            "Вы успешно отменили бронирование билетов в кинотеатре CINEMA.\n\n" +
            "С уважением,\n" +
            "Кинотеатр CINEMA"
        );
        
        message.setText(text);
        
        try {
            mailSender.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
    
}
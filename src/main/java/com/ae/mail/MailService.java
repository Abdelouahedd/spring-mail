package com.ae.mail;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
  JavaMailSender javaMailSender;

  public void sendMail(Email email) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(email.getFrom());
    mailMessage.setTo(email.getTo());
    mailMessage.setSubject(email.getSubject());
    mailMessage.setText(email.getContent());
    javaMailSender.send(mailMessage);
  }

}

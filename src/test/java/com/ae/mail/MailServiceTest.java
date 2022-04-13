package com.ae.mail;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Log4j2
class MailServiceTest {

  @RegisterExtension
  static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP).withConfiguration(GreenMailConfiguration.aConfig().withUser("john Doe", "password123")).withPerMethodLifecycle(false);

  @Autowired
  private MailService mailService;


  @Test
  void sendMail() throws MessagingException, IOException {
    Email email = new Email();
    email.setContent("Hello world !!");
    email.setSubject("Hello world");
    email.setTo("reciever@hotmail.fr");
    email.setFrom("sender@hotmail.fr");
    mailService.sendMail(email);
    MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
    assertEquals(1, greenMail.getReceivedMessages().length);
    assertEquals("Hello world", receivedMessage.getSubject());
    assertEquals("Hello world !!\n".trim(), receivedMessage.getContent().toString().trim());
  }
}

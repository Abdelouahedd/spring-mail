package com.ae.mail;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
@Log4j2
class MailServiceTest {

  @RegisterExtension
  static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP).withConfiguration(GreenMailConfiguration.aConfig().withUser("john Doe", "password123")).withPerMethodLifecycle(false);

  @Autowired
  private MailService mailService;


  @Test
  void sendMail() {
    Email email = new Email();
    email.setContent("Hello world !!");
    email.setSubject("Hello world");
    email.setTo("reciever@hotmail.fr");
    email.setFrom("sender@hotmail.fr");
    mailService.sendMail(email);
    await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
      MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
      assertEquals(1, greenMail.getReceivedMessages().length);
      assertEquals("Hello world", receivedMessage.getSubject());
      assertEquals("Hello world !!\n".trim(), receivedMessage.getContent().toString().trim());
    });
  }
}

package com.ae.mail;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/mail")
public class MailController {

  private final MailService mailService;

  @GetMapping("/send")
  public void sendMail(@RequestBody Email email) {
    mailService.sendMail(email);
  }
}


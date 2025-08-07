package com.example.notify;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "invoiceemailservice")
public interface MailClient {

    @PostMapping("api/email/send")
    String sendMail(@RequestBody MailRequest mail);
}

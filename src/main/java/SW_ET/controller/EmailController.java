package SW_ET.controller;/*
package SW_ET.controller;

import SW_ET.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final MailService mailService;

    @Autowired
    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send-verification-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestParam String email) {
        try {
            String title = "Verify Your Email Address";
            String text = "Thank you for registering. Please click on the below link to verify your email address: [Verification Link]";
            mailService.sendEmail(email, title, text);
            return ResponseEntity.ok("Verification email sent successfully to: " + email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send verification email.");
        }
    }
}
*/

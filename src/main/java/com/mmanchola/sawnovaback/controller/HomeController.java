package com.mmanchola.sawnovaback.controller;

import com.mmanchola.sawnovaback.exception.ApiRequestException;
import com.mmanchola.sawnovaback.model.ContactForm;
import com.mmanchola.sawnovaback.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@Slf4j
@RequestMapping("/")
public class HomeController {

    private final JavaMailSender mailSender;
    private final MessageSource messages;
    private final Environment env;
    private AdminUserService adminUserService;

    @Autowired
    public HomeController(JavaMailSender javaMailSender, @Qualifier("messageSource") MessageSource messages,
                          Environment env, AdminUserService adminUserService) {
        this.mailSender = javaMailSender;
        this.messages = messages;
        this.env = env;
        this.adminUserService = adminUserService;
    }

    @GetMapping(value = {"home", ""})
    public String showHome(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "index";
    }

    @GetMapping(value = {"services"})
    public String showServices(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "services";
    }

    @GetMapping(value = {"contact"})
    public String showContact(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }

    @PostMapping(value = "contact",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendContactForm(@Valid @RequestBody ContactForm contactForm,
                                             Errors errors) {
        log.info("Sending contact form...");
        // If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body("Bad request");
        }
        try {
            for (String adminEmail : adminUserService.getAdmins()) {
                mailSender.send(constructContactEmail(
                        new Locale("en", "US"), contactForm, adminEmail)
                );
            }
        } catch (MailException e) {
            log.error("Contact form failed to be sent", e);
            throw new ApiRequestException("Error while sending email: ", e);
        }
        return ResponseEntity.ok("Email has been successfully sent");
    }

    private SimpleMailMessage constructContactEmail(Locale locale, ContactForm contactForm, String adminEmail) {
        String subject = "New email from " + contactForm.getName();
        contactForm.setSubject(subject);
        String message = messages.getMessage("message.newContactForm", null, locale) +
                "\n\n" + contactForm.toString();
        return constructEmail(subject, message, adminEmail);
    }

    private SimpleMailMessage constructEmail(String subject, String body, String emailAddress) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(emailAddress);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    @GetMapping(value = {"gallery"})
    public String showGallery(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "gallery";
    }
}

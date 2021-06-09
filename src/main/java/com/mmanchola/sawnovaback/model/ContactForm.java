package com.mmanchola.sawnovaback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ContactForm {
    @NotBlank(message = "username cannot be empty")
    private String name;
    @NotBlank(message = "email cannot be empty")
    private String email;
    @NotBlank(message = "phone number cannot be empty")
    private String phone;
    private String subject;
    @NotBlank(message = "message cannot be empty")
    private String message;

    @Override
    public String toString() {
        return "From: " + this.name + "\n" +
                "Email: " + this.email + "\n\n" +
                "Phone Number: " + this.phone + "\n\n" +
                "Subject: " + this.subject + "\n" +
                "Message: " + this.message;
    }
}

package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvioEmail {

    //Importante hacer la inyección de dependencia de JavaMailSender:
    @Autowired
    private JavaMailSender mailSender;

    //Pasamos por parametro: destinatario, asunto y el mensaje
    public void sendEmail(List<String> correos, String subject, String content) {



        for (String c : correos)
        {
            SimpleMailMessage email = new SimpleMailMessage();

            //recorremos la lista y enviamos a cada cliente el mismo correo;
            email.setTo(c);
            email.setSubject(subject);
            email.setText(content);

            mailSender.send(email);
        }
    }

}

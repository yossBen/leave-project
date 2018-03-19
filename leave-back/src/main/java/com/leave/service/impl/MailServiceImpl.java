package com.leave.service.impl;

import com.leave.service.MailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MailServiceImpl implements MailService {
    private final static Logger LOGGER = Logger.getLogger(MailServiceImpl.class.getName());

    @Autowired
    private JavaMailSender mailSender;
    @Value("${smtp.mail.from}")
    private String from;

    @Override
    public void sendEmail(String subject, String body, String[] to, String[] ccList, File[] files) {
        MimeMessagePreparator preparator = getMessagePreparator(subject, body, to, ccList, files);
        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private MimeMessagePreparator getMessagePreparator(String subject, String body, String[] to, String[] ccList, File[] files) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage message) throws Exception {
                message.setFrom(from);

                // Set TO
                if (to != null && to.length > 0) {
                    Address[] adressList = new Address[to.length];
                    for (int i = 0; i < to.length; i++) {
                        if (StringUtils.isNotBlank(to[i])) {
                            adressList[i] = new InternetAddress(to[i].trim());
                        }
                    }
                    message.setRecipients(Message.RecipientType.TO, adressList);
                } else {
                    LOGGER.severe("La liste des destinataire est null ou vide , merci de renseigner un ou plusieurs destinataires");
                    return;
                }

                // Set CC List
                if (ccList != null && ccList.length > 0) {
                    Address[] adressList = new Address[ccList.length];
                    for (int i = 0; i < ccList.length; i++) {
                        if (StringUtils.isNotBlank(ccList[i])) {
                            adressList[i] = new InternetAddress(ccList[i].trim());
                        }
                    }
                    message.setRecipients(Message.RecipientType.CC, adressList);
                }

                Multipart multipart = new MimeMultipart();
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(body, "text/html; charset=" + StandardCharsets.UTF_8);
                multipart.addBodyPart(bodyPart);
                message.setContent(multipart);

                if (files != null && files.length > 0) {
                    for (File file : files) {
                        bodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(file.getAbsolutePath());
                        bodyPart.setDataHandler(new DataHandler(source));
                        bodyPart.setFileName(file.getName());
                        multipart.addBodyPart(bodyPart);
                    }
                }
                message.setSubject(subject);
            }
        };
        return preparator;
    }
}
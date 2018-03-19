package com.leave.service;

import java.io.File;

public interface MailService {
    void sendEmail(String subject, String body, String[] to, String[] ccList, File[] files);
}
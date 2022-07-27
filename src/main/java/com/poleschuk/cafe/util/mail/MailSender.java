package com.poleschuk.cafe.util.mail;


import com.poleschuk.cafe.exception.UtilException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * MailSender class encapsulates a mail subject, mail text and mail receiver.
 */
public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private MimeMessage message;
    private String sendToMail;
    private String mailSubject;
    private String mailText;
    private Properties properties;

    /**
     * Instantiates a new Mail sender.
     *
     * @param sendToMail  send to mail
     * @param mailSubject the mail subject
     * @param mailText    the mail text
     * @param properties  the properties
     */
    public MailSender(String sendToMail, String mailSubject, String mailText, Properties properties) {
        this.sendToMail = sendToMail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.properties = properties;
    }

    /**
     * Send a mail.
     */
    public void send() {
        try{
            initMessage();
            Transport.send(message);
            logger.log(Level.INFO,"Success");
        } catch ( AddressException e) {
            logger.log(Level.ERROR,"Invalid address: " + sendToMail + " " + e);
        } catch (UtilException | MessagingException e) {
            logger.log(Level.ERROR, "Error generating or sending message:" + e);
        }
    }

    private void initMessage() throws UtilException{
        Session mailSession = SessionFactory.createSession(properties);
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        try {
            message.setSubject(mailSubject);
            message.setText(mailText);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToMail));
        } catch (MessagingException e) {
            throw new UtilException("Message exception in set methods: initMessage()");
        }

    }
}
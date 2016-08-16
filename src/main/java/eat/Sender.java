package main.java.eat;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by atarasevich on 08.08.16.
 */
public class Sender {
    final static Logger logger = Logger.getLogger(Sender.class);
    private String username;
    private String password;
    private Properties properties;

    public Sender(String serv, String username, String password) {
        this.username = username;
        this.password = password;

        properties = new Properties();
        if(serv.equals("gmail")) {
            //Настройки для отправки с google почты
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.starttls.enable","true");
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.port","587");
            properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
        } else {
            //Настройки для отправки почты с помощью почтового сервера hMailServer
            properties.put("mail.transport.protocol","smtp");
            properties.put("mail.smtp.host","192.168.23.139");
            properties.put("mail.smtp.port","25");
        }
    }

    public void send(String serv, String subject, String text, String fromEmail, String toEmail){

        logger.info("Open session");
        Session session = null;
        if(serv.equals("gmail")) {
            //Под gmail только такая авторизация, в getInstance вторым парамметром должна идти аутентификация
            try {
                session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
                });
            } catch (Exception e) {
                logger.error("Error, doesn't open session!");
            }
        } else {

            try {
                session = Session.getDefaultInstance(properties, null);
            } catch (Exception e) {
                logger.error("Error, doesn't open session!");
            }
        }


        try {
            logger.info("Create message");
            Message message = new MimeMessage(session);
            //От кого
            logger.info("Set field FROM");
            message.setFrom(new InternetAddress(fromEmail));
            //Кому
            logger.info("Set field TO");
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //Заголовок письма
            logger.info("Set field subject");
            message.setSubject(subject);
            //Текст письма
            logger.info("Set field text");
            message.setText(text);
            //Отправляем сообщение
            logger.info("Transport mail");
            Transport.send(message);
        } catch (MessagingException e) {
            logger.info("Error, mail doesn't send");
            e.printStackTrace();
        }

    }
}

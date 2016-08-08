package main.java.eat;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by atarasevich on 08.08.16.
 */
public class Sender {
    private String username;//shop.meduza@gmail.com
    private String password;//a741852963
    private Properties properties;

    public Sender(String username, String password) {
        this.username = username;
        this.password = password;

        properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
    }

    public void send(String subject, String text, String fromEmail, String toEmail){
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            //От кого
            message.setFrom(new InternetAddress(fromEmail));
            //Кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //Заголовок письма
            message.setSubject(subject);
            //Текст письма
            message.setText(text);
            //Отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}

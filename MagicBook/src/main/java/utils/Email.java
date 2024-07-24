package utils;

import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler;
import javax.activation.DataSource;

public class Email {
    //Email: magicbooktest01@gmail.com
    //Pass: eijrvzerdmcyvtyw

    public static boolean sendEmail(String to, String title, String content) {
        final String from = "magicbooktest01@gmail.com";
        final String password = "eijrvzerdmcyvtyw";

        // Properties: khai bao cac thuoc tinh
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOSTT
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(from, password);
            }
        };

        //Phiên làm việc
        Session session = Session.getInstance(props, auth);

        //Tạo một tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            // Kiểu nội dung
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            // Người gửi
            msg.setFrom(from);
            // Người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            //Tiêu đề email
            msg.setSubject("Notification from Magic Book");
            // Quy định ngày gửi
            msg.setSentDate(new Date());
            //Quy định emmail nhận phản hồi
            msg.setReplyTo(new InternetAddress[]{new InternetAddress("noreply@example.com")});

            //Nội dung
            msg.setContent("<html><body><h1>"+content+"</h1></body></html>", "text/HTML; charset=UTF-8");

            // Gửi email
            Transport.send(msg);
            System.out.println("Send email successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("Error during sending email!");
            e.printStackTrace();
            return false;
        }      
    }

}
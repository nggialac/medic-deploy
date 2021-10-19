package com.abc.service;

//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class    MailService  {

    public boolean sendEmail(String subject, String message, String to) {
        boolean foo = false; // Set the false, default variable "foo", we will allow it after sending code process email

        String senderEmail = "lacnguyenspringmvc1@gmail.com"; // your gmail email id
        String senderPassword = "Llacnguyenspringmvc123"; // your gmail id password

        // Properties class enables us to connect to the host SMTP server
        Properties properties = new Properties();

        // Setting necessary information for object property

        // Setup host and mail server
        properties.put("mail.smtp.auth", "true"); // enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); // enable TLS-protected connection
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Mention the SMTP server address. Here Gmail's SMTP server is being used to send email
        properties.put("mail.smtp.port", "587"); // 587 is TLS port number

        // get the session object and pass username and password
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            MimeMessage msg = new MimeMessage(session); // Create a default MimeMessage object for compose the message

            msg.setFrom(new InternetAddress(senderEmail)); // adding sender email id to msg object

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // adding recipient to msg object

            msg.setSubject(subject); // adding subject to msg object
            msg.setText(message); // adding text to msg object

            Transport.send(msg); // Transport class send the message using send() method
            System.out.println("Email Sent Wtih Attachment Successfully...");

            foo = true; // Set the "foo" variable to true after successfully sending emails

        } catch (Exception e) {

            System.out.println("EmailService File Error" + e);
        }
        return foo; // and return foo variable
    }

//    public boolean sendHtmlTemplate(String subject, String message, String to)
//    {
//        boolean foo = false; // Set the false, default variable "foo", we will allow it after sending code process email
//
//        String senderEmail = ""; // your gmail email id
//        String senderPassword = ""; // your gmail id password
//
//        //Properties class enables us to connect to the host SMTP server
//        Properties properties = new Properties();
//
//        // Setting necessary information for object property
//
//        // Setup host and mail server
//        properties.put("mail.smtp.starttls.enable", "true"); // enable TLS-protected connection
//        properties.put("mail.smtp.host", "smtp.gmail.com"); // Mention the SMTP server address. Here Gmail's SMTP server is being used to send email
//        properties.put("mail.smtp.port", "587"); // 587 is TLS port number
//
//        // get the session object and pass username and password
//        Session session = Session.getDefaultInstance(properties, new Authenticator()
//        {
//            protected PasswordAuthentication getPasswordAuthentication(){
//
//                return new PasswordAuthentication(senderEmail, senderPassword);
//            }
//        });
//
//        try {
//
//            MimeMessage msg = new MimeMessage(session); // Create a default MimeMessage object for compose the message
//
//            MimeMessageHelper helper = new MimeMessageHelper(msg, true); // create MimeMessageHelper class
//
//            helper.setFrom(new InternetAddress(senderEmail)); // adding sender email id to helper object
//
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); //adding recipient to msg object
//
//            helper.setSubject(subject); // adding subject to helper object
//
//            MimeMultipart mimeMultipart = new MimeMultipart(); // create MimeMultipart object
//
//            MimeBodyPart textMime = new MimeBodyPart(); // create first MimeBodyPart object textMime for containing the message
//
//            MimeBodyPart messageBodyPart = new MimeBodyPart(); // create second MimeBodyPart object messageBodyPart for containing the html format data
//
//            textMime.setText(message); // sets message to textMime object
//
//            // create message within html format tag and assign to the content variable
//            String content = "<br><br><b>Hi friends</b>,<br><i>This email is HTML template style</i>";
//
//            // sets html format content to the messageBodyPart object
//            messageBodyPart.setContent(content,"text/html; charset=utf-8");
//
//            //The mimeMultipart adds textMime and messageBodypart to the
//            mimeMultipart.addBodyPart(textMime);
//            mimeMultipart.addBodyPart(messageBodyPart);
//
//            msg.setContent(mimeMultipart); // Set the mimeMultipart the contents of the msg
//
//            Transport.send(msg); // Transport class send the message using send() method
//            System.out.println("Email Sent With HTML Template Style Successfully...");
//
//            foo = true; // Set the "foo" variable to true after successfully sending emails
//
//        }catch(Exception e){
//            System.out.println("EmailService File Error"+ e);
//        }
//        return foo; //and return foo variable
//    }

}

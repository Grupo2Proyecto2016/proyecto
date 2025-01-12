package com.springmvc.logic.utils;

import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author doraemon
 */
class GoogleMail extends Thread
{
	String RecipientEmail;
	String CCEmail;
	String Title;
	String Msg;
	
    /**
     * Send email using GMail SMTP server.
     *
     * @param username GMail username
     * @param password GMail password
     * @param recipientEmail TO recipient
     * @param ccEmail CC recipient. Can be empty if there is no CC recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
     */
    @SuppressWarnings("restriction")
	public GoogleMail(String recipientEmail, String ccEmail, String title, String message)
	{
    	this.RecipientEmail = recipientEmail;
    	this.CCEmail = ccEmail;
    	this.Title = title;
    	this.Msg = message;
	}
    
	public void run()
    {
    	try 
    	{
	        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	        String username = "proyectotecnologo2016@gmail.com";
	        String password = "fksjdhf92yefklsj48dkdo";
	        // Get a Properties object
	        Properties props = System.getProperties();
	        props.setProperty("mail.smtps.host", "smtp.gmail.com");
	        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
	        props.setProperty("mail.smtp.port", "465");
	        props.setProperty("mail.smtp.socketFactory.port", "465");
	        props.setProperty("mail.smtps.auth", "true");
	
	        /*
	        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
	        to true (the default), causes the transport to wait for the response to the QUIT command.
	
	        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
	                http://forum.java.sun.com/thread.jspa?threadID=5205249
	                smtpsend.java - demo program from javamail
	        */
	        props.put("mail.smtps.quitwait", "false");
	
	        Session session = Session.getInstance(props, null);
	
	        // -- Create a new message --
	        final MimeMessage msg = new MimeMessage(session);

	        // -- Set the FROM and TO fields --
	        msg.setFrom(new InternetAddress(username));
	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RecipientEmail, false));
	
	        if (CCEmail.length() > 0) {
	            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CCEmail, false));
	        }
	
	        msg.setSubject(Title);
	        msg.setText(Msg, "utf-8");
	        msg.setSentDate(new Date());
	
	        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
	
	        t.connect("smtp.gmail.com", username, password);
	        t.sendMessage(msg, msg.getAllRecipients());      
	        t.close();
	        System.out.println("Correo enviado a " + RecipientEmail);
        }
    	catch(Exception e)
        {
        	System.out.println(e);
        }
    }
}
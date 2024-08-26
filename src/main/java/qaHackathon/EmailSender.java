package qaHackathon;



	

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


	import java.util.Properties;

	public class EmailSender {

	    public static void main(String[] args) {
	    	
	    	String host = "smtp.gmail.com";
	        final String user = "babuar29@gmail.com";
	        final String password = "";

	        String to = "babuar29@gmail.com";

	        // Setup mail server properties for SSL
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587"); // Port for TLS
	        props.put("mail.smtp.starttls.enable", "true"); // Enable TLS
	        props.put("mail.smtp.auth", "true"); // Enable authentication

	        // Get the default Session object.
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(user, password);
	            }
	        });
	        session.setDebug(true);

	        try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field
	            message.setFrom(new InternetAddress(user));

	            // Set To: header field
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            // Set Subject: header field
	            message.setSubject("Subject Line");

	            // Now set the actual message
	            message.setText("This is the actual message");

	            // Send message
	            Transport.send(message);
	            System.out.println("Sent message successfully...");
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }
	    }
	    	

		
	    	
	   
	

}

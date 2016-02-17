package homework1;



import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.datastore.Entity;

import com.google.appengine.api.datastore.Key;

import com.google.appengine.api.datastore.KeyFactory;

import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;

import com.googlecode.objectify.ObjectifyService;

import homework1.NewBlogpost;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class MailUpdate extends HttpServlet {
	static int blogCount = 0;
	public static int testCount = 0;
	public static String testString;
	public static String testString2;
	//public static List<User> subscribers = new ArrayList<User>();

public void doGet(HttpServletRequest req, HttpServletResponse resp){
	/*testCount += 1;
	Properties props1 = new Properties();
	Session session1 = Session.getDefaultInstance(props1, null);
	try {
	    Message msg = new MimeMessage(session1);
	    msg.setFrom(new InternetAddress("taiyi.o@gmail.com", "Example.com Admin"));
	    msg.addRecipient(Message.RecipientType.TO,
	     new InternetAddress("taiyi.o@gmail.com", "Mr. User"));
	    msg.setSubject("Your Example.com account has been activated");
	    msg.setText("Sup");
	    Transport.send(msg);

	} catch (AddressException e) {
		testCount = 1000;
	} catch (MessagingException  e){
		testCount = 2000;
	} catch (UnsupportedEncodingException e){
		testCount = 3000;
	} catch (Exception e){
		testCount = 4000;
	}*/
	ObjectifyService.register(Subscriber.class);
	List<Subscriber> subscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();
	if (subscribers.size() > 0){
testCount = 1;
		ObjectifyService.register(NewBlogpost.class);
	    List<NewBlogpost> newBlogposts = ObjectifyService.ofy().load().type(NewBlogpost.class).list();   
	    if (newBlogposts.size() > blogCount){
testCount = 2;
		    Collections.sort(newBlogposts); 
		    StringBuilder updateMessage = new StringBuilder();;
		    for (int i = blogCount; i < newBlogposts.size(); i += 1){
		    		updateMessage.append(newBlogposts.get(i).getContent() + "<br />\n");
		    		updateMessage.append("User: " + newBlogposts.get(i).getUser().getEmail() + "<br />\n");
		    		updateMessage.append("Date: " + newBlogposts.get(i).getDate() + "<br />\n<br />\n");
		    	blogCount += 1;
		    }	    
	    	InternetAddress[] cc = new InternetAddress[subscribers.size()];
	    	String subscriberEmails = "";
	    	for (int i = 0; i < subscribers.size(); i += 1){
	    		subscriberEmails = subscriberEmails + subscribers.get(i).getEmail() + ",";
	    	}
testString2 = subscriberEmails;
	    	try {
				cc = InternetAddress.parse(subscriberEmails);
			} catch (AddressException e1) {
				testCount = 1000;
			}
	    	Properties props = new Properties();
	    	Session session = Session.getDefaultInstance(props, null);
	    	Message msg = new MimeMessage(session);
	    	try{
	    		String sendMessage = updateMessage.toString();
	    		msg.setFrom(new InternetAddress("taiyi.o@gmail.com"));
	    		msg.addRecipients(Message.RecipientType.CC, cc);
	    		msg.setSubject("Updated subscription from Modular Conduit");
	    		msg.setContent(sendMessage, "text/html");
	    		Transport.send(msg);
	    	} catch (Exception e){
	    		testCount = 2000;
	    	}
	    }
	}
}

@Override
public void doPost(HttpServletRequest req, HttpServletResponse resp){
	doGet(req, resp);
}
}
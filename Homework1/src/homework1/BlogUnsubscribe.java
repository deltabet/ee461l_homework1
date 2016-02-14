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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



 

@SuppressWarnings("serial")
public class BlogUnsubscribe extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	System.out.println("1User has unsubscribed");
    	UserService userService = UserServiceFactory.getUserService();
    	User user = userService.getCurrentUser();
    	ObjectifyService.register(Subscriber.class);
    	Subscriber curSubscriber = new Subscriber(user.getEmail());
    	List<Subscriber> subscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();
    	Collections.sort(subscribers);
    	for (Subscriber subscriber : subscribers){
    		if (subscriber.getEmail().equals(user.getEmail())){
    			System.out.println(subscriber.getEmail() + user.getEmail());
    			curSubscriber = subscriber;
    			break;
    		}
    	}
    	
    	ofy().delete().entity(curSubscriber).now();

        /*try{
        	MailUpdate.subscribers.remove(userService.getCurrentUser());
        	//System.out.println(userService.getCurrentUser().getEmail() + " has unsubscribed");
        	//RequestDispatcher view = req.getRequestDispatcher("");
        	//view.forward(req, resp);
        	
        }catch (Exception e){}*/
    	subscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();
    	System.out.println("size" + subscribers.size());
    	 for (Subscriber subscriber : subscribers){
         	System.out.println(subscriber.getEmail());
         }
        resp.sendRedirect("/");
    }

}
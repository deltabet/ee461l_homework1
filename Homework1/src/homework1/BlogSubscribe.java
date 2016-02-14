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
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;


 

@SuppressWarnings("serial")
public class BlogSubscribe extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {
    	UserService userService = UserServiceFactory.getUserService();
    	User user = userService.getCurrentUser();
    	
    	ObjectifyService.register(Subscriber.class);
    	Subscriber newSubscriber = new Subscriber(user.getEmail());
    	ofy().save().entity(newSubscriber).now();
    	
       // MailUpdate.subscribers.add(user);
        
        System.out.println(user.getEmail() + " has subscribed");
        List<Subscriber> subscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();
        //for (Subscriber subscriber : subscribers){
        	//ofy().delete().entity(subscriber);
        //}
        Collections.sort(subscribers);
        System.out.println("size" + subscribers.size());
        for (Subscriber subscriber : subscribers){
        	System.out.println(subscriber.getEmail());
        }
        //try{
        //RequestDispatcher view = req.getRequestDispatcher("");
    	//view.forward(req, resp);
       
        //} catch (Exception e){}
        resp.sendRedirect("/");
    }

}
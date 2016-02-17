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

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

 

import java.io.IOException;

import java.util.Date;

 

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

 

@SuppressWarnings("serial")
public class Homework1Sign extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();

 

        // We have one entity group per Guestbook with all Greetings residing

        // in the same entity group as the Guestbook to which they belong.

        // This lets us run a transactional ancestor query to retrieve all

        // Greetings for a given Guestbook.  However, the write rate to each

        // Guestbook should be limited to ~1/second.

        ObjectifyService.register(NewBlogpost.class);
        String content = req.getParameter("content");
        String title = req.getParameter("title");
        Date date = new Date();
        NewBlogpost newBlogpost = new NewBlogpost(user, content, title);
        ofy().save().entity(newBlogpost).now();
        
        List<NewBlogpost> subscribers = ObjectifyService.ofy().load().type(NewBlogpost.class).list();
        
    	System.out.println("size" + subscribers.size());
    	 for (NewBlogpost subscriber : subscribers){
         	System.out.println(subscriber.getContent());
         }
        resp.sendRedirect("/");

    }

}
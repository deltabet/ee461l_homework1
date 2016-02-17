package homework1;

import java.util.Date;

 

import com.google.appengine.api.users.User;

import com.googlecode.objectify.annotation.Entity;

import com.googlecode.objectify.annotation.Id;

 

 

@Entity

public class NewBlogpost implements Comparable<NewBlogpost> {

    @Id Long id;

    User user;

    String content;

    Date date;
    
    String title;

    private NewBlogpost() {}

    public NewBlogpost(User user, String content, String Title) {

        this.user = user;

        this.content = content;

        date = new Date();
        
        this.title = Title;
        
        //System.out.println("Title: " + title);

    }

    public User getUser() {
    
        return user;

    }

    public String getContent() {

        return content;

    }
    
    public String getTitle(){
    	return title;
    }
    
    public String getDate(){
    	String dateTemp = date.toString();
    	return dateTemp.substring(0, 3) + ", " + dateTemp.substring(4, 10);
    }

    @Override

    public int compareTo(NewBlogpost other) {

        if (date.after(other.date)) {

            return 1;

        } else if (date.before(other.date)) {

            return -1;

        }

        return 0;

    }

}
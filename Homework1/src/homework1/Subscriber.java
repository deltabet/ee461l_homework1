package homework1;

import java.util.Date;

 

import com.google.appengine.api.users.User;

import com.googlecode.objectify.annotation.Entity;

import com.googlecode.objectify.annotation.Id;

 

 

@Entity

public class Subscriber implements Comparable<Subscriber> {

    @Id Long id;

    
    String email;

    private Subscriber() {}

    public Subscriber(String email1) {

        email = email1;
        //System.out.println("Title: " + title);

    }

    
    public String getEmail(){
    	return email;
    }

@Override
    public int compareTo(Subscriber other) {

       return this.email.compareTo(other.email);
    }


}
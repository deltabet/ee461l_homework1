package homework1;



 



import com.googlecode.objectify.annotation.Entity;

import com.googlecode.objectify.annotation.Id;

 

 

@Entity

public class BlogCount implements Comparable<BlogCount> {

    @Id Long id;

    
    int count;

    private BlogCount() {}

    public BlogCount(int i) {

        count = i;
        //System.out.println("Title: " + title);

    }

    
   public int getCount(){
	   return count;
   }

@Override
    public int compareTo(BlogCount other) {

       if (this.count > other.count){
    	   return 1;
       }
       else if (this.count < other.count){
    	   return -1;
       }
       else{
    	   return 0;
       }
    }


}
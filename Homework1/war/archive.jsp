<%@ page import="java.util.Collections" %>

<%@ page import="com.googlecode.objectify.*" %>

<%@ page import="homework1.NewBlogpost" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>

<%@ page import="com.google.appengine.api.users.User" %>

<%@ page import="com.google.appengine.api.users.UserService" %>

<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>

<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>

<%@ page import="com.google.appengine.api.datastore.Query" %>

<%@ page import="com.google.appengine.api.datastore.Entity" %>

<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>

<%@ page import="com.google.appengine.api.datastore.Key" %>

<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

  <head>
   <link type="text/css" rel="stylesheet" href="/stylesheets/????????.css" />
 </head>
 
   <body>
  <%

    UserService userService = UserServiceFactory.getUserService();

    User user = userService.getCurrentUser();

    if (user != null) {

      pageContext.setAttribute("user", user);

%>

<p>

<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<a href = "/"> Home</a> </br>
<a href = "/new"> New Post</a> </br>
</br>
<%

    } else {

%>

<p>

<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
<a href = "/"> Home</a> </br>
</p>
</br>


<%

    }

%>

<%
String homework1Name = request.getParameter("homework1Name");

if (homework1Name == null) {

    homework1Name = "default";

}
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Key guestbookKey = KeyFactory.createKey("NewBlogpost", homework1Name);

    // Run an ancestor query to ensure we see the most up-to-date

    // view of the Greetings belonging to the selected Guestbook.

    /*Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);

    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));*/
    
    ObjectifyService.register(NewBlogpost.class);

    List<NewBlogpost> newBlogposts = ObjectifyService.ofy().load().type(NewBlogpost.class).list();   

    Collections.sort(newBlogposts, Collections.reverseOrder()); 


        %>


        <%

        for (NewBlogpost newBlogpost : newBlogposts) {
        	pageContext.setAttribute("newBlogpost_title", newBlogpost.getTitle());

            pageContext.setAttribute("newBlogpost_content",

                                     newBlogpost.getContent());
                pageContext.setAttribute("newBlogpost_user",

                                         newBlogpost.getUser());
                pageContext.setAttribute("newBlogpost_date", newBlogpost.getDate());

                %>
                <p>${fn:escapeXml(newBlogpost_title)}</p></br>
				<blockquote>${fn:escapeXml(newBlogpost_content)}</blockquote>
                <p><b>User: ${fn:escapeXml(newBlogpost_user.nickname)}</b></p>
				<p>Date posted: ${fn:escapeXml(newBlogpost_date)}</p></br>

            

            <%

        }

    

%>

   
   
   </body>
   
   </html>
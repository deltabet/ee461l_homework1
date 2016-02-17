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
   <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
 </head>
 
   <body>
   <p style = "text-align:center;">
   <img class = "displayed" src="https://upload.wikimedia.org/wikipedia/commons/d/d1/Mount_Everest_as_seen_from_Drukair2_PLW_edit.jpg" style="width:743px;height:404px; "></img>
   </p>
   <p class = "blogTitle">Mountain Blog Archive</p>
   <div id = "archiveWhole">
   <div id = "wrapper">
   	<div id = "leftBuffer"></div>
  <%

    UserService userService = UserServiceFactory.getUserService();

    User user = userService.getCurrentUser();

    if (user != null) {

      pageContext.setAttribute("user", user);

%>

<p>

<div id = "loginLink" class = "sideBar">
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out</a></p>
</div>
<div id = "homeLink" class = "sideBar">
<a href = "/"> Home</a> </br>
</div>
<div id = "newLink" class = "sideBar">
<a href = "/new"> New Post</a> </br>
</div>
<%
}
    else{ %>



<p>
<div id = "loginLink" class = "sideBar">
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
</div>

</p>
</br>
<div id = "homeLink" class = "sideBar">
<a href = "/"> Home</a> </br>
</div>
<%

    }

%>
</div>

</p>
<div id = "rightSection">
	<div id = "blogPostBuffer"></div>
	<div id = "blogPosts">
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
                <div class = "newBlogPost">
                <p class = "blogPostTitle">${fn:escapeXml(newBlogpost_title)}</p>
				<p class = "blogPostContent">${fn:escapeXml(newBlogpost_content)}</p>
                <p class = "blogPostUser"><i>Posted by: ${fn:escapeXml(newBlogpost_user.nickname)}</i></p>
				<p class = "blogPostDate"><i>Date posted: ${fn:escapeXml(newBlogpost_date)}</i></p></br>
				</div>

            

            <%

        }

    

%>
</div>
</div>
</div>
   
   
   </body>
   
   </html>
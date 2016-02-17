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
   <p class = "blogTitle">New Post</p>
  <%

    UserService userService = UserServiceFactory.getUserService();

    User user = userService.getCurrentUser();

    if (user != null) {

      pageContext.setAttribute("user", user);

%>

<p>
<div id = "wrapper">
<div class = "sideBar">
<a href = "/"> Home</a> </br>
</div>
<div class = "sideBar">
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out</a></p>
</div>
<div class = "sideBar">
<a href = "/archive"> Archives</a> </br>
</div>
</div>
</br>
<form action="/newBlog" method="post">
   
      <div><textarea name = "title" rows = "1" cols = "50"></textarea></div>

      <div><textarea name="content" rows="3" cols="60"></textarea></div>

      <div><input type="submit" value="NewBlogpost" /></div>

      <input type="hidden" name="homework1Name" value="${fn:escapeXml(homework1Name)}"/>

    </form>
<%

    }
    else{ %>
    	<a href = "/"> You cannot create a new post; please go Home.</a>
    <% }

%>

   
   
   
   </body>
   
   </html>
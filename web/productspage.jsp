<%@page contentType="text/html" pageEncoding="ISO-8859-1" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TechBoom &raquo; Products</title>
        <link rel="stylesheet" type="text/css" href="styles/slider.css"/>
        <link rel="stylesheet" type="text/css" href="styles/main.css"/>
    </head>
    <body>
        <div id="hd">
            <img src="images/name.png" alt="logo"/>
        </div>
        <div class="nav">
            <ul>
                <li class="home"><a href="home.jsp">Home</a></li>
                <li class="products"><a class="active" href="productspage.jsp">Latest Tech</a></li>
                <li class="about"><a href="about.jsp">About</a></li>
                <li class="references"><a href="references.jsp">References</a></li>
            </ul>
        </div>  
        <%@include file="slider.jspf"%>
        <h1>Welcome to TechBoom!</h1>
        <h2 class="flash">${flash}</h2>
        <p>
            <%@include file="footer.jspf"%> 
    </body>
</html>
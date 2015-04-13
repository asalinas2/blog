<%@page contentType="text/html" pageEncoding="ISO-8859-1" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TechBoom &raquo; Join</title>
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
                <li class="products"><a href="productspage.jsp">Latest Tech</a></li>
                <li class="about"><a href="about.jsp">About</a></li>
                <li class="references"><a href="references.jsp">References</a></li>
            </ul>
        </div>

        <c:choose>
            <c:when test="${sessionScope.user.username != null}">
                <%@include file="slider.jspf"%>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>

        <h1>Join TechBoom!</h1>
        <h2 class="flash">${flash}</h2>
        <p>
        <form method="POST" action="eindex">
            <label>User Name:</label> <input type="text" name="username" required/>
            <label>Password:</label> <input type="password" name="password" required/>
            <label>Re-enter Password:</label> <input type="password" name="password2" required/>
            <input type="hidden" name="action" value="join"/>
            <input type="submit" value="Sign Up"/>
        </form>
        <%@include file="footer.jspf"%> 
    </body>
</html>
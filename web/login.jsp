<%@page contentType="text/html" pageEncoding="ISO-8859-1" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TechBoom &raquo; Login</title>
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
        <%@include file="slider.jspf"%>
        <h1>Login TechBoom!</h1>
        <h2 class="flash">${flash}</h2>
        <p>
        <section>
            <form method="POST" action="eindex">
                <input type="hidden" name="action" value="login"/>
                <table>
                    <tbody>
                        <tr>
                            <td><label class="formElement" for="username">User Name:</label></td>
                            <td><input class="formElement" type="text" name="username"/></td>
                        </tr>
                        <tr>
                            <td><label class="formElement" for="password">Password:</label></td>
                            <td><input class="formElement" type="password" name="password"/></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input class="formElement" type="submit" valu="Log me in"/></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </section>
        <%@include file="footer.jspf"%> 
    </body>
</html>
<%@page contentType="text/html" pageEncoding="ISO-8859-1" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TechBoom &raquo; References</title>
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
                <li class="references"><a class="active" href="references.jsp">References</a></li>
            </ul>
        </div> 
        <%@include file="slider.jspf"%>
        <h1>Welcome to TechBoom!</h1>
        <h2 class="flash">${flash}</h2>
        <p>
        <h2>References</h2>
        <ul>
            <li><a href="http://www.cssdesignawards.com/articles/10-excellent-html5-css3-js-tuts-demos/64/">CSS Design Awards</a></li>
            <li><a href="http://codepen.io/rexkirby/pen/yjxso">CodePen</a></li>
            <li><a href=http://css-snippets.com/simple-horizontal-navigation/">CSS-Snippets</a></li>
        </ul>
        <%@include file="footer.jspf"%> 
    </body>
</html>
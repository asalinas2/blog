<%@page contentType="text/html" pageEncoding="ISO-8859-1" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TechBOOM &raquo; Comment</title>
        <link rel="stylesheet" type="text/css" href="styles/main.css"/>
    </head>
    <body>
        <div id="hd">
            <img src="images/name.png" alt="logo"/>
        </div>
        <div id="bd">
            <h1>Leave a comment, ${sessionScope.user.username}?</h1>
            <h2 class="flash">${flash}</h2>
            <form method="POST" action="index">
                <textarea name="content" rows="3" cols="80" placeholder="Enter Text" required></textarea>
                <input type="hidden" name="action" value="post"/>
                <input type="submit" value="Post"/>
            </form>
            <a class="nav" href="index?action=home">Back home</a>
        </div>
        <%@include file="footer.jspf"%>
    </body>
</html>

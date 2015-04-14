-- blog BootStrap - use jdbc:derby://localhost:1527/blog;username=javauser;password=javauser (jdbc/blog) blogPU
DROP TABLE COMMENTS;
DROP TABLE BLOGGERROLES;
DROP TABLE BLOGGERPASS;
DROP TABLE POSTS;
DROP TABLE PROFILES;
DROP TABLE USERS;
CREATE TABLE USERS (
    username VARCHAR(10) NOT NULL UNIQUE,
    password VARCHAR(10) NOT NULL,
    joindate DATE DEFAULT CURRENT_DATE,
    profileid INT NOT NULL,
    id INT GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1) PRIMARY KEY
);
CREATE TABLE PROFILES (
    biography VARCHAR(1024),
    email VARCHAR(50),
    picture BLOB(200K),
    pictype VARCHAR(30),
    id INT GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1) PRIMARY KEY    
);

CREATE TABLE COMMENTS (
    content VARCHAR(300) NOT NULL,
    authorid INT NOT NULL,
    postid INT NOT NULL,
    commentdate DATE NOT NULL DEFAULT CURRENT_DATE,
    id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY
);

CREATE TABLE POSTS (
    postcontent VARCHAR(10000) NOT NULL,
    blogger VARCHAR(15) NOT NULL,
    postdate DATE NOT NULL DEFAULT CURRENT_DATE,
    picture BLOB(5M),
    pictype VARCHAR(30),
    id INT GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1) PRIMARY KEY
);

CREATE TABLE BLOGGERPASS (
    username VARCHAR(15) NOT NULL PRIMARY KEY,
    password VARCHAR(15) NOT NULL,
    userid INT NOT NULL
);

CREATE TABLE BLOGGERROLES (
    username VARCHAR(15) NOT NULL,
    rolename VARCHAR(15) NOT NULL,
    bloggerprofileid INT NOT NULL,
    PRIMARY KEY (username, rolename)
);

ALTER TABLE BLOGGERPASS ADD FOREIGN KEY (userid) REFERENCES USERS (id) ON DELETE CASCADE;
ALTER TABLE BLOGGERROLES ADD FOREIGN KEY (bloggerprofileid) REFERENCES PROFILES (id) ON DELETE CASCADE;
ALTER TABLE COMMENTS ADD FOREIGN KEY (authorid) REFERENCES USERS (id) ON DELETE CASCADE;
ALTER TABLE COMMENTS ADD FOREIGN KEY (postid) REFERENCES POSTS(id) ON DELETE CASCADE;
ALTER TABLE POSTS ADD FOREIGN KEY (blogger) REFERENCES BLOGGERPASS (username) ON DELETE CASCADE;
ALTER TABLE USERS ADD FOREIGN KEY (profileid) REFERENCES PROFILES (id) ON DELETE CASCADE;

INSERT INTO PROFILES (biography,email) VALUES
    ('','1stemail@hotmail.com'),
    ('','2ndemail@hotmail.com'),
    ('This is my biography. Blah blah blah.','thirdemail@hotmail.com'),
    ('','fourthemail@hotmail.com'),
    ('','fifthemail@hotmail.com');
INSERT INTO USERS (username,password,profileid) VALUES
    ('alpha','password',1),
    ('bravo','password',2),
    ('charlie','password',3),
    ('alfonso','password',4),
    ('hubbub','password',5);
INSERT INTO BLOGGERPASS (username,password,userid) VALUES
    ('alfonso', 'password',4),
    ('hubbub', 'password',5);
INSERT INTO BLOGGERROLES (username,rolename,bloggerprofileid) VALUES
    ('alfonso', 'programmer',4),
    ('hubbub', 'blogger',5);
INSERT INTO POSTS (blogger,postcontent) VALUES
    ('alfonso','This is my first blog. I can write plenty of stuff because I am have greater access.');
INSERT INTO COMMENTS (content,authorid,postid) VALUES 
    ('This is the content of my comment. I am Alpha and I am commenting on your blog. hehehe',1,1),
    ('Bravo says your post sucks!',2,1),
    ('Sorry Charlie is lost.',3,1);
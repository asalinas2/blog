package edu.acc.j2ee.blog.jpa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "home";
        }
        String destination;
        switch (action) {
            default:
            case "home":
                destination = home(request);
                break;
            case "join":
                destination = join(request);
                break;
            case "post":
                destination = post(request);
                break;
            case "comment":
                destination = comment(request);
            case "login":
                destination = login(request);
                break;
            case "logout":
                destination = logout(request);
                break;
            case "profile":
                destination = profile(request);
                break;
            case "pedit":
                destination = editProfile(request);
                break;
            case "upload":
                destination = uploadImage(request);
                break;
            case "image":
                image(request, response);
                return;
            case "pupload":
                destination = pUploadImage(request);
                break;
            case "pimage":
                pImage(request, response);
                return;
            case "wall":
                destination = wall(request);
        }
        request.getRequestDispatcher(destination + ".jsp").forward(request, response);
    }

    private String join(HttpServletRequest request) throws ServletException {
        if (request.getMethod().equals("GET")) {
            return "join";
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        if (username.length() < 4 || username.length() > 10
                || password.length() < 4 || password.length() > 10) {
            request.setAttribute("flash", "Username must be between 4 and 10 characters.");
            return "join";
        }
        if (!password.equals(password2)) {
            request.setAttribute("flash", "Passwords don't match.");
            return "join";
        }
        User user = new User(username, password);
        user.setProfileid(new Profile());
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.merge(user);
            em.getTransaction().commit();
            request.getSession().setAttribute("user", user);
            return home(request);
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
            return "join";
        }
    }

    private String home(HttpServletRequest request) throws ServletException {
        EntityManager em = getEM();
        try {
            List<Post> posts = em.createNamedQuery("Post.findAll").getResultList();
            request.setAttribute("posts", posts);
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
        }
        return "home";
    }

    private String wall(HttpServletRequest request) throws ServletException {
        User user = (User) request.getSession().getAttribute("user");
        EntityManager em = getEM();
        try {
            Query q = em.createQuery("SELECT c FROM Comment c WHERE c.authorid.id = :id ORDER BY c.postdate DESC");
            q.setParameter("id", user.getId());
            List<Post> posts = q.getResultList();
            request.setAttribute("posts", posts);
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
        }
        return "wall";
    }

    private String post(HttpServletRequest request) throws ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("flash", "You are not logged in!");
            return "join";
        }
        if (request.getMethod().equals("GET")) {
            return "post";
        }
        String content = request.getParameter("content");
        if (content.length() < 1 || content.length() > 140) {
            request.setAttribute("flash", "Content must be between 1 and 140 characters.");
            return "post";
        }
        Post post = new Post();
        post.setPostcontent(content);
        post.setBlogger(user);
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.persist(post);
            em.merge(post);
            em.getTransaction().commit();
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
            return "post";
        }
        return home(request);
    }

    private String login(HttpServletRequest request) throws ServletException {
        if (request.getMethod().equals("GET")) {
            return "login";
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        EntityManager em = getEM();
        try {
            User user = (User) em.createNamedQuery("User.findByUsername")
                    .setParameter("username", username)
                    .getSingleResult();
            if (!user.getPassword().equals(password)) {
                throw new Exception("Access Denied");
            }
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("userid", user.getId());
            return home(request);
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
            return "login";
        }
    }

    private String logout(HttpServletRequest request) throws ServletException {
        request.getSession().removeAttribute("user");
        return home(request);
    }

    private String profile(HttpServletRequest request) throws ServletException {
        String username = request.getParameter("username");
        if (username == null) {
            request.setAttribute("flash", "Whose profile are you looking for?");
            return "profile";
        }
        EntityManager em = getEM();
        try {
            User user = (User) em.createNamedQuery("User.findByUsername")
                    .setParameter("username", username)
                    .getSingleResult();
            Profile p = user.getProfileid();
            request.setAttribute("profileuser", user);
            return "profile";
        } catch (Exception sqle) {
            request.setAttribute("flash", sqle.getMessage());
            return "profile";
        }
    }

    private String editProfile(HttpServletRequest request)
            throws ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if (request.getMethod().equals("GET")) {
            request.setAttribute("user", user);
            return "profile_edit";
        }
        String bio = request.getParameter("biography");
        String email = request.getParameter("email");
        Profile p = user.getProfileid();
        p.setBiography(bio);
        p.setEmail(email);
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            return profile(request);
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
            return "profile_edit";
        }
    }

    private String uploadImage(HttpServletRequest request)
            throws ServletException, IOException {
        if (request.getMethod().equals("GET")) {
            return "upload";
        }
        final Part filePart = request.getPart("pic");
        String filename = filePart.getSubmittedFileName();
        String filetype = filePart.getContentType();
        if (!filetype.contains("image")) {
            request.setAttribute("flash", "The uploaded file is not an image!");
            return "upload";
        }
        InputStream imgdata = filePart.getInputStream();
        byte[] pixels = readImage(imgdata);
        User user = (User) request.getSession().getAttribute("user");
        Profile p = user.getProfileid();
        p.setPicture(pixels);
        p.setPictype(filetype);
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
            return "upload";
        }
        return "upload";
    }

    private byte[] readImage(InputStream imgdata) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len; (len = imgdata.read(buffer)) != -1;) {
            os.write(buffer, 0, len);
        }
        os.flush();
        return os.toByteArray();
    }

    private void image(HttpServletRequest request, HttpServletResponse response) {
        String profileFor = request.getParameter("for");
        EntityManager em = getEM();
        try {
            User user = (User) em.createNamedQuery("User.findByUsername")
                    .setParameter("username", profileFor)
                    .getSingleResult();
            Profile profile = user.getProfileid();
            String pictype = profile.getPictype();
            byte[] picdata = (byte[]) profile.getPicture();
            response.setContentType(pictype);
            response.getOutputStream().write(picdata);
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
        }
    }

    private EntityManager getEM() {
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        return emf.createEntityManager();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String pUploadImage(HttpServletRequest request) throws ServletException, IOException {
            if (request.getMethod().equals("GET")) {
                return "upload";
            }
            final Part filePart = request.getPart("pic");
            String filename = filePart.getSubmittedFileName();
            String filetype = filePart.getContentType();
            if (!filetype.contains("image")) {
                request.setAttribute("flash", "The uploaded file is not an image!");
                return "upload";
            }
            InputStream imgdata = filePart.getInputStream();
            byte[] pixels = readImage(imgdata);
            User user = (User) request.getSession().getAttribute("user");
            Profile p = user.getProfileid();
            p.setPicture(pixels);
            p.setPictype(filetype);
            EntityManager em = getEM();
            try {
                em.getTransaction().begin();
                em.merge(p);
                em.getTransaction().commit();
            } catch (Exception e) {
                request.setAttribute("flash", e.getMessage());
                return "upload";
            }
            return "upload";
        }

    

    private void pImage(HttpServletRequest request, HttpServletResponse response) {
        String profileFor = request.getParameter("for");
        EntityManager em = getEM();
        try {
            User user = (User) em.createNamedQuery("User.findByUsername")
                    .setParameter("username", profileFor)
                    .getSingleResult();
            Profile profile = user.getProfileid();
            String pictype = profile.getPictype();
            byte[] picdata = (byte[]) profile.getPicture();
            response.setContentType(pictype);
            response.getOutputStream().write(picdata);
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
        }
    }

    private String comment(HttpServletRequest request) throws ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("flash", "You are not logged in!");
            return "comment";
        }
        if (request.getMethod().equals("GET")) {
            return "post";
        }
        String content = request.getParameter("content");
        if (content.length() < 1 || content.length() > 140) {
            request.setAttribute("flash", "Content must be between 1 and 140 characters.");
            return "comment";
        }
        Post post = new Post();
        post.setPostcontent(content);
        post.setBlogger(user);
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.persist(post);
            em.merge(post);
            em.getTransaction().commit();
        } catch (Exception e) {
            request.setAttribute("flash", e.getMessage());
            return "comment";
        }
        return home(request);
    }

}

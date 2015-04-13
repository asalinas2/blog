package edu.acc.j2ee.blog.jpa;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "BLOGGERROLES")
@NamedQueries({
    @NamedQuery(name = "Bloggerrole.findAll", query = "SELECT b FROM Bloggerrole b"),
    @NamedQuery(name = "Bloggerrole.findByUsername", query = "SELECT b FROM Bloggerrole b WHERE b.bloggerrolePK.username = :username"),
    @NamedQuery(name = "Bloggerrole.findByRolename", query = "SELECT b FROM Bloggerrole b WHERE b.bloggerrolePK.rolename = :rolename")})
public class Bloggerrole implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BloggerrolePK bloggerrolePK;
    @JoinColumn(name = "BLOGGERPROFILEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Profile bloggerprofileid;

    public Bloggerrole() {
    }

    public Bloggerrole(BloggerrolePK bloggerrolePK) {
        this.bloggerrolePK = bloggerrolePK;
    }

    public Bloggerrole(String username, String rolename) {
        this.bloggerrolePK = new BloggerrolePK(username, rolename);
    }

    public BloggerrolePK getBloggerrolePK() {
        return bloggerrolePK;
    }

    public void setBloggerrolePK(BloggerrolePK bloggerrolePK) {
        this.bloggerrolePK = bloggerrolePK;
    }

    public Profile getBloggerprofileid() {
        return bloggerprofileid;
    }

    public void setBloggerprofileid(Profile bloggerprofileid) {
        this.bloggerprofileid = bloggerprofileid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bloggerrolePK != null ? bloggerrolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloggerrole)) {
            return false;
        }
        Bloggerrole other = (Bloggerrole) object;
        if ((this.bloggerrolePK == null && other.bloggerrolePK != null) || (this.bloggerrolePK != null && !this.bloggerrolePK.equals(other.bloggerrolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.acc.j2ee.blog.jpa.Bloggerrole[ bloggerrolePK=" + bloggerrolePK + " ]";
    }
    
}

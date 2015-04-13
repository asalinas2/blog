package edu.acc.j2ee.blog.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "BLOGGERPASS")
@NamedQueries({
    @NamedQuery(name = "Bloggerpass.findAll", query = "SELECT b FROM Bloggerpass b"),
    @NamedQuery(name = "Bloggerpass.findByUsername", query = "SELECT b FROM Bloggerpass b WHERE b.username = :username"),
    @NamedQuery(name = "Bloggerpass.findByPassword", query = "SELECT b FROM Bloggerpass b WHERE b.password = :password")})
public class Bloggerpass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 4, max = 15)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 4, max = 15)
    @Column(name = "PASSWORD")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blogger")
    private List<Post> postList;

    public Bloggerpass() {
    }

    public Bloggerpass(String username) {
        this.username = username;
    }

    public Bloggerpass(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloggerpass)) {
            return false;
        }
        Bloggerpass other = (Bloggerpass) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", username, password);
    }
    
}

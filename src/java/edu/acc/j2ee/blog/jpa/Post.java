package edu.acc.j2ee.blog.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "POSTS")
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p"),
    @NamedQuery(name = "Post.findByPostcontent", query = "SELECT p FROM Post p WHERE p.postcontent = :postcontent"),
    @NamedQuery(name = "Post.findByPostdate", query = "SELECT p FROM Post p WHERE p.postdate = :postdate"),
    @NamedQuery(name = "Post.findByPictype", query = "SELECT p FROM Post p WHERE p.pictype = :pictype"),
    @NamedQuery(name = "Post.findById", query = "SELECT p FROM Post p WHERE p.id = :id")})
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10000)
    @Column(name = "POSTCONTENT")
    private String postcontent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSTDATE")
    @Temporal(TemporalType.DATE)
    private Date postdate = CurrentDate.now();
    @Lob
    @Column(name = "PICTURE")
    private Serializable picture;
    @Size(max = 30)
    @Column(name = "PICTYPE")
    private String pictype;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postid")
    private List<Comment> commentList;
    @JoinColumn(name = "BLOGGER", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false)
    private Bloggerpass blogger;

    public Post() {
    }

    public Post(Integer id) {
        this.id = id;
    }

    public Post(Integer id, String postcontent, Date postdate) {
        this.id = id;
        this.postcontent = postcontent;
        this.postdate = postdate;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public Date getPostdate() {
        return postdate;
    }

    public void setPostdate(Date postdate) {
        this.postdate = postdate;
    }

    public Serializable getPicture() {
        return picture;
    }

    public void setPicture(Serializable picture) {
        this.picture = picture;
    }

    public String getPictype() {
        return pictype;
    }

    public void setPictype(String pictype) {
        this.pictype = pictype;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Bloggerpass getBlogger() {
        return blogger;
    }

    public void setBlogger(Bloggerpass blogger) {
        this.blogger = blogger;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s (posted %s)", postcontent, postdate);
    }
    
}

package net.davidportos.backendJavaWSService.dto;

import java.io.Serializable;
import java.util.Date;

public class SnippetPostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private long id;

    private String postId;

    private String title;

    private String content;

    private Date expirationDate;

    private Date createdDate;

    private UserDTO user;

    private ExposureDTO exposure;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public UserDTO getUser() {
        return this.user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ExposureDTO getExposure() {
        return this.exposure;
    }

    public void setExposure(ExposureDTO exposure) {
        this.exposure = exposure;
    }


    
}

package net.davidportos.backendJavaWSService.models.responses;

import java.util.Date;

public class SnippetPostRestResponse {

    private String postId;

    private String title;

    private String content;

    private Date expirationDate;

    private Date createdDate;

    private boolean expiredPost;

    private UserRestResponse user;

    private ExposureRestResponse exposureRestResponse;


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

    

    public boolean getExpiredPost() {
        this.expiredPost = getExpirationDate().compareTo(new Date(System.currentTimeMillis())) < 0;
        return this.expiredPost;
    }

    public void setExpiredPost(boolean expiredPost) {
        this.expiredPost = expiredPost;
    }

    public UserRestResponse getUser() {
        return this.user;
    }

    public void setUser(UserRestResponse user) {
        this.user = user;
    }

    public ExposureRestResponse getExposureRestResponse() {
        return this.exposureRestResponse;
    }

    public void setExposureRestResponse(ExposureRestResponse exposureRestResponse) {
        this.exposureRestResponse = exposureRestResponse;
    }

    
}

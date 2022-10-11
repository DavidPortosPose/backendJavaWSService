package net.davidportos.backendJavaWSService.models.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class PostCreateRequestModel {
    
    @NotEmpty(message = "The title is obligatory")
    private String title;
    
    @NotEmpty(message = "The content is obligatory")
    private String content;
    
    @NotNull(message = "The exposure of post is obligatory")
    @Range(min = 1, max = 2, message = "The exposure of post is invalid")
    private long exposureId;
    
    @NotNull(message = "The expiration time of post is obligatory")
    @Range(min = 0, max = 1440, message = "The expiration time of post is invalid")
    private int expirationTime;


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

    public long getExposureId() {
        return this.exposureId;
    }

    public void setExposureId(long exposureId) {
        this.exposureId = exposureId;
    }

    public int getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

}

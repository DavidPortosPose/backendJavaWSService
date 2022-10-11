package net.davidportos.backendJavaWSService.dto;

import java.io.Serializable;
import java.util.List;

public class ExposureDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private long id;

    private String type;

    private List<SnippetPostDTO> posts;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SnippetPostDTO> getPosts() {
        return this.posts;
    }

    public void setPosts(List<SnippetPostDTO> posts) {
        this.posts = posts;
    }

}

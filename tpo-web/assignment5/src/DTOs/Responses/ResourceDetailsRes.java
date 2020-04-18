package DTOs.Responses;

public class ResourceDetailsRes {

    private String resourceName;

    private String content;

    public ResourceDetailsRes(String resourceName, String content) {
        this.resourceName = resourceName;
        this.content = content;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

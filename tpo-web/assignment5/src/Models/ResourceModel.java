package Models;

import java.util.Objects;

public class ResourceModel {

    private int idResource;
    private String resourceName;
    private String content;

    public ResourceModel() {
    }

    public ResourceModel(int idResource, String resourceName) {
        this.idResource = idResource;
        this.resourceName = resourceName;
    }

    public ResourceModel(int idResource, String resourceName, String content) {
        this.idResource = idResource;
        this.resourceName = resourceName;
        this.content = content;
    }

    public int getIdResource() {
        return idResource;
    }

    public void setIdResource(int idResource) {
        this.idResource = idResource;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceModel)) return false;
        ResourceModel that = (ResourceModel) o;
        return getIdResource() == that.getIdResource();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdResource());
    }

}

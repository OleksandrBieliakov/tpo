package DTOs.Responses;

import Models.ResourceModel;

import java.util.HashSet;
import java.util.Set;

public class ListResourcesRes {

    private Set<ResourceModel> resources = new HashSet<>();

    public Set<ResourceModel> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceModel> resources) {
        this.resources = resources;
    }

    public void addResource(ResourceModel resource) {
        resources.add(resource);
    }

    public boolean isEmpty() {
        return resources.isEmpty();
    }

}

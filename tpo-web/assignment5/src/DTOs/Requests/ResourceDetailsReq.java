package DTOs.Requests;

public class ResourceDetailsReq {

    private int idResource;

    public ResourceDetailsReq(int idResource) {
        this.idResource = idResource;
    }

    public int getIdResource() {
        return idResource;
    }

    public void setIdResource(int idResource) {
        this.idResource = idResource;
    }

}

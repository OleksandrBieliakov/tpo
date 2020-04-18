package DTOs.Requests;

public class ResourceDetailsReq {

    private int idResource;

    private int idUser;

    public ResourceDetailsReq(int idResource, int idUser) {
        this.idResource = idResource;
        this.idUser = idUser;
    }

    public int getIdResource() {
        return idResource;
    }

    public void setIdResource(int idResource) {
        this.idResource = idResource;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

}

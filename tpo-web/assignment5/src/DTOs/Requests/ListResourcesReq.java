package DTOs.Requests;

public class ListResourcesReq {

    private int idUser;

    public ListResourcesReq(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

}

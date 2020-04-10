package DTOs.Responses;

public class LoginRes {

    private int idUser;

    public LoginRes(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

}

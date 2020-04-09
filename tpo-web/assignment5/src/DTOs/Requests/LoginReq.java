package DTOs.Requests;

public class LoginReq {

    private String _userName;
    private String _userPassword;

    public LoginReq(String _userName, String _userPassword) {
        this._userName = _userName;
        this._userPassword = _userPassword;
    }

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public String get_userPassword() {
        return _userPassword;
    }

    public void set_userPassword(String _userPassword) {
        this._userPassword = _userPassword;
    }

}

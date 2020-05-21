package assignment8;

import java.io.Serializable;

public class EchoRequest implements Serializable {

    private String message;

    public EchoRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

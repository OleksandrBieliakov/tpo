package assignment8;

import java.io.Serializable;

public class EchoResponse implements Serializable {

    private String message;

    public EchoResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

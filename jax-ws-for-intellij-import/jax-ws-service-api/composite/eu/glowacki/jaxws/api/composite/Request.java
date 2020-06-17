package eu.glowacki.jaxws.api.composite;

import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType( //
        name = "RequestMessage", // name of the XmlType should be different from the name of the class
        namespace = "http://glowacki.eu/composite" //
)
public final class Request {

    public String lastName;
    public Date birthDate;

    /**
     * empty parameterless constructor is required for unmarshalling
     */
    public Request() {
    }

    public Request(String lastName, Date birthDate) {
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Request(String lastName) {
        this.lastName = lastName;
    }

    public Request(Date birthDate) {
        this.birthDate = birthDate;
    }

}
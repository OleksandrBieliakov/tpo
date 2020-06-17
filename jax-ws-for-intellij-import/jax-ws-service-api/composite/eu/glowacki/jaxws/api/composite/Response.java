package eu.glowacki.jaxws.api.composite;

import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

@XmlType( //
        name = "ResponseMessage", // name of the XmlType should be different from the name of the class
        namespace = "http://glowacki.eu/composite" //
)
public final class Response {

    public Collection<Person> result;

    /**
     * empty parameterless constructor is required for unmarshalling
     */
    public Response() {
    }

    public Response(Collection<Person> result) {
        this.result = result;
    }
}